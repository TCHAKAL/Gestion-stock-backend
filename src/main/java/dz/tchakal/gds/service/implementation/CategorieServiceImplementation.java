package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.CategorieDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.Categorie;
import dz.tchakal.gds.repository.CategorieRepository;
import dz.tchakal.gds.service.CategorieService;
import dz.tchakal.gds.util.StaticUtil;
import dz.tchakal.gds.validator.CategorieValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("CategorieServiceImplementation")
@Slf4j
public class CategorieServiceImplementation implements CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieServiceImplementation(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public CategorieDto save(CategorieDto categorieDto) {
        List<String> errors = CategorieValidator.validate(categorieDto);
        if (!errors.isEmpty()) {
            log.error("La catégorie n'est pas valide");
            throw new InvalidEntityException("La catégorie n'est pas valide", ErrorCode.CATEGORIE_NOT_VALIDE, errors);
        }
        return CategorieDto.fromEntity(
                categorieRepository.save(CategorieDto.toEntity(categorieDto))
        );
    }

    @Override
    public CategorieDto findById(Integer id) {
        if (id == null) {
            log.error("L'id de la catégorie est null");
            return null;
        }
        Optional<Categorie> categorie = categorieRepository.findById(id);
        CategorieDto categorieDto = CategorieDto.fromEntity(categorie.get());
        return Optional.of(categorieDto)
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CATEGORIE_NOT_FOUND));
    }

    @Override
    public CategorieDto findByCode(String code) {
        if (code == null) {
            log.error("Le code de la catégorie est null");
            return null;
        }
        return Optional.of(CategorieDto.fromEntity(categorieRepository.findByCode(code)))
                .orElseThrow(()-> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE,ErrorCode.CATEGORIE_NOT_FOUND));
    }

    @Override
    public CategorieDto findByDesignation(String designation) {
        if (designation == null) {
            log.error("La designation de la catégorie est null");
            return null;
        }
        Categorie categorie = categorieRepository.findByDesignation(designation);
        CategorieDto articleDto = CategorieDto.fromEntity(categorie);
        return Optional.of(articleDto)
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CATEGORIE_NOT_FOUND));
    }

    @Override
    public List<CategorieDto> findAll() {
        return categorieRepository.findAll()
                .stream()
                .map(CategorieDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CATEGORIE_NOT_FOUND);
        } else {
            categorieRepository.deleteById(id);
        }
    }
}
