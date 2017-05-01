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
