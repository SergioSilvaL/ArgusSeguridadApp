package com.example.legible.seguridadargusapp.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.Controller.GuardiaRecyclerAdapter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Legible on 2/17/2017.
 */

public class GuardiaFragment extends Fragment {

    private GuardiaRecyclerAdapter mAdapter;


    public GuardiaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflates the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_guardia, container, false);

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Perform final Search
                Log.v(TAG, "Query text Sumbmit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                ArrayList<guardias> newList = new ArrayList<guardias>();

                for (guardias guardia: GuardiaRecyclerAdapter.filterGuardias){
                    String name = guardia.getUsuarioNombre().toLowerCase();

                    if (name.contains(newText)){
                        newList.add(guardia);
                    }
                }

                mAdapter.setFilter(newList);

                return false;
            }
        });
    }
}
