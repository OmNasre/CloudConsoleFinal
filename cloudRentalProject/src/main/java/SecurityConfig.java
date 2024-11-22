//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf().disable() // Disable CSRF for testing; enable it in production for security
//            .authorizeHttpRequests((authz) -> authz
//                .requestMatchers("/api/**").permitAll() // Public endpoints without authentication
//                .anyRequest().authenticated() // Authenticate all other requests
//            )
//            .httpBasic(); // Use HTTP Basic Authentication
//
//        return http.build();
//    }
//}
