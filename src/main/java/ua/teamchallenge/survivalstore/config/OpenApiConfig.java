package ua.teamchallenge.survivalstore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;

@OpenAPIDefinition(
        info = @Info(
                title = "Survival Store Api",
                description = "OpenApi documentation for Survival Store",
                version = "1.0"
        )
)
@Configuration
public class OpenApiConfig {
        public OpenApiConfig(MappingJackson2HttpMessageConverter converter) {
                var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
                supportedMediaTypes.add(new MediaType("application", "octet-stream"));
                converter.setSupportedMediaTypes(supportedMediaTypes);
        }
}
