package com.example.legible.seguridadargusapp.Model.ObjectModel;

/**
 * Created by sergiosilva on 3/11/17.
 */

public class GuardiaMoveBasicInfo {
    private String guardiaKey;
    private String guardiaClienteActual;
    private String guardiaClienteNuevo;

    public GuardiaMoveBasicInfo(){}

    public GuardiaMoveBasicInfo(String guardiaKey, String guardiaClienteActual, String guardiaClienteNuevo) {
        this.guardiaKey = guardiaKey;
        this.guardiaClienteActual = guardiaClienteActual;
        this.guardiaClienteNuevo = guardiaClienteNuevo;
    }

    public String getGuardiaKey() {
        return guardiaKey;
    }

    public void setGuardiaKey(String guardiaKey) {
        this.guardiaKey = guardiaKey;
    }

    public String getGuardiaClienteActual() {
        return guardiaClienteActual;
    }

    public void setGuardiaClienteActual(String guardiaClienteActual) {
        this.guardiaClienteActual = guardiaClienteActual;
    }

    public String getGuardiaClienteNuevo() {
        return guardiaClienteNuevo;
    }

    public void setGuardiaClienteNuevo(String guardiaClienteNuevo) {
        this.guardiaClienteNuevo = guardiaClienteNuevo;
    }
}
