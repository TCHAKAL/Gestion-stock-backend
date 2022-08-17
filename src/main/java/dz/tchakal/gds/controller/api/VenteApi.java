package dz.tchakal.gds.controller.api;

import dz.tchakal.gds.dto.FournisseurDto;
import dz.tchakal.gds.dto.UtilisateurDto;
import dz.tchakal.gds.dto.VenteDto;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Api(StaticRoot.VENTE_ENDPOINT)
public interface VenteApi {

    @PostMapping(value = StaticRoot.VENTE_ENDPOINT_SAVE,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une vente (Ajouter / Modifier)",notes = "Cette méthode permet de créer ou modifier une vente",response = FournisseurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "La vente est créée/modifiée")
    })
    VenteDto save(@RequestBody VenteDto venteDto);

    @GetMapping(value = StaticRoot.VENTE_ENDPOINT_FINDBYID,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une vente",notes = "Cette méthode permet de rechercher une vente par son id",response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "La vente est trouvée"),
            @ApiResponse(code=404,message = "La vente n'est pas trouvée")
    })
    VenteDto findById(@PathVariable("idVente") Integer id);

    @GetMapping(value = StaticRoot.VENTE_ENDPOINT_FINDBYCODE,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une vente avec le code",notes = "Cette méthode permet de rechercher une vente par son code",response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "La vente est trouvée"),
            @ApiResponse(code=404,message = "La vente n'est pas trouvée")
    })
    VenteDto findByCode(@PathVariable("codeVente")String code);
    @GetMapping(value = StaticRoot.VENTE_ENDPOINT_FINDBYDATEVENTE,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une vente avec la date vente",notes = "Cette méthode permet de rechercher une vente par sa date de vente",response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "La vente est trouvée"),
            @ApiResponse(code=404,message = "La vente n'est pas trouvée")
    })
    VenteDto findByDateVente(@PathVariable("dateVente")Instant dateVente);
    @GetMapping(value = StaticRoot.VENTE_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Charger la liste des ventes",notes = "Cette méthode permet de charger la liste des ventes",response = UtilisateurDto.class)
    @ApiResponses({
            @ApiResponse(code=200,message = "Les ventes sont trouvées"),
            @ApiResponse(code=404,message = "Les ventes ne sont pas trouvées")
    })
    List<VenteDto> findAll();

    @DeleteMapping(value = StaticRoot.VENTE_ENDPOINT_DELETE)
    @ApiOperation(value = "Supprimer une vente", notes = "Cette méthode permet de supprimer une vente par son ID ")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "L'objet vente est supprimé de la BDD"),
            @ApiResponse(code = 404, message = "L'objet vente n'est pas supprimé de la BDD")
    })
    void delete(Integer id);

}
