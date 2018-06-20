package com.manan.dev.shineymca;

import android.content.Context;
import android.content.SharedPreferences;

public class Methods {
    public static void callSharedPreference(Context context, String userEmail) {
        SharedPreferences sharedPref = context.getSharedPreferences("shared_preference_file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", userEmail);
        editor.commit();
    }
}
