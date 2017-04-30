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
 * Created by sergiosilva on 4/29/17.
 */

public class ConsignaDialogFragmentAdapter extends RecyclerView.Adapter<ConsignaDialogFragmentAdapter.ViewHolder>{


    private List<Consigna> mConsignaList;
    private Callback mCallback;
    private DatabaseReference mConsignaTareaRef;

    public ConsignaDialogFragmentAdapter(Callback callback){
        mCallback = callback;
        mConsignaList = new ArrayList<>();

        mConsignaTareaRef = FirebaseDatabase.getInstance().getReference().child("Argus").child("Consigna").child("Intech").child("Cerco");
        mConsignaTareaRef.addChildEventListener(new ConsignaChildEventListener());

    }

    private class ConsignaChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            Consigna consigna = dataSnapshot.getValue(Consigna.class);
            consigna.setKey(dataSnapshot.getKey());

            mConsignaList.add(0, consigna);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            Consigna updatedConsigna = dataSnapshot.getValue(Consigna.class);
            for (Consigna consigna : mConsignaList){
                if (consigna.getKey().equals(key)){
                    consigna.setConsignaNombre(updatedConsigna.getConsignaNombre());
                    notifyDataSetChanged();
                    return;
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            // Remove the item with the given key
            for (Consigna consigna : mConsignaList){
                if (consigna.getKey().equals(key)){
                    mConsignaList.remove(consigna);
                    break;
                }
            }
            notifyDataSetChanged();
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
    public ConsignaDialogFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_consignas, null);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConsignaDialogFragmentAdapter.ViewHolder holder, final int position) {

        final Consigna consignaTarea = mConsignaList.get(position);

        TextView textView = holder.textViewConsignaTarea;
        textView.setText(consignaTarea.getConsignaNombre());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onEdit(consignaTarea);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(mConsignaList.get(position));
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mConsignaList.size();
    }

    public void update(Consigna consigna ,String newConsignaTarea) {
        consigna.setConsignaNombre(newConsignaTarea);
        mConsignaTareaRef.child(consigna.getKey()).setValue(consigna);
    }

    public void add(Consigna consigna) {
        mConsignaTareaRef.push().setValue(consigna);
    }

    public void remove( Consigna consigna){
        mConsignaTareaRef.child(consigna.getKey()).removeValue();
    }

    public interface Callback {
        public void onEdit(Consigna consigna);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewConsignaTarea;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewConsignaTarea = (TextView) itemView.findViewById(R.id.textViewConsignaTarea);

        }
    }


}
