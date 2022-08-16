package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.UtilisateurDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.Utilisateur;
import dz.tchakal.gds.repository.UtilisateurRepository;
import dz.tchakal.gds.service.UtilisateurService;
import dz.tchakal.gds.util.StaticUtil;
import dz.tchakal.gds.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service("UtilisateurImplimentation")
@Slf4j
public class UtilisateurImplimentation implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurImplimentation(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto utilisateurDto) {
        List<String> errors = UtilisateurValidator.validate(utilisateurDto);
        if (!errors.isEmpty()) {
            log.error("Le utilisateur est invalide");
            throw new InvalidEntityException("Le utilisateur est invalide", ErrorCode.CATEGORIE_NOT_VALIDE, errors);
        }
        return UtilisateurDto.fromEntity(utilisateurRepository.save(UtilisateurDto.toEntity(utilisateurDto)));
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if (id == null) {
            log.error("Le utilisateur avec l'id " + id + " n'est pas présent dans la BDD");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get()))
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CLIENT_NOT_FOUND));
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        if (email == null) {
            log.error("Le utilisateur avec l'email " + email + " n'est pas présent dans la BDD");
            return null;
        }
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get()))
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CLIENT_NOT_FOUND));
    }

    @Override
    public UtilisateurDto findByEmailAndMotPasse(String email, String motPasse) {
    if(email==null || !StringUtils.hasLength(email)||motPasse==null||!StringUtils.hasLength(motPasse)){
        log.error("L'email et le mot de passe sont vides !!");
        return null;
    }
    Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmailAndMotPasse(email,motPasse);
    return Optional.of(UtilisateurDto.fromEntity(utilisateur.get()))
            .orElseThrow(()->new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE,ErrorCode.UTILISATEUR_NOT_FOUND));
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll()
                .stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.ARTICLE_NOT_FOUND);
        } else {
            utilisateurRepository.deleteById(id);
        }
    }
}
