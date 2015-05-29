package de.xwes.meinverein.main.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Xaver on 26.05.2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper
{
    public static String TABLE_TEAM="team";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEAM = "mannschaft";

    public static String TABLE_SPIELE = "spiele";
    public static final String COLUMN_ID_SPIELE = "id";
    public static final String COLUMN_HOMETEAM = "home";
    public static final String COLUMN_AWAYTEAM = "away";
    public static final String COLUMN_ERGEBNIS = "ergebnis";
    public static final String COLUMN_ORT = "ort";
    public static final String COLUMN_TIME = "zeit";

    public static final String DATABASE_NAME = "fussball_app";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_TEAM = "create table if not exists '"
            + TABLE_TEAM + "' (" + COLUMN_ID
            + " integer primary key, " + COLUMN_TEAM
            + " text not null);";

    private static final String DATABASE_CREATE_SPIELE = "create table if not exists '"
            + TABLE_SPIELE + "' (" + COLUMN_ID_SPIELE
            + " integer primary key, " + COLUMN_HOMETEAM
            + " text not null, " + COLUMN_AWAYTEAM + " text not null, " + COLUMN_ERGEBNIS + " text, " + COLUMN_ORT + " text, "  + COLUMN_TIME +   " text);";

    public MySQLiteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE_TEAM);
        db.execSQL(DATABASE_CREATE_SPIELE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
