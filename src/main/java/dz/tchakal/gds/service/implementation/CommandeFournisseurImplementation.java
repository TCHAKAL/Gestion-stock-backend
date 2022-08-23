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
import dz.tchakal.gds.repository.FournisseurRepository;
import dz.tchakal.gds.repository.CommandeFournisseurRepository;
import dz.tchakal.gds.repository.LigneCommandeFournisseurRepository;
import dz.tchakal.gds.service.CommandeFournisseurService;
import dz.tchakal.gds.service.MvtStockService;
import dz.tchakal.gds.util.StaticUtil;
import dz.tchakal.gds.validator.ArticleValidator;
import dz.tchakal.gds.validator.CommandeFournisseurValidator;
import dz.tchakal.gds.validator.LigneCommandeFournisseurValidator;
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

@Service("CommandeFournisseurImplementation")
@Slf4j
public class CommandeFournisseurImplementation implements CommandeFournisseurService {

    private final CommandeFournisseurRepository commandeFournisseurRepository;
    private final LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ArticleRepository articleRepository;
    private final MvtStockService mvtStockService;


    @Autowired
    public CommandeFournisseurImplementation(CommandeFournisseurRepository commandeFournisseurRepository, LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository, FournisseurRepository fournisseurRepository, ArticleRepository articleRepository, MvtStockService mvtStockService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.mvtStockService = mvtStockService;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto) {
        //Valider la commande fournisseur
        List<String> errors = CommandeFournisseurValidator.validate(commandeFournisseurDto);
        if (!errors.isEmpty()) {
            log.error("Commande fournisseur n'est pas valide");
            throw new InvalidEntityException("Commande fournisseur n'est pas valide", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE, errors);
        }
        if (commandeFournisseurDto.getId() != null && commandeFournisseurDto.isCommandeLivree()) {
            log.error("Commande fournisseur n'est pas modifiable");
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
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
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkQuantiteLigneCommande(idCommande == null, "Commande fournisseur id est null", "Impossible de modifier l'etat de la commande avec un id null !", !StringUtils.hasLength(String.valueOf(etatCommande)), "Commande fournisseur etatCommande est null", "Impossible de modifier l'etat de la commande avec un etatCommande null !");
        CommandeFournisseurDto commandeFournisseurDto = findById(idCommande);
        if (commandeFournisseurDto.isCommandeLivree()) {
            log.error("Commande fournisseur n'est pas modifiable");
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        commandeFournisseurDto.setEtatCommande(etatCommande);
        CommandeFournisseur savedCommandeFournisseur = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto));
        if (commandeFournisseurDto.isCommandeLivree()) {
            updateMvtStock(idCommande);
        }
        return CommandeFournisseurDto.fromEntity(savedCommandeFournisseur);
    }

