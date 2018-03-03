package rubenbaskaran.com.brainspeedchallenge.Games;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.LocalDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.Highscores.HighscoreActivity;
import rubenbaskaran.com.brainspeedchallenge.Models.Score;
import rubenbaskaran.com.brainspeedchallenge.R;

public class NumbersGameActivity extends AppCompatActivity
{
    //region Fields
    TextView equationTextView;
    TextView scoreTextView;
    TextView counterTextView;
    int correctAnswer;
    int correctAnswerIndexValue;
    int answeredCorrectly = 0;
    int questionsAnswered = 0;
    int counter = 16;
    GridLayout gridLayout;
    Timer timer = null;
    GameTypes gameType;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button restartButton;
    ImageView soundToggleImageView;
    SharedPreferences sharedPreferences;
    boolean SoundOn = true;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_game);

        gameType = (GameTypes) getIntent().getSerializableExtra("gametype");

        scoreTextView = findViewById(R.id.scoreTextView);
        equationTextView = findViewById(R.id.equationTextView);
        counterTextView = findViewById(R.id.counterTextView);
        gridLayout = findViewById(R.id.gridLayout);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        restartButton = findViewById(R.id.restartButton);
        soundToggleImageView = findViewById(R.id.soundToggleImageView);

        sharedPreferences = getSharedPreferences("rubenbaskaran.com.brainspeedchallenge", MODE_PRIVATE);
        SoundOn = sharedPreferences.getBoolean("sound", true);

        if (SoundOn)
        {
            soundToggleImageView.setImageResource(R.drawable.unmute);
            soundToggleImageView.setTag(R.drawable.unmute);
        }
        else
        {
            soundToggleImageView.setImageResource(R.drawable.mute);
            soundToggleImageView.setTag(R.drawable.mute);
        }

        new AlertDialog.Builder(this)
                .setTitle("Welcome!")
                .setMessage("Solve as many equations as possible within 15 seconds. Are you ready?")
                .setIcon(android.R.drawable.btn_star)
                .setCancelable(false)
                .setPositiveButton("Start game!", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        StartGame();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        finish();
                    }
                })
                .show();
    }

    public void StartGame()
    {
        EnableButtons();
        GenerateEquation();
        UpdateScore();
        StartTimer();
    }

    private void EnableButtons()
    {
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        restartButton.setEnabled(true);
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
                        String counterText = "Time left: " + String.valueOf(counter);
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
                .setTitle(SetResultComment())
                .setMessage("You scored " + answeredCorrectly + " out of " + questionsAnswered)
                .setPositiveButton("Finish", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        SaveScore();
                    }
                })
                .setNegativeButton("Restart level", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Restart(null);
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void SaveScore()
    {
        Score score = new Score();

        switch (gameType)
        {
            case Addition:
                score.setGameType(GameTypes.Addition);
                break;
            case Subtraction:
                score.setGameType(GameTypes.Subtraction);
                break;
            case Multiplication:
                score.setGameType(GameTypes.Multiplication);
                break;
            case Division:
                score.setGameType(GameTypes.Division);
                break;
        }
        score.setAnswered(questionsAnswered);
        score.setAnsweredCorrectly(answeredCorrectly);
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        decimalFormat.setMaximumFractionDigits(0);
        score.setPercentage(questionsAnswered == 0 ? 0 : Integer.parseInt(decimalFormat.format((((double) answeredCorrectly) / questionsAnswered) * 100)));

        LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(getApplicationContext());
        boolean navigateToHighscoreActivityNow = localDatabaseManager.CheckNewScore(score, getFragmentManager(), this);

        if (navigateToHighscoreActivityNow)
        {
            finish();
            Intent i = new Intent(getApplicationContext(), HighscoreActivity.class);
            i.putExtra("gametype", score.getGameType());
            startActivity(i);
        }
    }

    public String SetResultComment()
    {
        if (answeredCorrectly == questionsAnswered && answeredCorrectly != 0)
        {
            return "Perfect!";
        }
        else if (answeredCorrectly == 0)
        {
            return "Oooh!";
        }
        else if (answeredCorrectly > questionsAnswered / 2)
        {
            return "Awesome!";
        }
        else if (answeredCorrectly == questionsAnswered / 2 || answeredCorrectly < questionsAnswered / 2)
        {
            return "Too bad!";
        }

        return "Congratulations";
    }

    public void GenerateEquation()
    {
        Random random = new Random();
        int number1;
        int number2;
        String equation = "Equation is missing";

        switch (gameType)
        {
            case Addition:
                number1 = random.nextInt(50);
                number2 = random.nextInt(50);
                equation = String.format(String.valueOf(number1) + " + " + String.valueOf(number2));
                correctAnswer = number1 + number2;
                break;
            case Subtraction:
                number1 = random.nextInt(100);
                number2 = random.nextInt(100);
                while (number2 > number1)
                {
                    number2 = random.nextInt(100);
                }
                equation = String.format(String.valueOf(number1) + " - " + String.valueOf(number2));
                correctAnswer = number1 - number2;
                break;
            case Multiplication:
                number1 = random.nextInt(10);
                number2 = random.nextInt(10);
                equation = String.format(String.valueOf(number1) + " X " + String.valueOf(number2));
                correctAnswer = number1 * number2;
                break;
            case Division:
                number1 = random.nextInt(10);
                number2 = random.nextInt(10);
                while (number2 == 0)
                {
                    number2 = random.nextInt(10);
                }
                equation = String.format(String.valueOf(number1 * number2) + " / " + String.valueOf(number2));
                correctAnswer = number1;
                break;
        }

        equationTextView.setText(equation);
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
                String randomNumber = String.valueOf(random.nextInt(100));
                while (randomNumber.equals(answers[0]) || randomNumber.equals(answers[1]) || randomNumber.equals(answers[2]) || randomNumber.equals(answers[3]))
                {
                    randomNumber = String.valueOf(random.nextInt(100));
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
        counter = 16;
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

            if (SoundOn)
            {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    public void onCompletion(MediaPlayer mp)
                    {
                        mp.release();
                    }
                });
            }
        }
        else if (SoundOn && Integer.parseInt(button.getTag().toString()) != correctAnswerIndexValue)
        {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                public void onCompletion(MediaPlayer mp)
                {
                    mp.release();
                }
            });
        }

        questionsAnswered++;
        UpdateScore();
        if (counter != 0 && !(counter < 0))
        {
            GenerateEquation();
        }
    }

    public void UpdateScore()
    {
        String score = answeredCorrectly + "/" + questionsAnswered;
        scoreTextView.setText(score);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (timer != null)
        {
            timer.cancel();
        }
    }

    public void SoundToggle(View view)
    {
        ImageView imageView = (ImageView) view;
        Integer tagId = (Integer) imageView.getTag();

        if (tagId.equals(R.drawable.mute))
        {
            SoundOn = true;
            imageView.setImageResource(R.drawable.unmute);
            soundToggleImageView.setTag(R.drawable.unmute);
            sharedPreferences.edit().putBoolean("sound", true).apply();
        }
        else if (tagId.equals(R.drawable.unmute))
        {
            SoundOn = false;
            imageView.setImageResource(R.drawable.mute);
            soundToggleImageView.setTag(R.drawable.mute);
            sharedPreferences.edit().putBoolean("sound", false).apply();
        }
    }
}
