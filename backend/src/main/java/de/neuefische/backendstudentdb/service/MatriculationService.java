package de.neuefische.backendstudentdb.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MatriculationService {

    public String generateMatriculationNumber() {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(7);
        Random random = new Random();

        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(chars.length());
            char randomChar = chars.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
