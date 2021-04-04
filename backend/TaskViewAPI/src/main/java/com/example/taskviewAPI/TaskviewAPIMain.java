package com.example.taskviewAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@RestController
public class TaskviewAPIMain extends WebSecurityConfigurerAdapter {
    //
    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("id", principal.getAttribute("id"));
    }
// request. annab tagasi variable errori -> check why and possible fix. - HasieEST

//    @GetMapping("/error")
//    public String error() {
//        String message = (String) request.getSession().getAttribute("error.message");
//        request.getSession().removeAttribute("error.message");
//        return message;
//    }


    protected void kasKasutaja(String userid){

    }


    // Meetod, mille abil kasutaja saab sisse logida applikatsiooni läbi githubi.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .oauth2Login();
//                .oauth2Login(o -> o
//                        .failureHandler((request, response, exception) -> {
//                            request.getSession().setAttribute("error.message", exception.getMessage());
//                            handler.onAuthenticationFailure(request, response, exception);
//                        })
//                );
        // @formatter:on
    }

    //Ühendus database'iga
    public static Connection DatabaseConnectionStart() throws SQLException {
        String url = "jdbc:postgresql://oopdb.axynos.ee:5432/oopdb";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","");
        props.setProperty("ssl","false");

		return DriverManager.getConnection(url, props);
    }
    //TestQuery, kontrollimiseks kas tagastus on olemas
    public static void TestQuery(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM todoapp.users");
        while (rs.next()){
            System.out.print("Column 1 Returned ");
            System.out.println(rs.getString(1));
        }
        rs.close();
        st.close();
    }

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(TaskviewAPIMain.class, args);
        Connection dbconn = DatabaseConnectionStart();
        //TestQuery(dbconn);
    }


}