package com.sololaunches.www.guessthemoviehollywood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hp on 25-03-2018.
 */

public class WweDBAdapter extends SQLiteOpenHelper {

    private Context contextC;
    DBInserter dbinserter;


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Kerala_Railway_two.db";
    private static final String DATABASE_ALTER_TEAM_1 = "DROP TABLE IF EXISTS WWE_STAGE_MAIN";


    public WweDBAdapter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        this.contextC = context;
        dbinserter = new DBInserter();
        dbinserter.insertAllValues(context, db);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = WWEConstants.MAIN_WWE_TABLE;                //Railway Main table
        String queryMetro = WWEConstants.MAIN_PLAYER;
        db.execSQL(query);
        db.execSQL(queryMetro);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        if (oldVersion < 2) {
            //SQLiteDatabase db2 = ge.getWritableDatabase();
            db.execSQL(DATABASE_ALTER_TEAM_1);
            String query = WWEConstants.MAIN_WWE_TABLE;
            db.execSQL(query);               //Railway Main table
            dbinserter = new DBInserter();
        }

    }

    public void setPlayerName(String playerName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PLAYER_NAME", playerName);
        values.put("COINS", 50);
        values.put("POINTS", 0);
        values.put("STATUS", "N");
        db.insert("WWE_PLAYER_STAT", null, values);
        db.close();
    }

    public PlayerStatsBean getPlayerStats() {
        PlayerStatsBean playerStatsBean = new PlayerStatsBean();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from WWE_PLAYER_STAT WHERE STATUS='N'";
        Cursor cur = db.rawQuery(query, null);
        if (cur.getCount() == 0) {
            return null;
        }
        while (cur.moveToNext()) {
            playerStatsBean.setPlayer(cur.getString(1));
            playerStatsBean.setCoins(cur.getString(2));
            playerStatsBean.setPoints(cur.getString(3));
            playerStatsBean.setStatus(cur.getString(4));
        }
        return playerStatsBean;

    }


    public void updateFinishGame(String str) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("STATUS", "Y");
        db.update("WWE_PLAYER_STAT", values, "PLAYER_NAME=?", new String[]{str});

        ContentValues values2 = new ContentValues();
        values2.put("STATUS", "N");
        db.update("WWE_STAGE_MAIN", values2, "STATUS=?", new String[]{"Y"});
        db.close();
    }

    public void updateStateUp(PlayerStatsBean playerStatsBean, String player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("COINS", playerStatsBean.getCoins());
        values.put("POINTS", playerStatsBean.getPoints());
        db.update("WWE_PLAYER_STAT", values, "STATUS=?", new String[]{"N"});

        ContentValues values2 = new ContentValues();
        values2.put("STATUS", "Y");
        db.update("WWE_STAGE_MAIN", values2, "PLAYER=?", new String[]{player});
        db.close();
    }

    public void updateHintUsed(PlayerStatsBean playerStatsBean, String player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("COINS", playerStatsBean.getCoins());
        values.put("POINTS", playerStatsBean.getCoins());
        db.update("WWE_PLAYER_STAT", values, "STATUS=?", new String[]{"N"});

        ContentValues values2 = new ContentValues();
        values2.put("HINT_STATUS", "Y");
        db.update("WWE_STAGE_MAIN", values2, "PLAYER=?", new String[]{player});
        db.close();
    }

    public void updateAddedCoins(PlayerStatsBean playerStatsBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("COINS", playerStatsBean.getCoins());
        db.update("WWE_PLAYER_STAT", values, "STATUS=?", new String[]{"N"});
        db.close();
    }


    public class DBInserter {
        public void insertAllValues(Context context, SQLiteDatabase db) {

            Cursor cur = checkDatabase();
            if (cur.getCount() > 0) {
                return;
            } else {
                try {
                    int result = 0;
                    InputStream insertsStream = context.getResources().openRawResource(R.raw.inser_main);
                    BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));
                    while (insertReader.ready()) {
                        String insertStmt = insertReader.readLine();
                        db.execSQL(insertStmt);
                        result++;
                    }
                    insertReader.close();
                    Log.e("Error ", "onCreate: " + result);
                } catch (Exception e) {
                    Log.e("Error ", "onCreate: " + e);
                }
            }
        }
    }


    public Cursor checkDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from WWE_STAGE_MAIN";
        Cursor cur = db.rawQuery(query, null);
        return cur;
    }


    public Cursor getTimeTableForStation() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from WWE_STAGE_MAIN  WHERE STATUS='N' ORDER BY MAIN_ID LIMIT 1";
        Cursor cur = db.rawQuery(query, null);
        return cur;
    }

    public Cursor getSuperStar(String player) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from WWE_STAGE_MAIN WHERE PLAYER='" + player + "'";
        Cursor cur = db.rawQuery(query, null);
        return cur;
    }


    public Cursor getPlayerScore() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from WWE_PLAYER_STAT WHERE STATUS='N'";
        Cursor cur = db.rawQuery(query, null);
        return cur;
    }

}
