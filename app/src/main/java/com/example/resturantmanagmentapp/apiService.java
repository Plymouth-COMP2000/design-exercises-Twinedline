package com.example.resturantmanagmentapp;

import android.content.Context;
import android.util.Log;

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

    private static final String BASE_URL = "http://10.224.41.11/comp2000";
    private static RequestQueue requestQueue;
    private static final Gson gson = new Gson();

    // Initialize RequestQueue if needed
    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    // Method to get all menu items
    public static void getAllMenuItems(Context context) {
        initQueue(context);
        String url = BASE_URL + "/menu";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<MenuItem>>() {}.getType();
                        List<MenuItem> menuItems = gson.fromJson(response.toString(), listType);

                        // Handle the list of menu items
                        for (MenuItem menuItem : menuItems) {
                            System.out.println(menuItem.getName());

                            // Log the menu item info
                            Log.d("MenuItemInfo", "Name: " + menuItem.getName() +
                                    ", Price: " + menuItem.getPrice());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error fetching menu items: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(request);
    }

    // Add a menu item
    public static void addMenuItem(Context context, MenuItem menuItem) {
        initQueue(context);
        String url = BASE_URL + "/menu/add";

        try {
            JSONObject jsonRequest = new JSONObject(gson.toJson(menuItem));

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            String message = response.optString("message", "Menu item added successfully");
                            Log.d("MenuService", message);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("MenuError", "Error adding menu item: " + error.getMessage());
                        }
                    }
            );

            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("MenuError", "Invalid JSON format: " + e.getMessage());
        }
    }

    // Method to delete a menu item
    public static void deleteMenuItem(Context context, int id) {
        initQueue(context);
        String url = BASE_URL + "/menu/delete/" + id;

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MenuService", "Menu item deleted successfully");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MenuError", "Error deleting menu item: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(request);
    }
}
