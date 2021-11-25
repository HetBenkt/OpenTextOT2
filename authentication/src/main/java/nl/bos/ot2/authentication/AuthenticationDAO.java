package nl.bos.ot2.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.PropertiesBuilderParameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpResponse.BodyHandlers;

public class AuthenticationDAO implements IAuthenticationDAO {
    private final Configuration configCommon;

    public AuthenticationDAO() {
        PropertiesBuilderParameters properties = new Parameters().properties();
        properties.setFileName("config.properties");

        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(properties);

        try {
            configCommon = builder.getConfiguration();
        } catch (ConfigurationException e) {
            throw new RuntimeException("Config file not found!", e);
        }
    }

    @Override
    public String getOauth2Token() throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String requestBody = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(new AuthenticationDTO("password", configCommon.getString("tenant_client_secret"), configCommon.getString("tenant_client_id"), configCommon.getString("username"), configCommon.getString("password")));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(configCommon.getString("tenants_url")))
                    .header("Content-Type", MediaType.APPLICATION_JSON)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            JsonNode jsonNode = objectMapper.readTree(response.body());
            return jsonNode.get("access_token").asText();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }
}