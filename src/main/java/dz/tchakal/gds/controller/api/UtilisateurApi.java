package dz.tchakal.gds.controller.api;


import dz.tchakal.gds.dto.UtilisateurDto;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(StaticRoot.UTILISATEUR_ENDPOINT)
public interface UtilisateurApi {

    @PostMapping(value = StaticRoot.UTILISATEUR_ENDPOINT_SAVE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un utilisateur (Ajouter / Modifier)", notes = "Cette méthode permet de créer ou modifier un utilisateur", response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Utlisateur est crée/modifié")
    })
    UtilisateurDto save(@RequestBody UtilisateurDto utilisateurDto);

    @GetMapping(value = StaticRoot.UTILISATEUR_ENDPOINT_FINDBYID)
    @ApiOperation(value = "Rechercher un utilisateur", notes = "Cette méthode permet de rechercher un utilisateur par son id", response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Utlisateur est trouvé"),
            @ApiResponse(code = 404, message = "Utlisateur n'est pas trouvé")
    })
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);


    @GetMapping(value = StaticRoot.UTILISATEUR_ENDPOINT_FINDBYMAIL)
    @ApiOperation(value = "Rechercher un utilisateur", notes = "Cette méthode permet de rechercher un utilisateur par son email", response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Utlisateur est trouvé dans la BDD"),
            @ApiResponse(code = 404, message = "Utlisateur n'est pas trouvé dans la BDD")

    })
    @CrossOrigin
    UtilisateurDto findByEmail(String email);

    @GetMapping(value = StaticRoot.UTILISATEUR_ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des utilisateurs", notes = "Cette méthode permet de charger la liste des utilisateurs ", responseContainer = "List<UtilisateurDto>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "les Utlisateurs sont trouvés dans la BDD"),
            @ApiResponse(code = 404, message = "les Utlisateurs ne sont pas trouvés dans la BDD"),

    })
    List<UtilisateurDto> findAll();

    @DeleteMapping(value = StaticRoot.UTILISATEUR_ENDPOINT_DELETE)
    @ApiOperation(value = "Supprimer un utilisateur", notes = "Cette méthode permet de supprimer un utilisateur par son ID ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet utilisateur est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet utilisateur n'est pas supprimé de la BDD")
    })
    void delete(@PathVariable("idUtilisateur") Integer id);

}
