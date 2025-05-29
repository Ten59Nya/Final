package com.example.afinal;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Data {
    private static List<Word> wordList = new ArrayList<>();
    private static int numCount = 0;
    private static int nowWordIndex = 0;

    public static void initWordList(Context context) {
        wordList.clear(); // 清空已有词表
        try {
            InputStream is = context.getResources().openRawResource(R.raw.words);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    Word word = new Word(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                    wordList.add(word);
                    count++;
                }
            }
            reader.close();
            Log.d("Data", "Loaded words count: " + count);
            if (count > 0) {
                nowWordIndex = getRandomNum(count);
            }
        } catch (Exception e) {
            Log.e("Data", "Error loading words", e);
        }
    }

    public static void setNumCount(int numCount){
        Data.numCount = numCount;
    }

    public static void setNewWord(){
        if (wordList.size() > 0) {
            nowWordIndex = getRandomNum(wordList.size());
        } else {
            nowWordIndex = 0;
        }
    }

    public static int getNumCount() {
        return numCount;
    }

    public static int getNowWordIndex() {
        return nowWordIndex;
    }

    public static String getWord(int index) {
        if (index >= 0 && index < wordList.size()) {
            return wordList.get(index).getWord();
        }
        return "";
    }

    public static String getPron(int index) {
        if (index >= 0 && index < wordList.size()) {
            return wordList.get(index).getPron();
        }
        return "";
    }

    public static String getwordDefine(int index) {
        if (index >= 0 && index < wordList.size()) {
            return wordList.get(index).getInterpret();
        }
        return "";
    }


    private static int getRandomNum(int endNum){
        if (endNum > 0){
            Random random = new Random();
            return random.nextInt(endNum);
        }
        return 0;
    }
}
