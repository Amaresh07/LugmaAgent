package com.lugma.LugmaAgent;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.lugma.LugmaAgent.Utilities.Group;

public class GroupDetailActivity extends AppCompatActivity {
   TextView tvgroup_name,tvwebsite,tvaddress,tvcperson,tvtelephone,tvmobile,tvemail;
    String group,website,addresss,cperson,telephone,mobile,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        tvwebsite = (TextView)findViewById(R.id.website);
        tvwebsite.setPaintFlags(tvwebsite.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        tvgroup_name=(TextView)findViewById(R.id.group_name);
        tvaddress = (TextView)findViewById(R.id.address);
        tvcperson = (TextView)findViewById(R.id.contact_person);
        tvtelephone = (TextView)findViewById(R.id.telephone);
        tvmobile = (TextView)findViewById(R.id.mobile);
        tvemail = (TextView)findViewById(R.id.email);
        Group group = (Group) getIntent().getParcelableExtra("parcel_data");
        tvgroup_name.setText(group.getGroup_name());
        tvwebsite.setText(group.getWebsite());
        tvaddress.setText(group.getAddress());
        tvcperson.setText(group.getContact_person());
        tvtelephone.setText(group.getTelephone());
        tvmobile.setText(group.getMobile());
        tvemail.setText(group.getEmail());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
