package com.lugma.LugmaAgent.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lugma.LugmaAgent.R;

/**
 * Created by CodeBele-PC on 14-12-2017.
 */

public class BranchFragment extends Fragment{
    View v;
    public BranchFragment() {
    }

    public static BranchFragment newInstance() {
        
        Bundle args = new Bundle();
        
        BranchFragment fragment = new BranchFragment();
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
//        return super.onCreateView(inflater, container, savedInstanceState);
        v=inflater.inflate(R.layout.fragment_branch,container,false);
        return v;
    }

}
