package de.xwes.meinverein.main.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.xwes.meinverein.R;
import de.xwes.meinverein.main.model.Game;
import de.xwes.meinverein.main.model.GameDataSource;
import de.xwes.meinverein.main.model.MySQLiteHelper;
import de.xwes.meinverein.main.model.Team;
import de.xwes.meinverein.main.model.TeamDataSource;

public class OverviewActivity extends ActionBarActivity
{
    private String json ="";
    private JSONArray jsonArray;
    private ArrayList<String> teams = new ArrayList<String>();
    private ArrayList<Game> games = new ArrayList<Game>();
    private TeamDataSource teamDataSource;
    private GameDataSource gameDataSource;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        mContext = this;

        SharedPreferences prefs = getSharedPreferences("de.xwes.meinverein", Context.MODE_PRIVATE);
        String teamNameKey = "de.xwes.meinverein.teamname";
        String teamname = prefs.getString(teamNameKey, null);

        if(teamname != null)
        {
            teamname = teamname.replace("%20", " ");
            setTitle(teamname);
        }

        Intent intent = getIntent();
        json = intent.getStringExtra("json");
        if(json!= "" && json!= null)
        {
            try {
                jsonArray = new JSONArray(json);
                JSONArray teamArray = jsonArray.getJSONArray(0);
                for(int i = 0; i < teamArray.length(); i++)
                {
                    JSONObject jsonObject = teamArray.getJSONObject(i);
                    teams.add(jsonObject.getString("team"));

                }

                JSONArray gamesArray = jsonArray.getJSONArray(1);
                for (int i = 0; i< gamesArray.length(); i++)
                {
                    JSONObject jsonObject = gamesArray.getJSONObject(i);
                    games.add(new Game(jsonObject.getString("home"), jsonObject.getString("away"), jsonObject.getString("ergebnis"), jsonObject.getString("spielort"), jsonObject.getString("zeit")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            teamDataSource = new TeamDataSource(this);
            teamDataSource.open();

            for (String team : teams)
            {
                teamDataSource.createTeam(team);
            }
            teamDataSource.close();

            gameDataSource = new GameDataSource(this);
            gameDataSource.open();
            //games to DB
            for (Game game : games)
            {
                gameDataSource.createGame(game);
            }

            gameDataSource.close();
        }
        else
        {
            TeamDataSource teamDataSource = new TeamDataSource(this);
            teamDataSource.open();
            teams = teamDataSource.getAllTeams();
            teamDataSource.close();
        }


        ListView listView = (ListView) findViewById(R.id.list_teams);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.search_item, R.id.teamname, teams);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                GameDataSource gameDataSource = new GameDataSource(mContext);
                gameDataSource.open();
                ArrayList<Game> gamesOfTeam = gameDataSource.getAllGames(parent.getItemAtPosition(position).toString());

                Bundle extra = new Bundle();
                extra.putSerializable("games", gamesOfTeam);

                Intent intent = new Intent(mContext, GamesOverviewActivity.class);
                intent.putExtra("extra", extra);
                Log.i("Teamname", parent.getItemAtPosition(position).toString());
                intent.putExtra("team", parent.getItemAtPosition(position).toString());
                mContext.startActivity(intent);
            }
        });

    }

}
