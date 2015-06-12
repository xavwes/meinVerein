package de.xwes.meinverein.main.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import de.xwes.meinverein.R;
import de.xwes.meinverein.main.model.MySQLiteHelper;
import de.xwes.meinverein.main.service.request.CreateTeamDataRequest;
import de.xwes.meinverein.main.service.request.RegisterRequest;


public class SearchResultsActivity extends ActionBarActivity
{

    private String[] teams;
    private String[] links;
    private String selectedTeam ="";
    public static Context mContext = null;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setTitle("Vereinsauswahl");

        mContext = this;
        ListView list = (ListView) findViewById(R.id.search_results);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        Intent intent = getIntent();

        final String json = intent.getStringExtra("json");
        try {
            JSONArray jObj = new JSONArray(json);
            teams = new String[jObj.length()];
            links = new String[jObj.length()];
            for(int i = 0; i < jObj.length(); i++)
            {
                JSONObject jsonObject = jObj.getJSONObject(i);
                teams[i] = jObj.getJSONObject(i).getString("teamname");
                links[i] = jObj.getJSONObject(i).getString("teamlink");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.search_item, R.id.teamname, teams);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTeam = parent.getItemAtPosition(position).toString();
                int index = Arrays.asList(teams).indexOf(selectedTeam);
                String link = "www.fupa.net" + links[index];

                selectedTeam = selectedTeam.replaceAll(" ", "%20");
                selectedTeam = selectedTeam.replaceAll("ö", "oe");
                selectedTeam = selectedTeam.replaceAll("ä", "ae");
                selectedTeam = selectedTeam.replaceAll("ü", "ue");
                String dbName = selectedTeam;
                dbName = dbName.replaceAll("%20", "");
                dbName = dbName.replaceAll("/", "");
                dbName = dbName.replaceAll("-", "");
                dbName = dbName.replaceAll("ö", "oe");
                dbName = dbName.replaceAll("ü", "ue");
                dbName = dbName.replaceAll("ä", "ae");
                dbName = dbName.toLowerCase();
                if(dbName.contains("."))
                {
                    dbName = dbName.replace(".","");
                }

                RegisterRequest registerRequest = new RegisterRequest();
                String returnValue = "";
                try {
                    returnValue = registerRequest.execute(selectedTeam, link).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if(returnValue.contains("Status=ok"))
                {
                    SharedPreferences prefs = getSharedPreferences("de.xwes.meinverein", Context.MODE_PRIVATE);
                    String dbNameKey = "de.xwes.meinverein.dbname";
                    String linkKey = "de.xwes.meinverein.link";
                    String teamNameKey = "de.xwes.meinverein.teamname";

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(dbNameKey, dbName);
                    editor.putString(teamNameKey, selectedTeam);
                    editor.putString(linkKey, link);
                    editor.commit();

                    //Progress Dialog
                    new CreateTeamDataRequest(SearchResultsActivity.this).execute(selectedTeam, link);

                }
                else if(returnValue.contains("Status=existing"))
                {
                    SharedPreferences prefs = getSharedPreferences("de.xwes.meinverein", Context.MODE_PRIVATE);
                    String dbNameKey = "de.xwes.meinverein.dbname";
                    String linkKey = "de.xwes.meinverein.link";
                    String teamNameKey = "de.xwes.meinverein.teamname";

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(dbNameKey, dbName);
                    editor.putString(teamNameKey, selectedTeam);
                    editor.putString(linkKey, link);
                    editor.commit();

                    Intent myIntent = new Intent(SearchResultsActivity.this, OverviewActivity.class);
                    startActivity(myIntent);
                }
                else
                {
                    //Fehlerfall
                }

            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("de.xwes.meinverein", Context.MODE_PRIVATE);
        String dbNameKey = "de.xwes.meinverein.dbname";
        String linkKey = "de.xwes.meinverein.link";
        String teamNameKey = "de.xwes.meinverein.teamname";

        String teamname = prefs.getString(teamNameKey, null);
        String link = prefs.getString(linkKey, null);
        String dbName = prefs.getString(dbNameKey, null);

        if(teamname != null && link != null && dbName != null)
        {
            Intent myIntent = new Intent(SearchResultsActivity.this, OverviewActivity.class);
            startActivity(myIntent);
        }
        else
        {
            Log.i("Fehler", " noch nicht da");
        }
    }





}
