package uz.devior.fullsecuritywithrolesandjwt.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.devior.fullsecuritywithrolesandjwt.jwt.JwtService;
import uz.devior.fullsecuritywithrolesandjwt.user.Role;
import uz.devior.fullsecuritywithrolesandjwt.user.UserEntity;
import uz.devior.fullsecuritywithrolesandjwt.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(UserEntity userEntity) {
        var user = UserEntity
                .builder()
                .username(userEntity.getUsername())
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);

        var token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(UserEntity userEntity) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userEntity.getUsername(),
                        userEntity.getPassword()
                )
        );
        var user = userRepository.findByUsername(userEntity.getUsername()).orElseThrow();
        var token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

//    public AuthenticationResponse registerAdmin(RegisterRequest request) {
//        var user = UserEntity
//                .builder()
//                .username(request.getUsername())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.ADMIN)
//                .build();
//        userRepository.save(user);
//
//        var token = jwtService.generateToken(user);
//        return AuthenticationResponse
//                .builder()
//                .token(token)
//                .build();
//    }
}
