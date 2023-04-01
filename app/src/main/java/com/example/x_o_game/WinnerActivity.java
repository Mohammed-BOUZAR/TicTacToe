package com.example.x_o_game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class WinnerActivity extends Activity {
    private TextView textView;
    private Button replay;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_activity);

        String win = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            win = extras.getString("winner");
        }
        textView = findViewById(R.id.text);
        replay = findViewById(R.id.replay);

        textView.setText(win);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WinnerActivity.this, ChoiceActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
