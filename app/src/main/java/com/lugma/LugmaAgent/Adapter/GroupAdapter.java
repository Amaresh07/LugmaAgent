package com.lugma.LugmaAgent.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lugma.LugmaAgent.GroupDetailActivity;
import com.lugma.LugmaAgent.R;
import com.lugma.LugmaAgent.Utilities.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeBele-PC on 14-12-2017.
 */

public class GroupAdapter  extends RecyclerView.Adapter<GroupAdapter.MyViewHolder>  {
    List<Group> groupList = new ArrayList<Group>();
    Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView groupName,website;
        public LinearLayout ll_listitem;
        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            groupName = (TextView) view.findViewById(R.id.group_name);
            website=(TextView)view.findViewById(R.id.website);
        }

        @Override
        public void onClick(View view) {
            int pos = this.getAdapterPosition();
            Group group = groupList.get(pos);
            Intent intent = new Intent(mContext, GroupDetailActivity.class);
// using putExtra(String key, Parcelable value) method
            intent.putExtra("parcel_data", group);
            view.getContext().startActivity(intent);

        }
    }

    public GroupAdapter(Context context, List<Group> groupList) {
        this.groupList = groupList;
        this.mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupAdapter.MyViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.groupName.setText(String.valueOf(group.getGroup_name()));
        holder.website.setText(String.valueOf(group.getWebsite()));
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
