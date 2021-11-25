package nl.bos.ot2.authentication;

public interface IAuthenticationDAO {
    String getOauth2Token() throws AuthenticationException;
}
