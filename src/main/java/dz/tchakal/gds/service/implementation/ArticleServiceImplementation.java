package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.ArticleDto;
import dz.tchakal.gds.dto.LigneCommandeClientDto;
import dz.tchakal.gds.dto.LigneCommandeFournisseurDto;
import dz.tchakal.gds.dto.LigneVenteDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.exception.InvalidOperationException;
import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.LigneCommandeClient;
import dz.tchakal.gds.model.LigneCommandeFournisseur;
import dz.tchakal.gds.model.LigneVente;
import dz.tchakal.gds.repository.ArticleRepository;
import dz.tchakal.gds.repository.LigneCommandeClientRepository;
import dz.tchakal.gds.repository.LigneCommandeFournisseurRepository;
import dz.tchakal.gds.repository.LigneVenteRepository;
import dz.tchakal.gds.service.ArticleService;
import dz.tchakal.gds.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ArticleServiceImplementation")
@Slf4j
public class ArticleServiceImplementation implements ArticleService {

    private ArticleRepository articleRepository;
    private LigneVenteRepository ligneVenteRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;

    @Autowired
    public ArticleServiceImplementation(ArticleRepository articleRepository, LigneVenteRepository ligneVenteRepository, LigneCommandeClientRepository ligneCommandeClientRepository, LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository) {
        this.articleRepository = articleRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        List<String> errors = ArticleValidator.validate(articleDto);
        if (!errors.isEmpty()) {
            log.error("L'article n'est pas valide");
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCode.ARTICLE_NOT_VALIDE, errors);
        }
        return ArticleDto.fromEntity(
                articleRepository.save(ArticleDto.toEntity(articleDto))
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        if (id == null) {
            log.error("Article ID est null");
            return null;
        }
        Optional<Article> article = articleRepository.findById(id);
        ArticleDto articleDto = ArticleDto.fromEntity(article.get());
        return Optional.of(articleDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun article avec l'id = " + id + " n'est trouvé dans la BDD", ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public ArticleDto findByCode(String code) {
        if (code == null) {
            log.error("Article code est null");
            return null;
        }
        Article article = articleRepository.findByCode(code);
        ArticleDto articleDto = ArticleDto.fromEntity(article);
        return Optional.of(articleDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun article avec le code = " + code + " n'est trouvé dans la BDD", ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll()
                .stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllByCategorie(Integer idCategotie) {
        return articleRepository.findAllByCategorieId(idCategotie).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVente(Integer idArticle) {
        return ligneVenteRepository.findAllByArticleId(idArticle)
                .stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return ligneCommandeClientRepository.findAllByArticleId(idArticle)
                .stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return ligneCommandeFournisseurRepository.findAllByArticleId(idArticle)
                .stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Impossible de supprimer l'artile, l'id est null");
            return;
        }
        List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientRepository.findAllByArticleId(id);
        if (!ligneCommandeClients.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer l'article, il est utilisée dans des lignes commande client", ErrorCode.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByArticleId(id);
        if (!ligneCommandeFournisseurs.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer l'article, il est utilisée dans des lignes commande fournisseur", ErrorCode.ARTICLE_ALREADY_IN_USE);
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByArticleId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer l'article, il est utilisée dans des lignes de vente", ErrorCode.ARTICLE_ALREADY_IN_USE);
        }
        articleRepository.deleteById(id);

    }
}
