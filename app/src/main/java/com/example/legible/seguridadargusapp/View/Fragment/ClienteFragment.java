package com.example.legible.seguridadargusapp.View.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Model.ObjectModel.Cliente;
import com.example.legible.seguridadargusapp.Model.ObjectModel.supervisores;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.Controller.ClienteRecyclerAdapter;
import com.example.legible.seguridadargusapp.View.Activity.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by Legible on 2/17/2017.
 */

public class ClienteFragment extends Fragment {

    private ClienteRecyclerAdapter mAdapter;
    private static final String TAG = "ClientFragment";

    public ClienteFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //Inflates the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        //Create the Adapter
        ClienteRecyclerAdapter mAdapter = new ClienteRecyclerAdapter(
                ClienteFragment.this.getContext(),
                ClienteRecyclerAdapter.myZona,
                ClienteRecyclerAdapter.mySupervisor);

        RecyclerView recyclerView = (RecyclerView)
                view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(mAdapter);

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Perform final Search
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Cliente> newList = new ArrayList<>();

                for(Cliente cliente : ClienteRecyclerAdapter.filterClientes){
                    String name = cliente.getClienteNombre().toLowerCase();

                    if (name.contains(newText)){
                        newList.add(cliente);
                    }
                }

                mAdapter.setFilter(newList);
                return false;
            }
        });
    }
}