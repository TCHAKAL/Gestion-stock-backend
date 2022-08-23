package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.ArticleDto;
import dz.tchakal.gds.dto.LigneVenteDto;
import dz.tchakal.gds.dto.MvtStockDto;
import dz.tchakal.gds.dto.VenteDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.exception.InvalidOperationException;
import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.LigneVente;
import dz.tchakal.gds.model.Vente;
import dz.tchakal.gds.model.enumeration.SourceMvtStock;
import dz.tchakal.gds.model.enumeration.TypeMvt;
import dz.tchakal.gds.repository.ArticleRepository;
import dz.tchakal.gds.repository.LigneVenteRepository;
import dz.tchakal.gds.repository.VenteRepository;
import dz.tchakal.gds.service.MvtStockService;
import dz.tchakal.gds.service.VenteService;
import dz.tchakal.gds.validator.LigneVenteValidator;
import dz.tchakal.gds.validator.VenteValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("VenteServiceImplementation")
@Slf4j
public class VenteServiceImplementation implements VenteService {

    private final VenteRepository venteRepository;
    private final ArticleRepository articleRepository;
    private final LigneVenteRepository ligneVenteRepository;
    private final MvtStockService mvtStockService;

    @Autowired
    public VenteServiceImplementation(VenteRepository venteRepository, ArticleRepository articleRepository, LigneVenteRepository ligneVenteRepository, MvtStockService mvtStockService) {
        this.venteRepository = venteRepository;
        this.articleRepository = articleRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStockService = mvtStockService;
    }

    @Override
    public VenteDto save(VenteDto venteDto) {
        //Valider la vente
        List<String> errors = VenteValidator.validate(venteDto);
        if (!errors.isEmpty()) {
            log.error("La vente n'est pas valide");
            throw new InvalidEntityException("La vente n'est pas valide", ErrorCode.VENTE_NOT_VALIDE, errors);
        }

        //Valider les lignes de vente
        List<String> articleErrors = new ArrayList<>();
        venteDto.getLigneVente().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if (!article.isPresent()) {
                articleErrors.add("Aucun article avec l'id " + ligneVenteDto.getArticle().getId() + "n'est présent dans la BDD");
            } else {
                articleErrors.addAll(LigneVenteValidator.validate(ligneVenteDto));
            }
        });
        if (!articleErrors.isEmpty()) {
            log.error("La liste des lignes articles n'est pas valide");
            throw new InvalidEntityException("La liste des lignes articles n'est pas valide", ErrorCode.ARTICLE_NOT_VALIDE, articleErrors);
        }

        Vente savedVente = venteRepository.save(VenteDto.toEntity(venteDto));
        venteDto.getLigneVente().forEach(ligneVenteDto -> {
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVente(savedVente);
            ligneVenteRepository.save(ligneVente);
            updateMvtStock(ligneVente);

        });
        return VenteDto.fromEntity(savedVente);
    }

    @Override
    public VenteDto findById(Integer id) {
        if (id == null) {
            log.error("Vente ID est null");
            return null;
        }
        Optional<Vente> vente = venteRepository.findById(id);
        VenteDto venteDto = VenteDto.fromEntity(vente.get());
        return Optional.of(venteDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun vente avec l'id = " + id + " n'est pas présente dans la BDD", ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public VenteDto findByCode(String code) {
        if (code == null) {
            log.error("Vente code est null");
            return null;
        }
        Optional<Vente> vente = venteRepository.findByCode(code);
        VenteDto venteDto = VenteDto.fromEntity(vente.get());
        return Optional.of(venteDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente avec le code = " + code + " n'est pas présente dans la BDD", ErrorCode.ARTICLE_NOT_FOUND));

    }

    @Override
    public VenteDto findByDateVente(Instant dateVente) {
        if (dateVente == null) {
            log.error("Vente dateVente est null");
            return null;
        }
        Optional<Vente> vente = venteRepository.findByDateVente(dateVente);
        VenteDto venteDto = VenteDto.fromEntity(vente.get());
        return Optional.of(venteDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente avec la date = " + dateVente + " n'est pas présente dans la BDD", ErrorCode.ARTICLE_NOT_FOUND));

    }

    @Override
    public List<VenteDto> findAll() {
        return venteRepository.findAll()
                .stream()
                .map(VenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            log.error("Impossible de supprimer la vente, l'id est null");
            return;
        }
        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVenteId(id);
        if (!ligneVentes.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer la vente, elle est utilisée dans des lignes de vente", ErrorCode.VENTE_ALREADY_IN_USE);
        }
            venteRepository.deleteById(id);
    }

    private void updateMvtStock(LigneVente ligneVente) {
       MvtStockDto sortieStock = MvtStockDto.builder()
                .article(ArticleDto.fromEntity(ligneVente.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvt.SORTIE)
                .sourceMvtStock(SourceMvtStock.VENTE)
                .quantite(ligneVente.getQuantite())
                .idEntreprise(ligneVente.getIdEntreprise())
                .build();
        mvtStockService.sortieStock(sortieStock);

    }
}
