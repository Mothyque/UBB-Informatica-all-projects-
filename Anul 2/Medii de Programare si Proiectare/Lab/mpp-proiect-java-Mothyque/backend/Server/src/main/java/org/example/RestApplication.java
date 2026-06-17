package org.example;

import org.example.repository.MatchRepository;
import org.example.repository.db.MatchDBRepository;
import org.example.repository.db.UserDBRepository;
import org.example.service.MatchService;
import org.example.service.UserService;
import org.example.utils.WebSocketObserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
@ComponentScan(basePackages = "org.example")
public class RestApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    public Properties dbProperties()
    {
        Properties prop = new Properties();
        try
        {
            prop.load(RestApplication.class.getClassLoader().getResourceAsStream("db.properties"));
        }
        catch (IOException e)
        {
            System.out.println("Cannot find db.properties " + e);
        }
        return prop;
    }

    @Bean
    public MatchService matchService(SimpMessagingTemplate simpMessagingTemplate)
    {
        String url = dbProperties().getProperty("jdbc.url", "jdbc:sqlite:basketball.db");
        MatchRepository matchRepository = new MatchDBRepository(url);
        WebSocketObserver wsObserver = new WebSocketObserver(simpMessagingTemplate);
        return new MatchService(matchRepository,  wsObserver);
    }

    @Bean
    public UserService userService()
    {
        String url = dbProperties().getProperty("jdbc.url", "jdbc:sqlite:basketball.db");
        UserDBRepository userRepository = new UserDBRepository(url);
        return new UserService(userRepository);
    }
}