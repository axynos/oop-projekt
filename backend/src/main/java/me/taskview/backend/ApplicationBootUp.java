package me.taskview.backend;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
public class ApplicationBootUp {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SocialApplication.class, args);
    }
}
