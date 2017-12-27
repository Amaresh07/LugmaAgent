package com.lugma.LugmaAgent.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lugma.LugmaAgent.R;
import com.lugma.LugmaAgent.Utilities.Group;

import java.util.ArrayList;

/**
 * Created by CodeBele-PC on 20-12-2017.
 */

public class GroupSpinnerAdpter extends ArrayAdapter<Group> {
    private Context mcontext;
    private ArrayList<Group> groups;
    private Activity activity;
    LayoutInflater inflater;
    public GroupSpinnerAdpter(@NonNull Context context, @LayoutRes int resource,ArrayList<Group> groups) {
        super(context, resource,groups);
        this.mcontext=context;
        this.groups=groups;
    }
    public int getCount(){
        return groups.size();
    }

    public Group getItem(int position){
        return groups.get( position );
    }
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Group group=groups.get(position);
        LayoutInflater mInflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=mInflater.inflate(R.layout.spinner_item, parent, false);
        TextView groupName= (TextView)row.findViewById(R.id.group_name);

            groupName.setText("ahaha");

        return row;
    }

}
