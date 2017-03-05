package com.example.legible.seguridadargusapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.legible.seguridadargusapp.ObjectModel.supervisores;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.adapter.ClienteRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by Legible on 2/17/2017.
 */

public class ClienteFragment extends Fragment {

    private ClienteRecyclerAdapter mAdapter;

    //Test it out on here
    private static final String TAG = "ClientFragment";


    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String ZonaReference = "";

    //Get Firebase Reference
    private DatabaseReference mDatabaseReference =
            FirebaseDatabase.getInstance().getReference()
                    .child("Argus")
                    .child("supervisores");


    public static Fragment newInstance(String zonaSupervisorRef) {
        //Save the Zona Database Reference
        Bundle bundle = new Bundle();
        bundle.putString("ZonaSupervisorRef", zonaSupervisorRef);

        // set Fragmentclass Arguments
        ClienteFragment fragobj = new ClienteFragment();
        fragobj.setArguments(bundle);
        return fragobj;
    }


    public ClienteFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        //ChildEvent Listener
//        mDatabaseReference.addValueEventListener(new ValueEventListenerZona());
//        Log.v(" OnCreate ", ZonaReference );

        String ZonaRef = "";
        Bundle arguments = getArguments();
        if (arguments != null) {
            ZonaRef = arguments.getString("ZonaSupervisorRef");
        }

        //Inflates the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        //Capture the recyclerView

        RecyclerView recyclerView = (RecyclerView)
                view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);

        //Create the Adapter
        mAdapter = new ClienteRecyclerAdapter(this.getContext(), recyclerView, ZonaRef);

        //Binding
        recyclerView.setAdapter(mAdapter);

        return view;
    }


//    private class ValueEventListenerZona implements ValueEventListener {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            for (DataSnapshot data : dataSnapshot.getChildren()){
//                supervisores supervisor = data.getValue(supervisores.class);
//
//                if (user.getEmail().equals(supervisor.getUsuarioEmail())){
//                    ZonaReference = supervisor.getUsuarioZona();
//                    Log.v(TAG, ZonaReference);
//                }
//
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    }


}