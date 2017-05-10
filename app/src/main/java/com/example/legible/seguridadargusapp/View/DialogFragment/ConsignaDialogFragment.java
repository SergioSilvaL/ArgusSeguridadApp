package com.example.legible.seguridadargusapp.View.DialogFragment;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.legible.seguridadargusapp.Controller.ConsignaDialogFragmentAdapter;
import com.example.legible.seguridadargusapp.Model.ObjectModel.Consigna;
import com.example.legible.seguridadargusapp.R;

/**
 * Created by sergiosilva on 4/29/17.
 */

public class ConsignaDialogFragment extends DialogFragment implements  ConsignaDialogFragmentAdapter.Callback{

    private RecyclerView recyclerView;
    ConsignaDialogFragmentAdapter myAdapter;

    public ConsignaDialogFragment(){
        //Empty constructor required for DialogFragment
    }

    public static ConsignaDialogFragment newInstance(String consigna){
        ConsignaDialogFragment frag = new ConsignaDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", consigna);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString("title"));

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view  = inflater.inflate(R.layout.dialog_fragment_consigna, null);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewConsignaDialogFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setHasFixedSize(true);
        myAdapter = new ConsignaDialogFragmentAdapter(this, getArguments().getString("title"));
        recyclerView.setAdapter(myAdapter);



        builder.setView(view);

        builder.setPositiveButton("ok" ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Anadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showAddEditDialog(null);
            }
        });

        return builder.create();

    }

    @Override
    public void onEdit(final Consigna consigna) {
        showAddEditDialog(consigna);
    }

    private void showAddEditDialog(final Consigna consignaTarea) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText edittext = new EditText(getContext());
        alert.setTitle(consignaTarea == null ? "Agregar Consigna" : "Editar Consigna");
        if (consignaTarea!= null)
        edittext.setText(consignaTarea.getConsignaNombre());
        alert.setView(edittext);


        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value

                String text = edittext.getText().toString();

                if (consignaTarea == null){
                    myAdapter.add(new Consigna(text));
                }else{
                    myAdapter.update(consignaTarea, text);
                }
            }
        });

        alert.setNegativeButton(android.R.string.cancel, null);

        alert.show();

    }



}
