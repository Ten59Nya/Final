package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class WrongActivity extends AppCompatActivity {
    ListView wrongWordsListView;
    WordAdapter adapter;
    List<Word> wrongWordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.failed_item) {
                return true; // 已经在错题集页面
            }
            else if (itemId == R.id.recite_item) {
                startActivity(new Intent(this, ReciteActivity.class));
                return true;
            }
            return false;
        });

        // 初始化列表
        wrongWordsListView = findViewById(R.id.listView);
        wrongWordsList = new ArrayList<>();

        // 加载错误单词
        loadWrongWords();
        Log.d("WrongActivity", "错题数量 = " + wrongWordsList.size());
    }

    private void loadWrongWords() {
        // 确保单词列表已初始化
        if (Data.getWordList() == null || Data.getWordList().isEmpty()) {
            Data.initWordList(this);
        }

        List<Word> allWords = Data.getWordList();
        wrongWordsList.clear();

        SharedPreferences sharedPref = getSharedPreferences("recite_prefs", Context.MODE_PRIVATE);
        
        // 遍历所有单词，检查错误次数
        for (Word word : allWords) {
            // 获取单词在列表中的索引
            int index = allWords.indexOf(word);
            String wrongKey = "wrong_" + index + "_count";
            int wrongCount = sharedPref.getInt(wrongKey, 0);
            
            Log.d("WrongActivity", "单词: " + word.getWord() + " 索引: " + index + " 错误次数: " + wrongCount);
            
            if (wrongCount > 0) {
                word.setShowNum(wrongCount);
                wrongWordsList.add(word);
            }
        }

        // 更新列表显示
        if (adapter == null) {
            adapter = new WordAdapter(this, R.layout.word_item, wrongWordsList);
            wrongWordsListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 每次回到页面时刷新数据
        loadWrongWords();
    }
}
