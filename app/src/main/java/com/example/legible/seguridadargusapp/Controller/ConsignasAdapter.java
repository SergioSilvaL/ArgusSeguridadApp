package com.example.legible.seguridadargusapp.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.Consigna;
import com.example.legible.seguridadargusapp.R;

import java.util.List;

/**
 * Created by sergiosilva on 4/27/17.
 */

public class ConsignasAdapter extends RecyclerView.Adapter<ConsignasAdapter.ViewHolder> {

    private List<Consigna> mConsignas;

    public ConsignasAdapter(List<Consigna> mConsignas) {
        this.mConsignas = mConsignas;
    }

    public void addConsigna(Consigna consigna){
        mConsignas.add(0,consigna);
        notifyDataSetChanged();
    }

    @Override
    public ConsignasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_consignas,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConsignasAdapter.ViewHolder holder, int position) {

        Consigna consignas = mConsignas.get(position);

        TextView textView = holder.textViewConsignaTarea;
        textView.setText(consignas.getConsignaTarea());

    }

    @Override
    public int getItemCount() {
        return mConsignas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewConsignaTarea;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewConsignaTarea = (TextView) itemView.findViewById(R.id.textViewConsignaTarea);

        }
    }
}
