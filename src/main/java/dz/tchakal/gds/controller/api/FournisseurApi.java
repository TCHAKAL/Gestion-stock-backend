package dz.tchakal.gds.controller.api;

import dz.tchakal.gds.dto.FournisseurDto;
import dz.tchakal.gds.dto.EntrepriseDto;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(StaticRoot.ENTREPRISE_ENDPOINT)
public interface FournisseurApi {

    @PostMapping(value = StaticRoot.FOURNISSEUR_ENDPOINT_SAVE,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un fournisseur (Ajouter / Modifier)",notes = "Cette méthode permet de créer ou modifier un fournisseur",response = FournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Utlisateur est crée/modifié")
    })
    FournisseurDto save(@RequestBody FournisseurDto fournisseurDto);

    @GetMapping(value = StaticRoot.FOURNISSEUR_ENDPOINT_FINDBYID,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un fournisseur par son id",notes = "Cette méthode permet de rechercher un fournisseur par son id",response = FournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Utlisateur est trouvé"),
            @ApiResponse(code=404,message = "Utlisateur n'est pas trouvé")
    })
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping(value = StaticRoot.FOURNISSEUR_ENDPOINT_FINDBYMAIL,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un fournisseur par mail",notes = "Cette méthode permet de rechercher un fournisseur par son email",response = FournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Utlisateur est trouvé dans la BDD"),
            @ApiResponse(code=404,message = "Utlisateur n'est pas trouvé dans la BDD")

    })
    FournisseurDto findByMail(@PathVariable("mail") String email);

    @GetMapping(value = StaticRoot.FOURNISSEUR_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des fournisseurs",notes = "Cette méthode permet de charger la liste des fournisseurs ",responseContainer ="List<FournisseurDto>")
    @ApiResponses({
            @ApiResponse(code=200,message = "les Utlisateurs sont trouvés dans la BDD"),
            @ApiResponse(code=404,message = "les Utlisateurs ne sont pas trouvés dans la BDD"),

    })
    List<FournisseurDto> findAll();

    @DeleteMapping(value = StaticRoot.FOURNISSEUR_ENDPOINT_DELETE)
    @ApiOperation(value = "Supprimer un fournisseur", notes = "Cette méthode permet de supprimer un fournisseur par son ID ")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "L'objet fournisseur est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet fournisseur n'est pas supprimé de la BDD")
    })
    void delete(@PathVariable("idFournisseur") Integer id);

}
