package de.xwes.meinverein.main.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Xaver on 08.06.2015.
 */
public class TeamDataSource
{

        private SQLiteDatabase database;
        private MySQLiteHelper dbHelper;
        private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_TEAM};

        public TeamDataSource(Context context)
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

        public void createTeam(String teamname)
        {
            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_TEAM, teamname);
            long id = database.insert(dbHelper.TABLE_TEAM, dbHelper.COLUMN_ID, values);
        }

        public void createOrUpdateTeam(long id,String teamname)
        {
            ArrayList<String> teams = new ArrayList<String>();
            String sql = "Select * from team where id = ?";
            Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(id)});
            //Values einfügen
            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_TEAM, teamname);

            if(cursor.getCount() == 0 && cursor != null)
            {
                long dbid = database.insert(dbHelper.TABLE_TEAM, dbHelper.COLUMN_ID, values);
            }
            else
            {
                database.update(dbHelper.TABLE_TEAM, values, "id=" + id, null);
            }

        }

        public void deleteTeam(Team team)
        {
            long id = team.getId();
            database.delete(MySQLiteHelper.TABLE_TEAM, MySQLiteHelper.COLUMN_ID + " = " + id, null);
        }

        public ArrayList<String> getAllTeams()
        {
            ArrayList<String> teams = new ArrayList<String>();

            Cursor cursor = database.query(dbHelper.TABLE_TEAM, allColumns, null,null,null,null,null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                String team = cursorToTeam(cursor);
                teams.add(team);
                cursor.moveToNext();
            }
            cursor.close();
            return teams;
        }

        private String cursorToTeam(Cursor cursor)
        {
            return cursor.getString(1);
        }


}
