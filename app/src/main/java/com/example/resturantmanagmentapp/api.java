package com.example.resturantmanagmentapp;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

public class Api {

    private static final String BASE_URL = "http://127.0.0.1:8000"; // FastAPI URL
    private static RequestQueue requestQueue;
    private static final Gson gson = new Gson();

    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    public static void addMenuItem(Context context, MenuItem menuItem) {
        initQueue(context);
        String url = BASE_URL + "/menu/add";

        try {
            JSONObject jsonRequest = new JSONObject(gson.toJson(menuItem));

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                    response -> Log.d("MenuService", "Menu item added successfully"),
                    error -> Log.e("MenuError", "Error adding menu item: " + error.getMessage())
            );

            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("MenuError", "Invalid JSON format: " + e.getMessage());
        }
    }
}
