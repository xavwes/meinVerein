package de.xwes.meinverein.main.view;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.xwes.meinverein.R;
import de.xwes.meinverein.main.model.Game;

public class GameDetailActivity extends ActionBarActivity {

    private Game game;
    private String team;
    private GoogleMap map;
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Intent intent = this.getIntent();
        Bundle extra = intent.getBundleExtra("extra");
        game = (Game) extra.getSerializable("game");
        team = intent.getStringExtra("teamname");

        if(team.equals(game.getAway()))
        {
            setTitle(game.getHome());
        }
        else
        {
            setTitle(game.getAway());
        }

        String zeit_game = game.getZeit();
        String[] zeiten = zeit_game.split(" ");
        String newZeit = zeiten[0] + " " + zeiten[1] + "\n" + zeiten[2] + " Uhr";

        TextView zeit = (TextView) findViewById(R.id.zeit_detail);
        TextView ort = (TextView) findViewById(R.id.spielort);

        zeit.setText(newZeit);

        if(game.getOrt().equals(""))
        {
            ort.setText(game.getOrt());
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            if(map != null)
            {
                Geocoder coder = new Geocoder(this);
                List<Address> address;

                try {
                    address = coder.getFromLocationName(game.getOrt(), 3);
                    Address location = address.get(0);
                    LatLng adresse = new LatLng(location.getLatitude(), location.getLongitude());
                    Log.i("Adresse", adresse.toString());
                    map.addMarker(new MarkerOptions().position(adresse).title("Spielort"));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(adresse, 15));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra("mannschaftsname", team);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
