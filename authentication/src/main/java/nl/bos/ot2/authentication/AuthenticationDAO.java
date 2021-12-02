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

//todo move the property reading in a utils class/module!
public class AuthenticationDAO implements IAuthenticationDAO {
    private Configuration configCommon;

    @Override
    public String getOauth2Token() throws AuthenticationException {
        if(configCommon == null) {
            initConfigurationFile("config.properties");
        }

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
            if(response.statusCode() == 200) {
                JsonNode jsonNode = objectMapper.readTree(response.body());
                return jsonNode.get("access_token").asText();
            } else {
                throw new AuthenticationException(response.body());
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @Override
    public void initConfigurationFile(String fileName) {
        PropertiesBuilderParameters properties = new Parameters().properties();
        properties.setFileName(fileName);

        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(properties);
        try {
            configCommon = builder.getConfiguration();
        } catch (ConfigurationException e) {
            throw new RuntimeException("Config file not found!", e);
        }
    }
}