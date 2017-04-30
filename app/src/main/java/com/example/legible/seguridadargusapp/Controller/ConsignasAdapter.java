package com.example.legible.seguridadargusapp.Controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.legible.seguridadargusapp.Model.ObjectModel.Consigna;
import com.example.legible.seguridadargusapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 4/27/17.
 */

public class ConsignasAdapter extends RecyclerView.Adapter<ConsignasAdapter.ViewHolder> {

    private List<Consigna> mConsignas;
    private Callback mCallback;

    public ConsignasAdapter(Callback callback) {

        mCallback = callback;
        mConsignas = new ArrayList<>();
        //Populate Consignas
        mConsignas = getConsignas();
    }

    @Override
    public ConsignasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_consignas,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConsignasAdapter.ViewHolder holder, int position) {

        final Consigna consigna = mConsignas.get(position);

        TextView textView = holder.textViewConsignaTarea;
        textView.setText(consigna.getConsignaNombre());

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

    public ArrayList<Consigna> getConsignas(){

        List<Consigna> consignaList = new ArrayList<>();

        // ArrayList de Consginas
        for (int i = 0 ; i< 10; i++){
            Consigna consigna = new Consigna("consigna"+i);
            consignaList.add(mConsignas.size(),consigna);
        }

        return (ArrayList<Consigna>) consignaList;

    }

    public void add(Consigna consigna){
        //TODO: Remove the lines(s) and use Firebase instead
        mConsignas.add(0,consigna);
        notifyDataSetChanged();
    }

    public void update(){}

    public void  remove(){}

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
