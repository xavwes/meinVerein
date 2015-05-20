package de.xwes.meinverein.main.service.request;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Xaver on 20.05.2015.
 */
public class RegisterRequest  extends AsyncTask<String, Void,String>
{
    static InputStream is = null;
    static JSONArray jObj = null;
    static String json = "";

    @Override
    protected String doInBackground(String... params)
    {
        try {
            StringBuilder url_request = new StringBuilder("http://192.168.0.7/fussball-app/register.php?name=");
            url_request.append(params[0]);
            url_request.append("&link=");
            url_request.append(params[1]);

            Log.i("url", url_request.toString());
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet(url_request.toString());
            HttpResponse getResponse = httpClient.execute(httpGet);

            final int statusCode = getResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(),
                        "Error " + statusCode + " for URL " + url_request.toString());
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            is = getResponseEntity.getContent();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return json;
    }
}
