package com.example.legible.seguridadargusapp.Model.ObjectModel;

/**
 * Created by sergiosilva on 3/10/17.
 */

public class Incidente {
    private String incidenteCliente;
    private String incidenteFecha;
    private String incidenteObservacion;
    private String incidenteTipo;
    private String incidenteUsuario;


    public Incidente(String incidenteCliente, String incidenteFecha, String incidenteObservacion, String incidenteTipo, String incidenteUsuario) {
        this.incidenteCliente = incidenteCliente;
        this.incidenteFecha = incidenteFecha;
        this.incidenteObservacion = incidenteObservacion;
        this.incidenteTipo = incidenteTipo;
        this.incidenteUsuario = incidenteUsuario;
    }

    public String getIncidenteCliente() {
        return incidenteCliente;
    }

    public void setIncidenteCliente(String incidenteCliente) {
        this.incidenteCliente = incidenteCliente;
    }

    public String getIncidenteFecha() {
        return incidenteFecha;
    }

    public void setIncidenteFecha(String incidenteFecha) {
        this.incidenteFecha = incidenteFecha;
    }

    public String getIncidenteObservacion() {
        return incidenteObservacion;
    }

    public void setIncidenteObservacion(String incidenteObservacion) {
        this.incidenteObservacion = incidenteObservacion;
    }

    public String getIncidenteTipo() {
        return incidenteTipo;
    }

    public void setIncidenteTipo(String incidenteTipo) {
        this.incidenteTipo = incidenteTipo;
    }

    public String getIncidenteUsuario() {
        return incidenteUsuario;
    }

    public void setIncidenteUsuario(String incidenteUsuario) {
        this.incidenteUsuario = incidenteUsuario;
    }
}
