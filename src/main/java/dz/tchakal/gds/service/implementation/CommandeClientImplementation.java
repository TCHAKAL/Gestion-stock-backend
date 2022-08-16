package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.ArticleDto;
import dz.tchakal.gds.dto.CommandeClientDto;
import dz.tchakal.gds.dto.LigneCommandeClientDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.Client;
import dz.tchakal.gds.model.CommandeClient;
import dz.tchakal.gds.model.LigneCommandeClient;
import dz.tchakal.gds.repository.ArticleRepository;
import dz.tchakal.gds.repository.ClientRepository;
import dz.tchakal.gds.repository.CommandeClientRepository;
import dz.tchakal.gds.repository.LigneCommandeClientRepository;
import dz.tchakal.gds.service.CommandeClientService;
import dz.tchakal.gds.util.StaticUtil;
import dz.tchakal.gds.validator.CommandeClientValidator;
import dz.tchakal.gds.validator.LigneCommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("CommandeClientImplementation")
@Slf4j
public class CommandeClientImplementation implements CommandeClientService {

    private final CommandeClientRepository commandeClientRepository;
    private final LigneCommandeClientRepository ligneCommandeClientRepository;
    private final ClientRepository clientRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommandeClientImplementation(CommandeClientRepository commandeClientRepository, LigneCommandeClientRepository ligneCommandeClientRepository, ClientRepository clientRepository, ArticleRepository articleRepository) {
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto commandeClientDto) {
        //Valider la commande client
        List<String> errors = CommandeClientValidator.validate(commandeClientDto);
        if (!errors.isEmpty()) {
            log.error("Commande client n'est pas valide");
            throw new InvalidEntityException("Commande client n'est pas valide", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE, errors);
        }

        //Vérifier si le client est présent dans la BDD
        Optional<Client> client = clientRepository.findById(commandeClientDto.getClient().getId());
        if (!client.isPresent()) {
            log.error("Le client avec ID {} n'existe pas dans la BDD");
            throw new EntityNotFoundException("Le client n'est pas présent dans la BDD", ErrorCode.CLIENT_NOT_FOUND);
        }

        //Vérifier les ligne de la commande client
        if (commandeClientDto.getLigneCommandeClients() != null) {
            List<String> articleErrors = new ArrayList<>();
            for (LigneCommandeClientDto ligneCommandeClient : commandeClientDto.getLigneCommandeClients()) {
                if (ligneCommandeClient.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligneCommandeClient.getArticle().getId());
                    if (!article.isPresent()) {
                        articleErrors.add("L'article avec l'id " + ligneCommandeClient.getArticle().getId() + " n'est pas présent dans la BDD");
                    } else {
                        articleErrors.addAll(LigneCommandeClientValidator.validate(ligneCommandeClient));
                    }
                } else {
                    articleErrors.add("Impossible d'enregistrer une commande client avec un article null");
                }

            }
            if (!articleErrors.isEmpty()) {
                log.error("Les lignes commande client ne sont pas valides");
                throw new InvalidEntityException("La ligne commande client n'est pas valide", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE, articleErrors);
            }
        }

        //Save pour chaque ligne commande client
        CommandeClient savedCommandeClient = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto));
        if (commandeClientDto.getLigneCommandeClients() != null) {
            commandeClientDto.getLigneCommandeClients().forEach(ligneCommandeClientDto -> {
                LigneCommandeClient newLigneCommandeClient = LigneCommandeClientDto.toEntity(ligneCommandeClientDto);
                newLigneCommandeClient.setCommandeClient(savedCommandeClient);
                ligneCommandeClientRepository.save(newLigneCommandeClient);
            });
        }
        return CommandeClientDto.fromEntity(savedCommandeClient);
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if(id==null){
            log.error("Commande client id est null");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE,ErrorCode.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if(code==null){
            log.error("Article code est null");
            return null;
        }
        return commandeClientRepository.findByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE,ErrorCode.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll()
                .stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            throw new InvalidEntityException("La commande client avec l'id "+id+" n'est présent dans la BDD",ErrorCode.COMMANDE_CLIENT_NOT_FOUND);
        }else{
            commandeClientRepository.deleteById(id);
        }
    }
}
