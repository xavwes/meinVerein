package de.xwes.meinverein.main.view;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.xwes.meinverein.R;
import de.xwes.meinverein.main.model.Game;
import de.xwes.meinverein.main.model.GameDataSource;
import de.xwes.meinverein.main.model.TeamDataSource;
import de.xwes.meinverein.main.service.request.SynchronizeDataRequest;

public class OverviewActivity extends ActionBarActivity
{
    private String json ="";
    private JSONArray jsonArray;
    private ArrayList<String> teams = new ArrayList<String>();
    private ArrayList<Game> games = new ArrayList<Game>();
    private TeamDataSource teamDataSource;
    private GameDataSource gameDataSource;
    private Context mContext;
    private String teamname;
    private String dbTeamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        mContext = this;

        SharedPreferences prefs = getSharedPreferences("de.xwes.meinverein", Context.MODE_PRIVATE);
        String teamNameKey = "de.xwes.meinverein.teamname";
        teamname = prefs.getString(teamNameKey, null);

        String dbNameKey = "de.xwes.meinverein.dbname";
        dbTeamName = prefs.getString(dbNameKey, null);

        if(teamname != null)
        {
            teamname = teamname.replace("%20", " ");
            setTitle(teamname);
        }

        //JSON in DB wenn JSON im Intent vorhanden
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
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


            if(activeNetwork != null)
            {
                //Sync Service
                SynchronizeDataRequest syncRequest = new SynchronizeDataRequest();
                try {
                    jsonArray = syncRequest.execute(dbTeamName).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                GameDataSource gameData = new GameDataSource(this);
                gameData.open();

                Log.i("JSONLe", jsonArray.length() + "");
                //Json updaten auf DB
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                        long id = Long.parseLong(jsonObject.getString("id"));
                        gameData.updateGame(id, new Game(jsonObject.getString("home"), jsonObject.getString("away"), jsonObject.getString("ergebnis"), jsonObject.getString("ort"), jsonObject.getString("zeit")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                gameData.close();
            }

            teamDataSource = new TeamDataSource(this);
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
                String mannschaftsName = parent.getItemAtPosition(position).toString();
                Log.i("MannschaftsNAme", mannschaftsName);

                Intent intent = new Intent(mContext, GamesOverviewActivity.class);
                intent.putExtra("mannschaftsname", mannschaftsName);
                mContext.startActivity(intent);
            }
        });

    }

}
