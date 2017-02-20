package com.example.legible.seguridadargusapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Legible on 2/17/2017.
 */

public class ZonaFragment extends Fragment {

    private ZonaRecyclerAdapter mAdapter;

    public ZonaFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflates the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_zona,container,false);

        //Capture the recyclerView

        RecyclerView recyclerView = (RecyclerView)
                view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);

        //Create the Adapter
        mAdapter = new ZonaRecyclerAdapter(this.getContext(),recyclerView);

        //Binding
        recyclerView.setAdapter(mAdapter);


        return view;





    }
}
