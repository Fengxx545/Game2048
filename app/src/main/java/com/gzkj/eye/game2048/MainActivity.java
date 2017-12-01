package com.gzkj.eye.game2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static MainActivity mainActivity = null;
    private TextView mScore;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        initView();

    }

    private void initView() {
        mScore = (TextView) findViewById(R.id.score);
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
    public void clearScore(){
        score = 0;
        showScore();
    }
    public void showScore(){
        mScore.setText(score+"");
    }
    public void addScore(int s){
        score += s;
        showScore();
    }

}
