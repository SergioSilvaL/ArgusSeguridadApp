package com.example.legible.seguridadargusapp.Controller;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.BitacoraRegistro;
import com.example.legible.seguridadargusapp.Model.ObjectModel.DatePost;
import com.example.legible.seguridadargusapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 5/9/17.
 */

public class BitacoraRegistroNoResueltoAdapter
        extends RecyclerView.Adapter<BitacoraRegistroNoResueltoAdapter.ViewHolder>{

    private Callback mCallback;
    private List<BitacoraRegistro> mRegistroList;
    private DatabaseReference mBitacoraRegistroNRref;

    public BitacoraRegistroNoResueltoAdapter(Callback callback){
        mCallback  = callback;
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
        holder.mHoraTextView.setText(bitacoraRegistro.getHora());
        holder.mObservacionTextView.setText(bitacoraRegistro.getObservacion());

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onEdit(bitacoraRegistro);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRegistroList.size();
    }

    public void update(BitacoraRegistro bitacoraRegistro, String newObservacion, long newSemaforoStatus){
        bitacoraRegistro.setObservacion(newObservacion);
        bitacoraRegistro.setSemaforo(newSemaforoStatus);


        remove(bitacoraRegistro);

        // Son de la misma fecha
        // Todo Remove with old code
        if (bitacoraRegistro.getDateCreationKey().equals(new DatePost().getDateKey())){
            // Se agrega dentro de la otra lista y tiene to_do el dia para resolver
            DatabaseReference bitacoraRegistroNRRef = FirebaseDatabase.getInstance().getReference()
                    .child("Argus")
                    .child("BitacoraRegistro")
                    .child(new DatePost().getDateKey())
                    .child(ClienteRecyclerAdapter.mySupervisorKey)
                    .child(new DatePost().getTimeCompletetKey());

            bitacoraRegistroNRRef.setValue(bitacoraRegistro);
        }
    }

    private void remove(BitacoraRegistro bitacoraRegistro) {
        mBitacoraRegistroNRref.child(bitacoraRegistro.getKey()).removeValue();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mObservacionTextView;
        private TextView mHoraTextView;
        private LinearLayout mSemaforoView;

        public ViewHolder(View itemView) {
            super(itemView);
            mObservacionTextView = (TextView) itemView.findViewById(R.id.textViewBitacoraRegistroObservacion);
            mHoraTextView = (TextView) itemView.findViewById(R.id.textViewBitacoraRegistroHora);
            mSemaforoView = (LinearLayout) itemView.findViewById(R.id.LinearLayoutSemaforoRepresentation);
        }
    }


    private class BitacoraRegistroNRChildListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            BitacoraRegistro registro = dataSnapshot.getValue(BitacoraRegistro.class);
            registro.setKey(dataSnapshot.getKey());
            mRegistroList.add(mRegistroList.size(), registro);
            notifyDataSetChanged();
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
