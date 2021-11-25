package nl.bos.ot2.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationDTO(
        @JsonProperty("grant_type") String grantType,
        @JsonProperty("client_secret") String clientSecret,
        @JsonProperty("client_id") String clientId,
        String username,
        String password)
{}