package com.example.legible.seguridadargusapp.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.Consigna;
import com.example.legible.seguridadargusapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 4/27/17.
 */

public class ConsignasAdapter extends RecyclerView.Adapter<ConsignasAdapter.ViewHolder> {

    private List<String> mConsignas;
    private Callback mCallback;
    DatabaseReference mConsigaTareaRef;

    public ConsignasAdapter(Callback callback) {

        mCallback = callback;
        mConsignas = new ArrayList<>();
        mConsigaTareaRef  = FirebaseDatabase.getInstance().getReference().child("Argus").child("Consigna").child("Intech");
        mConsigaTareaRef.addChildEventListener(new ConsignaChildEventListener());
    }

    private class ConsignaChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            mConsignas.add(0,dataSnapshot.getKey());
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
    public ConsignasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_consignas,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConsignasAdapter.ViewHolder holder, int position) {

        final String consigna = mConsignas.get(position);

        TextView textView = holder.textViewConsignaTarea;
        textView.setText(consigna);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open new Dialog
                mCallback.onEdit(consigna);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mConsignas.size();
    }

    public void add(Consigna consigna){
//        //TODO: Remove the lines(s) and use Firebase instead
//        mConsignas.add(0,consigna);
//        notifyDataSetChanged();
    }

    public void update(){}

    public void  remove(){}

    public interface Callback {
        public void onEdit(String consigna);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewConsignaTarea;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewConsignaTarea = (TextView) itemView.findViewById(R.id.textViewConsignaTarea);

        }
    }
}
