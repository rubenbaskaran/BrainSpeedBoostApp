package rubenbaskaran.com.brainchallenge.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rubenbaskaran.com.brainchallenge.GameCategories.AdditionActivity;
import rubenbaskaran.com.brainchallenge.GameCategories.DivisionActivity;
import rubenbaskaran.com.brainchallenge.GameCategories.MultiplicationActivity;
import rubenbaskaran.com.brainchallenge.GameCategories.SubtractionActivity;
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
        Intent i = null;

        switch (tag)
        {
            case "addition":
                i = new Intent(getApplicationContext(), AdditionActivity.class);
                break;
            case "subtraction":
                i = new Intent(getApplicationContext(), SubtractionActivity.class);
                break;
            case "multiplication":
                i = new Intent(getApplicationContext(), MultiplicationActivity.class);
                break;
            case "division":
                i = new Intent(getApplicationContext(), DivisionActivity.class);
                break;
        }

        startActivity(i);
    }
}
