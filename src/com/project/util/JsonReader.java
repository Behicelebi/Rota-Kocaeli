package com.project.util;

import com.google.gson.Gson;
import com.project.main.Main;
import com.project.model.AnaVeri;
import com.project.model.Durak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonReader {
    private static final Logger logger = Logger.getLogger(JsonReader.class.getName());

    public JsonReader() {
        String jsonContent;
        try {
            jsonContent = new String(Files.readAllBytes(Paths.get("veriseti.json")));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file: veriseti.json", e);
            return;
        }

        Gson gson = new Gson();
        Main.anaVeri = gson.fromJson(jsonContent, AnaVeri.class);
        for(Durak durak : Main.anaVeri.getDuraklar()){
            Main.anaVeri.putDurakMap(durak);
        }
    }
}
