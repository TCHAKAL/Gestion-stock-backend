package dz.tchakal.gds.controller.api;


import dz.tchakal.gds.dto.CommandeClientDto;
import dz.tchakal.gds.dto.LigneCommandeClientDto;
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

@Api(StaticRoot.APP_ROOT + "commandeClients")
public interface CommandeClientApi {

    @PostMapping(value = StaticRoot.APP_ROOT + "/commandeClients/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une commande client (Ajouter / Modifier)", notes = "Cette méthode permet de créer ou modifier une commande client", response = CommandeClientDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "commandeClient est créée/modifiée")
    })
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto commandeClientDto);

    @PatchMapping(value = StaticRoot.APP_ROOT + "/commandeClients/update/etat/{idCommande}/{etatCommande}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Mis à jour l'etat d'une commande client", notes = "Cette méthode permet de modifier l'état d'une commande client", response = CommandeClientDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "L'état de la commande client est modifiée"),
            @ApiResponse(code = 404, message = "La commande client n'est pas trouvée")
    })
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value = StaticRoot.APP_ROOT + "/commandeClients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Mis à jour la quantite d'une ligne commande client", notes = "Cette méthode permet de modifier la quantite d'une ligne commande client", response = CommandeClientDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "La ligne de la commande client est modifiée"),
    })
    ResponseEntity<CommandeClientDto> updateQuantite(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(value = StaticRoot.APP_ROOT + "/commandeClients/update/client/{idCommande}/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Mis à jour le client d'une ligne commande client", notes = "Cette méthode permet de modifier le client d'une ligne commande client", response = CommandeClientDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Le client de la commande client est modifié"),
    })
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    @PatchMapping(value = StaticRoot.APP_ROOT + "/commandeClients/update/article/{idCommande}/{idLigneCommmande}/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Mis à jour l'article d'une ligne commande client", notes = "Cette méthode permet de modifier l'article d'une ligne commande client", response = CommandeClientDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "l'article de ligne commande client est modifié"),
    })
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommmande") Integer idLigneCommmande, @PathVariable("idArticle") Integer idArticle);

    @DeleteMapping(value = StaticRoot.APP_ROOT + "/commandeClients/delete/article/{idCommande}/{idLigneCommmande}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Supprimer une ligne commande client", notes = "Cette méthode permet de supprimer une ligne commande client", response = CommandeClientDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "La ligne commande client est supprimée"),
    })
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommmande") Integer idLigneCommmande);

    @GetMapping(value = StaticRoot.APP_ROOT + "/commandeClients/{idCommandeClient}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande client par son id", notes = "Cette méthode permet de rechercher une commande client par son id", response = CommandeClientDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "commandeClient est trouvé"),
            @ApiResponse(code = 404, message = "commandeClient n'est pas trouvé")
    })
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer id);

    @GetMapping(value = StaticRoot.APP_ROOT + "/commandeClients", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des commandeClients", notes = "Cette méthode permet de charger la liste des commandeClients ", responseContainer = "List<CommandeClientDto>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "les commandeClients sont trouvés dans la BDD"),
            @ApiResponse(code = 404, message = "les commandeClients ne sont pas trouvés dans la BDD"),

    })
    ResponseEntity<List<CommandeClientDto>> findAll();
    @GetMapping(value = StaticRoot.APP_ROOT + "/commandeClients/lignesCommande/{idCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des lignes commande clients avec id de la commande", notes = "Cette méthode permet de charger la liste des lignes commande clients avec id de la commande", responseContainer = "List<LigneCommandeClientDto>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "les lignes commande clients sont trouvées dans la BDD"),
            @ApiResponse(code = 404, message = "les lignes commande clients  ne sont pas trouvées dans la BDD"),

    })
    ResponseEntity<List<LigneCommandeClientDto>> findAllLigneCommandeClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(value = StaticRoot.APP_ROOT + "/commandeClients/delete/{idCommande}")
    @ApiOperation(value = "Supprimer une commande client", notes = "Cette méthode permet de supprimer une commande client par son ID ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet commandeClient est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet commandeClient n'est pas supprimé de la BDD")
    })
    ResponseEntity delete(@PathVariable("idCommande") Integer id);

}
