package com.lugma.LugmaAgent.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lugma.LugmaAgent.GroupDetailActivity;
import com.lugma.LugmaAgent.R;
import com.lugma.LugmaAgent.RestaurantDetailActivity;
import com.lugma.LugmaAgent.Utilities.Group;
import com.lugma.LugmaAgent.Utilities.Restaurant;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeBele-PC on 14-12-2017.
 */

public class RestaurantAdapter  extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>{
    List<Restaurant> restaurantList = new ArrayList<Restaurant>();
    Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView groupName,restaurant,status;
        public LinearLayout ll_listitem;
        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            groupName = (TextView) view.findViewById(R.id.group);
            restaurant=(TextView)view.findViewById(R.id.restaurant);
            status = (TextView)view.findViewById(R.id.status);
        }

        @Override
        public void onClick(View view) {
            int pos = this.getAdapterPosition();
            Animation animation = AnimationUtils.loadAnimation(mContext,
                    R.anim.blink);
            view.startAnimation(animation);
//            view.clearAnimation();
            Toast.makeText(mContext,"CLicked",Toast.LENGTH_LONG).show();
            Restaurant restaurant = restaurantList.get(pos);
            Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
            intent.putExtra("parcel_data", restaurant);
            view.getContext().startActivity(intent);

        }
    }


    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        this.mContext=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.MyViewHolder holder, int position) {

        Restaurant restaurant = restaurantList.get(position);
        holder.groupName.setText(String.valueOf(restaurant.getGroupname()));
        holder.restaurant.setText(String.valueOf(restaurant.getRestaurentName()));
        holder.status.setText(String.valueOf(restaurant.getStatus()));
        if (restaurant.getStatus().toUpperCase().equals("ACTIVE")){
            holder.status.setTextColor(mContext.getResources().getColor(R.color.activeColor));
        }else if(restaurant.getStatus().toUpperCase().equals("PENDING")){
            holder.status.setTextColor(mContext.getResources().getColor(R.color.pendColor));
        }else {
            holder.status.setTextColor(mContext.getResources().getColor(R.color.nonconformColor));
        }
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
