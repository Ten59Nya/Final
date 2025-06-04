package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 初始化 SharedPreferences
        sharedPref = getSharedPreferences("recite_prefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        // 清除之前的错题数据
        clearWrongRecords();

        // 初始化词库
        Data.initWordList(this);

        // 设置开始按钮
        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(v -> {
            // 跳转到背诵页面
            startActivity(new Intent(MainActivity.this, ReciteActivity.class));
        });
    }

    private void clearWrongRecords() {
        // 清除所有保存的错题记录
        editor.clear();
        editor.apply();
        Log.d("MainActivity", "已清除所有错题记录");
    }
}
