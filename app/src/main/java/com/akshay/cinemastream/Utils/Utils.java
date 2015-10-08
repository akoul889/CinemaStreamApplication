package com.akshay.cinemastream.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKSHAY on 02-10-2015.
 */
public class Utils {

    public static String getFormattedTime(int minutes){
        int hours = minutes/60;
        int min = minutes%60;
        if (hours>0) {
            return Integer.toString(hours)+" : "+Integer.toString(min);
        }else{
            return Integer.toString(min)+" minutes";
        }
    }

    public static String getFormattedDisplayText(String text){
        if(TextUtils.isEmpty(text)){
            return "Not Available";
        }else{
            return text;
        }
    }
    public static SharedPreferences getSharedPreference(Context context){
        SharedPreferences prefs = context.getSharedPreferences(
                "com.akshay.cinemastream", Context.MODE_PRIVATE);
        return prefs;
    }

public static String[] getSugessions(Context context){
    SharedPreferences preferences = getSharedPreference(context);
    String sugessions = preferences.getString(AppConstants.KEY_SUGESIONS,AppConstants.DEF_VALUE);
    if (sugessions.equals(AppConstants.DEF_VALUE)) {
        String[] sugession = {};
        return sugession;
    }else{
        List<String> suggesionList = new Gson().fromJson(sugessions,new TypeToken<List<String>>(){}.getType());
        return suggesionList.toArray(new String[suggesionList.size()]);
    }
}
    public static void saveSugession(Context context,String sugession){
        SharedPreferences preferences = getSharedPreference(context);
        String sugessions = preferences.getString(AppConstants.KEY_SUGESIONS,AppConstants.DEF_VALUE);
        List<String> suggesionList;
        if (sugessions.equals(AppConstants.DEF_VALUE)) {
            suggesionList = new ArrayList<String>();
            suggesionList.add(sugession);
        }else{
            suggesionList = new Gson().fromJson(sugessions,new TypeToken<List<String>>(){}.getType());
            if (!suggesionList.contains(sugession)) {
                suggesionList.add(0,sugession);
                if(suggesionList.size() >50){
                    for(int i=51;i<suggesionList.size();i++){
                        suggesionList.remove(i);
                    }
                }
            }
        }
        String saveSugession;
        if(suggesionList!=null && !suggesionList.isEmpty()){
           saveSugession =  new Gson().toJson(suggesionList);
        }else{
            saveSugession = AppConstants.DEF_VALUE;
        }

        preferences.edit().putString(AppConstants.KEY_SUGESIONS, saveSugession).apply();
    }

}
