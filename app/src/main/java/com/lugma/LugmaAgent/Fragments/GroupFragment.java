package com.lugma.LugmaAgent.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lugma.LugmaAgent.Adapter.GroupAdapter;
import com.lugma.LugmaAgent.R;
import com.lugma.LugmaAgent.Utilities.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeBele-PC on 14-12-2017.
 */

public class GroupFragment extends Fragment {
    View v;
    List<Group> groupList = new ArrayList<>();
    GroupAdapter groupAdapter;
    private RecyclerView recyclerView;
    public GroupFragment() {
    }

    public static GroupFragment newInstance() {
        
        Bundle args = new Bundle();
        
        GroupFragment fragment = new GroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_group, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        groupAdapter = new GroupAdapter(v.getContext(),groupList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(groupAdapter);
        return v;
    }
}
