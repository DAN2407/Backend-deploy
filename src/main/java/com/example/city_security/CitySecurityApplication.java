package com.example.city_security;

import com.example.city_security.Repository.RoleRepository;
import com.example.city_security.models.entities.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.role;

@SpringBootApplication
@EnableScheduling
public class CitySecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitySecurityApplication.class, args);
    }

}
