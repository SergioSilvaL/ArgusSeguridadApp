package com.example.legible.seguridadargusapp.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
 * Created by sergiosilva on 5/1/17.
 */

public class BitacoraRegistroAdapter extends RecyclerView.Adapter<BitacoraRegistroAdapter.ViewHolder>{


    private List<BitacoraRegistro> mRegistro;
    private Callback mCallback;
    private DatabaseReference mBitacoraRegistroRef;

    public BitacoraRegistroAdapter(Callback callback){
        mCallback = callback;
        mRegistro = new ArrayList<>();
        mBitacoraRegistroRef = FirebaseDatabase.getInstance().getReference().child("Argus").child("BitacoraRegistro").child(new DatePost().getDateKey()).child(ClienteRecyclerAdapter.mySupervisorKey);
        mBitacoraRegistroRef.addChildEventListener(new bitacoraRegistroChildEventListener());
    }

    private class bitacoraRegistroChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            BitacoraRegistro registro = dataSnapshot.getValue(BitacoraRegistro.class);
            if (registro.getObservacion()!=null)  {
                registro.setKey(dataSnapshot.getKey());
                mRegistro.add(mRegistro.size(), registro);
                notifyDataSetChanged();
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            BitacoraRegistro updatedBitacoraRegistro = dataSnapshot.getValue(BitacoraRegistro.class);
            for (BitacoraRegistro br: mRegistro){
                if (br.getKey().equals(key)){
                    br.setValues(updatedBitacoraRegistro);
                    notifyDataSetChanged();
                    return;
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            // TODO
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            // Empty
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Firebase Error
        }
    }
    @Override
    public BitacoraRegistroAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_bitacora_registro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BitacoraRegistroAdapter.ViewHolder holder, final int position) {
        final BitacoraRegistro bitacoraRegistro = mRegistro.get(position);
        holder.mObservacionTextView.setText(bitacoraRegistro.getObservacion());
        holder.mHoraTextView.setText(bitacoraRegistro.getHora());

        switch ((int) bitacoraRegistro.getSemaforo()){
            case 1:
                holder.mSemaforoImageView.setBackgroundResource(R.drawable.semaforo_verde);
                break;
            case 2:
                holder.mSemaforoImageView.setBackgroundResource(R.drawable.semaforo_amarillo);
                break;
            case 3:
                holder.mSemaforoImageView.setBackgroundResource(R.drawable.semaforo_rojo);
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
        return mRegistro.size();
    }

    public void add(BitacoraRegistro bitacoraRegistro){
        bitacoraRegistro.setHora(new DatePost().get24HourFormat());
        mBitacoraRegistroRef.push().setValue(bitacoraRegistro);
    }

    public void update(BitacoraRegistro bitacoraRegistro, String newObservacion, long newSemaforoStatus){
        bitacoraRegistro.setObservacion(newObservacion);
        bitacoraRegistro.setSemaforo(newSemaforoStatus);
        mBitacoraRegistroRef.child(bitacoraRegistro.getKey()).setValue(bitacoraRegistro);
    }

    public interface Callback {
        public void onEdit(BitacoraRegistro bitacoraRegistro);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mObservacionTextView;
        private TextView mHoraTextView;
        private ImageView mSemaforoImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mObservacionTextView = (TextView) itemView.findViewById(R.id.textViewBitacoraRegistroObservacion);
            mHoraTextView = (TextView) itemView.findViewById(R.id.textViewBitacoraRegistroHora);
            mSemaforoImageView = (ImageView) itemView.findViewById(R.id.imageViewSemaforo);
        }
    }


}
