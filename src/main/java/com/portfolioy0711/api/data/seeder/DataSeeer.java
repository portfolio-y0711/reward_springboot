package com.portfolioy0711.api.data.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioy0711.api.data.models.place.PlaceCmdRepository;
import com.portfolioy0711.api.data.models.user.UserCmdRepository;
import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataSeeer implements CommandLineRunner {
    @Autowired
    Environment env;
    @Autowired
    UserCmdRepository userRepository;
    @Autowired
    PlaceCmdRepository placeRepository;

    public void seedUsers() throws IOException {
        File resource = new ClassPathResource("/seeds/users.json").getFile();
        String json = new String(Files.readAllBytes(resource.toPath()));

        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users = objectMapper.readValue(json, new TypeReference<List<User>>(){});

        users.forEach(user -> userRepository.save(user));
    }

    public void seedPlaces() throws IOException {
        File resource = new ClassPathResource("/seeds/places.json").getFile();
        String json = new String(Files.readAllBytes(resource.toPath()));

        ObjectMapper objectMapper = new ObjectMapper();
        List<Place> places = objectMapper.readValue(json, new TypeReference<List<Place>>(){});

        places.forEach(place -> placeRepository.save(place));
    }

    @Override
    public void run(String... args) throws Exception {
            if (Arrays.stream(env.getActiveProfiles()).collect(Collectors.toList()).contains("production")) {
                seedUsers();
                seedPlaces();
            }
    }
}
