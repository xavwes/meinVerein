package de.xwes.meinverein.main.service.request;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.InputStream;

/**
 * Created by Xaver on 22.05.2015.
 */
public class SynchronizeDataRequest extends AsyncTask<String, Void, JSONArray>
{
    static InputStream is = null;
    static JSONArray jsonArray;
    static String json = "";

    @Override
    protected JSONArray doInBackground(String... params)
    {
        return  jsonArray;

    }
}
