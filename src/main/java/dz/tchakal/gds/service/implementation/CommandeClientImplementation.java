package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.*;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.exception.InvalidOperationException;
import dz.tchakal.gds.model.*;
import dz.tchakal.gds.model.enumeration.EtatCommande;
import dz.tchakal.gds.model.enumeration.SourceMvtStock;
import dz.tchakal.gds.model.enumeration.TypeMvt;
import dz.tchakal.gds.repository.ArticleRepository;
import dz.tchakal.gds.repository.ClientRepository;
import dz.tchakal.gds.repository.CommandeClientRepository;
import dz.tchakal.gds.repository.LigneCommandeClientRepository;
import dz.tchakal.gds.service.CommandeClientService;
import dz.tchakal.gds.service.MvtStockService;
import dz.tchakal.gds.util.StaticUtil;
import dz.tchakal.gds.validator.ArticleValidator;
import dz.tchakal.gds.validator.CommandeClientValidator;
import dz.tchakal.gds.validator.LigneCommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
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
    private final MvtStockService mvtStockService;

    @Autowired
    public CommandeClientImplementation(CommandeClientRepository commandeClientRepository, LigneCommandeClientRepository ligneCommandeClientRepository, ClientRepository clientRepository, ArticleRepository articleRepository, MvtStockService mvtStockService) {
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.mvtStockService = mvtStockService;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto commandeClientDto) {
        //Valider la commande client
        List<String> errors = CommandeClientValidator.validate(commandeClientDto);
        if (!errors.isEmpty()) {
            log.error("Commande client n'est pas valide");
            throw new InvalidEntityException("Commande client n'est pas valide", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE, errors);
        }
        if (commandeClientDto.getId() != null && commandeClientDto.isCommandeLivree()) {
            log.error("Commande client n'est pas modifiable");
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
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
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkQuantiteLigneCommande(idCommande == null, "Commande client id est null", "Impossible de modifier l'etat de la commande avec un id null !", !StringUtils.hasLength(String.valueOf(etatCommande)), "Commande client etatCommande est null", "Impossible de modifier l'etat de la commande avec un etatCommande null !");
        CommandeClientDto commandeClientDto = findById(idCommande);
        if (commandeClientDto.isCommandeLivree()) {
            log.error("Commande client n'est pas modifiable");
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        commandeClientDto.setEtatCommande(etatCommande);
        CommandeClient savedCommandeClient = commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto));
        if (commandeClientDto.isCommandeLivree()){
            updateMvtStock(idCommande);
        }
        return CommandeClientDto.fromEntity(savedCommandeClient);
    }

    @Override
    public CommandeClientDto updateQuantite(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        if (idLigneCommande == null) {
            log.error("Ligne Commande client id est null");
            throw new InvalidOperationException("Impossible de modifier la quantite ligne avec un id null !", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        checkQuantiteLigneCommande(quantite == null, "Ligne Commande client quantite est null", "Impossible de modifier la ligne, la quantité est null !", BigDecimal.ZERO.compareTo(quantite) >= 0, "La quantité est négative", "Impossible de modifier la ligne, la quantité est inférieure à 0");
        CommandeClientDto commandeClientDto = findById(idCommande);
        if (commandeClientDto.isCommandeLivree()) {
            log.error("Commande client n'est pas modifiable");
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        Optional<LigneCommandeClient> ligneCommandeClient = ligneCommandeClientRepository.findById(idLigneCommande);
        if (ligneCommandeClient.isEmpty()) {
            log.error("Ligne commande client n'est pas trouvée");
            throw new EntityNotFoundException("Ligne commande client n'est pas trouvée", ErrorCode.LIGNE_COMMANDE_CLIENT_NOT_FOUND);
        }
        ligneCommandeClient.get().setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient.get());
        return commandeClientDto;
    }


    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
        checkIdCommande(idCommande);
        if (idClient == null) {
            log.error("client id est null");
            throw new InvalidOperationException("Impossible de modifier le client avec un id null !", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        CommandeClientDto commandeClientDto = findById(idCommande);
        if (commandeClientDto.isCommandeLivree()) {
            log.error("Commande client n'est pas modifiable");
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        Optional<Client> client = clientRepository.findById(idClient);
        if (client.isEmpty()) {
            log.error("le client n'est pas trouvé");
            throw new EntityNotFoundException("Le client n'est pas trouvé", ErrorCode.CLIENT_NOT_FOUND);
        }
        commandeClientDto.setClient(ClientDto.fromEntity(client.get()));
        return CommandeClientDto.fromEntity(commandeClientRepository.save(CommandeClientDto.toEntity(commandeClientDto)));
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkLigneCommande(idLigneCommande);
        checkArticle(idArticle);
        CommandeClientDto commandeClientDto = findById(idCommande);
        checkIsCommandeLivree(commandeClientDto);
        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClientById(idLigneCommande);
        Optional<Article> article = articleRepository.findById(idArticle);
        if (ligneCommandeClient.isEmpty()) {
            log.error("Impossible de modifier l'article le nouveau article est introuvable");
            throw new EntityNotFoundException("Impossible de modifier l'article le nouveau article est introuvable", ErrorCode.ARTICLE_NOT_FOUND);
        }
        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(article.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalide", ErrorCode.ARTICLE_NOT_FOUND, errors);
        }

        LigneCommandeClient ligneCommandeClientToSave = ligneCommandeClient.get();
        ligneCommandeClientToSave.setArticle(article.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSave);

        return commandeClientDto;
    }

    private void checkIsCommandeLivree(CommandeClientDto commandeClientDto) {
        if (commandeClientDto.isCommandeLivree()) {
            log.error("Commande client n'est pas modifiable");
            throw new EntityNotFoundException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_FOUND);
        }
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        CommandeClientDto commandeClientDto = findById(idCommande);
        checkIsCommandeLivree(commandeClientDto);
        checkLigneCommande(idLigneCommande);
        ligneCommandeClientRepository.deleteById(idLigneCommande);
        return commandeClientDto;
    }

    private void checkArticle(Integer newIdArticle) {
        if (newIdArticle == null) {
            log.error("newIdArticle id est null");
            throw new InvalidOperationException("Impossible de modifier l'article l'id du nouvel article est null !", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
    }

    private Optional<LigneCommandeClient> findLigneCommandeClientById(Integer idLigneCommande) {
        Optional<LigneCommandeClient> ligneCommandeClient = ligneCommandeClientRepository.findById(idLigneCommande);
        if (ligneCommandeClient.isEmpty()) {
            log.error("Impossible de modifier l'article");
            throw new EntityNotFoundException("Impossible de modifier l'article, la ligne est introuvable", ErrorCode.LIGNE_COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeClient;
    }


    @Override
    public CommandeClientDto findById(Integer id) {
        if (id == null) {
            log.error("Commande client id est null");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if (code == null) {
            log.error("Article code est null");
            return null;
        }
        return commandeClientRepository.findByCode(code)
                .map(CommandeClientDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientRepository.findAll()
                .stream()
                .map(CommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findAllLigneCommandeClientByCommandeClientId(Integer idCommande) {
        return ligneCommandeClientRepository.findAllByCommandeClientId(idCommande).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {

        checkIdCommande(id);
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(id);
        if (!ligneCommandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer la commande, elle est utilisé dans des lignes commandes", ErrorCode.COMMANDE_CLIENT_ALREADY_IN_USE);
        }
        Optional<CommandeClient> commandeClient = commandeClientRepository.findById(id);
        checkIsCommandeLivree(CommandeClientDto.fromEntity(commandeClient.get()));
        commandeClientRepository.deleteById(id);

    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande client id est null");
            throw new InvalidOperationException("l'id Commande client est null", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
    }

    private void checkQuantiteLigneCommande(boolean quantite, String s, String message, boolean ZERO, String La_quantité_est_négative, String message1) {
        if (quantite) {
            log.error(s);
            throw new InvalidOperationException(message, ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        if (ZERO) {
            log.error(La_quantité_est_négative);
            throw new InvalidOperationException(message1, ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
    }

    private void checkLigneCommande(Integer idLigneCommande) {
        if (idLigneCommande == null) {
            log.error("Ligne Commande client id est null");
            throw new InvalidOperationException("Impossible de modifier l'article, l'id de la ligne est  null !", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
    }

    private void updateMvtStock(Integer idCommande) {
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByCommandeClientId(idCommande);
        ligneCommandeClients.forEach(ligneCommandeClient -> {
            MvtStockDto sortieStock = MvtStockDto.builder()
                    .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvt.SORTIE)
                    .sourceMvtStock(SourceMvtStock.COMMANDE_CLIENT)
                    .quantite(ligneCommandeClient.getQuantite())
                    .idEntreprise(ligneCommandeClient.getIdEntreprise())
                    .build();
            mvtStockService.sortieStock(sortieStock);
        });
    }
}
