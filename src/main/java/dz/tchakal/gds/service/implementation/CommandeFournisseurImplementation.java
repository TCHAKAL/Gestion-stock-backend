package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.CommandeFournisseurDto;
import dz.tchakal.gds.dto.LigneCommandeFournisseurDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.Fournisseur;
import dz.tchakal.gds.model.CommandeFournisseur;
import dz.tchakal.gds.model.LigneCommandeFournisseur;
import dz.tchakal.gds.repository.ArticleRepository;
import dz.tchakal.gds.repository.FournisseurRepository;
import dz.tchakal.gds.repository.CommandeFournisseurRepository;
import dz.tchakal.gds.repository.LigneCommandeFournisseurRepository;
import dz.tchakal.gds.service.CommandeFournisseurService;
import dz.tchakal.gds.util.StaticUtil;
import dz.tchakal.gds.validator.CommandeFournisseurValidator;
import dz.tchakal.gds.validator.LigneCommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("CommandeFournisseurImplementation")
@Slf4j
public class CommandeFournisseurImplementation implements CommandeFournisseurService {

    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommandeFournisseurImplementation(CommandeFournisseurRepository commandeFournisseurRepository, LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, FournisseurRepository fournisseurRepository, ArticleRepository articleRepository) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto) {
        //Valider la commande fournisseur
        List<String> errors = CommandeFournisseurValidator.validate(commandeFournisseurDto);
        if (!errors.isEmpty()) {
            log.error("Commande fournisseur n'est pas valide");
            throw new InvalidEntityException("Commande fournisseur n'est pas valide", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE, errors);
        }

        //Vérifier si le fournisseur est présent dans la BDD
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getFournisseur().getId());
        if (!fournisseur.isPresent()) {
            log.error("Le fournisseur avec ID {} n'existe pas dans la BDD");
            throw new EntityNotFoundException("Le fournisseur n'est pas présent dans la BDD", ErrorCode.CLIENT_NOT_FOUND);
        }

        //Vérifier les ligne de la commande fournisseur
        if (commandeFournisseurDto.getLigneCommandeFournisseurs() != null) {
            List<String> articleErrors = new ArrayList<>();
            for (LigneCommandeFournisseurDto ligneCommandeFournisseur : commandeFournisseurDto.getLigneCommandeFournisseurs()) {
                if (ligneCommandeFournisseur.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligneCommandeFournisseur.getArticle().getId());
                    if (!article.isPresent()) {
                        articleErrors.add("L'article avec l'id " + ligneCommandeFournisseur.getArticle().getId() + " n'est pas présent dans la BDD");
                    } else {
                        articleErrors.addAll(LigneCommandeFournisseurValidator.validate(ligneCommandeFournisseur));
                    }
                } else {
                    articleErrors.add("Impossible d'enregistrer une commande fournisseur avec un article null");
                }

            }
            if (!articleErrors.isEmpty()) {
                log.error("Les lignes commande fournisseur ne sont pas valides");
                throw new InvalidEntityException("La ligne commande fournisseur n'est pas valide", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE, articleErrors);
            }
        }

        //Save pour chaque ligne commande fournisseur
        CommandeFournisseur savedCommandeFournisseur = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto));
        if (commandeFournisseurDto.getLigneCommandeFournisseurs() != null) {
            commandeFournisseurDto.getLigneCommandeFournisseurs().forEach(ligneCommandeFournisseurDto -> {
                LigneCommandeFournisseur newLigneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligneCommandeFournisseurDto);
                newLigneCommandeFournisseur.setCommandeFournisseur(savedCommandeFournisseur);
                ligneCommandeFournisseurRepository.save(newLigneCommandeFournisseur);
            });
        }
        return CommandeFournisseurDto.fromEntity(savedCommandeFournisseur);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if(id==null){
            log.error("Commande fournisseur id est null");
            return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(()->new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE,ErrorCode.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if(code==null){
            log.error("Article code est null");
            return null;
        }
        return commandeFournisseurRepository.findByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE,ErrorCode.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll()
                .stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            throw new InvalidEntityException("La commande fournisseur avec l'id "+id+" n'est présent dans la BDD",ErrorCode.COMMANDE_CLIENT_NOT_FOUND);
        }else{
            commandeFournisseurRepository.deleteById(id);
        }
    }
}
