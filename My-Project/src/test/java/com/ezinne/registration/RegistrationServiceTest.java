package com.ezinne.registration;

import com.ezinne.appUser.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Disabled
class RegistrationServiceTest {

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private RegistrationService registrationService;

    private RegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
          registrationRequest = new RegistrationRequest("Chidi", "Eby", "eby@gmail.com", "password");
    }

    @Test
    void testRegisterUserSuccess() {
        when(appUserService.signUpUser(any())).thenReturn("user has signed up");

        String result = registrationService.register(registrationRequest);
        assertEquals("user has signed up", result);
    }

}