package de.xwes.meinverein.main.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.xwes.meinverein.R;
import de.xwes.meinverein.main.model.Game;

public class GamesOverviewActivity extends ActionBarActivity
{
    private ArrayList<Game> games;
    private String teamname;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_overview);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.games_overview_list);

        Intent intent = this.getIntent();
        Bundle extra = intent.getBundleExtra("extra");

        if(extra != null)
        {
            teamname = intent.getStringExtra("team");
            games = (ArrayList<Game>) extra.getSerializable("games");
            setTitle(teamname);

            GamesOverviewAdapter gamesOverviewAdapter = new GamesOverviewAdapter(this,games, teamname);

            listView.setAdapter(gamesOverviewAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Game g = (Game) parent.getItemAtPosition(position);
                    Bundle extra = new Bundle();
                    extra.putSerializable("game", g);

                    Intent intent = new Intent(mContext, GameDetailActivity.class);
                    intent.putExtra("extra", extra);
                    intent.putExtra("team", teamname);
                    mContext.startActivity(intent);
                }
            });
        }



     }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
