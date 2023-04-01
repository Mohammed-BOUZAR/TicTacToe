package com.example.x_o_game;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[][] game;
    private ImageView[][] btns;
    private Boolean player1;
    private Boolean player2;
    private Boolean robot;
    private ImageView button_00;
    private ImageView button_01;
    private ImageView button_02;
    
    private ImageView button_10;
    private ImageView button_11;
    private ImageView button_12;

    private ImageView button_20;
    private ImageView button_21;
    private ImageView button_22;
    private Button replay;

    public MainActivity(){
        game = new int[3][3];
        btns = new ImageView[3][3];
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        int type = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getInt("type");
        }

        player1 = true;
        if(type == 1) robot = false;
        else player2 = false;

        game[0][0] = 0;
        game[0][1] = 0;
        game[0][2] = 0;

        game[1][0] = 0;
        game[1][1] = 0;
        game[1][2] = 0;

        game[2][0] = 0;
        game[2][1] = 0;
        game[2][2] = 0;

        btns[0][0] = findViewById(R.id.button_00);
        btns[0][1] = findViewById(R.id.button_01);
        btns[0][2] = findViewById(R.id.button_02);

        btns[1][0] = findViewById(R.id.button_10);
        btns[1][1] = findViewById(R.id.button_11);
        btns[1][2] = findViewById(R.id.button_12);

        btns[2][0] = findViewById(R.id.button_20);
        btns[2][1] = findViewById(R.id.button_21);
        btns[2][2] = findViewById(R.id.button_22);

        btns[0][0].setOnClickListener(setImageAction(btns[0][0],0,0));
        btns[0][1].setOnClickListener(setImageAction(btns[0][1],0,1));
        btns[0][2].setOnClickListener(setImageAction(btns[0][2],0,2));
        btns[1][0].setOnClickListener(setImageAction(btns[1][0],1,0));
        btns[1][1].setOnClickListener(setImageAction(btns[1][1],1,1));
        btns[1][2].setOnClickListener(setImageAction(btns[1][2],1,2));
        btns[2][0].setOnClickListener(setImageAction(btns[2][0],2,0));
        btns[2][1].setOnClickListener(setImageAction(btns[2][1],2,1));
        btns[2][2].setOnClickListener(setImageAction(btns[2][2],2,2));

        replay = findViewById(R.id.replay_main);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private View.OnClickListener setImageAction(ImageView image, int i, int j){
        return (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player1 == true && game[i][j] == 0){
                    image.setImageResource(R.drawable.x);
                    game[i][j] = 1;
                    if (isWin(1)) {
                        Intent intent = new Intent(MainActivity.this, WinnerActivity.class);
                        intent.putExtra("winner", "Player 1 won!");
                        startActivity(intent);
                        finish();
                        return;
                    }
                    if (isFinish())
                        Toast.makeText(MainActivity.this, "New Game", Toast.LENGTH_SHORT).show();

                    player1 = false;
                    if (player2 != null) player2 = true;
                    else{
                        if (isFinish())
                            Toast.makeText(MainActivity.this, "New Game", Toast.LENGTH_SHORT).show();
                        Random random = new Random();
                        int i2 = random.nextInt(3);
                        int j2 = random.nextInt(3);
                        while ((i == i2 && j == j2) || (game[i2][j2] != 0)){
                            i2 = random.nextInt(3);
                            j2 = random.nextInt(3);
                        }
                        player1 = true;
                        game[i2][j2] = 2;
                        btns[i2][j2].setImageResource(R.drawable.o);
                        if (isWin(2)) {
                            Intent intent = new Intent(MainActivity.this, WinnerActivity.class);
                            intent.putExtra("winner", "Robot won!");
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                }
                else{
                    if (player2 == true && game[i][j] == 0){
                        image.setImageResource(R.drawable.o);
                        game[i][j] = 2;
                        player1 = true;
                        player2 = false;
                        if (isWin(2)) {
                            Intent intent = new Intent(MainActivity.this, WinnerActivity.class);
                            intent.putExtra("winner", "Player 2 won!");
                            startActivity(intent);
                            finish();
                            return;
                        }
                        if (isFinish())
                            Toast.makeText(MainActivity.this, "New Game", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private Boolean isWin(int k){
        Boolean win = true;
        String numbers = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                numbers += String.valueOf(game[i][j]);
            }
        }
        Log.i("bool", numbers);
        if(!(game[0][0] == k && game[1][1] == k && game[2][2] == k)) win = false;
        else win = true;
        if (win) return true;

        if(!(game[0][2] == k && game[1][1] == k && game[2][0] == k)) win = false;
        else win = true;
        if (win) return true;

        if(!(game[0][0] == k && game[1][0] == k && game[2][0] == k)) win = false;
        else win = true;
        if (win) return true;

        if(!(game[0][1] == k && game[1][1] == k && game[2][1] == k)) win = false;
        else win = true;
        if (win) return true;

        if(!(game[0][2] == k && game[1][2] == k && game[2][2] == k)) win = false;
        else win = true;
        if (win) return true;

        if(!(game[0][0] == k && game[0][1] == k && game[0][2] == k)) win = false;
        else win = true;
        if (win) return true;

        if(!(game[1][0] == k && game[1][1] == k && game[1][2] == k)) win = false;
        else win = true;
        if (win) return true;

        if(!(game[2][0] == k && game[2][1] == k && game[2][2] == k)) win = false;
        else win = true;
        if (win) return true;
        return win;
    }
    public boolean isFinish(){
        int cpt = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game[i][j] != 0)
                    cpt++;
            }
        }
        if (cpt == 8 || cpt == 9){
            Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}
