package de.xwes.meinverein.main.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.xwes.meinverein.R;


public class SearchResultsActivity extends Activity
{

    private String[] teams;
    private String[] links;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ListView list = (ListView) findViewById(R.id.search_results);

        Intent intent = getIntent();

        String json = intent.getStringExtra("json");
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

        for(int j = 0; j < teams.length; j++)
        {
            Log.i("Team " + j, links[j]);
        }

    }

}
