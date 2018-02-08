package rubenbaskaran.com.brainspeedchallenge.Highscores;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rubenbaskaran.com.brainspeedchallenge.R;

public class GlobalHighscoreFragment extends Fragment
{
    public GlobalHighscoreFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_global_highscore, container, false);
    }
}
