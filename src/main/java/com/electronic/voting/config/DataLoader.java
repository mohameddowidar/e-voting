package com.electronic.voting.config;

import com.electronic.voting.entities.Voter;
import com.electronic.voting.repositories.VoterRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(VoterRepository voterRepository) {
        return args -> {
            String[] arabicNames = {"علي", "أحمد", "فاطمة", "سارة", "يوسف", /* more names */ };
            Random random = new Random();
            for (int i = 0; i < 10; i++) { // Create 10 dummy voters
                Voter voter = new Voter();
                // Construct a random Arabic name
                StringBuilder nameBuilder = new StringBuilder();
                for (int j = 0; j < 4; j++) {
                    nameBuilder.append(arabicNames[random.nextInt(arabicNames.length)]);
                    if (j < 3) {
                        nameBuilder.append(" "); // add space between names
                    }
                }
                voter.setName(nameBuilder.toString());

                voter.setDateOfBirth(new Date());
                voter.setAddress("1234 Main St");
                voter.setEmail("example" + i + "@mail.com");
                voter.setPhoneNumber("123-456-" + RandomStringUtils.randomNumeric(4));

                // Generate a 16-digit national ID starting with 29
                String nationalId = "29" + RandomStringUtils.randomNumeric(14);
                voter.setNationalId(nationalId);

                voter.setAge(18 + random.nextInt(30)); // Age between 18 and 48
                // Add other fields as necessary

                voterRepository.save(voter);
            }
        };
    }
}
