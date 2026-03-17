package com.mycompany.dictionary;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    private HashMap<String, Word> words;

    public Dictionary() {
        words = new HashMap<>();
    }

    public int getWordCount() {
        return words.size();
    }

    public boolean addWord(String word, String meaning) {
        String key = word.trim().toLowerCase();
        if (key.isEmpty()) {
            return false;
        }
        if (words.containsKey(key)) {
            return false;
        }
        words.put(key, new Word(word.trim(), meaning.trim()));
        return true;
    }

    public Map<String, Word> searchWordsByPrefix(String prefix) {
        Map<String, Word> result = new HashMap<>();
        String lowerPrefix = prefix.trim().toLowerCase();
        if (lowerPrefix.isEmpty()) {
            return result;
        }
        for (Map.Entry<String, Word> entry : words.entrySet()) {
            if (entry.getKey().startsWith(lowerPrefix)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    public String searchWord(String word) {
        String key = word.trim().toLowerCase();
        Word w = words.get(key);
        if (w == null) {
            return null;
        }
        return w.getMeaning();
    }

    public boolean updateWord(String word, String newMeaning) {
        String key = word.trim().toLowerCase();
        if (!words.containsKey(key)) {
            return false;
        }
        Word w = words.get(key);
        w.setMeaning(newMeaning.trim());
        return true;
    }

    public boolean deleteWord(String word) {
        String key = word.trim().toLowerCase();
        if (words.remove(key) != null) {
            return true;
        }
        return false;
    }

    public Map<String, Word> showAllWords() {
        return new HashMap<>(words);
    }

    public HashMap<String, Word> getWords() {
        return words;
    }

    public void setWords(HashMap<String, Word> words) {
        this.words = words;
    }
}
