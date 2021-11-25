package nl.bos.ot2.authentication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AuthenticationDAOTest {

    @Test
    void getOauth2Token() throws AuthenticationException {
        IAuthenticationService instance = EAuthenticationService.INSTANCE;
        String oauth2Token = instance.getOauth2Token();
        Assertions.assertNotNull(oauth2Token);
    }
}