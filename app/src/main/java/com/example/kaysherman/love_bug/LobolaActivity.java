package com.example.kaysherman.love_bug;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LobolaActivity extends AppCompatActivity {

    TextView TotalLobola, Fmale, male;
    ImageView life_stock;
    RadioGroup radioGroup;
    RadioButton rbCow, rbchicken, rbMoney;
    int numCow = 0;
    String Female,Male;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobola);

        TotalLobola = (TextView) findViewById(R.id.tv_TotalLobola);
        Fmale = (TextView) findViewById(R.id.textFemale);
        male = (TextView) findViewById(R.id.textMale);
        life_stock = (ImageView) findViewById(R.id.img);
        radioGroup = (RadioGroup) findViewById(R.id.rGroup);

        rbCow = (RadioButton) findViewById(R.id.R1);
        rbchicken = (RadioButton) findViewById(R.id.R2);
        rbMoney = (RadioButton) findViewById(R.id.R3);

        final int Score = Integer.parseInt(getIntent().getExtras().getString("Score"));
        int lobolaBase = 50;
        numCow = (Score * lobolaBase) / 100;
        Female = getIntent().getExtras().getString("Female");
        Male = getIntent().getExtras().getString("Male");
        Fmale.setText(Female);
        male.setText(Male);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.R1) {

                    life_stock.setImageResource(R.drawable.downloadfile_1);
                    TotalLobola.setText("You need " + numCow + " live Cow(s) for Lobola ");
                    mediaPlayer = new MediaPlayer().create(LobolaActivity.this,R.raw.cow_mooing);
                    mediaPlayer.start();
                }
                if (checkedId == R.id.R2) {
                    int baseChicken = 7;
                    int TotalNumChicken = numCow * baseChicken;
                    life_stock.setImageResource(R.drawable.downloadfile_3);
                    TotalLobola.setText("You need " + TotalNumChicken + " Inner peace Chicken(s) for Lobola ");
                    mediaPlayer = new MediaPlayer().create(LobolaActivity.this,R.raw.rooster);
                    mediaPlayer.start();

                }
                if (checkedId == R.id.R3) {

                    int BaseRands = 7000;
                    int TotalRands = numCow * BaseRands;
                    TotalLobola.setText("You need R" + TotalRands + "  for Lobola ");
                    life_stock.setImageResource(R.drawable.wallet);
                    mediaPlayer = new MediaPlayer().create(LobolaActivity.this,R.raw.cha_ching_sound);
                    mediaPlayer.start();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
