package dz.tchakal.gds.controller.api;

import dz.tchakal.gds.dto.MvtStockDto;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Api(StaticRoot.MVT_ENDPOINT)
public interface MvtStockApi {

    @GetMapping(StaticRoot.MVT_ENDPOINT + "/stockreel/{idArticle}")
    BigDecimal stockReelArticle(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(StaticRoot.MVT_ENDPOINT + "/filter/article/{idArticle}")
    List<MvtStockDto> findAllByArticleId(@PathVariable("idArticle") Integer idArticle);

    @GetMapping(StaticRoot.MVT_ENDPOINT + "/stockreel/entree")
    MvtStockDto entreeStock(@RequestBody MvtStockDto mvtStockDto);

    @GetMapping(StaticRoot.MVT_ENDPOINT + "/stockreel/sortie")
    MvtStockDto sortieStock(@RequestBody MvtStockDto mvtStockDto);

    @GetMapping(StaticRoot.MVT_ENDPOINT + "/stockreel/correction/positive")
    MvtStockDto correctionStockPositive(@RequestBody MvtStockDto mvtStockDto);

    @GetMapping(StaticRoot.MVT_ENDPOINT + "/stockreel/correction/negative")
    MvtStockDto correctionStockNegative(@RequestBody MvtStockDto mvtStockDto);
}
