package com.example.legible.seguridadargusapp.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import com.example.legible.seguridadargusapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SERGIO on 25/02/2017.
 */

public class GuardiaRecyclerAdapter extends RecyclerView.Adapter<GuardiaRecyclerAdapter.ViewHolder>{

    private Context mContext;
    private List<guardias> mGuardia;

    private DatabaseReference mGuardiasRef;


    public static List<guardias> filterGuardias;

    public GuardiaRecyclerAdapter(Context context){
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
            guardias currentGuardia =  dataSnapshot.getValue(guardias.class);
            mGuardia.add(0,currentGuardia);
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

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_guardia,parent,false);


        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final guardias currentGuardia = mGuardia.get(position);

        holder.bindToView(currentGuardia);

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
