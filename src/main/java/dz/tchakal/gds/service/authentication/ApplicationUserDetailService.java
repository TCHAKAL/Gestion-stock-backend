package dz.tchakal.gds.service.authentication;

import dz.tchakal.gds.dto.UtilisateurDto;
import dz.tchakal.gds.model.authentication.ExtendedUser;
import dz.tchakal.gds.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationUserDetailService implements UserDetailsService {
    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Charger l'utilisateur par son nom
        UtilisateurDto utilisateur = utilisateurService.findByEmail(email);
        //Charger la liste des privileges
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (utilisateur.getRoles() != null) {
            utilisateur.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            });
        }
        return new ExtendedUser(utilisateur.getEmail(), utilisateur.getMotPasse(), authorities, utilisateur.getEntreprise().getId());
    }

//    @Autowired
//    private UtilisateurRepository utilisateurRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        //System.err.println(utilisateurRepository.findByEmail(email).toString());
//        Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElseThrow(() -> {
//            throw new EntityNotFoundException(StaticUtil.UTILISATEUR_NOT_FOUND, ErrorCode.UTILISATEUR_NOT_FOUND);
//        });
//        //"{noop}" pour dire a spring boot que le mot de passe n'est pas crypté
//        return new User(utilisateur.getEmail(),utilisateur.getMotPasse(), Collections.emptyList());
//    }
}
