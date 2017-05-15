package com.example.legible.seguridadargusapp.Controller;

import com.example.legible.seguridadargusapp.Model.ObjectModel.Cliente;
import com.example.legible.seguridadargusapp.Model.ObjectModel.guardias;
import java.util.Comparator;

/**
 * Created by joshuansu on 15/05/17.
 */

class CompareGuard implements Comparator<guardias> {

    @Override
    public int compare(guardias o1, guardias o2) {
        return o1.getUsuarioNombre().compareTo(o2.getUsuarioNombre());
    }
}


class CompareServices implements Comparator<Cliente> {

    @Override
    public int compare(Cliente o1, Cliente o2) {
        return o1.getClienteNombre().compareTo(o2.getClienteNombre());
    }
}