package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.EntrepriseDto;
import dz.tchakal.gds.dto.RoleDto;
import dz.tchakal.gds.dto.UtilisateurDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.Entreprise;
import dz.tchakal.gds.repository.EntrepriseRepository;
import dz.tchakal.gds.repository.RoleRepository;
import dz.tchakal.gds.repository.UtilisateurRepository;
import dz.tchakal.gds.service.EntrepriseService;
import dz.tchakal.gds.service.UtilisateurService;
import dz.tchakal.gds.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("EntrepriseServiceImplementation")
@Slf4j
public class EntrepriseServiceImplementation implements EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurService utilisateurService;
    private final RoleRepository roleRepository;

    @Autowired
    public EntrepriseServiceImplementation(EntrepriseRepository entrepriseRepository, UtilisateurRepository utilisateurRepository, UtilisateurService utilisateurService, RoleRepository roleRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurService = utilisateurService;
        this.roleRepository = roleRepository;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto entrepriseDto) {
        List<String> errors = EntrepriseValidator.validate(entrepriseDto);
        if (!errors.isEmpty()) {
            log.error("L'entreprise n'est pas valide");
            throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCode.ARTICLE_NOT_VALIDE, errors);
        }
        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(entrepriseRepository.save(EntrepriseDto.toEntity(entrepriseDto)));
        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);
        UtilisateurDto savedUser = utilisateurService.save(utilisateur);
        RoleDto roleDto = RoleDto.builder()
                .roleName("ADMIN")
                .utilisateur(savedUser)
                .build();
        roleRepository.save(RoleDto.toEntity(roleDto));
        return savedEntreprise;
    }

    private UtilisateurDto fromEntreprise(EntrepriseDto savedEntreprise) {
        return UtilisateurDto.builder()
                .adresse(savedEntreprise.getAdresse())
                .nom(savedEntreprise.getNom())
                .nom(savedEntreprise.getNom())
                .email(savedEntreprise.getEmail())
                .motPasse(generateMotPasse())
                .dateNaissance(Instant.now())
                .photo(savedEntreprise.getPhoto())
                .build();
    }

    private String generateMotPasse() {
        return "som3Randomp@assWord";
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if (id == null) {
            log.error("Entreprise ID est null");
            return null;
        }
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        EntrepriseDto entrepriseDto = EntrepriseDto.fromEntity(entreprise.get());
        return Optional.of(entrepriseDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun entreprise avec l'id = " + id + " n'est trouvé dans la BDD", ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public EntrepriseDto findByNom(String nom) {
        if (nom == null) {
            log.error("Entreprise nom est null");
            return null;
        }
        Entreprise entreprise = entrepriseRepository.findByNom(nom);
        EntrepriseDto entrepriseDto = EntrepriseDto.fromEntity(entreprise);
        return Optional.of(entrepriseDto)
                .orElseThrow(() -> new EntityNotFoundException("Aucun entreprise avec le code = " + nom + " n'est trouvé dans la BDD", ErrorCode.ARTICLE_NOT_FOUND));
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
        if (id == null) {
            throw new InvalidEntityException("L'entreprise avec l'id " + id + " n'est présent dans la BDD", ErrorCode.ARTICLE_NOT_FOUND);
        } else {
            entrepriseRepository.deleteById(id);
        }
    }
}
