package rubenbaskaran.com.brainchallenge.Highscore;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rubenbaskaran.com.brainchallenge.R;

public class LocalHighscoreFragment extends Fragment
{
    public LocalHighscoreFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //TODO: Initialize content
        return inflater.inflate(R.layout.fragment_local_highscore, container, false);
    }
}
