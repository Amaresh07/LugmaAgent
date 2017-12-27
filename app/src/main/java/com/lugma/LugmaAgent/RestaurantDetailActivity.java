package com.lugma.LugmaAgent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lugma.LugmaAgent.Utilities.Group;
import com.lugma.LugmaAgent.Utilities.Restaurant;

public class RestaurantDetailActivity extends AppCompatActivity {
    TextView tvRname,tvWebsite,tvAddress,tvCon_per,tvCon_num,tvTel,tvEmail,tvDes,tvRes,tvFace,tvTwit,tvInst,tvWatsup;
    ImageView imCir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        imCir=(ImageView)findViewById(R.id.circleimage);
        tvRname = (TextView)findViewById(R.id.restaurant_name);
        tvWebsite=(TextView)findViewById(R.id.website);
        tvAddress=(TextView)findViewById(R.id.address);
        tvCon_per=(TextView)findViewById(R.id.contact_person);
        tvCon_num=(TextView)findViewById(R.id.contact_number);
        tvTel=(TextView)findViewById(R.id.telephone) ;
        tvEmail=(TextView)findViewById(R.id.email);
        tvDes=(TextView)findViewById(R.id.designation);
        tvRes=(TextView)findViewById(R.id.res_type);
        tvFace=(TextView)findViewById(R.id.facebook);
        tvTwit=(TextView)findViewById(R.id.twitter);
        tvInst=(TextView)findViewById(R.id.instagram);
        tvWatsup=(TextView)findViewById(R.id.whatsup);
        Restaurant restaurant = (Restaurant) getIntent().getParcelableExtra("parcel_data");
        tvRname.setText(restaurant.getRestaurentName());
        tvWebsite.setText(restaurant.getWebsite());
        tvCon_per.setText(restaurant.getContact_person());
        tvCon_num.setText(restaurant.getMobile());
        tvTel.setText(restaurant.getTelephone());
        tvEmail.setText(restaurant.getEmail());
        tvDes.setText(restaurant.getDesignation());
        tvFace.setText(restaurant.getFacebook());
        tvTwit.setText(restaurant.getTwitter());
        tvInst.setText(restaurant.getInstagram());
        tvWatsup.setText(restaurant.getWhatsapp_mobile());

    }
}
