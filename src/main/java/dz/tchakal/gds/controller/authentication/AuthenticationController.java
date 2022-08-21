package dz.tchakal.gds.controller.authentication;

import dz.tchakal.gds.dto.authentication.AuthenticationRequest;
import dz.tchakal.gds.dto.authentication.AuthenticationResponse;
import dz.tchakal.gds.model.authentication.ExtendedUser;
import dz.tchakal.gds.service.authentication.ApplicationUserDetailService;
import dz.tchakal.gds.util.JWTUtil;
import dz.tchakal.gds.util.StaticRoot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(StaticRoot.AUTHENTICATION_ENDPOINT)
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ApplicationUserDetailService userDetailService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotPasse()
                )
        );
        final UserDetails userDetails = userDetailService.loadUserByUsername(request.getEmail());
        final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);
        log.info("jwt "+jwt);
        return ResponseEntity.ok(AuthenticationResponse.builder().jwtAccessToken(jwt).build());
    }
}
