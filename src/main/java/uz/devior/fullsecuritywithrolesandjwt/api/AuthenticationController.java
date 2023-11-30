package uz.devior.fullsecuritywithrolesandjwt.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.devior.fullsecuritywithrolesandjwt.auth.AuthenticationResponse;
import uz.devior.fullsecuritywithrolesandjwt.auth.AuthenticationService;
import uz.devior.fullsecuritywithrolesandjwt.user.UserEntity;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserEntity userEntity){
        return ResponseEntity.ok(authenticationService.register(userEntity));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserEntity userEntity){
        return ResponseEntity.ok(authenticationService.authenticate(userEntity));
    }

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
//        return ResponseEntity.ok(authenticationService.register(request));
//    }
//
//    @PostMapping("/register/admin")
//    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody RegisterRequest request){
//        return ResponseEntity.ok(authenticationService.registerAdmin(request));
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody RegisterRequest request){
//        return ResponseEntity.ok(authenticationService.authenticate(request));
//    }
}
