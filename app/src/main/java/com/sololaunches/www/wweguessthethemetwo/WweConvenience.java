package com.sololaunches.www.wweguessthethemetwo;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by hp on 25-03-2018.
 */

public class WweConvenience {

    WweDBAdapter wweDBAdapter;

    public WweMainBean getSuperStarBean(String player) {
        WweMainBean wweMainBean = new WweMainBean();
        Cursor cur2 = wweDBAdapter.getSuperStar(player);
        if (cur2.getCount() == 0) {
            return wweMainBean;
        }
        while (cur2.moveToNext()) {
            wweMainBean.setPosition(cur2.getString(0));
            wweMainBean.setPlayer(cur2.getString(1));
            wweMainBean.setAlias(cur2.getString(2));
            wweMainBean.setPath(cur2.getString(3));
            wweMainBean.setDisplay(cur2.getString(4));
            wweMainBean.setHint(cur2.getString(5));
            wweMainBean.setHintStatus(cur2.getString(6));
            wweMainBean.setAdStatus(cur2.getString(7));
            wweMainBean.setStatus(cur2.getString(8));
        }
        return wweMainBean;

    }


    public PlayerStatsBean getPlayerStats(Context context) {

        PlayerStatsBean playerStatsBean = new PlayerStatsBean();
        wweDBAdapter = new WweDBAdapter(context, null, null, 1);
        Cursor cur = wweDBAdapter.getPlayerScore();

        if (cur.getCount() == 0) {
            return null;
        }

        while (cur.moveToNext()) {
            playerStatsBean.setPlayer(cur.getString(1));
            playerStatsBean.setCoins(cur.getString(2));
            playerStatsBean.setPoints(cur.getString(3));
            playerStatsBean.setStatus(cur.getString(4));
            return playerStatsBean;
        }
        return null;
    }

    public boolean getResult(String stored, String value) {


        if (stored.equals(value.trim().toLowerCase())) {
            return true;
        }

        String fullValue = getFullString(value.toLowerCase().trim());

        if (stored.equals(fullValue.trim())) {
            return true;
        }

        if (value.trim().contains(" ")) {
            String[] arr = value.toLowerCase().split(" ");
            int checkc = 0;
            for (String s : arr) {
                int num = getMatchPercentage(stored, s);
                checkc = checkc + num;
            }
            if (checkc > 50) {
                return true;
            }
        }

        String[] doubleEnd = getDoubleStrings(fullValue);
        for (String str : doubleEnd) {
            int check1 = getMatchPercentage(stored, str.toLowerCase());
            if (check1 > 70) {
                return true;
            }
        }

        char[] charArr = fullValue.toCharArray();
        StringBuffer stb = new StringBuffer();
        for (char c : charArr) {
            stb.append(c);
            int check1 = getMatchPercentage(stored, stb.toString().toLowerCase());
            if (check1 > 70) {
                return true;
            }
        }
        stb = new StringBuffer();
        for (int i = (charArr.length - 1); i >= 0; i--) {
            String st = String.valueOf(charArr[i]);
            stb.append(stb + st);
            int check1 = getMatchPercentage(stored, stb.toString().toLowerCase());
            if (check1 > 70) {
                return true;
            }
        }

        String arr = value;
        return false;
    }

    private String[] getDoubleStrings(String fullValue) {
        char[] charArr = fullValue.toCharArray();
        StringBuffer stb = new StringBuffer();
        StringBuffer stb1 = new StringBuffer();
        String[] str = new String[2];

        for (int i = (charArr.length - 1); i > 0; i--) {
            stb.append(String.valueOf(charArr[i]));
        }
        stb.reverse();

        for (int i = 0; i < (charArr.length - 1); i++) {
            stb1.append(charArr[i]);
        }
        str[0] = stb.toString();
        str[1] = stb1.toString();
        return str;
    }

    private String getFullString(String str) {
        String[] arr = str.toLowerCase().split("\\s+");
        StringBuffer stbf = new StringBuffer();
        for (String s : arr) {
            stbf.append(s);
        }
        return stbf.toString();
    }

    public int getProgressPercentage(int totalDuration, int currentPosition) {
        float proportion = ((float) currentPosition) / ((float) totalDuration);
        float progress = proportion * 100;
        int finalCal = (int) progress;
        return finalCal;
    }

    public int getMatchPercentage(String stored, String value) {

        int storedVal = stored.length();
        int val = value.length();

        float proportion = ((float) val) / ((float) storedVal);
        float progress = proportion * 100;
        int finalCal = (int) progress;
        if (stored.contains(value.toLowerCase().trim())) {
            return finalCal;
        } else {
            return 0;
        }


    }
}
