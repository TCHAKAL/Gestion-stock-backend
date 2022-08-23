package dz.tchakal.gds.controller.api;


import dz.tchakal.gds.dto.ClientDto;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(StaticRoot.APP_ROOT+"clients")
public interface ClientApi {

    @PostMapping(value = StaticRoot.APP_ROOT+"/clients/create",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un client (Ajouter / Modifier)",notes = "Cette méthode permet de créer ou modifier un client",response = ClientDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Utlisateur est crée/modifié")
    })
    ClientDto save(@RequestBody ClientDto clientDto);

    @GetMapping(value = StaticRoot.APP_ROOT+"/clients/{idClient}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un client par son id",notes = "Cette méthode permet de rechercher un client par son id",response = ClientDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Utlisateur est trouvé"),
            @ApiResponse(code=404,message = "Utlisateur n'est pas trouvé")
    })
    ClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = StaticRoot.APP_ROOT+"/clients/{mail}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un client par mail",notes = "Cette méthode permet de rechercher un client par son email",response = ClientDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Utlisateur est trouvé dans la BDD"),
            @ApiResponse(code=404,message = "Utlisateur n'est pas trouvé dans la BDD")

    })
    ClientDto findByMail(@PathVariable("mail") String email);

    @GetMapping(value = StaticRoot.APP_ROOT+"/clients",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des clients",notes = "Cette méthode permet de charger la liste des clients ",responseContainer ="List<ClientDto>")
    @ApiResponses({
            @ApiResponse(code=200,message = "les Utlisateurs sont trouvés dans la BDD"),
            @ApiResponse(code=404,message = "les Utlisateurs ne sont pas trouvés dans la BDD"),

    })
    List<ClientDto> findAll();

    @DeleteMapping(value = StaticRoot.APP_ROOT + "/clients/delete/{idClient}")
    @ApiOperation(value = "Supprimer un client", notes = "Cette méthode permet de supprimer un client par son ID ")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "L'objet client est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet client n'est pas supprimé de la BDD")
    })
    void delete(@PathVariable("idClient") Integer id);

}
