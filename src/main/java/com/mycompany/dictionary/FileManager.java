package com.mycompany.dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FileManager {

    private static final String FILE_NAME = "dictionary.txt";

    public HashMap<String, Word> loadDictionaryFromFile() {
        HashMap<String, Word> loaded = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(FILE_NAME), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                int colonIndex = line.indexOf(':');
                if (colonIndex <= 0) continue;
                String word = line.substring(0, colonIndex).trim();
                String meaning = line.substring(colonIndex + 1).trim();
                if (!word.isEmpty()) {
                    loaded.put(word.toLowerCase(), new Word(word, meaning));
                }
            }
        } catch (IOException e) {
        }
        return loaded;
    }

    public void saveDictionaryToFile(HashMap<String, Word> words) throws IOException {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_NAME), StandardCharsets.UTF_8))) {
            for (Word w : words.values()) {
                String line = w.getWord() + ":" + w.getMeaning() + "\n";
                writer.write(line);
            }
        }
    }
}
