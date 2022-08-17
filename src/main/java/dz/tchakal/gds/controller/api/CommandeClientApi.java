package dz.tchakal.gds.controller.api;


import dz.tchakal.gds.dto.CommandeClientDto;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(StaticRoot.APP_ROOT+"commandeClients")
public interface CommandeClientApi {

    @PostMapping(value = StaticRoot.APP_ROOT+"commandeClients/create",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une commande client (Ajouter / Modifier)",notes = "Cette méthode permet de créer ou modifier une commande client",response = CommandeClientDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "commandeClient est créée/modifiée")
    })
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto commandeClientDto);

    @GetMapping(value = StaticRoot.APP_ROOT+"commandeClients/{idCommandeClient}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande client par son id",notes = "Cette méthode permet de rechercher une commande client par son id",response = CommandeClientDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "commandeClient est trouvé"),
            @ApiResponse(code=404,message = "commandeClient n'est pas trouvé")
    })
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer id);

    @GetMapping(value = StaticRoot.APP_ROOT+"commandeClients",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des commandeClients",notes = "Cette méthode permet de charger la liste des commandeClients ",responseContainer ="List<CommandeClientDto>")
    @ApiResponses({
            @ApiResponse(code=200,message = "les commandeClients sont trouvés dans la BDD"),
            @ApiResponse(code=404,message = "les commandeClients ne sont pas trouvés dans la BDD"),

    })
    ResponseEntity<List<CommandeClientDto>> findAll();

    @DeleteMapping(value = StaticRoot.APP_ROOT + "/commandeClients/delete/{idCommandeClient}")
    @ApiOperation(value = "Supprimer une commande client", notes = "Cette méthode permet de supprimer une commande client par son ID ")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "L'objet commandeClient est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet commandeClient n'est pas supprimé de la BDD")
    })
    ResponseEntity delete(@PathVariable("idCommandeClient") Integer id);

}
