package com.example.legible.seguridadargusapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by SERGIO on 20/02/2017.
 */

public class ClienteRecyclerAdapter extends RecyclerView.Adapter<ClienteRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private final List<Cliente> mClient;
    private Random mRandom;

    //Firebase Reference
    private DatabaseReference mClientRef;
    private String ZonaRef;

    public ClienteRecyclerAdapter(Context context, RecyclerView recyclerView,String ZonaRef) {
        mClient = new ArrayList<>();//mPasswords = new ArrayList<>();
        mContext = context;//mInflator = LayoutInflator.from(context);
        mRandom = new Random();
        mRecyclerView = recyclerView;
        this.ZonaRef=ZonaRef;

        //get all the clients from the database reference
        mClientRef = FirebaseDatabase.getInstance().getReference()
                .child("Argus")
                .child("Zonas")

                //.child(ZonaRef)
                .child("ZONA3")

                .child("zonaClientes");


        mClientRef.addChildEventListener(new ClientChildEventListener());


    }

    class ClientChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Cliente cliente = dataSnapshot.getValue(Cliente.class);
            Log.v("Cliente: ",cliente.getClienteNombre());
            mClient.add(0,cliente);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_zona, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Cliente currentCliente = mClient.get(position);

        holder.bindToView(currentCliente);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GuardiaListaActivity.class);
                intent.putExtra("Cliente",currentCliente.getClienteNombre());
                Log.v("OnClickCliente: ",currentCliente.getClienteNombre());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClient.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewZonaName;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewZonaName = (TextView) itemView.findViewById(R.id.textViewZonaName);

        }

        public void bindToView(Cliente currentCliente) {
            textViewZonaName.setText(currentCliente.getClienteNombre());
        }
    }
}