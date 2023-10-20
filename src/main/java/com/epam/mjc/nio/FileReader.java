package com.epam.mjc.nio;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class FileReader {

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();

        List<String> strings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        fillProfile(profile, strings);

        return profile;
    }

    private void fillProfile(Profile profile, List<String> strings) {
        Map<String, Consumer<String>> map = getCommands(profile);
        String[] params;
        for (String line : strings) {
            params = line.split(": ");
            map.get(params[0]).accept(params[1]);
        }
    }

    private Map<String, Consumer<String>> getCommands(Profile profile) {
        Map<String, Consumer<String>> commands = new HashMap<>();
        commands.put("Name", profile::setName);
        commands.put("Age", s -> profile.setAge(Integer.parseInt(s)));
        commands.put("Email", profile::setEmail);
        commands.put("Phone", s -> profile.setPhone(Long.parseLong(s)));
        return commands;
    }
}
