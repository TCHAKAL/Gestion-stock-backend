package dz.tchakal.gds.controller.api;

import dz.tchakal.gds.dto.ArticleDto;
import dz.tchakal.gds.util.Constant;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ArticleApi {

    @PostMapping(value = Constant.APP_ROOT + "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto save(@RequestBody ArticleDto articleDto);

    @GetMapping(value = Constant.APP_ROOT + "/articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    @GetMapping(value = Constant.APP_ROOT + "/articles/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findByCode(@PathVariable("codeArticle") String code);

    @GetMapping(value = Constant.APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findAll();

    @DeleteMapping(value = Constant.APP_ROOT + "/articles/delete/{codeArticle}")
    void delete(@PathVariable("idArticle") Integer id);
}
