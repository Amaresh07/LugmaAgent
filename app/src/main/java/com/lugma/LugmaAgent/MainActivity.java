package com.lugma.LugmaAgent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lugma.LugmaAgent.Utilities.Constants;
import com.lugma.LugmaAgent.Utilities.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity{
    UserSessionManager session;
    String member_name,email_id,gender,emp_id,result2,total,pending,nonverified,active;
    TextView tvemail,tvname,total_count,active_count,pending_count,nonverified_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvname = (TextView)findViewById(R.id.name);
        tvemail = (TextView)findViewById(R.id.emailid);
        total_count = (TextView)findViewById(R.id.total_count);

        active_count = (TextView)findViewById(R.id.active_count);
        pending_count =(TextView)findViewById(R.id.pending_count);
        nonverified_count=(TextView)findViewById(R.id.unconfirmed_count);
        session = new UserSessionManager(getApplicationContext());
        if(session.checkLogin())
        {
            Toast.makeText(MainActivity.this, "You are not logged in Please Login Once again", Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            HashMap<String, String> user = session.getUserDetails();
            member_name = user.get(UserSessionManager.KEY_NAME);
           email_id = user.get(UserSessionManager.KEY_email);
            emp_id = user.get(UserSessionManager.KEY_id);
            Log.d("MainActivty","emp_id:"+emp_id);
            tvname.setText(member_name);
            tvemail.setText(email_id);
        }
        setCounts();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.active_restaurent:
                Intent intent = new Intent(MainActivity.this,RestaurantActivity.class);
                intent.putExtra("type","Active");
                startActivity(intent);
                break;
            case R.id.total_restaurent:
                Intent intent1 = new Intent(MainActivity.this,RestaurantActivity.class);
                intent1.putExtra("type","All");
                startActivity(intent1);
                break;
            case R.id.pending_restaurent:
                Intent intent2 = new Intent(MainActivity.this,RestaurantActivity.class);
                intent2.putExtra("type","Pending");
                startActivity(intent2);
                break;
            case R.id.unconfirmed_restaurent:
                Intent intent3 = new Intent(MainActivity.this,RestaurantActivity.class);
                intent3.putExtra("type","Unconfirmed");
                startActivity(intent3);
                break;
            case R.id.group:
                Intent groupintent= new Intent(MainActivity.this,GroupActivity.class);
                startActivity(groupintent);
            case R.id.rewards:
                break;
            case R.id.edit:
                Intent intent4 = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent4);
                break;
            case R.id.addnew:
                Intent restaurant = new Intent(MainActivity.this,AddRestaurantActivity.class);
                startActivity(restaurant);
                break;
        }
    }

    private void setCounts(){
         new GetCountsAsyncTask().execute();
    }

    private class GetCountsAsyncTask extends AsyncTask<Object, Object, Void> {
        ProgressDialog loading;
        protected void onPreExecute() {
            super.onPreExecute();
//            loading = ProgressDialog.show(MainActivity.this,"Logging in...","Wait...",false,false);
        }
        public Void doInBackground(Object... params) {
            //In background these both functions will be executed
            try {
                getCounts();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // The system calls this to perform work in the UI thread and
        // delivers the result from doInBackground() method defined above
        @Override
        protected void onPostExecute(Void result) {
//            Toast.makeText(MainActivity.this,result2,Toast.LENGTH_LONG).show();
            total_count.setText(total);
            active_count.setText(active);
            pending_count.setText(pending);
            nonverified_count.setText(nonverified);

        }
    }

    private void getCounts() throws JSONException, IOException {
        OkHttpClient client2 = new OkHttpClient();
        RequestBody body2 = new FormBody.Builder()
                .add("emp_id",emp_id)
                .build();
        // Log.d(TAG,"In try "+body2);// Log is used to Display some text in Android Monitor-Logcat below in Android Studio(like printf for Checking)
        Request request2 = new Request.Builder()
                .url(Constants.SERVER_URL+"getCountOfRestaurants")
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
            JSONObject object = new JSONObject(jsonData);
            result2 = object.getString("message");
            total=object.getString("total_res");
            active=object.getString("active_res");
            pending=object.getString("pending_res");
            nonverified=object.getString("not_verified");

        }
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
//            Toast.makeText(this,"logout",Toast.LENGTH_LONG).show();
            session.logoutUser();
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
