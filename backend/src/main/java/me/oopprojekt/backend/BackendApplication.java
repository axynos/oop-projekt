package me.oopprojekt.backend;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

import me.oopprojekt.backend.database.DBData;
import me.oopprojekt.backend.database.User;

@SpringBootApplication
@Controller
public class BackendApplication extends WebSecurityConfigurerAdapter {

    @Bean
    public WebClient rest(ClientRegistrationRepository clients, OAuth2AuthorizedClientRepository authz) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clients, authz);
        return WebClient.builder()
                .filter(oauth2).build();
    }

//    @Bean
//    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService(WebClient rest) {
//        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
//        return request -> {
//            OAuth2User user = delegate.loadUser(request);
//            if (!"github".equals(request.getClientRegistration().getRegistrationId())) {
//                return user;
//            }
//
//            OAuth2AuthorizedClient client = new OAuth2AuthorizedClient
//                    (request.getClientRegistration(), user.getName(), request.getAccessToken());
//            String url = user.getAttribute("organizations_url");
//            List<Map<String, Object>> orgs = rest
//                    .get().uri(url)
//                    .attributes(oauth2AuthorizedClient(client))
//                    .retrieve()
//                    .bodyToMono(List.class)
//                    .block();
//
//            if (orgs.stream().anyMatch(org -> "spring-projects".equals(org.get("login")))) {
//                return user;
//            }
//
//            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Not in Spring Team", ""));
//        };
//    }

    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("id", principal.getAttribute("id"));
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        DBData db;
        User user = new User();

        try {
            db = new DBData();
            db.insertUser("Mari Maasikas", "https://example.com/example.png");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return "test";
    }

    @GetMapping("/error")
    @ResponseBody
    public String error(HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");

        // @formatter:off
        http.antMatcher("/**")
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .oauth2Login(o -> o
                        .failureHandler((request, response, exception) -> {
                            request.getSession().setAttribute("error.message", exception.getMessage());
                            handler.onAuthenticationFailure(request, response, exception);
                        })
                );
        // @formatter:on
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}