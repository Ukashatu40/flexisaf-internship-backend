package com.flexisaf.student.config;

import com.flexisaf.student.model.Student;
import com.flexisaf.student.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(StudentRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Student("Ukashatu", "Abdullahi", "Software Engineering")));
            log.info("Preloading " + repository.save(new Student("Umar", "Musa", "Computer Science")));
            log.info("Preloading " + repository.save(new Student("Najib", "Muhammed", "Computer Science")));
            log.info("Preloading " + repository.save(new Student("John", "Blessing", "Education Science")));
            log.info("Preloading " + repository.save(new Student("Emmanuel", "Smith", "Mathematics")));
        };
    }
}