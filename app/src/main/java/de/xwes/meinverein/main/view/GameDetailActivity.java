package de.xwes.meinverein.main.view;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import de.xwes.meinverein.R;
import de.xwes.meinverein.main.model.Game;

public class GameDetailActivity extends ActionBarActivity {

    private Game game;
    private String team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Intent intent = this.getIntent();
        Bundle extra = intent.getBundleExtra("extra");
        game = (Game) extra.getSerializable("game");
        team = intent.getStringExtra("team");

        if(team.equals(game.getAway()))
        {
            setTitle(game.getHome());
        }
        else
        {
            setTitle(game.getAway());
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
