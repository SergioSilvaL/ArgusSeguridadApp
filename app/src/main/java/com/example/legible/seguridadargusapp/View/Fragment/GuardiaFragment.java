package com.example.legible.seguridadargusapp.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.Controller.GuardiaRecyclerAdapter;

/**
 * Created by Legible on 2/17/2017.
 */

public class GuardiaFragment extends Fragment {

    private GuardiaRecyclerAdapter mAdapter;


    public GuardiaFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflates the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_guardia,container,false);

        //Capture the recyclerView

        RecyclerView recyclerView = (RecyclerView)
                view.findViewById(R.id.recyclerView_guardia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);

        //Create the Adapter
        mAdapter = new GuardiaRecyclerAdapter(this.getContext());

        //Binding
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}