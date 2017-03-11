package com.example.legible.seguridadargusapp.Controller;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.View.GuardiaInfoDialogFragment;
import com.example.legible.seguridadargusapp.View.GuardiaMoveDialogFragment;
import com.example.legible.seguridadargusapp.View.GuardiaSignatureActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Guard;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SERGIO on 25/02/2017.
 */

public class GuardiaListaRecyclerAdapter extends RecyclerView.Adapter<GuardiaListaRecyclerAdapter.ViewHolder>{

    private List<guardias> mGuardiasList;
    private Context mContext;
    private DatabaseReference guardiaListaRef;
    private String clienteRef;
    private String guardiaNombreRef;
    private android.support.v4.app.FragmentManager fm;

    public GuardiaListaRecyclerAdapter(Context context, String clienteRef, android.support.v4.app.FragmentManager fm){

        mGuardiasList =  new ArrayList<>();
        this.clienteRef = clienteRef;
        mContext = context;
        this.fm = fm;


        //Database Reference Setup

        guardiaListaRef = FirebaseDatabase.getInstance().getReference()
                .child("Argus")
                .child("Clientes")
                .child(clienteRef)
                .child("clienteGuardias");

        guardiaListaRef.addChildEventListener(new GuardiaListChildEventListener());


    }

    class GuardiaListChildEventListener implements ChildEventListener{
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            guardias guardia = dataSnapshot.getValue(guardias.class);
            guardia.setKey(dataSnapshot.getKey());
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
        //Todo Delete ASAP tested to see example of Signature
        holder.nameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, GuardiaSignatureActivity.class));
            }
        });



        holder.optionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showGuardiaOptionsMenu(guardia.getKey(),guardia.getUsuarioNombre());


                //showDialogFragment(guardia.getKey());

            }
        });


    }


    public void showGuardiaOptionsMenu(final String key,final String guardiaNombre){

        final String options[] = {"Detalles", "Mover"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("Opciones");
        dialog.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0 :
                        // Detallles;
                        showGuardiaInfoDialogFragment(key);
                        break;

                    case 1 :
                        //Mover
                        showMoveGuardiaDialogFragment(key,guardiaNombre);

                        break;
                    default:
                        dialog.dismiss();
                }
            }
        });

        dialog.show();

    }

    public void showGuardiaInfoDialogFragment(String guardiaRef){

        GuardiaInfoDialogFragment df = GuardiaInfoDialogFragment.newInstance(guardiaRef);
        df.show(fm,"fragment_guardia_info");
    }

    public void showMoveGuardiaDialogFragment(String key,String guardiaNombre){
        GuardiaMoveDialogFragment df = GuardiaMoveDialogFragment.newInstance(clienteRef,"supervisor",key,guardiaNombre);
        df.show(fm,"fragment_guardia_move");
    }


    @Override
    public int getItemCount() {
        return mGuardiasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        ImageView optionMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
            optionMenu = (ImageView) itemView.findViewById(R.id.imageViewOption);

        }
    }
}
