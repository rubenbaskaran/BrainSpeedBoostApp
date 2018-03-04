package rubenbaskaran.com.brainspeedchallenge.Menus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

import rubenbaskaran.com.brainspeedchallenge.Databases.Managers.LocalDatabaseManager;
import rubenbaskaran.com.brainspeedchallenge.Enums.GameTypes;
import rubenbaskaran.com.brainspeedchallenge.R;

public class NumbersMenuActivity extends AppCompatActivity
{
    // TODO: Remember to link app to google play in admob after app has been released
    // TODO: Insert real ad IDs
    // TODO: Add credits to creator of images used for app icon
    // TODO: Use names "Peter Parker", "Bruce Wayne", "Clark Kent", "Tony Stark" & "Bruce Banner" for global highscore screenshot
    // TODO: Use names "Me", "Mom", "Dad", "Grandma" & "Grandpa" for local highscore screenshot
    // TODO: Change game length back to 30 seconds

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.about_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.about)
        {
            Toast.makeText(getApplicationContext(), "Made by Ruben Baskaran", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
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
