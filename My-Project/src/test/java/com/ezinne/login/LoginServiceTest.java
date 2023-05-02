package com.ezinne.login;

import com.ezinne.appUser.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private LoginService loginService;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("chidi@gmail.com", "password");
    }

    @Test
    public void testLoginUserSuccess() {

        when(appUserService.loginUser(any())).thenReturn("User is successfully logged in, user can now save notes");

        String result = loginService.login(loginRequest);
        assertEquals("User is successfully logged in, user can now save notes", result);
    }
}