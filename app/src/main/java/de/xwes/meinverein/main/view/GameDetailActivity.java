package de.xwes.meinverein.main.view;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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
        team = intent.getStringExtra("teamname");

        if(team.equals(game.getAway()))
        {
            setTitle(game.getHome());
        }
        else
        {
            setTitle(game.getAway());
        }

        String zeit_game = game.getZeit();
        String[] zeiten = zeit_game.split(" ");
        String newZeit = zeiten[0] + " " + zeiten[1] + "\n" + zeiten[2] + " Uhr";

        TextView zeit = (TextView) findViewById(R.id.zeit_detail);
        TextView ort = (TextView) findViewById(R.id.spielort);

        ort.setText(game.getOrt());
        zeit.setText(newZeit);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra("mannschaftsname", team);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
