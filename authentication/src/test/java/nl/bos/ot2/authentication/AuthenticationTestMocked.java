package nl.bos.ot2.authentication;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest
class AuthenticationTestMocked {

    @Test
    void getOauth2Token(WireMockRuntimeInfo wmRuntimeInfo) {
        stubFor(post("/oauth2/token").willReturn(unauthorized()));

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

}