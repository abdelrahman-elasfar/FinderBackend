package com.finder.prod;
import com.finder.prod.Repositories.WordRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = WordRepository.class)
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
        System.out.println("Server Start!");
	}

}

