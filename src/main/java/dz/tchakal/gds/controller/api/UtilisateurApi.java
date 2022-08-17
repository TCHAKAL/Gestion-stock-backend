package dz.tchakal.gds.controller.api;


import dz.tchakal.gds.dto.UtilisateurDto;
import dz.tchakal.gds.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Api(Constant.APP_ROOT+"utilisateurs")
public interface UtilisateurApi {

    @PostMapping(value = Constant.APP_ROOT+"utilisateurs/create",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un utilisateur (Ajouter / Modifier)",notes = "Cette méthode permet de créer ou modifier un utilisateur",response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Utlisateur est crée/modifié")
    })
    UtilisateurDto save(@RequestBody UtilisateurDto utilisateurDto);

    @GetMapping(value = Constant.APP_ROOT+"utilisateurs/{idUtilisateur}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur",notes = "Cette méthode permet de rechercher un utilisateur par son id",response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Utlisateur est trouvé"),
            @ApiResponse(code=404,message = "Utlisateur n'est pas trouvé")
    })
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(value = {Constant.APP_ROOT+"utilisateurs/{email}",Constant.APP_ROOT+"utilisateurs/{motPasse}"},produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur",notes = "Cette méthode permet de rechercher un utilisateur par son email et mot de passe",response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Utlisateur est trouvé dans la BDD"),
            @ApiResponse(code=404,message = "Utlisateur n'est pas trouvé dans la BDD")

    })
    UtilisateurDto findByEmailAndMotPasse(@PathVariable("email") String email,@PathVariable("motPasse")String motPasse);

    @GetMapping(value = Constant.APP_ROOT+"utilisateurs",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des utilisateurs",notes = "Cette méthode permet de charger la liste des utilisateurs ",responseContainer ="List<UtilisateurDto>")
    @ApiResponses({
            @ApiResponse(code=200,message = "les Utlisateurs sont trouvés dans la BDD"),
            @ApiResponse(code=404,message = "les Utlisateurs ne sont pas trouvés dans la BDD"),

    })
    List<UtilisateurDto> findAll();

    @DeleteMapping(value = Constant.APP_ROOT + "/utilisateurs/delete/{idUtilisateur}")
    @ApiOperation(value = "Supprimer un utilisateur", notes = "Cette méthode permet de supprimer un utilisateur par son ID ")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "L'objet utilisateur est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet utilisateur n'est pas supprimé de la BDD")
    })
    void delete(@PathVariable("idUtilisateur") Integer id);

}
