package com.singha.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load(); // Loads .env file
        System.setProperty("MONGO_USER", dotenv.get("MONGO_USER"));
        System.setProperty("MONGO_PASS", dotenv.get("MONGO_PASS"));
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager  add(MongoDatabaseFactory databaseFactory){
        return new MongoTransactionManager(databaseFactory);
    }


}