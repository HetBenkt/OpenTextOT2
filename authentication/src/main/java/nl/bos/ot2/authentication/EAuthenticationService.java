package nl.bos.ot2.authentication;

public enum EAuthenticationService implements IAuthenticationService {
    INSTANCE;

    private final IAuthenticationDAO authenticationDAO = new AuthenticationDAO();

    @Override
    public String getOauth2Token() throws AuthenticationException {
        return authenticationDAO.getOauth2Token();
    }

    @Override
    public void setPropertyFile(String fileName) {
        authenticationDAO.initConfigurationFile(fileName);
    }
}
