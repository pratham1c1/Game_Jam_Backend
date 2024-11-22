package PCGamesGroup.PCGamesBackend.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for all endpoints with the specified origins
        registry.addMapping("/**") // Match all paths
                .allowedOrigins("http://localhost:5173") // Allow requests from React app
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Allowed HTTP methods
    }
}