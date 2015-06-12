package de.xwes.meinverein.main.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

import de.xwes.meinverein.R;
import de.xwes.meinverein.main.service.request.SearchRequest;


public class MainActivity extends ActionBarActivity
{
    EditText search_input;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Vereinssuche");
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
            Log.i("Schon vorhanden" , teamname);
            Intent myIntent = new Intent(MainActivity.this, OverviewActivity.class);
            startActivity(myIntent);
        }
        else
        {
            Log.i("Fehler", " noch nicht da");
        }

        search_input = (EditText) findViewById(R.id.search_input);

        Button search = (Button) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String search_string = search_input.getText().toString();
                search_string = search_string.replaceAll(" ", "%20");
                SearchRequest searchRequest = new SearchRequest();
                JSONArray jsonArray = null;
                try {
                    jsonArray = searchRequest.execute(search_string).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent myIntent = new Intent(MainActivity.this, SearchResultsActivity.class);
                myIntent.putExtra("json", jsonArray.toString());
                startActivity(myIntent);
            }
        });
    }

}
