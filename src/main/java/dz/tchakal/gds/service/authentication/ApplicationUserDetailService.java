package dz.tchakal.gds.service.authentication;

import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.model.Utilisateur;
import dz.tchakal.gds.repository.UtilisateurRepository;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("ApplicationUserDetailService")
public class ApplicationUserDetailService implements UserDetailsService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //System.err.println(utilisateurRepository.findByEmail(email).toString());
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElseThrow(() -> {
            throw new EntityNotFoundException(StaticUtil.UTILISATEUR_NOT_FOUND, ErrorCode.UTILISATEUR_NOT_FOUND);
        });
        //"{noop}" pour dire a spring boot que le mot de passe n'est pas crypté
        return new User(utilisateur.getEmail(),utilisateur.getMotPasse(), Collections.emptyList());
    }
}
