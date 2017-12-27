package com.lugma.LugmaAgent;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.lugma.LugmaAgent.Adapter.GroupAdapter;
import com.lugma.LugmaAgent.Utilities.Constants;
import com.lugma.LugmaAgent.Utilities.Group;
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

public class GroupActivity extends AppCompatActivity {
    List<Group> groupList = new ArrayList<>();
    GroupAdapter groupAdapter;
    String result2,emp_id;
    UserSessionManager session;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        this.setTitle("Groups");
        session = new UserSessionManager(getApplicationContext());
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        if(session.checkLogin())
        {
            Toast.makeText(this, "You are not logged in Please Login Once again", Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            HashMap<String, String> user = session.getUserDetails();
            emp_id=user.get(UserSessionManager.KEY_id);
        }
        groupList.clear();
        new GetGroupssAsyncTask().execute();
        groupAdapter = new GroupAdapter(getApplicationContext(),groupList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        recyclerView.setAdapter(groupAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert();
            }
        });
    }
    private class GetGroupssAsyncTask extends AsyncTask<Object, Object, Void> {
        ProgressDialog loading;
        protected void onPreExecute() {
            super.onPreExecute();
//            loading = ProgressDialog.show(GroupActivity.this,"Logging in...","Wait...",false,false);
        }
        public Void doInBackground(Object... params) {
            //In background these both functions will be executed
            try {
                getGroups();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // The system calls this to perform work in the UI thread and
        // delivers the result from doInBackground() method defined above
        @Override
        protected void onPostExecute(Void result) {
//            loading.cancel();
            groupAdapter = new GroupAdapter(getApplicationContext(),groupList);
            recyclerView.setAdapter(groupAdapter);
        }
    }

    public void getGroups() throws IOException, JSONException {
        OkHttpClient client2 = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url(Constants.SERVER_URL+"getAllGroups")
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
            Log.d("GroupActivity","json:"+jsonData);
//            JSONObject object = new JSONObject(jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);
            Log.d("GroupActivity","json:"+jsonArray.getJSONObject(0).toString());
            for (int i=0;i<jsonArray.length();i++){
                JSONObject json = jsonArray.getJSONObject(i);
                Group group = new Group(Integer.parseInt(json.getString("group_id")),json.getString("group_name"),json.getString("website"),json.getString("address"),
                        json.getString("contact_person"),json.getString("telephone"),json.getString("mobile"),json.getString("email"));
                groupList.add(group);
            }
//            Toast.makeText(GroupActivity.this,jsonData,Toast.LENGTH_LONG).show();

        }
    }

    public void showAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GroupActivity.this);
        alertDialog.setTitle("Add Group");
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_add_group, (ViewGroup)findViewById(R.id.addgroup_layout));
        final EditText etGroup_name = alertLayout.findViewById(R.id.group_name);
        final EditText etWebsite = alertLayout.findViewById(R.id.website);
        final EditText etEmail = alertLayout.findViewById(R.id.email);
        final EditText etContact= alertLayout.findViewById(R.id.contact_person);
        final EditText etTelephone = alertLayout.findViewById(R.id.tel_no);
        final EditText etAddress = alertLayout.findViewById(R.id.address);
        final EditText etDesignation = alertLayout.findViewById(R.id.designation);
        final EditText etMobileno = alertLayout.findViewById(R.id.mobile_no);
        alertDialog.setView(alertLayout);
        alertDialog.setPositiveButton("ADD",
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
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
//                String MobilePattern = "[0-9]{10}";


//                String contact=etContact.getText().toString();
                Boolean wantToCloseDialog = false;
                //Do stuff, possibly set wantToCloseDialog to true then...
                if(etGroup_name.length()<=0){
                   Toast.makeText(v.getContext(),"Enter Group name",Toast.LENGTH_LONG).show();
                   etGroup_name.requestFocus();
                }else if(etWebsite.length()<=0){
                    Toast.makeText(v.getContext(), "Enter Website", Toast.LENGTH_SHORT).show();
                    etWebsite.requestFocus();
                }else if(etEmail.length()<=0){
                    Toast.makeText(v.getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }else if(etContact.length()<=0){
                    Toast.makeText(v.getContext(), "Enter Contact", Toast.LENGTH_SHORT).show();
                    etContact.requestFocus();
                }
                else if(etTelephone.length()<=0){
                Toast.makeText(v.getContext(), "Enter Telephone", Toast.LENGTH_SHORT).show();
                etTelephone.requestFocus();
            }
            else if(etAddress.length()<=0) {
                    Toast.makeText(v.getContext(), "Enter Address", Toast.LENGTH_SHORT).show();
                    etAddress.requestFocus();
                }
                else if(etDesignation.length()<=0) {
                    Toast.makeText(v.getContext(), "Enter Designation", Toast.LENGTH_SHORT).show();
                    etDesignation.requestFocus();
                }
                else if(etMobileno.length()<=0) {
                    Toast.makeText(v.getContext(), "Enter Mobileno", Toast.LENGTH_SHORT).show();
                    etMobileno.requestFocus();
                }
                else{
                    String groupname=etGroup_name.getText().toString();
                    String website=etWebsite.getText().toString();
                    String email=etEmail.getText().toString();
                    String telphone=etTelephone.getText().toString();
                    String contact=etContact.getText().toString();
                    String address=etAddress.getText().toString();
                    String designation=etDesignation.getText().toString();
                    String mobileno=etMobileno.getText().toString();
                    Group group=new Group(groupname,website,address,contact,designation,telphone,mobileno,email);
                    new AddgroupAsyncTask(group).execute();
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

    private class AddgroupAsyncTask extends AsyncTask<Object, Object, Void> {
        ProgressDialog loading;
        Group group;
        public AddgroupAsyncTask(Group group) {
            this.group=group;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(GroupActivity.this,"Updating in...","Wait...",false,false);
        }
        public Void doInBackground(Object... params) {
            //In background these both functions will be executed
            try {
                update(group);
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
                if(result2.equals("added successfully")){
                    finish();
                    startActivity(getIntent());
                }
                Toast.makeText(GroupActivity.this, result2, Toast.LENGTH_SHORT).show();

        }
    }

    private void update(Group group) throws JSONException, IOException {

        OkHttpClient client2 = new OkHttpClient();
        RequestBody body2 = new FormBody.Builder()
                .add("group_name", group.getGroup_name())
                .add("website",group.getWebsite())
                .add("address",group.getAddress())
                .add("contact_person",group.getContact_person())
                .add("designation",group.getDesignation())
                .add("telephone",group.getTelephone())
                .add("mobile",group.getMobile())
                .add("email",group.getEmail())
                .build();
        // Log.d(TAG,"In try "+body2);// Log is used to Display some text in Android Monitor-Logcat below in Android Studio(like printf for Checking)
        Request request2 = new Request.Builder()
                .url(Constants.SERVER_URL+"addgroup")
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
            result2 = object.getString("res");

        }
    }
}
