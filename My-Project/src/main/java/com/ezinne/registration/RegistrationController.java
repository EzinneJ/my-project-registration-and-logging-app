package com.ezinne.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String deleteRegisteredUser(@PathVariable Long id) {
        return registrationService.deleteRegisteredUser(id);
    }

}
