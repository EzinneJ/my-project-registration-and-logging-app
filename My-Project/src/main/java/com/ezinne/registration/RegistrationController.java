package com.ezinne.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {

        String result = registrationService.register(request);
        String alreadyExist = String.format("user with %s already exists", request.getEmail());

        if (result.contains(alreadyExist)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(alreadyExist);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegisteredUser(@PathVariable Long id) {

        String result = registrationService.deleteRegisteredUser(id);
        String notFound = "user not found";
        if (result.contains(notFound)) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }

        return ResponseEntity.status(HttpStatus.OK).body(String.format("AppUser with %d has been deleted", id));
    }

}
