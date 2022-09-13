package dz.tchakal.gds.controller.api;

import dz.tchakal.gds.dto.EntrepriseDto;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(StaticRoot.ENTREPRISE_ENDPOINT)
@CrossOrigin(origins = "http://localhost:4200")
public interface EntrepriseApi {

    @PostMapping(value = StaticRoot.ENTREPRISE_ENDPOINT_SAVE)
    EntrepriseDto save(@RequestBody EntrepriseDto entrepriseDto);

    @GetMapping(value = StaticRoot.ENTREPRISE_ENDPOINT_FINDBYID)
    EntrepriseDto findById(Integer id);

    @GetMapping(value = StaticRoot.ENTREPRISE_ENDPOINT_FINDBYNOM)
    EntrepriseDto findByNom(String nom);

    @GetMapping(value = StaticRoot.ENTREPRISE_ENDPOINT)
    List<EntrepriseDto> findAll();

    @DeleteMapping(value = StaticRoot.ENTREPRISE_ENDPOINT_DELETE)
    void delete(Integer id);
}
