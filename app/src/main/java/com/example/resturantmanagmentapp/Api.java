package com.example.resturantmanagmentapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class Api {

    private static final String BASE_URL = "http://10.0.2.2:8000";
    private static RequestQueue requestQueue;
    private static final Gson gson = new Gson();


    public interface AddMenuItemCallback {
        void onSaved(MenuItem savedItem);
    }

    public interface MenuCallback {
        void onSuccess(List<MenuItem> items);
    }

    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    public static void getAllMenuItems(Context context, MenuCallback callback) {
        initQueue(context);
        String url = BASE_URL + "/menu";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Type listType = new TypeToken<List<MenuItem>>() {}.getType();
                    List<MenuItem> menuItems = gson.fromJson(response.toString(), listType);
                    if (callback != null) {
                        callback.onSuccess(menuItems);
                    }
                },
                error -> Log.e("Volley", "Error fetching menu items: " + error.getMessage())
        );
        requestQueue.add(request);
    }


    public static void addMenuItem(Context context, MenuItem item, AddMenuItemCallback callback) {
        initQueue(context);
        String url = BASE_URL + "/menu/add";

        try {
            JSONObject json = new JSONObject();
            json.put("name", item.getName());
            json.put("price", item.getPrice());
            json.put("description", item.getDescription());
            json.put("category", item.getCategory());
            json.put("image_url", item.getImageUrl());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json,
                    response -> {

                        MenuItem savedItem = gson.fromJson(response.toString(), MenuItem.class);
                        if (callback != null) {
                            callback.onSaved(savedItem);
                        }
                    },
                    error -> Log.e("API", "Error: " + error.getMessage())
            );
            requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMenuItem(Context context, int id) {
        initQueue(context);
        String url = BASE_URL + "/menu/delete/" + id;
        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> Log.d("MenuService", "Deleted"),
                error -> Log.e("MenuError", "Error: " + error.getMessage())
        );
        requestQueue.add(request);
    }
}