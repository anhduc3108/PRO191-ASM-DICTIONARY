package com.mycompany.dictionary;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        FileManager fileManager = new FileManager();
        Scanner scanner = new Scanner(System.in);

        dictionary.setWords(fileManager.loadDictionaryFromFile());
        System.out.println("Dictionary loaded from file.");

        while (true) {
            printMenu();
            int choice = getIntInput(scanner, "Your choice: ");
            if (choice < 1 || choice > 7) {
                System.out.println("Invalid choice. Please enter 1-7.");
                continue;
            }

            switch (choice) {
                case 1:
                    doAddWord(dictionary, fileManager, scanner);
                    break;
                case 2:
                    doSearchWord(dictionary, scanner);
                    break;
                case 3:
                    doSearchWordsByPrefix(dictionary, scanner);
                    break;
                case 4:
                    doUpdateWord(dictionary, fileManager, scanner);
                    break;
                case 5:
                    doDeleteWord(dictionary, fileManager, scanner);
                    break;
                case 6:
                    doShowAllWords(dictionary);
                    break;
                case 7:
                    doExit(dictionary, fileManager, scanner);
                    return;
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n========== DICTIONARY SYSTEM ==========");
        System.out.println("1. Add word");
        System.out.println("2. Search word");
        System.out.println("3. Search words by prefix");
        System.out.println("4. Update word");
        System.out.println("5. Delete word");
        System.out.println("6. Show all words");
        System.out.println("7. Exit");
        System.out.println("=======================================");
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void doAddWord(Dictionary dictionary, FileManager fileManager, Scanner scanner) {
        System.out.print("Enter word: ");
        String word = scanner.nextLine().trim();
        if (word.isEmpty()) {
            System.out.println("Word cannot be empty.");
            return;
        }
        System.out.print("Enter meaning: ");
        String meaning = scanner.nextLine().trim();
        if (meaning.isEmpty()) {
            System.out.println("Meaning cannot be empty.");
            return;
        }
        if (dictionary.addWord(word, meaning)) {
            saveToFile(dictionary, fileManager);
            System.out.println("Word added successfully.");
        } else {
            System.out.println("Word already exists. Cannot add duplicate.");
        }
    }

    private static void doSearchWord(Dictionary dictionary, Scanner scanner) {
        System.out.print("Enter word to search: ");
        String word = scanner.nextLine().trim();
        if (word.isEmpty()) {
            System.out.println("Please enter a word to search.");
            return;
        }
        String meaning = dictionary.searchWord(word);
        if (meaning != null) {
            System.out.println("Meaning: " + meaning);
        } else {
            System.out.println("Word not found.");
        }
    }

    private static void doSearchWordsByPrefix(Dictionary dictionary, Scanner scanner) {
        System.out.print("Enter prefix to search: ");
        String prefix = scanner.nextLine().trim();
        if (prefix.isEmpty()) {
            System.out.println("Please enter a prefix.");
            return;
        }
        Map<String, Word> found = dictionary.searchWordsByPrefix(prefix);
        if (found.isEmpty()) {
            System.out.println("No words found with that prefix.");
        } else {
            System.out.println("\n--- Words starting with \"" + prefix + "\" ---");
            for (Word w : found.values()) {
                System.out.println(w.getWord() + ": " + w.getMeaning());
            }
        }
    }

    private static void doUpdateWord(Dictionary dictionary, FileManager fileManager, Scanner scanner) {
        System.out.print("Enter word to update: ");
        String word = scanner.nextLine().trim();
        if (word.isEmpty()) {
            System.out.println("Please enter a word to update.");
            return;
        }
        String meaning = dictionary.searchWord(word);
        if (meaning == null) {
            System.out.println("Word not found.");
            return;
        }
        System.out.println("Current meaning: " + meaning);
        System.out.print("Enter new meaning: ");
        String newMeaning = scanner.nextLine().trim();
        if (newMeaning.isEmpty()) {
            System.out.println("Meaning cannot be empty.");
            return;
        }
        if (dictionary.updateWord(word, newMeaning)) {
            saveToFile(dictionary, fileManager);
            System.out.println("Word updated successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }

    private static void doDeleteWord(Dictionary dictionary, FileManager fileManager, Scanner scanner) {
        System.out.print("Enter word to delete: ");
        String word = scanner.nextLine().trim();
        if (word.isEmpty()) {
            System.out.println("Please enter a word to delete.");
            return;
        }
        if (dictionary.deleteWord(word)) {
            saveToFile(dictionary, fileManager);
            System.out.println("Word deleted successfully.");
        } else {
            System.out.println("Word not found.");
        }
    }

    private static void doShowAllWords(Dictionary dictionary) {
        Map<String, Word> all = dictionary.showAllWords();
        if (all.isEmpty()) {
            System.out.println("Dictionary is empty.");
            return;
        }
        System.out.println("\n--- All words ---");
        for (Word w : all.values()) {
            System.out.println(w.getWord() + ": " + w.getMeaning());
        }
    }

    private static void saveToFile(Dictionary dictionary, FileManager fileManager) {
        try {
            fileManager.saveDictionaryToFile(dictionary.getWords());
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void doExit(Dictionary dictionary, FileManager fileManager, Scanner scanner) {
        saveToFile(dictionary, fileManager);
        scanner.close();
        System.out.println("Goodbye!");
    }
}
