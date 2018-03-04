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

import rubenbaskaran.com.brainspeedchallenge.R;

public class NumbersMenuActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_menu);

        MobileAds.initialize(this, "ca-app-pub-4429595719358536~3725225234");
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
