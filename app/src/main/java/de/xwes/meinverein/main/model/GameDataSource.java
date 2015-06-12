package de.xwes.meinverein.main.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xaver on 29.05.2015.
 */
public class GameDataSource
{
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID_SPIELE, MySQLiteHelper.COLUMN_HOMETEAM, MySQLiteHelper.COLUMN_AWAYTEAM, MySQLiteHelper.COLUMN_ERGEBNIS, MySQLiteHelper.COLUMN_ORT, MySQLiteHelper.COLUMN_TIME};

    public GameDataSource(Context context)
    {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open()
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }

    public void createGame(Game game)
    {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_HOMETEAM, game.getHome());
        values.put(dbHelper.COLUMN_AWAYTEAM, game.getAway());
        values.put(dbHelper.COLUMN_ERGEBNIS, game.getErgebnis());
        values.put(dbHelper.COLUMN_ORT, game.getOrt());
        values.put(dbHelper.COLUMN_TIME, game.getZeit());
        long id = database.insert(dbHelper.TABLE_SPIELE, dbHelper.COLUMN_ID_SPIELE, values);
    }

    public void updateGame(long id, Game game)
    {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_HOMETEAM, game.getHome());
        values.put(dbHelper.COLUMN_AWAYTEAM, game.getAway());
        values.put(dbHelper.COLUMN_ERGEBNIS, game.getErgebnis());
        values.put(dbHelper.COLUMN_ORT, game.getOrt());
        values.put(dbHelper.COLUMN_TIME, game.getZeit());
        database.update(dbHelper.TABLE_SPIELE, values, "id=" + id, null);
    }

    public void createOrUpdateGame(long id, Game game)
    {
        ArrayList<Game> games = new ArrayList<Game>();
        String sql = "Select * from spiele where id = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
         //Values einfügen
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_HOMETEAM, game.getHome());
        values.put(dbHelper.COLUMN_AWAYTEAM, game.getAway());
        values.put(dbHelper.COLUMN_ERGEBNIS, game.getErgebnis());
        values.put(dbHelper.COLUMN_ORT, game.getOrt());
        values.put(dbHelper.COLUMN_TIME, game.getZeit());

        if(cursor.getCount() == 0 && cursor != null)
        {
            long dbid = database.insert(dbHelper.TABLE_SPIELE, dbHelper.COLUMN_ID_SPIELE, values);
        }
        else
        {
            database.update(dbHelper.TABLE_SPIELE, values, "id=" + id, null);
        }

    }

    public ArrayList<Game> getAllGames(String mannschaft)
    {
        ArrayList<Game> games = new ArrayList<Game>();

        String sql = "Select * from spiele where home = ? or away = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{mannschaft, mannschaft});

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Game game = cursorToGame(cursor);
            games.add(game);
            cursor.moveToNext();
        }
        cursor.close();

        return games;
    }

    private Game cursorToGame(Cursor cursor)
    {
        Game game = new Game();
        game.setId(cursor.getLong(0));
        game.setHome(cursor.getString(1));
        game.setAway(cursor.getString(2));
        game.setErgebnis(cursor.getString(3));
        game.setOrt(cursor.getString(4));
        game.setZeit(cursor.getString(5));

        return game;
    }


}