    @Override
    public CommandeFournisseurDto updateQuantite(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
        checkIdCommande(idCommande);
        if (idLigneCommande == null) {
            log.error("Ligne Commande fournisseur id est null");
            throw new InvalidOperationException("Impossible de modifier la quantite ligne avec un id null !", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        checkQuantiteLigneCommande(quantite == null, "Ligne Commande fournisseur quantite est null", "Impossible de modifier la ligne, la quantité est null !", BigDecimal.ZERO.compareTo(quantite) >= 0, "La quantité est négative", "Impossible de modifier la ligne, la quantité est inférieure à 0");
        CommandeFournisseurDto commandeFournisseurDto = findById(idCommande);
        if (commandeFournisseurDto.isCommandeLivree()) {
            log.error("Commande fournisseur n'est pas modifiable");
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseur.isEmpty()) {
            log.error("Ligne commande fournisseur n'est pas trouvée");
            throw new EntityNotFoundException("Ligne commande fournisseur n'est pas trouvée", ErrorCode.LIGNE_COMMANDE_CLIENT_NOT_FOUND);
        }
        ligneCommandeFournisseur.get().setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur.get());
        return commandeFournisseurDto;
    }


    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {
        checkIdCommande(idCommande);
        if (idFournisseur == null) {
            log.error("fournisseur id est null");
            throw new InvalidOperationException("Impossible de modifier le fournisseur avec un id null !", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        CommandeFournisseurDto commandeFournisseurDto = findById(idCommande);
        if (commandeFournisseurDto.isCommandeLivree()) {
            log.error("Commande fournisseur n'est pas modifiable");
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(idFournisseur);
        if (fournisseur.isEmpty()) {
            log.error("le fournisseur n'est pas trouvé");
            throw new EntityNotFoundException("Le fournisseur n'est pas trouvé", ErrorCode.CLIENT_NOT_FOUND);
        }
        commandeFournisseurDto.setFournisseur(FournisseurDto.fromEntity(fournisseur.get()));
        return CommandeFournisseurDto.fromEntity(commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseurDto)));
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkLigneCommande(idLigneCommande);
        checkArticle(idArticle);
        CommandeFournisseurDto commandeFournisseurDto = findById(idCommande);
        checkIsCommandeLivree(commandeFournisseurDto);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseurById(idLigneCommande);
        Optional<Article> article = articleRepository.findById(idArticle);
        if (ligneCommandeFournisseur.isEmpty()) {
            log.error("Impossible de modifier l'article le nouveau article est introuvable");
            throw new EntityNotFoundException("Impossible de modifier l'article le nouveau article est introuvable", ErrorCode.ARTICLE_NOT_FOUND);
        }
        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(article.get()));
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Article invalide", ErrorCode.ARTICLE_NOT_FOUND, errors);
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSave = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSave.setArticle(article.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSave);

        return commandeFournisseurDto;
    }

    private void checkIsCommandeLivree(CommandeFournisseurDto commandeFournisseurDto) {
        if (commandeFournisseurDto.isCommandeLivree()) {
            log.error("Commande fournisseur n'est pas modifiable");
            throw new EntityNotFoundException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCode.COMMANDE_CLIENT_NOT_FOUND);
        }
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        CommandeFournisseurDto commandeFournisseurDto = findById(idCommande);
        checkIsCommandeLivree(commandeFournisseurDto);
        checkLigneCommande(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);
        return commandeFournisseurDto;
    }

    private void checkArticle(Integer newIdArticle) {
        if (newIdArticle == null) {
            log.error("newIdArticle id est null");
            throw new InvalidOperationException("Impossible de modifier l'article l'id du nouvel article est null !", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseurById(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findById(idLigneCommande);
        if (ligneCommandeFournisseur.isEmpty()) {
            log.error("Impossible de modifier l'article");
            throw new EntityNotFoundException("Impossible de modifier l'article, la ligne est introuvable", ErrorCode.LIGNE_COMMANDE_CLIENT_NOT_FOUND);
        }
        return ligneCommandeFournisseur;
    }


    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("Commande fournisseur id est null");
            return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if (code == null) {
            log.error("Article code est null");
            return null;
        }
        return commandeFournisseurRepository.findByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll()
                .stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLigneCommandeFournisseurByCommandeFournisseurId(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException("La commande fournisseur avec l'id " + id + " n'est présent dans la BDD", ErrorCode.COMMANDE_CLIENT_NOT_FOUND);
        }
        checkIdCommande(id);
        Optional<CommandeFournisseur> commandeFournisseur = commandeFournisseurRepository.findById(id);
        checkIsCommandeLivree(CommandeFournisseurDto.fromEntity(commandeFournisseur.get()));
        commandeFournisseurRepository.deleteById(id);

    }

    private void checkIdCommande(Integer idCommande) {
        if (idCommande == null) {
            log.error("Commande fournisseur id est null");
            throw new InvalidOperationException("l'id Commande fournisseur est null", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
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
            log.error("Ligne Commande fournisseur id est null");
            throw new InvalidOperationException("Impossible de modifier l'article, l'id de la ligne est  null !", ErrorCode.COMMANDE_CLIENT_NOT_VALIDE);
        }
    }

    private void updateMvtStock(Integer idCommande) {
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseurId(idCommande);
        ligneCommandeFournisseurs.forEach(ligneCommandeFournisseur -> {
            MvtStockDto entreeStock = MvtStockDto.builder()
                    .article(ArticleDto.fromEntity(ligneCommandeFournisseur.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvt.ENTREE)
                    .sourceMvtStock(SourceMvtStock.COMMANDE_FOURNISSEUR)
                    .quantite(ligneCommandeFournisseur.getQuantite())
                    .entreprise(ligneCommandeFournisseur.getEntreprise())
                    .build();
            mvtStockService.entreeStock(entreeStock);
        });
    }
}
