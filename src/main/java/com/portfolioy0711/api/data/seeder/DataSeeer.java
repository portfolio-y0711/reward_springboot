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
    private final Environment env;
    private final UserCmdRepository userRepository;
    private final PlaceCmdRepository placeRepository;

    public void seedUsers() throws IOException {
        final File resource = new ClassPathResource("/seeds/users.json").getFile();
        final String json = new String(Files.readAllBytes(resource.toPath()));

        final ObjectMapper objectMapper = new ObjectMapper();
        final List<User> users = objectMapper.readValue(json, new TypeReference<List<User>>(){});

        users.forEach(user -> userRepository.save(user));
    }

    public void seedPlaces() throws IOException {
        final File resource = new ClassPathResource("/seeds/places.json").getFile();
        final String json = new String(Files.readAllBytes(resource.toPath()));

        final ObjectMapper objectMapper = new ObjectMapper();
        final List<Place> places = objectMapper.readValue(json, new TypeReference<List<Place>>(){});

        places.forEach(place -> placeRepository.save(place));
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Arrays.toString(env.getActiveProfiles()));

        final boolean isProduction = Arrays.stream(env.getActiveProfiles()).collect(Collectors.toList()).contains("production") || Arrays.stream(args).collect(Collectors.toList()).contains("production");
        if (isProduction) {
            seedUsers();
            seedPlaces();
        }
    }
}
