package com.example.legible.seguridadargusapp.Model.ObjectModel;

/**
 * Created by sergiosilva on 3/9/17.
 */

public class Notificacion {

    public String accion="";
    public String observacion;
    public String fecha;
    public String referenceKey;
    public String fechaCodigo;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    private String cliente;

    public String getFechaCodigo() {
        return fechaCodigo;
    }

    public void setFechaCodigo(String fechaCodigo) {
        this.fechaCodigo = fechaCodigo;
    }

    public Notificacion(String accion, String descripcion, String fecha, String referenceKey) {
        this.accion = accion;
        this.observacion = descripcion;
        this.fecha = fecha;
        this.referenceKey = referenceKey;
    }

    public Notificacion(String accion, String descripcion, String fecha,String fechaCodigo,String referenceKey, String cliente){
        this.accion = accion;
        this.observacion = descripcion;
        this.fecha = fecha;
        this.fechaCodigo = fechaCodigo;
        this.referenceKey = referenceKey;
        this.cliente = cliente;
    }

    public Notificacion(String accion, String descripcion, String fecha) {
        this.accion = accion;
        this.observacion = descripcion;
        this.fecha = fecha;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }


}
