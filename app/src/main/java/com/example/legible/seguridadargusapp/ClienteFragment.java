package com.example.legible.seguridadargusapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * Created by Legible on 2/17/2017.
 */

public class ClienteFragment extends Fragment {

    private ClienteRecyclerAdapter mAdapter;

    public ClienteFragment(){
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Get the Reference


        String ZonaRef = "";//getArguments().getString("ZonaSupervisorRef");*/

        //Inflates the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cliente,container,false);

        //Capture the recyclerView

        RecyclerView recyclerView = (RecyclerView)
                view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);

        //Create the Adapter
        mAdapter = new ClienteRecyclerAdapter(this.getContext(),recyclerView, ZonaRef);

        //Binding
        recyclerView.setAdapter(mAdapter);

        return view;
    }
}