package nl.bos.ot2.authentication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTestIntegration {

    @Test
    void getOauth2Token() {
        IAuthenticationService instance = EAuthenticationService.INSTANCE;
        instance.setPropertyFile("config.properties");
        String oauth2Token;
        try {
            oauth2Token = instance.getOauth2Token();
            assertNotNull(oauth2Token);
        } catch (AuthenticationException e) {
            System.out.printf("Integration test not possible because %s", e.getMessage());
        }
    }

    @Test
    void getOauth2TokenWrongPropertyFile() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> {
            IAuthenticationService instance = EAuthenticationService.INSTANCE;
            instance.setPropertyFile("");
            instance.getOauth2Token();
        });
        assertEquals("Config file not found!", runtimeException.getMessage());
    }

    @Test
    void getOauth2TokenDefaultPropertyFile() {
        IAuthenticationService instance = EAuthenticationService.INSTANCE;
        String oauth2Token;
        try {
            oauth2Token = instance.getOauth2Token();
            assertNotNull(oauth2Token);
        } catch (AuthenticationException e) {
            System.out.printf("Integration test not possible because %s", e.getMessage());
        }
    }

}