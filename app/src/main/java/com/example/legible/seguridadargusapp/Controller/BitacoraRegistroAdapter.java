package com.example.legible.seguridadargusapp.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            registro.setKey(dataSnapshot.getKey());
            mRegistro.add(mRegistro.size(), registro);
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
    public BitacoraRegistroAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_bitacora_registro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BitacoraRegistroAdapter.ViewHolder holder, final int position) {
        final BitacoraRegistro bitacoraRegistro = mRegistro.get(position);
        holder.mObservacionTextView.setText(bitacoraRegistro.getObservacion());
        holder.mHoraTextView.setText(bitacoraRegistro.getHora());
    }



    @Override
    public int getItemCount() {
        return mRegistro.size();
    }

    public void add(BitacoraRegistro bitacoraRegistro){
        //TODO: Get Current Hour
        bitacoraRegistro.setHora(new DatePost().get24HourFormat());
        mBitacoraRegistroRef.push().setValue(bitacoraRegistro);
    }

    public interface Callback {
        public void onEdit(BitacoraRegistro bitacoraRegistro);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mObservacionTextView;
        private TextView mHoraTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mObservacionTextView = (TextView) itemView.findViewById(R.id.textViewBitacoraRegistroObservacion);
            mHoraTextView = (TextView) itemView.findViewById(R.id.textViewBitacoraRegistroHora);
        }
    }


}
