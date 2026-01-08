package com.example.resturantmanagmentapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

public class logoututils {
    public static void setupLogout(Activity activity) {
        View btn = activity.findViewById(R.id.logout_button);

        if (btn != null) {
            btn.setOnClickListener(v -> {
                SharedPreferences sharedPref = activity.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                sharedPref.edit().clear().apply();

                Intent intent = new Intent(activity, LoginActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                activity.startActivity(intent);
                activity.finish();
            });
        }
    }
}