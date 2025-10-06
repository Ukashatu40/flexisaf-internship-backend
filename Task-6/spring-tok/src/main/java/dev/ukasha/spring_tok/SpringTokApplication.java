package dev.ukasha.spring_tok;

import dev.ukasha.spring_tok.controller.VideoController;
import dev.ukasha.spring_tok.repository.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class SpringTokApplication {

	public static void main(String[] args) {

        ConfigurableApplicationContext app = SpringApplication.run(SpringTokApplication.class, args);
        Arrays.stream(app.getBeanDefinitionNames()).forEach(System.out::println);
	}

    @Bean
    public CommandLineRunner commandLineRunner(){
        return args -> {
            System.out.println("Hello From commandline runner");
        };
    }

}
