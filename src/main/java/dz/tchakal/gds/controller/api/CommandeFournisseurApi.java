package dz.tchakal.gds.controller.api;


import dz.tchakal.gds.dto.CommandeFournisseurDto;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(StaticRoot.APP_ROOT+"commandeFournisseurs")
public interface CommandeFournisseurApi {

    @PostMapping(value = StaticRoot.COMMANDE_FOURNISSEUR_ENDPOINT+"/create",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une commande fournisseur (Ajouter / Modifier)",notes = "Cette méthode permet de créer ou modifier une commande fournisseur",response = CommandeFournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "commandeFournisseur est créée/modifiée")
    })
    ResponseEntity<CommandeFournisseurDto> save(@RequestBody CommandeFournisseurDto commandeFournisseurDto);

    @GetMapping(value = StaticRoot.COMMANDE_FOURNISSEUR_ENDPOINT+"/{idCommandeFournisseur}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande fournisseur par son id",notes = "Cette méthode permet de rechercher une commande fournisseur par son id",response = CommandeFournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "commandeFournisseur est trouvé"),
            @ApiResponse(code=404,message = "commandeFournisseur n'est pas trouvé")
    })
    ResponseEntity<CommandeFournisseurDto> findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(value = StaticRoot.COMMANDE_FOURNISSEUR_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des commandeFournisseurs",notes = "Cette méthode permet de charger la liste des commandeFournisseurs ",responseContainer ="List<CommandeFournisseurDto>")
    @ApiResponses({
            @ApiResponse(code=200,message = "les commandeFournisseurs sont trouvés dans la BDD"),
            @ApiResponse(code=404,message = "les commandeFournisseurs ne sont pas trouvés dans la BDD"),

    })
    ResponseEntity<List<CommandeFournisseurDto>> findAll();

    @DeleteMapping(value = StaticRoot.COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/{idCommandeFournisseur}")
    @ApiOperation(value = "Supprimer une commande fournisseur", notes = "Cette méthode permet de supprimer une commande fournisseur par son ID ")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "L'objet commandeFournisseur est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet commandeFournisseur n'est pas supprimé de la BDD")
    })
    ResponseEntity delete(@PathVariable("idCommandeFournisseur") Integer id);

}
