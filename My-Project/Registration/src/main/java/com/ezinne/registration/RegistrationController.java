package com.ezinne.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @DeleteMapping("/{email}")
    public String deleteRegisteredUser(@PathVariable String email) {
        return registrationService.deleteRegisteredUser(email);
    }

}
