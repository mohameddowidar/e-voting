package com.electronic.voting.config;

import com.electronic.voting.entities.Voter;
import com.electronic.voting.repositories.VoterRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(VoterRepository voterRepository) {
        return args -> {
            /*for (int i = 0; i < 10; i++) { // Create 10 dummy voters
                Voter voter = new Voter();
                voter.setName("Voter " + RandomStringUtils.randomAlphabetic(5));
                voter.setDateOfBirth(new Date());
                voter.setAddress("1234 Main St");
                voter.setEmail("example" + i + "@mail.com");
                voter.setPhoneNumber("123-456-" + RandomStringUtils.randomNumeric(4));
                voter.setNationalId(RandomStringUtils.randomNumeric(10));
                voter.setAge(18 + (int) (Math.random() * 30));
                // Add other fields as necessary

                voterRepository.save(voter);
            }*/
        };
    }
}
