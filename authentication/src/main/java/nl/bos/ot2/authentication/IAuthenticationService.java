package nl.bos.ot2.authentication;

public interface IAuthenticationService {
    String getOauth2Token() throws AuthenticationException;
}
