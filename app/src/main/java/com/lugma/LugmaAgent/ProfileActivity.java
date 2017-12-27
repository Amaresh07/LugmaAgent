package com.lugma.LugmaAgent;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lugma.LugmaAgent.Utilities.Constants;
import com.lugma.LugmaAgent.Utilities.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {
    EditText etname,etphone,etdob,etaddress;
    TextView tvuname,tvemail,tvchangepassword;
    String uname,phone,email,dob,address,result2,emp_id;
    Calendar myCalendar ;
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = new UserSessionManager(getApplicationContext());
        tvuname = (TextView) findViewById(R.id.uname);
        etname = (EditText)findViewById(R.id.username);
        etphone = (EditText)findViewById(R.id.phone);
        tvemail = (TextView)findViewById(R.id.emailid);
        etdob = (EditText)findViewById(R.id.dob);
        etaddress = (EditText)findViewById(R.id.address);
        tvchangepassword = (TextView)findViewById(R.id.changepassword);
        tvchangepassword.setPaintFlags(tvchangepassword.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        myCalendar = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            etdob.setShowSoftInputOnFocus(false);
        } else { // API 11-20
            etdob.setTextIsSelectable(true);
        }
        if(session.checkLogin())
        {
            Toast.makeText(this, "You are not logged in Please Login Once again", Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            HashMap<String, String> user = session.getUserDetails();
            emp_id = user.get(UserSessionManager.KEY_id);
            uname = user.get(UserSessionManager.KEY_NAME);
            email = user.get(UserSessionManager.KEY_email);
            phone = user.get(UserSessionManager.KEY_phone);
            dob = user.get(UserSessionManager.KEY_dob);
            address = user.get(UserSessionManager.KEY_address);
            tvuname.setText(uname);
            tvemail.setText(email);
            etphone.setText(phone);
            etname.setText(uname);
            etdob.setText(dob);
            etaddress.setText(address);
        }
        etdob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        etdob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            updateLabel();
                        }

                    };
                    new DatePickerDialog(ProfileActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
    }

   public void OnClick(View view){
       switch (view.getId()){
           case R.id.changepassword:
               showAlert();
       }
   }
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etdob.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.update) {
            Toast.makeText(this,"Updated",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void showAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
        alertDialog.setTitle("Change Password");
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.change_password, (ViewGroup)findViewById(R.id.passlayout));
        final EditText etCurrentPass = alertLayout.findViewById(R.id.currentpassword);
        final EditText etNewPasss = alertLayout.findViewById(R.id.newpassword);
        final EditText etConfirmPass = alertLayout.findViewById(R.id.confirmpassword);
        alertDialog.setView(alertLayout);
        alertDialog.setPositiveButton("SUBMIT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
//                String MobilePattern = "[0-9]{10}";
               String currentpass=etCurrentPass.getText().toString();
               String newPass=etNewPasss.getText().toString();
                String confirmPass=etConfirmPass.getText().toString();
                Boolean wantToCloseDialog = false;
                //Do stuff, possibly set wantToCloseDialog to true then...
                if(currentpass.length()<=0){
                    Toast.makeText(v.getContext(),"Enter Current password",Toast.LENGTH_LONG).show();
                    etCurrentPass.requestFocus();
                }else if(etNewPasss.length()<=0){
                    Toast.makeText(v.getContext(), "Enter new passsword", Toast.LENGTH_SHORT).show();
                    etNewPasss.requestFocus();
                }else if(confirmPass.length()<=0){
                    Toast.makeText(v.getContext(), "Confirm passsword", Toast.LENGTH_SHORT).show();
                    etConfirmPass.requestFocus();
                }else if(!newPass.equals(confirmPass)){
                    Toast.makeText(v.getContext(), "Password confirmation is wrong", Toast.LENGTH_SHORT).show();
                    etConfirmPass.requestFocus();
                }else{
                    currentpass=etCurrentPass.getText().toString();
                    newPass=etNewPasss.getText().toString();
                    new PasswordChangeAsyncTask(currentpass,newPass).execute();
                    wantToCloseDialog=true;
                }
                if(wantToCloseDialog)
                    dialog.dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean wantToCloseDialog = true;
                //Do stuff, possibly set wantToCloseDialog to true then...
                if(wantToCloseDialog)
                    dialog.dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }


    private class PasswordChangeAsyncTask extends AsyncTask<Object, Object, Void> {
        ProgressDialog loading;
        private String currentpass,newPass;
        public PasswordChangeAsyncTask(String currentpass, String newPass)
        {
            this.currentpass=currentpass;
            this.newPass=newPass;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(ProfileActivity.this,"Updating...","Wait...",false,false);
        }
        public Void doInBackground(Object... params) {
            //In background these both functions will be executed
            try {
                update(currentpass,newPass);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // The system calls this to perform work in the UI thread and
        // delivers the result from doInBackground() method defined above
        @Override
        protected void onPostExecute(Void result) {

                loading.cancel();
                Toast.makeText(ProfileActivity.this, result2, Toast.LENGTH_SHORT).show();
            }
        }

    private void update(String curpass,String newpass) throws JSONException, IOException {

        OkHttpClient client2 = new OkHttpClient();
        RequestBody body2 = new FormBody.Builder()
                .add("emp_id",emp_id)
                .add("password", curpass)
                .add("new_password",newpass)
                .build();
        // Log.d(TAG,"In try "+body2);// Log is used to Display some text in Android Monitor-Logcat below in Android Studio(like printf for Checking)
        Request request2 = new Request.Builder()
                .url(Constants.SERVER_URL+"changePassword")
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

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
