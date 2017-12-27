package com.lugma.LugmaAgent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lugma.LugmaAgent.Utilities.Constants;
import com.lugma.LugmaAgent.Utilities.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SigninActivity extends AppCompatActivity {
Button signin;
    EditText etemail,etpassword;
    String result2,email,password,name,emp_id,mobile,dob,gender,address;
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        session = new UserSessionManager(getApplicationContext());
        signin=(Button)findViewById(R.id.signin);
        etemail = (EditText)findViewById(R.id.emailid);
        etpassword = (EditText)findViewById(R.id.password);

    }

    public  void OnCick(View view){
        switch (view.getId()){
            case R.id.signin:
                final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                final String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
                email=etemail.getText().toString();
                password = etpassword.getText().toString();
                if (etemail.length() <= 0) {
                    Toast.makeText(this, "Please enter registered email", Toast.LENGTH_SHORT).show();
                    etemail.requestFocus();
                } else if (etpassword.length() <= 0) {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                    etpassword.requestFocus();
                }else if (!email.matches(emailPattern) && !email.matches(emailPattern2)) {
                    Toast.makeText(this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
                    etemail.requestFocus();
                }
                else {
                    new SigninAsyncTask().execute();
                }
                break;
            case R.id.forgotpass:
                break;
        }
    }
    private class SigninAsyncTask extends AsyncTask<Object, Object, Void> {
        ProgressDialog loading;
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(SigninActivity.this,"Logging in...","Wait...",false,false);
        }
        public Void doInBackground(Object... params) {
            //In background these both functions will be executed
            try {
                signin();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // The system calls this to perform work in the UI thread and
        // delivers the result from doInBackground() method defined above
        @Override
        protected void onPostExecute(Void result) {

            if (result2.equals("Authenticated")) {
                loading.cancel();
                session.createUserLoginSession(emp_id,email,mobile,name,dob,gender,address);
                // Starting MainActivity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.putExtra("member_name",member_name);
                startActivity(i);
                finish();
            }
            else {
                loading.cancel();
                Toast.makeText(SigninActivity.this, result2, Toast.LENGTH_SHORT).show();
                Toast.makeText(SigninActivity.this, "Login once again with valid authentication", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signin() throws JSONException, IOException {
        email = etemail.getText().toString();
        password = etpassword.getText().toString();
        OkHttpClient client2 = new OkHttpClient();
        RequestBody body2 = new FormBody.Builder()
                .add("email",email)
                .add("password", password)
                .build();
        // Log.d(TAG,"In try "+body2);// Log is used to Display some text in Android Monitor-Logcat below in Android Studio(like printf for Checking)
        Request request2 = new Request.Builder()
                .url(Constants.SERVER_URL+"salessignin")
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
            emp_id = object.getString("emp_id");
            name = object.getString("emp_name");
            mobile = object.getString("mobile");
            dob = object.getString("dob");
            gender = object.getString("gender");
            address = object.getString("address");
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
