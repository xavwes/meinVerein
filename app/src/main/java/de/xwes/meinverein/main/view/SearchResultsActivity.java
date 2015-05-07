package de.xwes.meinverein.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.xwes.meinverein.R;


public class SearchResultsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        TextView teamname = (TextView) findViewById(R.id.teamname);

        Intent intent = getIntent();

        String json = intent.getStringExtra("json");
        try {
            JSONArray jObj = new JSONArray(json);
            for(int i = 0; i < jObj.length(); i++)
            {
                JSONObject jsonObject = jObj.getJSONObject(i);
                teamname.setText(jsonObject.getString("teamname"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
