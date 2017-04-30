package com.example.legible.seguridadargusapp.Model.ObjectModel;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 4/27/17.
 */

public class Consigna {

    private String consignaNombre;
    private String key;
    private List<String> tareaList;

    public Consigna(){}

    public Consigna(String consignaNombre) {
        this.consignaNombre = consignaNombre;
       tareaList = new ArrayList<>();
        populateTareas();
    }

    private void populateTareas(){

        String tarea = "tarea";

        for (int i = 0; i< 10; i++){
            tareaList.add(0, tarea += "" + i);
        }

    }

    public List<String> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<String> tareaList) {
        this.tareaList = tareaList;
    }

    public String getConsignaNombre() {
        return consignaNombre;
    }

    public void setConsignaNombre(String consignaNombre) {
        this.consignaNombre = consignaNombre;
    }

    @Exclude

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
