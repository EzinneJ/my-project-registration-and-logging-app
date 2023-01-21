package com.ezinne.login;

import com.ezinne.appUser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService logInService;

    @PostMapping
    public String logIn(@RequestBody LoginRequest request) {
        return logInService.login(request);
    }

    @GetMapping("/{email}")
    public String appUserByEmail(@PathVariable String email){
        logInService.findByEmail(email);
        return String.format("this %s exists", email);
    }
}
