package dz.tchakal.gds.controller;

import dz.tchakal.gds.controller.api.ArticleApi;
import dz.tchakal.gds.dto.ArticleDto;
import dz.tchakal.gds.dto.LigneCommandeClientDto;
import dz.tchakal.gds.dto.LigneCommandeFournisseurDto;
import dz.tchakal.gds.dto.LigneVenteDto;
import dz.tchakal.gds.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController implements ArticleApi {

    @Autowired
    @Qualifier("ArticleServiceImplementation")
    private ArticleService articleService;

    public ArticleController() {
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        return articleService.save(articleDto);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public ArticleDto findByCode(String code) {
        return articleService.findByCode(code);
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }

    @Override
    public List<ArticleDto> findAllByCategorie(Integer idCategorie) {
        return articleService.findAllByCategorie(idCategorie);
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVente(Integer idArticle) {
        return articleService.findHistoriqueVente(idArticle);
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return articleService.findHistoriqueCommandeClient(idArticle);
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return articleService.findHistoriqueCommandeFournisseur(idArticle);
    }
}
