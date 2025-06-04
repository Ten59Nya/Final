package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        // 初始化 SharedPreferences
        sharedPref = getSharedPreferences("recite_prefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        // 初始化视图
        initializeViews();

        // 设置导航栏
        setupNavigation();

        // 显示第一个单词
        showNextWord();

        // 设置按钮点击事件
        setupButtonListeners();
    }

    private void initializeViews() {
        knowButton = findViewById(R.id.know);
        unknowButton = findViewById(R.id.dontknow);
        wordText = findViewById(R.id.word);
        ptText = findViewById(R.id.pt);
        definitionText = findViewById(R.id.definition);
    }

    private void setupNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.recite_item) {
                return true; // 已经在背诵页面
            }
            else if (itemId == R.id.failed_item) {
                startActivity(new Intent(this, WrongActivity.class));
                return true;
            }
            return false;
        });
    }

    private void setupButtonListeners() {
        knowButton.setOnClickListener(v -> showNextWord());

        unknowButton.setOnClickListener(v -> {
            int nowIndex = Data.getNowWordIndex();
            String wrongKey = "wrong_" + nowIndex + "_count";
            int count = sharedPref.getInt(wrongKey, 0);
            editor.putInt(wrongKey, count + 1);
            editor.apply();
            Log.d("ReciteActivity", "记录错误单词，索引: " + nowIndex + ", 错误次数: " + (count + 1));

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
            definitionText.setText(Data.getwordDefine(Data.getNowWordIndex()));
        } else {
            definitionText.setText("");
        }
    }
}
