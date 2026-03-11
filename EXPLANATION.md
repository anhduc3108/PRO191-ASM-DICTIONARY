# Dictionary System - Explanation for Oral Exam (PRO192)

This document helps you explain the code in your oral exam. Read it and practice saying it in your own words.

---

## 1. Full Project Structure

```
Dictionary/
├── pom.xml                          (Maven config, main class = Main)
├── dictionary.txt                   (created when you save; word:meaning per line)
├── EXPLANATION.md                   (this file)
└── src/main/java/com/mycompany/dictionary/
    ├── Word.java                    (model: one word + meaning)
    ├── Dictionary.java              (stores words in HashMap, add/search/update/delete)
    ├── FileManager.java             (load from file, save to file)
    └── Main.java                    (menu, user input, calls Dictionary)
```

---

## 2. How the Program Works (Flow)

1. **Program starts**  
   - `Main.main()` runs.  
   - We create a `Dictionary` and a `FileManager`.  
   - We call `fileManager.loadDictionaryFromFile()` and put the result into `dictionary` with `setWords()`.  
   - So when the program starts, we load all words from `dictionary.txt` (if the file exists).

2. **Menu loop**  
   - We show the menu (1–6) and read the user’s choice with `Scanner`.  
   - We use `switch` to call the right method:  
     - 1 → Add word  
     - 2 → Search word  
     - 3 → Update word  
     - 4 → Delete word  
     - 5 → Show all words  
     - 6 → Exit  

3. **Each action**  
   - Add: ask word and meaning, call `dictionary.addWord()`. No duplicates (same word cannot be added twice).  
   - Search: ask word, call `dictionary.searchWord()`, print meaning or "Word not found".  
   - Update: ask word, if found ask new meaning, call `dictionary.updateWord()`.  
   - Delete: ask word, call `dictionary.deleteWord()`.  
   - Show all: call `dictionary.showAllWords()` and print each word and meaning.

4. **Save after change**  
   - After **Add word** (success): we save to file.  
   - After **Update word** (success): we save to file.  
   - After **Delete word** (success): we save to file.  
   - So the file is always up to date; we do not wait until Exit.

5. **Exit (option 6)**  
   - We call `saveToFile()` once more (optional, for safety), then close the scanner and the program ends.

So: **start → load file → menu loop (add/update/delete trigger save; search and show do not) → on exit say goodbye.**

---

## 3. How HashMap Is Used in This Program

- We use **one** `HashMap<String, Word>` inside `Dictionary` to store all entries.
  - **Key** = word in lowercase (e.g. `"apple"`). We use lowercase so "Apple" and "apple" are treated as the same word.
  - **Value** = `Word` object (which has `word` and `meaning`).

- **Why HashMap?**
  - **Fast lookup by word**: to search, update, or delete we need to find the word quickly. With `words.get(key)` we get the `Word` in O(1) time instead of looping through a list.
  - **No duplicates**: `containsKey(key)` tells us if the word already exists before adding.
  - **Easy update/delete**: `put(key, value)` updates, `remove(key)` deletes.

- **How we use it:**
  - **Add:** `words.containsKey(key)` → if false, `words.put(key, new Word(...))`.
  - **Search:** `words.get(key)` → if not null, return `word.getMeaning()`.
  - **Update:** `words.get(key)` → get the `Word`, then `word.setMeaning(newMeaning)`.
  - **Delete:** `words.remove(key)`.
  - **Show all:** we return or iterate over `words.values()` to show every word and meaning.

So in one sentence: **HashMap stores each word (as key) with its Word object (as value), so we can add, search, update, and delete by word name very quickly and without duplicates.**

---

## 4. How File Persistence Works

- **File name:** `dictionary.txt` (in the project root / current directory when you run the program).

- **Format in file:**  
  One line per word:  
  `word:meaning`  
  Example:
  ```
  apple:a fruit
  book:something to read
  computer:electronic device
  ```

- **Loading (when program starts):**
  - `FileManager.loadDictionaryFromFile()` uses `BufferedReader` and `FileReader` to read the file line by line.
  - For each line we find the first `:`: text before `:` = word, text after `:` = meaning.
  - We create a `Word(word, meaning)` and put it in a `HashMap<String, Word>` with key = word in lowercase.
  - If the file does not exist or is empty, we return an empty HashMap (no crash).

- **Saving (after Add / Update / Delete, and on Exit):**
  - Each time the user adds, updates, or deletes a word, we call `saveToFile()` which uses `FileManager.saveDictionaryToFile(words)`.
  - It receives the `HashMap<String, Word>` from `dictionary.getWords()`, uses `FileWriter` to overwrite `dictionary.txt`, and writes one line per word: `word + ":" + meaning + "\n"`.
  - So the file is updated immediately; we do not wait until Exit.

So: **load = read each line, split at `:`, build HashMap; save = after every add/update/delete (and on exit), write all words to file.**

---

## 5. Short Summary for Each Class (What to Say in Exam)

- **Word**  
  A simple class with two fields: `word` and `meaning`. It has a constructor, getters, and setters. It is the “model” of one dictionary entry.

- **Dictionary**  
  Holds the `HashMap<String, Word>` and implements the operations: add (no duplicates), search, update, delete, and show all. All logic for “where is the word?” and “change/remove it” is here.

- **FileManager**  
  Only two jobs: load from `dictionary.txt` into a HashMap, and save the HashMap back to `dictionary.txt` in `word:meaning` format. No menu, no Scanner.

- **Main**  
  Entry point: load dictionary from file, show menu in a loop, read user choice with Scanner, call the right method on `Dictionary`, and on Exit call `FileManager.saveDictionaryToFile()` then close.

If you can explain the project structure, the flow (load → menu → save on exit), how HashMap is used (key = word, value = Word; add/search/update/delete), and how the file format and load/save work, you will cover the main points for the oral exam.
