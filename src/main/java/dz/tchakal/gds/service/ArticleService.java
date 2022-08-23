package dz.tchakal.gds.service;

import dz.tchakal.gds.dto.ArticleDto;
import dz.tchakal.gds.dto.LigneCommandeClientDto;
import dz.tchakal.gds.dto.LigneCommandeFournisseurDto;
import dz.tchakal.gds.dto.LigneVenteDto;

import java.util.List;

public interface ArticleService {
    ArticleDto save(ArticleDto articleDto);
    ArticleDto findById(Integer id);
    ArticleDto findByCode(String code);
    List<ArticleDto> findAll();
    List<ArticleDto> findAllByCategorie(Integer idCategorie);
    List<LigneVenteDto> findHistoriqueVente(Integer idArticle);
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);
    void delete(Integer id);
}
