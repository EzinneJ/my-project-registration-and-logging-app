package com.ezinne.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService logInService;

    @PostMapping
    public String logIn(@RequestBody LoginRequest request) {
        return logInService.login(request);
    }

}
