package com.example.legible.seguridadargusapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by SERGIO on 20/02/2017.
 */

public class ClienteRecyclerAdapter extends RecyclerView.Adapter<ClienteRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private final List<Cliente> mCliente;
    private Random mRandom;

    public ClienteRecyclerAdapter(Context context, RecyclerView recyclerView) {
        mCliente = new ArrayList<>();//mPasswords = new ArrayList<>();
        mContext = context;//mInflator = LayoutInflator.from(context);
        mRandom = new Random();
        mRecyclerView = recyclerView;


        //Dummy Content
        for (int i = 0; i < 19; i++) {
            mCliente.add(new Cliente(getRandomZona()));
        }
    }

    private String getRandomZona() {
        String[] names = new String[]{
                "SALUD",
                "ISSSTESON",
                "NORTE",
                "RIO",
                "NOROESTE",
                "CENTRO",
                "CFE",
                "AGUA DE HERMOSILLO",
                "CUBREDESCANSO"
        };
        return names[mRandom.nextInt(names.length)];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_zona, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Cliente currentCliente = mCliente.get(position);

        holder.bindToView(currentCliente);
    }

    @Override
    public int getItemCount() {
        return mCliente.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewZonaName;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewZonaName = (TextView) itemView.findViewById(R.id.textViewZonaName);

        }

        public void bindToView(Cliente currentCliente) {
            textViewZonaName.setText(currentCliente.getClienteNombre());
        }
    }
}
