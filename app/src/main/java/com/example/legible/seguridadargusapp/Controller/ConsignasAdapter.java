package com.example.legible.seguridadargusapp.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legible.seguridadargusapp.Model.ObjectModel.Consigna;
import com.example.legible.seguridadargusapp.R;
import com.example.legible.seguridadargusapp.View.Activity.ConsignasActivity;
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
    private Context mContext;

    public ConsignasAdapter(Callback callback, Context context) {
        mContext = context;
        mCallback = callback;
        mConsignas = new ArrayList<>();
        mConsigaTareaRef  = FirebaseDatabase.getInstance().getReference().child("Argus").child("Consigna").child(ClienteRecyclerAdapter.myCliente);
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
            Toast.makeText(mContext, "Intente  de nuevo", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(mContext, "Intente  de nuevo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public ConsignasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_consignas,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConsignasAdapter.ViewHolder holder, final int position) {

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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(mConsignas.get(position));
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mConsignas.size();
    }

    public void add(String consigna){
        mConsigaTareaRef.child(consigna).push().setValue(new Consigna(""));
    }

    public void update(){}

    public void  remove(String s){
        mConsigaTareaRef.child(s).removeValue();
    }

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
