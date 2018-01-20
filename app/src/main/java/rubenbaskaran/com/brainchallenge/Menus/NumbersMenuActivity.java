package rubenbaskaran.com.brainchallenge.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rubenbaskaran.com.brainchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainchallenge.GameCategories.NumbersGameActivity;
import rubenbaskaran.com.brainchallenge.R;

public class NumbersMenuActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_menu);
    }

    public void StartGame(View view)
    {
        String tag = (String) view.getTag();
        GameTypes gameType = null;

        switch (tag)
        {
            case "addition":
                gameType = GameTypes.Addition;
                break;
            case "subtraction":
                gameType = GameTypes.Subtraction;
                break;
            case "multiplication":
                gameType = GameTypes.Multiplication;
                break;
            case "division":
                gameType = GameTypes.Division;
                break;
        }

        Intent i = new Intent(getApplicationContext(), NumbersGameActivity.class);
        i.putExtra("gametype", gameType);
        startActivity(i);
    }
}
