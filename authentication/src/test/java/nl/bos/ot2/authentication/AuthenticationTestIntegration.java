package nl.bos.ot2.authentication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AuthenticationTestIntegration {

    @Test
    void getOauth2Token() {
        IAuthenticationService instance = EAuthenticationService.INSTANCE;
        instance.setPropertyFile("config.properties");
        String oauth2Token;
        try {
            oauth2Token = instance.getOauth2Token();
            Assertions.assertNotNull(oauth2Token);
        } catch (AuthenticationException e) {
            System.out.printf("Integration test not possible because %s", e.getMessage());
        }
    }

    @Test
    void getOauth2TokenWrongPropertyFile() {
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            IAuthenticationService instance = EAuthenticationService.INSTANCE;
            instance.setPropertyFile("");
            instance.getOauth2Token();
        });
        Assertions.assertEquals("Config file not found!", runtimeException.getMessage());
    }

    @Test
    void getOauth2TokenDefaultPropertyFile() {
        IAuthenticationService instance = EAuthenticationService.INSTANCE;
        String oauth2Token;
        try {
            oauth2Token = instance.getOauth2Token();
            Assertions.assertNotNull(oauth2Token);
        } catch (AuthenticationException e) {
            System.out.printf("Integration test not possible because %s", e.getMessage());
        }
    }

}