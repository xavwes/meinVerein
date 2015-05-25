package de.xwes.meinverein.main.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import de.xwes.meinverein.R;

public class OverviewActivity extends ActionBarActivity
{
    String json ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Intent intent = getIntent();
        json = intent.getStringExtra("json");
        if(json!= "" && json!= null)
        {
            Log.i("Success", json);
        }

    }

}
