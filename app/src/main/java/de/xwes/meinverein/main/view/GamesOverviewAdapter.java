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

        if(games.get(position).getHome().equals(selectedTeam))
        {
            String awayTeam = games.get(position).getAway();
            if(awayTeam.contains("/"))
            {
                String [] team = awayTeam.split("/");
                awayTeam = team[0] + "/\n" + team[1];
            }
            else if(awayTeam.contains("-"))
            {
                String [] team = awayTeam.split("-");
                awayTeam = team[0] + "-\n" + team[1];
            }
            else if(awayTeam.length() > 15)
            {
                String first = awayTeam.substring(0, 15);
                String second = awayTeam.substring(15, awayTeam.length());
                awayTeam = first + "-\n" + second;
            }
            gegner.setText(awayTeam);
            ergebnis.setText(games.get(position).getErgebnis());
        }
        else
        {
            String homeTeam = games.get(position).getHome();
            if(homeTeam.contains("/"))
            {
                String [] team = homeTeam.split("/");
                homeTeam = team[0] + "/\n" + team[1];
            }
            else if(homeTeam.contains("-"))
            {
                String [] team = homeTeam.split("-");
                homeTeam = team[0] + "-\n" + team[1];
            }
            else if(homeTeam.length() > 15)
            {
                String first = homeTeam.substring(0, 15);
                String second = homeTeam.substring(15, homeTeam.length());
                homeTeam = first + "-\n" + second;
            }
            gegner.setText(homeTeam);
            ergebnis.setText(changeErgebnis(games.get(position).getErgebnis()));
        }
        return rowView;
    }

    public String changeErgebnis(String ergebnis)
    {
        String newErgebnis = ergebnis;
        char home = ergebnis.charAt(0);
        char away = ergebnis.charAt(2);
        newErgebnis = away + ":" + home;
        return newErgebnis;
    }
}
