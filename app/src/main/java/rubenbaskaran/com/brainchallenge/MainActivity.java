package rubenbaskaran.com.brainchallenge;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{

    TextView equationTextView;
    TextView scoreTextView;
    TextView counterTextView;
    int correctAnswer;
    int correctAnswerIndexValue;
    int answeredCorrectly = 0;
    int questionsAnswered = 0;
    int counter = 31;
    GridLayout gridLayout;
    Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        equationTextView = (TextView) findViewById(R.id.equationTextView);
        counterTextView = (TextView) findViewById(R.id.counterTextView);
        gridLayout = findViewById(R.id.gridLayout);

        GenerateEquation();
        UpdateScore();
        StartTimer();
    }

    public void GridlayoutSetEnabled(boolean bool)
    {
        final boolean input = bool;
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < gridLayout.getChildCount(); i++)
                {
                    gridLayout.getChildAt(i).setEnabled(input);
                }
            }
        });
    }

    public void StartTimer()
    {
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                counter--;

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        String counterText = "Time left: " + String.valueOf(counter) + " sec";
                        counterTextView.setText(counterText);
                    }
                });

                if (counter == 0 || counter < 0)
                {
                    GridlayoutSetEnabled(false);
                    timer.cancel();

                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            OpenDialog();
                        }
                    });
                }
            }
        }, 0, 1000);
    }

    public void OpenDialog()
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star)
                .setTitle("Congratulations!")
                .setMessage("You scored " + answeredCorrectly + " out of " + questionsAnswered + ". Restart game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Restart(null);
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(false)
                .show();
    }

    public void GenerateEquation()
    {
        Random random = new Random();
        int number1 = random.nextInt(20);
        int number2 = random.nextInt(20);

        String equation = String.format(String.valueOf(number1) + " + " + String.valueOf(number2));
        equationTextView.setText(equation);
        correctAnswer = number1 + number2;

        GenerateAnswers();
    }

    private void GenerateAnswers()
    {
        Random random = new Random();
        String[] answers = new String[4];

        correctAnswerIndexValue = random.nextInt(3);
        answers[correctAnswerIndexValue] = String.valueOf(correctAnswer);

        for (int i = 0; i < 4; i++)
        {
            if (answers[i] == null)
            {
                String randomNumber = String.valueOf(random.nextInt(20));
                while (randomNumber.equals(answers[0]) || randomNumber.equals(answers[1]) || randomNumber.equals(answers[2]) || randomNumber.equals(answers[3]))
                {
                    randomNumber = String.valueOf(random.nextInt(20));
                }
                answers[i] = randomNumber;
                Button button = gridLayout.findViewWithTag(String.valueOf(i));
                button.setText(String.valueOf(answers[i]));
            }
            else if (answers[i] != null)
            {
                Button button = gridLayout.findViewWithTag(String.valueOf(i));
                button.setText(String.valueOf(answers[correctAnswerIndexValue]));
            }
        }
    }

    public void Restart(View view)
    {
        answeredCorrectly = 0;
        questionsAnswered = 0;
        counter = 31;
        GenerateEquation();
        UpdateScore();
        GridlayoutSetEnabled(true);

        if (timer != null)
        {
            timer.cancel();
        }

        StartTimer();
    }

    public void AnswerSelected(View view)
    {
        Button button = (Button) view;
        if (Integer.parseInt(button.getTag().toString()) == correctAnswerIndexValue)
        {
            answeredCorrectly++;
        }

        questionsAnswered++;
        UpdateScore();
        GenerateEquation();
    }

    public void UpdateScore()
    {
        String score = answeredCorrectly + "/" + questionsAnswered;
        scoreTextView.setText(score);
    }
}
