package dz.tchakal.gds.controller.api;

import dz.tchakal.gds.dto.CategorieDto;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(StaticRoot.APP_ROOT + "/categories")
public interface CategorieApi {

    @PostMapping(value = StaticRoot.APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une categorie (Ajouter / Modifier)", notes = "Cette méthode permet d'ajouter et de modifier une categorie", response = CategorieDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet categorie créé / modifié")
    })
    CategorieDto save(@RequestBody CategorieDto categorieDto);

    @GetMapping(value = StaticRoot.APP_ROOT + "/categories/{idCategorie}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie", notes = "Cette méthode permet de recherhcer une categorie par son ID ", response = CategorieDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet categorie est trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "L'objet categorie n'est pas trouvé dans la BDD")
    })
    CategorieDto findById(@PathVariable("idCategorie") Integer id);

    @GetMapping(value = StaticRoot.APP_ROOT + "/categories/{codeCategorie}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie avec son code", notes = "Cette méthode permet de rechercher une categorie par son code", response = CategorieDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "La categorie est trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "La categorie n'est pas trouvé dans la BDD")
    })
        // [CAT1,CAT2,CAT3]
    CategorieDto findByCode(@ApiParam(value = "Le code de catégorie doit etre dans l'un des valeurs suivant [CAT1,CAT2,CAT3] ") @PathVariable("codeCategorie") String code);

    @GetMapping(value = StaticRoot.APP_ROOT + "/categories/{designation}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie avec sa designation", notes = "Cette méthode permet de rechercher une categorie par sa designation", response = CategorieDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "La catégorie est trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "La catégorie n'est pas trouvé dans la BDD")
    })
    CategorieDto findByDesignation(@PathVariable("designation") String designation);

    @GetMapping(value = StaticRoot.APP_ROOT + "/categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des categories", notes = "Cette méthode permet de renvoyer la liste des categories", responseContainer = "List<CategorieDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet categorie est trouvé dans la BDD"),
            @ApiResponse(code = 403, message = "Vous pouvez pas acceder a cette ressource  JWT"),
            @ApiResponse(code = 404, message = "L'objet categorie n'est pas trouvé dans la BDD")
    })
    List<CategorieDto> findAll();

    @DeleteMapping(value = StaticRoot.APP_ROOT + "/categories/delete/{idCategorie}")
    @ApiOperation(value = "Supprimer une categorie", notes = "Cette méthode permet de supprimer une categorie par son ID ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet categorie est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet categorie n'est pas supprimé de la BDD")
    })
    void delete(@PathVariable("idCategorie") Integer id);
}
