package com.lugma.LugmaAgent.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lugma.LugmaAgent.Adapter.RestaurantAdapter;
import com.lugma.LugmaAgent.R;
import com.lugma.LugmaAgent.Utilities.Constants;
import com.lugma.LugmaAgent.Utilities.Restaurant;
import com.lugma.LugmaAgent.Utilities.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by CodeBele-PC on 15-12-2017.
 */

public class ClaimedFragment extends Fragment {
    View v;
    List<Restaurant> restaurantList = new ArrayList<>();
    RestaurantAdapter restaurantAdapter;
    private RecyclerView recyclerView;
    UserSessionManager session;
    private String emp_id;
    public static String type;
    ProgressBar progressBar;
    public ClaimedFragment() {
    }

    public static ClaimedFragment newInstance(String type) {
        ClaimedFragment.type=type;
        Bundle args = new Bundle();
        ClaimedFragment fragment = new ClaimedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_claimedtab, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        progressBar=(ProgressBar)v.findViewById(R.id.progressBar) ;
        session = new UserSessionManager(v.getContext());
        if(session.checkLogin())
        {
            Toast.makeText(v.getContext(), "You are not logged in Please Login Once again", Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String, String> user = session.getUserDetails();
            emp_id = user.get(UserSessionManager.KEY_id);

        }
        restaurantList.clear();

        restaurantAdapter = new RestaurantAdapter(v.getContext(),restaurantList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new getRestaurantAsyncTask().execute();
        recyclerView.setAdapter(restaurantAdapter);
        return v;
    }
    private class getRestaurantAsyncTask extends AsyncTask<Object, Object, Void> {
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        public Void doInBackground(Object... params) {
            //In background these both functions will be executed
            try {
                getRestaurants();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // The system calls this to perform work in the UI thread and
        // delivers the result from doInBackground() method defined above
        @Override
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(restaurantAdapter);

        }
    }
    private void getRestaurants() throws JSONException, IOException {
        OkHttpClient client2 = new OkHttpClient();
        RequestBody body2 = new FormBody.Builder()
                .add("emp_id","4")
                .add("status", type.toLowerCase())
                .add("restaurant_type","Claimed")
                .build();
        // Log.d(TAG,"In try "+body2);// Log is used to Display some text in Android Monitor-Logcat below in Android Studio(like printf for Checking)
        Request request2 = new Request.Builder()
                .url(Constants.SERVER_URL+"getRestaurantsBystatus")
                .post(body2)
                .build();
        Response responses = null;
        try {
            responses=client2.newCall(request2).execute();
            //Log.d(TAG,"In try");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(responses != null) {
            String jsonData = responses.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);
            Log.d("GroupActivity","json:"+jsonData);
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Restaurant restaurant=new Restaurant(Integer.parseInt(json.getString("restaurant_id")),json.getString("restaurant_name"),json.getString("restaurant_info"),json.getString("website"),json.getString("contact_person")
                        ,json.getString("designation"),json.getString("email"),json.getString("mobile"),json.getString("telephone"),json.getString("whatsapp_mobile")
                        ,json.getString("facebook"),json.getString("instagram"),json.getString("twitter"),json.getString("meal_for_two"),json.getString("status"),"Group");
                restaurantList.add(restaurant);
            }
        }
    }
}
