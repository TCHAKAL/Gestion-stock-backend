package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.FournisseurDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.Fournisseur;
import dz.tchakal.gds.repository.FournisseurRepository;
import dz.tchakal.gds.service.FournisseurService;
import dz.tchakal.gds.util.StaticUtil;
import dz.tchakal.gds.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("FournisseurServiceImplementation")
@Slf4j
public class FournisseurServiceImplementation implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    @Autowired
    public FournisseurServiceImplementation(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public FournisseurDto save(FournisseurDto fournisseurDto) {
        List<String> errors = FournisseurValidator.validate(fournisseurDto);
        if (!errors.isEmpty()) {
            log.error("Le fournisseur est invalide");
            throw new InvalidEntityException("Le fournisseur est invalide", ErrorCode.CATEGORIE_NOT_VALIDE, errors);
        }
        return FournisseurDto.fromEntity(fournisseurRepository.save(FournisseurDto.toEntity(fournisseurDto)));
    }

    @Override
    public FournisseurDto findById(Integer id) {
        if (id == null) {
            log.error("Le fournisseur avec l'id " + id + " n'est pas présent dans la BDD");
            return null;
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);
        return Optional.of(FournisseurDto.fromEntity(fournisseur.get()))
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CLIENT_NOT_FOUND));
    }

    @Override
    public FournisseurDto findByMail(String mail) {
        if (mail == null) {
            log.error("Le fournisseur avec l'email " + mail + " n'est pas présent dans la BDD");
            return null;
        }
        Optional<Fournisseur> fournisseur = fournisseurRepository.findByMail(mail);
        return Optional.of(FournisseurDto.fromEntity(fournisseur.get()))
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CLIENT_NOT_FOUND));

    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll()
                .stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.ARTICLE_NOT_FOUND);
        } else {
            fournisseurRepository.deleteById(id);
        }
    }
}
