package com.example.legible.seguridadargusapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.View.Activity.GuardiaListaActivity;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Cliente;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.View.Activity.GuardiaSignatureActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SERGIO on 20/02/2017.
 */

public class ClienteRecyclerAdapter extends RecyclerView.Adapter<ClienteRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private final List<Cliente> mClient;
    public static String myCliente;
    public static String myZona;
    public static String mySupervisor;
    public static String mySupervisorKey;
    //Firebase Reference
    private DatabaseReference mClientRef;


    public ClienteRecyclerAdapter(Context context, String Zona,String supervisor) {

        mClient = new ArrayList<>();
        mContext = context;//mInflator = LayoutInflator.from(context);

        //get all the clients from the database reference
        mClientRef = FirebaseDatabase.getInstance().getReference()
                .child("Argus")
                .child("Zonas")
                .child(Zona)
                .child("zonaClientes");


        mClientRef.addChildEventListener(new ClientChildEventListener());

        myZona = Zona;
        mySupervisor = supervisor;

    }


    class ClientChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            Cliente cliente = dataSnapshot.getValue(Cliente.class);

            boolean bandera = false;

            if (mClient.size()>0) {
                for (Cliente Cliente : mClient) {
                    if (Cliente.getClienteNombre().equals(cliente.getClienteNombre()))
                        bandera = true;
                }
            }

            if (bandera==false){
                mClient.add(mClient.size(),cliente);
            }

            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            //Empty
        }



        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

            Cliente clientRemoved = dataSnapshot.getValue(Cliente.class);

            int i = 0;

            for (Cliente cliente : mClient){
                if (mClient.get(i).getClienteNombre().equals(clientRemoved.getClienteNombre())){

                    mClient.remove(i);
                }
                i++;
            }

            notifyItemRemoved(i);

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            //Empty
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            //Firebase Error
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

        Collections.sort(mClient, new CompareServices());

        final Cliente currentCliente = mClient.get(position);

        holder.bindToView(currentCliente);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Todo my Cliente Ref
                Intent i = new Intent(mContext, GuardiaSignatureActivity.class);
                i.putExtra("cliente",currentCliente.getClienteNombre());
                myCliente = currentCliente.getClienteNombre();


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