package com.ezinne.registration;

import lombok.*;
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private final String lastName;
    private final String firstName;
    private final String email;
    private final String password;
}
