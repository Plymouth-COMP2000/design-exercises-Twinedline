package com.example.resturantmanagmentapp;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Api {

    private static final String BASE_URL = "http://10.0.2.2:8000";
    private static RequestQueue requestQueue;
    private static final Gson gson = new Gson();

    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    // --- MENU METHODS ---

    public static void getAllMenuItems(Context context, MenuCallback callback) {
        initQueue(context);
        String url = BASE_URL + "/menu";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Type listType = new TypeToken<List<MenuItem>>() {}.getType();
                    List<MenuItem> menuItems = gson.fromJson(response.toString(), listType);
                    if (callback != null) callback.onSuccess(menuItems);
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
                        if (callback != null) callback.onSaved(savedItem);
                    },
                    error -> Log.e("API", "Error adding menu: " + error.getMessage())
            );
            requestQueue.add(request);
        } catch (JSONException e) { e.printStackTrace(); }
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

    // Inside Api.java
    public static void updateMenuItem(Context context, MenuItem item, UpdateCallback callback) {
        initQueue(context);

        String url = BASE_URL + "/menu/update/" + item.getId();
        Log.d("MENU_API", "URL: " + url);

        try {
            JSONObject json = new JSONObject();
            json.put("name", item.getName());
            json.put("price", item.getPrice());
            json.put("description", item.getDescription());
            json.put("category", item.getCategory());
            json.put("image_url", item.getImageUrl());

            Log.d("MENU_API", "Body: " + json.toString());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT, url, json,
                    response -> {
                        Log.d("MENU_API", "SUCCESS: " + response.toString());
                        if (callback != null) callback.onUpdated();
                    },
                    error -> {
                        String body = "";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            body = new String(error.networkResponse.data);
                        }
                        Log.e("MENU_API", "ERROR: " + body);
                        if (callback != null) callback.onError(body);
                    }
            );

            requestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void addReservation(Context context, Reservation res, AddReservationCallback callback) {
        initQueue(context);
        String url = BASE_URL + "/reservations/add";
        try {
            JSONObject json = new JSONObject();
            json.put("customer_name", res.customerName);
            // Convert to Integer for safety
            json.put("number_of_people", Integer.parseInt(res.numberOfPeople));
            json.put("reservation_date", res.date);
            json.put("reservation_time", res.time);
            json.put("location", res.location);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json,
                    response -> { if (callback != null) callback.onSaved("Success"); },
                    error -> { if (callback != null) callback.onError(error.getMessage()); }
            );
            requestQueue.add(request);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void getAllReservations(Context context, ReservationCallback callback) {
        initQueue(context);
        String url = BASE_URL + "/reservations";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Reservation> reservations = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            Reservation res = new Reservation();
                            res.id = obj.getInt("ReservationID");
                            res.customerName = obj.getString("customer_name");
                            res.numberOfPeople = obj.getString("number_of_people");
                            res.date = obj.getString("reservation_date");
                            res.time = obj.getString("reservation_time");
                            res.location = obj.optString("location", "Outside: Garden");
                            reservations.add(res);
                        }
                        if (callback != null) callback.onSuccess(reservations);
                    } catch (JSONException e) { e.printStackTrace(); }
                },
                error -> Log.e("API_ERROR", error.toString())
        );
        requestQueue.add(request);
    }

    public static void updateReservation(Context context, int resId, Reservation res, UpdateCallback callback) {
        initQueue(context);

        String url = BASE_URL + "/reservations/update/" + resId;

        try {
            JSONObject json = new JSONObject();

            json.put("customer_name", res.customerName);

            json.put("number_of_people", res.numberOfPeople);

            json.put("reservation_date", res.date);

            json.put("reservation_time", res.time);
            json.put("location", res.location);

            Log.d("API_DEBUG", "MATCHING URL: " + url);
            Log.d("API_DEBUG", "MATCHING Body: " + json.toString());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, json,
                    response -> {
                        Log.d("API_DEBUG", "SUCCESS: " + response.toString());
                        if (callback != null) callback.onUpdated();
                    },
                    error -> {
                        String body = "";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            body = new String(error.networkResponse.data);
                        }
                        Log.e("API_DEBUG", "STILL ERROR: " + error.toString() + " | Body: " + body);
                        if (callback != null) callback.onError(error.toString());
                    }
            );

            request.setShouldCache(false);
            requestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteReservation(Context context, int reservationId, DeleteCallback callback) {
        initQueue(context);
        String url = BASE_URL + "/reservations/delete/" + reservationId;
        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                response -> { if (callback != null) callback.onDeleted(); },
                error -> { if (callback != null) callback.onError(error.toString()); }
        );
        requestQueue.add(request);
    }

    public interface MenuCallback { void onSuccess(List<MenuItem> items); }
    public interface AddMenuItemCallback { void onSaved(MenuItem savedItem); }
    public interface AddReservationCallback { void onSaved(String message); void onError(String error); }
    public interface ReservationCallback { void onSuccess(List<Reservation> reservations); }
    public interface UpdateCallback { void onUpdated(); void onError(String error); }
    public interface DeleteCallback { void onDeleted(); void onError(String error); }
}