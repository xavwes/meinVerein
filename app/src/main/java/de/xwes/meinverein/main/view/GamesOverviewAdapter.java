package de.xwes.meinverein.main.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.xwes.meinverein.R;
import de.xwes.meinverein.main.model.Game;

/**
 * Created by Xaver on 31.05.2015.
 */
public class GamesOverviewAdapter extends ArrayAdapter<Game>
{
    private final Context context;
    private final ArrayList<Game> games;
    private String selectedTeam;

    public GamesOverviewAdapter(Context context, ArrayList<Game> games, String team)
    {
        super(context, -1, games);
        this.context = context;
        this.selectedTeam = team;
        this.games = games;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.game_overview, parent, false);
        TextView datum = (TextView) rowView.findViewById(R.id.datum);
        TextView gegner = (TextView) rowView.findViewById(R.id.gegner);
        TextView ergebnis = (TextView) rowView.findViewById(R.id.ergebnis);

        String zeit = games.get(position).getZeit();
        String[] zeiten = zeit.split(" ");
        String newZeit = zeiten[0] + "\n" + zeiten[1] + "\n" + zeiten[2];
        datum.setText(newZeit);
        ergebnis.setText(games.get(position).getErgebnis());
        if(games.get(position).getHome().equals(selectedTeam))
        {
            gegner.setText(games.get(position).getAway());
        }
        else
        {
            gegner.setText(games.get(position).getHome());
        }
        return rowView;
    }
}
