package dz.tchakal.gds.controller.api;


import dz.tchakal.gds.dto.CommandeFournisseurDto;
import dz.tchakal.gds.dto.LigneCommandeFournisseurDto;
import dz.tchakal.gds.model.enumeration.EtatCommande;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(StaticRoot.APP_ROOT + "commandeFournisseurs")
public interface CommandeFournisseurApi {

    @PostMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une commande fournisseur (Ajouter / Modifier)", notes = "Cette méthode permet de créer ou modifier une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "commandeFournisseur est créée/modifiée")
    })
    ResponseEntity<CommandeFournisseurDto> save(@RequestBody CommandeFournisseurDto commandeFournisseurDto);

    @PatchMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs/update/etat/{idCommande}/{etatCommande}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Mis à jour l'etat d'une commande fournisseur", notes = "Cette méthode permet de modifier l'état d'une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "L'état de la commande fournisseur est modifiée"),
            @ApiResponse(code = 404, message = "La commande fournisseur n'est pas trouvée")
    })
    ResponseEntity<CommandeFournisseurDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs/update/quantite/{idCommande}/{idLigneCommande}/{quantite}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Mis à jour la quantite d'une ligne commande fournisseur", notes = "Cette méthode permet de modifier la quantite d'une ligne commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "La ligne de la commande fournisseur est modifiée"),
    })
    ResponseEntity<CommandeFournisseurDto> updateQuantite(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs/update/fournisseur/{idCommande}/{idFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Mis à jour le fournisseur d'une ligne commande fournisseur", notes = "Cette méthode permet de modifier le fournisseur d'une ligne commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Le fournisseur de la commande fournisseur est modifié"),
    })
    ResponseEntity<CommandeFournisseurDto> updateFournisseur(@PathVariable("idCommande") Integer idCommande, @PathVariable("idFournisseur") Integer idFournisseur);

    @PatchMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs/update/article/{idCommande}/{idLigneCommmande}/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Mis à jour l'article d'une ligne commande fournisseur", notes = "Cette méthode permet de modifier l'article d'une ligne commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "l'article de ligne commande fournisseur est modifié"),
    })
    ResponseEntity<CommandeFournisseurDto> updateArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommmande") Integer idLigneCommmande, @PathVariable("idArticle") Integer idArticle);

    @DeleteMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs/delete/article/{idCommande}/{idLigneCommmande}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Supprimer une ligne commande fournisseur", notes = "Cette méthode permet de supprimer une ligne commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "La ligne commande fournisseur est supprimée"),
    })
    ResponseEntity<CommandeFournisseurDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommmande") Integer idLigneCommmande);

    @GetMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs/{idCommandeFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande fournisseur par son id", notes = "Cette méthode permet de rechercher une commande fournisseur par son id", response = CommandeFournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "commandeFournisseur est trouvé"),
            @ApiResponse(code = 404, message = "commandeFournisseur n'est pas trouvé")
    })
    ResponseEntity<CommandeFournisseurDto> findById(@PathVariable("idCommandeFournisseur") Integer id);

    @GetMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des commandeFournisseurs", notes = "Cette méthode permet de charger la liste des commandeFournisseurs ", responseContainer = "List<CommandeFournisseurDto>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "les commandeFournisseurs sont trouvés dans la BDD"),
            @ApiResponse(code = 404, message = "les commandeFournisseurs ne sont pas trouvés dans la BDD"),

    })
    ResponseEntity<List<CommandeFournisseurDto>> findAll();
    @GetMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs/lignesCommande/{idCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des lignes commande fournisseurs avec id de la commande", notes = "Cette méthode permet de charger la liste des lignes commande fournisseurs avec id de la commande", responseContainer = "List<LigneCommandeFournisseurDto>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "les lignes commande fournisseurs sont trouvées dans la BDD"),
            @ApiResponse(code = 404, message = "les lignes commande fournisseurs  ne sont pas trouvées dans la BDD"),

    })
    ResponseEntity<List<LigneCommandeFournisseurDto>> findAllLigneCommandeFournisseurByCommandeFournisseurId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(value = StaticRoot.APP_ROOT + "/commandeFournisseurs/delete/{idCommande}")
    @ApiOperation(value = "Supprimer une commande fournisseur", notes = "Cette méthode permet de supprimer une commande fournisseur par son ID ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet commandeFournisseur est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet commandeFournisseur n'est pas supprimé de la BDD")
    })
    ResponseEntity delete(@PathVariable("idCommande") Integer id);

}
