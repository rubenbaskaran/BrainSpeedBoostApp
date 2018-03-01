package rubenbaskaran.com.brainspeedchallenge.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.LocalDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.R;
import com.google.android.gms.ads.MobileAds;

public class NumbersMenuActivity extends AppCompatActivity
{
    // TODO: Remember to link app to google play in admob after app has been released

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_menu);

        MobileAds.initialize(this, "ca-app-pub-4429595719358536~3725225234");

        LocalDatabaseManager localDatabaseManager = new LocalDatabaseManager(getApplicationContext());
        localDatabaseManager.GetLocalHighscores(GameTypes.Addition);
        localDatabaseManager.GetLocalHighscores(GameTypes.Subtraction);
        localDatabaseManager.GetLocalHighscores(GameTypes.Multiplication);
        localDatabaseManager.GetLocalHighscores(GameTypes.Division);
        localDatabaseManager.GetLocalHighscores(GameTypes.Color);
    }

    public void StartGame(View view)
    {
        String tag = (String) view.getTag();
        Intent i = null;

        switch (tag)
        {
            case "addition":
                i = new Intent(getApplicationContext(), AdditionMenuActivity.class);
                break;
            case "subtraction":
                i = new Intent(getApplicationContext(), SubtractionMenuActivity.class);
                break;
            case "multiplication":
                i = new Intent(getApplicationContext(), MultiplicationMenuActivity.class);
                break;
            case "division":
                i = new Intent(getApplicationContext(), DivisionMenuActivity.class);
                break;
        }

        startActivity(i);
    }
}
