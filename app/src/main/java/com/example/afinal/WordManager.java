package com.example.afinal;

import android.content.Context;

import com.example.afinal.R;
import com.example.afinal.Word;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WordManager {
    private static WordManager instance;
    private List<Word> allWords;
    private List<Word> failedWords;
    private Random random;

    private WordManager() {
        allWords = new ArrayList<>();
        failedWords = new ArrayList<>();
        random = new Random();
    }

    public static WordManager getInstance() {
        if (instance == null) {
            instance = new WordManager();
        }
        return instance;
    }

    public void loadWords(Context context) {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.words);//读取words
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    Word word = new Word(
                            parts[0],
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4])
                    );
                    allWords.add(word);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Word getRandomWord() {
        if (allWords.isEmpty()) return null;
        return allWords.get(random.nextInt(allWords.size()));
    }

    public List<String> getRandomDefinitions(Word correctWord, int count) {
        List<String> definitions = new ArrayList<>();
        definitions.add(correctWord.getInterpret());

        List<Word> tempWords = new ArrayList<>(allWords);
        tempWords.remove(correctWord);
        Collections.shuffle(tempWords);

        for (int i = 0; i < Math.min(count - 1, tempWords.size()); i++) {
            definitions.add(tempWords.get(i).getInterpret());
        }

        Collections.shuffle(definitions);
        return definitions;
    }

    public void addToFailedWords(Word word) {
        if (!failedWords.contains(word)) {
            failedWords.add(word);
            word.setErrorNum(word.getShowNum() + 1);
        }
    }

    public List<Word> getFailedWords() {
        return failedWords;
    }

    public List<Word> getAllWords() {
        return allWords;
    }
}
