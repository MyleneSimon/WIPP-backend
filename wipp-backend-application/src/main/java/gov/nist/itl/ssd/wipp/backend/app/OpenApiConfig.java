package gov.nist.itl.ssd.wipp.backend.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.models.media.MapSchema;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * OpenAPI docs configuration
 *
 * @author Mylene Simon <mylene.simon at nist.gov>
 */
@Component
@OpenAPIDefinition(info = @Info(title = "WIPP",
        description = "WIPP REST API", version = "v3.2.0"),
        security = @SecurityRequirement(name = "security_auth"))
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}"
                , tokenUrl = "${springdoc.oAuthFlow.tokenUrl}", scopes = {
                @OAuthScope(name = "openid", description = "Keycloak") })))
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer enableArbitraryObjects() {
        return openApi -> openApi.getComponents().getSchemas().values().forEach( s -> enableArbitraryObjects(s));
    }

    // Workaround for https://github.com/springdoc/springdoc-openapi/issues/1927 (Map<String,Object> are properly represented in schema)
    private void enableArbitraryObjects(Schema schema) {
        if (schema instanceof MapSchema) {
            if (schema.getAdditionalProperties() instanceof Schema &&
                    ((Schema) schema.getAdditionalProperties()).getType().equalsIgnoreCase("object")) {
                schema.setAdditionalProperties(true);
            }
        } else if (schema.getType() != null && schema.getType().equalsIgnoreCase("object") &&
                schema.getProperties() != null) {
            Map<String, Schema> properties = schema.getProperties();
            properties.values().forEach(s -> enableArbitraryObjects(s));
        }
    }
}

