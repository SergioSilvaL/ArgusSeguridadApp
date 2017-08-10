package com.example.legible.seguridadargusapp.Controller;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Model.ObjectModel.BitacoraRegistro;
import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
import com.example.legible.seguridadargusapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sergiosilva on 5/9/17.
 */

public class BitacoraRegistroNoResueltoAdapter
        extends RecyclerView.Adapter<BitacoraRegistroNoResueltoAdapter.ViewHolder>{

    private Callback mCallback;
    private List<BitacoraRegistro> mRegistroList;
    private DatabaseReference mBitacoraRegistroNRref;
    private Context mContext;

    public BitacoraRegistroNoResueltoAdapter(Callback callback, Context context){
        mCallback  = callback;
        mContext = context;
        mRegistroList = new ArrayList<>();
        mBitacoraRegistroNRref = FirebaseDatabase.getInstance().getReference()
                .child("Argus")
                .child("BitacoraRegistroNoResuelto")
                .child(ClienteRecyclerAdapter.mySupervisorKey);

        mBitacoraRegistroNRref.addChildEventListener(new BitacoraRegistroNRChildListener());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_bitacora_registro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BitacoraRegistro bitacoraRegistro = mRegistroList.get(position);
        holder.mObservacionTextView.setText(bitacoraRegistro.getDescripcion());
        holder.mTextViewFecha.setText(bitacoraRegistro.getDateCreation());

        switch ((int) bitacoraRegistro.getSemaforo()){
            case 1:
                holder.mSemaforoView.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                holder.mSemaforoView.setBackgroundColor(Color.YELLOW);
                break;
            case 3:
                holder.mSemaforoView.setBackgroundColor(Color.RED);
                break;
        }


        if (bitacoraRegistro.getSupervisorResponsibility()) {
            holder.mRatingBarSupervisorResponsibilityIndicator.setRating(1);
        }

        // Si es la responsabilidad del Supervisor de Resolverlo, lo podra editar  y resolverlo
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitacoraRegistro.getSupervisorResponsibility()) {
                    mCallback.onEdit(bitacoraRegistro);
                }else{
                    Toast.makeText(mContext, "No le corresponde al Supervisor", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mRegistroList.size();
    }

    public void update(BitacoraRegistro bitacoraRegistro, String newObservacion, long newSemaforoStatus){


        if (!bitacoraRegistro.getDescripcion().equals(newObservacion) || bitacoraRegistro.getSemaforo()!= newSemaforoStatus) {


            bitacoraRegistro.setDescripcion(newObservacion);
            bitacoraRegistro.setSemaforo(newSemaforoStatus);

            remove(bitacoraRegistro);

            if (newSemaforoStatus == 1) {

                // Son de la misma fecha
                if (bitacoraRegistro.getDateCreationKey().equals(new DatePost().getDateKey())) {
                    // Se agrega dentro de la otra lista y tiene to_do el dia para resolver
                    DatabaseReference bitacoraRegistroNRRef = FirebaseDatabase.getInstance().getReference()
                            .child("Argus")
                            .child("BitacoraRegistro")
                            .child(new DatePost().getDateKey())
                            .child(ClienteRecyclerAdapter.mySupervisorKey)
                            .child(bitacoraRegistro.getObservacionKey());

                    bitacoraRegistroNRRef.setValue(bitacoraRegistro);
                }
            } else {
                add(bitacoraRegistro);
            }

        }
    }

    private void add(BitacoraRegistro bitacoraRegistro){

        String key;

        if (bitacoraRegistro.getObservacionKey() == null) {
            key = new DatePost().getTimeCompletetKey();
        }else{
            key = bitacoraRegistro.getObservacionKey();
        }

        bitacoraRegistro.setObservacionKey(key);


        bitacoraRegistro.setKey(key);

        mBitacoraRegistroNRref.child(key).setValue(bitacoraRegistro);
        updateBitacoraRegistroNRSupervisorInfo(bitacoraRegistro.getSupervisor());
    }

    private void remove(BitacoraRegistro bitacoraRegistro) {
        mBitacoraRegistroNRref.child(bitacoraRegistro.getKey()).removeValue();
    }

    private void updateBitacoraRegistroNRSupervisorInfo(String supervisor){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/supervisor", supervisor);
        mBitacoraRegistroNRref.updateChildren(childUpdates);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mObservacionTextView;
        private TextView mTextViewFecha;
        private LinearLayout mSemaforoView;
        private RatingBar mRatingBarSupervisorResponsibilityIndicator;

        public ViewHolder(View itemView) {
            super(itemView);
            mObservacionTextView = (TextView) itemView.findViewById(R.id.textViewBitacoraRegistroObservacion);
            mSemaforoView = (LinearLayout) itemView.findViewById(R.id.LinearLayoutSemaforoRepresentation);
            mTextViewFecha = (TextView) itemView.findViewById(R.id.textViewFecha);
            mRatingBarSupervisorResponsibilityIndicator = (RatingBar) itemView.findViewById(R.id.ratingBarSupervisorResponsibilityIndicator);
        }
    }


    private class BitacoraRegistroNRChildListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            // TODO: ADD
            if (!dataSnapshot.getKey().equals("supervisor") && !dataSnapshot.getKey().equals("fecha")) {

                try {
                    BitacoraRegistro registro = dataSnapshot.getValue(BitacoraRegistro.class);
                    registro.setKey(dataSnapshot.getKey());
                    mRegistroList.add(mRegistroList.size(), registro);
                    notifyDataSetChanged();
                }catch (Exception e){
                    Log.v("BRegistroNoResuelto", e.toString());
                }


            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            // Remove the item with the given key
            for (BitacoraRegistro br : mRegistroList){
                if (br.getKey().equals(key)){
                    mRegistroList.remove(br);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    public interface Callback{
        public void onEdit(BitacoraRegistro bitacoraRegistro);
    }
}
