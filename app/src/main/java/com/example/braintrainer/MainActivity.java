package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText verdict;
    EditText expression;
    EditText timerView;
    EditText roundNumView;
    TextView finalScore;
    Button startButton;
    CountDownTimer countDownTimer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
   int ansLocation;
   int roundNumber;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    ImageView confetti;
    int points;
    public void set(String s, int points, boolean toShow)
    {
        if(toShow)
        {
            finalScore.setText("Final Score: " + points);
            finalScore.setVisibility(View.VISIBLE);
        }
         verdict.setVisibility(View.GONE);
         expression.setVisibility(View.GONE);
         timerView.setVisibility(View.GONE);
         roundNumView.setVisibility(View.GONE);
         button0.setVisibility(View.GONE);
         button1.setVisibility(View.GONE);
         button2.setVisibility(View.GONE);
         button3.setVisibility(View.GONE);
         startButton.setText(s);
         startButton.setVisibility(View.VISIBLE);
         ansLocation = 0;
         roundNumber = 0;
    }
   public void round()
   {
       verdict.setVisibility(View.GONE);
       roundNumber++;
       if(roundNumber<=4)
       {
           roundNumView.setText(roundNumber + "/4");
           Random random = new Random();
           int n1 = random.nextInt(15);
           int n2 = random.nextInt(15);
           expression.setText(n1 + " + " + n2);

           ansLocation = random.nextInt(4);
           for(int i=0;i<4;i++)
           {
               if(i == ansLocation)
                   answers.add(n1+n2);
               else
               {
                   int wrongAns = random.nextInt(30);
                   while (wrongAns == (n1+n2))
                       wrongAns = random.nextInt(30);
                   answers.add(wrongAns);
               }
           }
           button0.setText(Integer.toString(answers.get(0)));
           button1.setText(Integer.toString(answers.get(1)));
           button2.setText(Integer.toString(answers.get(2)));
           button3.setText(Integer.toString(answers.get(3)));
       }
       else
       {
           countDownTimer.cancel();
           confetti.setVisibility(View.VISIBLE);
           set("You Won, Play Again!",points,true);
       }


   }
   public void answerClick(View view)
   {
       String buttonTag = view.getTag().toString();
       String roundVerdict = "";
       if(Integer.parseInt(buttonTag) == ansLocation)
       {
           roundVerdict = "Correct!";
           points++;
           answers.clear();
           round();
       }

       else
       {
           roundVerdict = "Wrong:(";
           countDownTimer.cancel();
           set("Restart",points,true);
       }
       Toast.makeText(getApplicationContext(),roundVerdict,Toast.LENGTH_SHORT).show();
   }
    public void start(View view)
    {
        roundNumber = 0;
        points = 0;
        verdict.setVisibility(View.VISIBLE);
        expression.setVisibility(View.VISIBLE);
        timerView.setVisibility(View.VISIBLE);
        roundNumView.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int totalSeconds = (int) millisUntilFinished/1000;
                int minutes = totalSeconds/60;
                int seconds = totalSeconds%60;
                String time = String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
                timerView.setText(time);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Time's up!",Toast.LENGTH_SHORT).show();
                set("Restart!",points,true);
            }
        }.start();
        round();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verdict = (EditText) findViewById(R.id.verdict);
        verdict.setVisibility(View.INVISIBLE);
        timerView = (EditText) findViewById(R.id.timerView);
        expression = (EditText) findViewById(R.id.expression);
        roundNumView = (EditText) findViewById(R.id.roundNumView);
        points = 0;
        finalScore = (TextView) findViewById(R.id.finalScore);
        startButton = (Button) findViewById(R.id.startButton);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        confetti = (ImageView) findViewById(R.id.confetti);
        set("Go!",points,false);

    }
}