# Dictionary System (PRO192 - Java Console)

Simple Java console Dictionary program for OOP practice (PRO192).

## Features

- Add word (no duplicates)
- Search word
- Search words by prefix
- Update word
- Delete word
- Show all words
- Auto load/save data with `dictionary.txt` (UTF-8)
  - Save happens immediately after Add/Update/Delete (not only on Exit)

## Project Structure

```
Dictionary/
├── pom.xml
├── dictionary.txt
├── EXPLANATION.md
└── src/main/java/com/mycompany/dictionary/
    ├── Main.java
    ├── Dictionary.java
    ├── Word.java
    └── FileManager.java
```

## How to Run

### Run with Maven

```bash
mvn compile exec:java
```

### Run in IDE

Run the main class:

- `com.mycompany.dictionary.Main`

## Data File (Persistence)

- File name: `dictionary.txt`
- Encoding: **UTF-8**
- Format: one entry per line:

```
word:meaning
```

Example:

```
apple:a fruit
book:something to read
học sinh:student
```

## Notes

- The program uses `HashMap<String, Word>`:
  - Key = word in lowercase
  - Value = `Word` object (word + meaning)
- If your old `dictionary.txt` was saved with wrong encoding and Vietnamese is already broken, delete the file and let the program generate a new UTF-8 file.

