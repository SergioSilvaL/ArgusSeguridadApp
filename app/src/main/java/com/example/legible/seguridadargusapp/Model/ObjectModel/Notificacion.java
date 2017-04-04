package com.example.legible.seguridadargusapp.Model.ObjectModel;

/**
 * Created by sergiosilva on 3/9/17.
 */

public class Notificacion {

    public String accion="";
    public String descripcion;
    public String fecha;
    public String referenceKey;
    public String fechaCodigo;

    public String getFechaCodigo() {
        return fechaCodigo;
    }

    public void setFechaCodigo(String fechaCodigo) {
        this.fechaCodigo = fechaCodigo;
    }

    public Notificacion(String accion, String descripcion, String fecha, String referenceKey) {
        this.accion = accion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.referenceKey = referenceKey;
    }

    public Notificacion(String accion, String descripcion, String fecha,String fechaCodigo,String referenceKey){
        this.accion = accion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.fechaCodigo = fechaCodigo;
        this.referenceKey = referenceKey;
    }

    public Notificacion(String accion, String descripcion, String fecha) {
        this.accion = accion;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
