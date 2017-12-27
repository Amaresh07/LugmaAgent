package com.lugma.LugmaAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.lugma.LugmaAgent.Adapter.GroupAdapter;
import com.lugma.LugmaAgent.Adapter.GroupSpinnerAdpter;
import com.lugma.LugmaAgent.Utilities.Constants;
import com.lugma.LugmaAgent.Utilities.Group;
import com.lugma.LugmaAgent.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddRestaurantActivity extends AppCompatActivity {
  Spinner groupSpinner,typeSpinner;
    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    String types[];
    ArrayAdapter<String> spinnerArrayAdapter;
    MyAdapter groupSpinnerAdpter;
    ArrayList<Group> groupList = new ArrayList<Group>();
    String type,groupId;
    private Bitmap bitmap;
    private String userChoosenTask;
    ImageView imageView;
    final int CAMERA_REQUEST = 13323;
    final int GALLERY_REQUEST = 22131;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        groupSpinner = (Spinner) findViewById(R.id.groupspinner);
//        typeSpinner = (Spinner)findViewById(R.id.typespinner);
        imageView = (ImageView)findViewById(R.id.add_thumb);
        initializeGroups();

        initializeTypes();
//        setTypeAdapter();
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());
    }

    private void initializeTypes(){
        types = new String[]{
                "Type of Restaurant",
                "Claimbed",
                "Unclaimbed",
                "Premium",
        };
    }
    private void setTypeAdapter(){
        spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,types
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        typeSpinner.setAdapter(spinnerArrayAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type=types[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initializeGroups(){
        groupList.add(new Group(-1,"Select Group"));
      new GetGroupssAsyncTask().execute();
    }
    private void setGroupAdapter(){
        groupSpinnerAdpter = new MyAdapter(getApplicationContext(),R.layout.spinner_item,groupList);
        groupSpinner.setAdapter(groupSpinnerAdpter);
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             Group group = groupSpinnerAdpter.getItem(i);
                groupId=String.valueOf(group.getGroup_id());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public class MyAdapter extends ArrayAdapter<Group> {
        Context mContext;
        ArrayList<Group> groups=new ArrayList<Group>();
        public MyAdapter(Context ctx, int txtViewResourceId, ArrayList<Group> groupArrayList) {
            super(ctx, txtViewResourceId, groupArrayList);

        }
        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt)
        {
            return getCustomView(position, cnvtView, prnt);
        }
        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt)
        {
            return getCustomView(pos, cnvtView, prnt);
        }
        public Group getItem(int position){
            return groupList.get( position );
        }
        public View getCustomView(int position, View convertView, ViewGroup parent)
        {

            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.spinner_item, parent, false);
            TextView main_text = (TextView) mySpinner .findViewById(R.id.group_name);
            main_text.setText(groupList.get(position).getGroup_name());
            return mySpinner;
        }
    }
    private class GetGroupssAsyncTask extends AsyncTask<Object, Object, Void> {
        ProgressDialog loading;
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(AddRestaurantActivity.this,"Logging in...","Wait...",false,false);
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
            loading.cancel();
            setGroupAdapter();
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
                Group group = new Group(Integer.parseInt(json.getString("group_id")),json.getString("group_name"));
                groupList.add(group);
            }
//            Toast.makeText(GroupActivity.this,jsonData,Toast.LENGTH_LONG).show();

        }
    }
    public void onClickupload(View v) {
        switch (v.getId()) {
            case R.id.add_thumb:  galleryIntent();
                break;
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 98, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddRestaurantActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(AddRestaurantActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),GALLERY_REQUEST);
    }

    private void cameraIntent()
    {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST)
                onSelectFromGalleryResult(data);
            else if (requestCode == CAMERA_REQUEST)
                onCaptureImageResult(data);
        }
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        bitmap= thumbnail;//utility_classes.getResizedBitmap(thumbnail,500);
        imageView.setImageBitmap(bitmap);
    }
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        try {
            bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            bitmap=bm;//utility_classes.getResizedBitmap(bm,500);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
