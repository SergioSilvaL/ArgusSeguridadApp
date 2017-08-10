package com.example.legible.seguridadargusapp.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.View.DialogFragment.GuardiaInfoDialogFragment;
import com.example.legible.seguridadargusapp.View.DialogFragment.GuardiaMoveDialogFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SERGIO on 25/02/2017.
 */

public class GuardiaRecyclerAdapter extends RecyclerView.Adapter<GuardiaRecyclerAdapter.ViewHolder>{

    private Context mContext;
    private List<guardias> mGuardia;
    private android.support.v4.app.FragmentManager fm;

    private DatabaseReference mGuardiasRef;


    public static List<guardias> filterGuardias;

    public GuardiaRecyclerAdapter(Context context, android.support.v4.app.FragmentManager fm){
        this.fm = fm;
        mGuardia =  new ArrayList<>();
        mContext = context;

        //Database reference
        mGuardiasRef = FirebaseDatabase.getInstance().getReference()
                .child("Argus")
                .child("guardias");

        mGuardiasRef.addChildEventListener(new GuardiaChildEventListener());
        filterGuardias = mGuardia;

    }

    public void setFilter(ArrayList<guardias> newList){
        mGuardia = new ArrayList<>();
        mGuardia.addAll(newList);
        notifyDataSetChanged();
    }

    class GuardiaChildEventListener implements ChildEventListener{
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            if (!dataSnapshot.getKey().equals("undefined")){

                guardias currentGuardia = dataSnapshot.getValue(guardias.class);

                boolean bandera = false;

                if (mGuardia.size() > 0) {
                    for (guardias guardia : mGuardia) {
                        if (guardia.getUsuarioNombre().equals(currentGuardia.getUsuarioNombre()))
                            bandera = true;
                    }
                }

                // Vertifies that all guards inside the list are available(Disponible)
                if (bandera == false && currentGuardia.isUsuarioDisponible()) {

                    currentGuardia.setUsuarioKey(dataSnapshot.getKey());
                    mGuardia.add(0, currentGuardia);

                }

                notifyDataSetChanged();
            }


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
                .inflate(R.layout.view_guardia,parent,false);


        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Collections.sort(mGuardia, new CompareGuard());

        final guardias currentGuardia = mGuardia.get(position);

        holder.bindToView(currentGuardia);


        // Makes Cards Clickable
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGuardiaOptionsMenu(currentGuardia.getUsuarioKey(), currentGuardia.getUsuarioNombre());
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
        GuardiaMoveDialogFragment df = GuardiaMoveDialogFragment.newInstance(
                null,
                ClienteRecyclerAdapter.mySupervisor,
                key,
                guardiaNombre);

        df.show(fm,"fragment_guardia_move");
    }

    @Override
    public int getItemCount() {
        return mGuardia.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView mTextView;
        private ImageView mImageViewDisponible;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.nameTxtGuardia);
            mImageViewDisponible = (ImageView) itemView.findViewById(R.id.imageViewDisponibleStatus);


        }

        public void bindToView(guardias currentGuardia) {
            mTextView.setText(currentGuardia.getUsuarioNombre());
            if (currentGuardia.isUsuarioDisponible()){
                mImageViewDisponible.setBackgroundResource(R.drawable.disponible);
            }
        }
    }
}
