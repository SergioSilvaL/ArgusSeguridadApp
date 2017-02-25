package com.example.legible.seguridadargusapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by SERGIO on 25/02/2017.
 */

public class GuardiaListaAdapter extends RecyclerView.Adapter<GuardiaListaAdapter.ViewHolder>{

    private List<guardias> mGuardiasList;
    private Context mContext;
    private DatabaseReference guardiaListaRef;
    private String clienteRef;


    public GuardiaListaAdapter(Context context, String clienteRef){

        mGuardiasList =  new ArrayList<>();
        this.clienteRef = clienteRef;
        mContext = context;



        //Database Reference Setup

        guardiaListaRef = FirebaseDatabase.getInstance().getReference()
                .child("Argus")
                .child("Clientes")

                //.child("TECNO")
                .child(clienteRef)

                .child("clienteGuardias");

        guardiaListaRef.addChildEventListener(new GuardiaListChildEventListener());


    }

    class GuardiaListChildEventListener implements ChildEventListener{
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            guardias guardia = dataSnapshot.getValue(guardias.class);
            mGuardiasList.add(0,guardia);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lista_guardia,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final guardias guardia = mGuardiasList.get(position);
        holder.nameTxt.setText(guardia.getUsuarioNombre());
    }

    @Override
    public int getItemCount() {
        return mGuardiasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        public ViewHolder(View itemView) {
            super(itemView);
            nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
        }
    }
}
