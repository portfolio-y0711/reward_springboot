package com.portfolioy0711.api.data.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolioy0711.api.data.models.place.PlaceCmdRepository;
import com.portfolioy0711.api.data.models.user.UserCmdRepository;
import com.portfolioy0711.api.data.entities.Place;
import com.portfolioy0711.api.data.entities.User;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DataSeeer implements CommandLineRunner, ApplicationRunner {
    private final Environment env;
    private final UserCmdRepository userRepository;
    private final PlaceCmdRepository placeRepository;

    public void seedUsers() throws IOException {
//        final File resource = new ClassPathResource("/seeds/users.json").getFile();
        final ClassPathResource classPathResource = new ClassPathResource("/seeds/users.json");
        final InputStreamReader isr = new InputStreamReader(classPathResource.getInputStream());
        final Stream<String> streamOfString = new BufferedReader(isr).lines();
        final String json = streamOfString.collect(Collectors.joining());
//        final String json = new String(Files.readAllBytes(resource.toPath()));

        final ObjectMapper objectMapper = new ObjectMapper();
        final List<User> users = objectMapper.readValue(json, new TypeReference<List<User>>(){});

        users.forEach(user -> userRepository.save(user));
    }

    public void seedPlaces() throws IOException {
//        final File resource = new ClassPathResource("/seeds/places.json").getFile();
        final ClassPathResource classPathResource = new ClassPathResource("/seeds/places.json");
        final InputStreamReader isr = new InputStreamReader(classPathResource.getInputStream());
        final Stream<String> streamOfString = new BufferedReader(isr).lines();
        final String json = streamOfString.collect(Collectors.joining());

//        final String json = new String(Files.readAllBytes(resource.toPath()));

        final ObjectMapper objectMapper = new ObjectMapper();
        final List<Place> places = objectMapper.readValue(json, new TypeReference<List<Place>>(){});

        places.forEach(place -> placeRepository.save(place));
    }

    @Override
    public void run(String... args) throws Exception {
        final boolean isProduction = Arrays.stream(env.getActiveProfiles()).collect(Collectors.toList()).contains("production");
        if (isProduction) {
            seedUsers();
            seedPlaces();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        final boolean isProduction = args.getNonOptionArgs().contains("production");
        if (isProduction) {
            seedUsers();
            seedPlaces();
        }
    }
}
