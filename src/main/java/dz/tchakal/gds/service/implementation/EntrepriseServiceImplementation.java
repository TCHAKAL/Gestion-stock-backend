package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.EntrepriseDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.Entreprise;
import dz.tchakal.gds.repository.EntrepriseRepository;
import dz.tchakal.gds.service.EntrepriseService;
import dz.tchakal.gds.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("EntrepriseServiceImplementation")
@Slf4j
public class EntrepriseServiceImplementation implements EntrepriseService {

    private EntrepriseRepository entrepriseRepository;

    @Autowired
    public EntrepriseServiceImplementation(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto entrepriseDto) {
        List<String> errors = EntrepriseValidator.validate(entrepriseDto);
        if(!errors.isEmpty()){
            log.error("L'entreprise n'est pas valide");
            throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCode.ARTICLE_NOT_VALIDE,errors);
        }
        return EntrepriseDto.fromEntity(
                entrepriseRepository.save(EntrepriseDto.toEntity(entrepriseDto))
        );
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if(id==null){
            log.error("Entreprise ID est null");
            return null;
        }
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        EntrepriseDto entrepriseDto = EntrepriseDto.fromEntity(entreprise.get());
        return Optional.of(entrepriseDto)
                .orElseThrow(()->new EntityNotFoundException("Aucun entreprise avec l'id = "+id+" n'est trouvé dans la BDD",ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public EntrepriseDto findByNom(String nom) {
        if(nom==null){
            log.error("Entreprise nom est null");
            return null;
        }
        Entreprise entreprise = entrepriseRepository.findByNom(nom);
        EntrepriseDto entrepriseDto = EntrepriseDto.fromEntity(entreprise);
        return Optional.of(entrepriseDto)
                .orElseThrow(()->new EntityNotFoundException("Aucun entreprise avec le code = "+nom+" n'est trouvé dans la BDD",ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll()
                .stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            throw new InvalidEntityException("L'entreprise avec l'id "+id+" n'est présent dans la BDD",ErrorCode.ARTICLE_NOT_FOUND);
        }else{
            entrepriseRepository.deleteById(id);
        }
    }
}
