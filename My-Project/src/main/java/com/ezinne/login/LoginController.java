package com.ezinne.login;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService logInService;

    @PostMapping
    public ResponseEntity<String> logIn(@RequestBody LoginRequest request) {
        String result = logInService.login(request);
        String signUp = "user needs to sign up";
        if (result.contains(signUp)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(signUp);
        }
        else {
            String loggedIn = "User is successfully logged in, user can now save notes";
            if (result.contains(loggedIn)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(loggedIn);

            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect password");
    }

}
