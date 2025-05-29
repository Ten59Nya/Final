package com.example.afinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReciteActivity extends AppCompatActivity {
    private Button knowButton, unknowButton;

    private TextView wordText, definitionText,ptText;
    private boolean showDefinition = true; // 控制是否显示释义
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);

        knowButton = findViewById(R.id.know);
        unknowButton = findViewById(R.id.dontknow);
        wordText = findViewById(R.id.word);
        ptText = findViewById(R.id.pt);
        definitionText = findViewById(R.id.definition);

        // 初始化 SharedPreferences
        sharedPref = getSharedPreferences("recite_prefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        // 显示第一个单词
        showNextWord();

        if (Data.getNumCount() == 0) {
            Data.initWordList(this);
        }

        // 按钮点击事件
        knowButton.setOnClickListener(v -> showNextWord());

        unknowButton.setOnClickListener(v -> {
            // 记录错误单词
            int wrongNum = sharedPref.getInt("wrongNum", 0);
            wrongNum++;
            editor.putInt("wrongNum", wrongNum);
            editor.putInt("wrong_" + wrongNum, Data.getNowWordIndex());

            // 更新当前单词的错误次数
            String wrongKey = "wrong_" + Data.getNowWordIndex() + "_count";
            editor.putInt(wrongKey, sharedPref.getInt(wrongKey, 0) + 1);
            editor.apply();
            showNextWord();
        });

    }

    // 显示下一个单词
    private void showNextWord() {
        Data.setNewWord(); // 获取新单词
        updateWordCount();
        wordText.setText(Data.getWord(Data.getNowWordIndex()));
        ptText.setText(Data.getPron(Data.getNowWordIndex()));
        updateDefinitionVisibility();
    }

    // 更新单词出现次数
    private void updateWordCount() {
        String key = "word_" + Data.getNowWordIndex();
        int count = sharedPref.getInt(key, 0);
        editor.putInt(key, count + 1);
        editor.apply();
    }

    // 控制释义显示/隐藏
    private void updateDefinitionVisibility() {
        if (showDefinition) {
            definitionText.setText(Data.getwordDefine(Data.getNowWordIndex()) + "\n" +
                    Data.getwordDefine(Data.getNowWordIndex()));
        } else {
            definitionText.setText("");
        }
    }
}
