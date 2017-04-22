package com.example.legible.seguridadargusapp.Model.ObjectModel;

/**
 * Created by SERGIO on 25/02/2017.
 */

public class guardias {

    private boolean usuarioDisponible;
    private String usuarioDomicilio;
    private String usuarioNombre;
    private long usuarioTelefono;
    private String usuarioTipo;
    private String usuarioTurno;
    private String usuarioAsistenciaDelDia;

    public boolean isUsuarioAsistio() {
        return usuarioAsistio;
    }

    public void setUsuarioAsistio(boolean usuarioAsistio) {
        this.usuarioAsistio = usuarioAsistio;
    }

    public boolean isUsuarioDobleTurno() {
        return usuarioDobleTurno;
    }

    public void setUsuarioDobleTurno(boolean usuarioDobleTurno) {
        this.usuarioDobleTurno = usuarioDobleTurno;
    }

    public boolean isUsuarioCubreTurno() {
        return usuarioCubreTurno;
    }

    public void setUsuarioCubreTurno(boolean usuarioCubreTurno) {
        this.usuarioCubreTurno = usuarioCubreTurno;
    }

    private boolean usuarioAsistio = false;
    private boolean usuarioDobleTurno = false;
    private boolean usuarioCubreTurno = false;

    public long getUsuarioHorasExtra() {
        return usuarioHorasExtra;
    }

    public void setUsuarioHorasExtra(long usuarioHorasExtra) {
        this.usuarioHorasExtra = usuarioHorasExtra;
    }

    private long usuarioHorasExtra = 0;

    public String getUsuarioAsistenciaExtraDelDia() {
        return usuarioAsistenciaExtraDelDia;
    }

    public void setUsuarioAsistenciaExtraDelDia(String usuarioAsistenciaExtraDelDia) {
        this.usuarioAsistenciaExtraDelDia = usuarioAsistenciaExtraDelDia;
    }

    private String usuarioAsistenciaExtraDelDia = "";


    public String getUsuarioAsistenciaFecha() {
        return usuarioAsistenciaFecha;
    }

    public void setUsuarioAsistenciaFecha(String usuarioAsistenciaFecha) {
        this.usuarioAsistenciaFecha = usuarioAsistenciaFecha;
    }



    private String usuarioAsistenciaFecha;


    //Really? Zona are you even using that bro...
    private String usuarioZona;

    public String getUsuarioClienteAsignado() {
        return usuarioClienteAsignado;
    }

    public void setUsuarioClienteAsignado(String usuarioClienteAsignado) {
        this.usuarioClienteAsignado = usuarioClienteAsignado;
    }

    private String usuarioClienteAsignado;
    private String usuarioKey;


    public guardias(){}

    //Construct Guardia for clienteGuardias listview show
//    public guardias(String usuarioKey, String name, String status, String statusExtra, String date ){
//        this.usuarioKey = usuarioKey;
//        this.usuarioNombre = name;
//        this.usuarioAsistenciaDelDia = status;
//        this.usuarioAsistenciaExtraDelDia = statusExtra;
//        this.usuarioAsistenciaFecha = date;
//    }


    public guardias(String usuarioKey, String name, boolean asistio, boolean cubreDescanso,boolean dobleTurno, long horas,String date ){
        this.usuarioKey = usuarioKey;
        this.usuarioNombre = name;
        this.usuarioAsistio = asistio;
        this.usuarioCubreTurno = cubreDescanso;
        this.usuarioDobleTurno = dobleTurno;
        this.usuarioHorasExtra = horas;
        this.usuarioAsistenciaFecha = date;
    }

    public guardias(boolean usuarioDisponible,String usuarioDomicilio, String usuarioNombre, String usuarioTipo,long usuarioTelefono,String usuarioTurno, String usuarioZona) {
        this.usuarioDisponible = usuarioDisponible;
        this.usuarioDomicilio = usuarioDomicilio;
        this.usuarioNombre = usuarioNombre;
        this.usuarioTelefono = usuarioTelefono;
        this.usuarioTipo = usuarioTipo;
        this.usuarioTurno = usuarioTurno;
        this.usuarioZona = usuarioZona;
        this.usuarioKey = usuarioKey;
    }


    public String getUsuarioAsistenciaDelDia() {
        return usuarioAsistenciaDelDia;
    }

    public void setUsuarioAsistenciaDelDia(String usuarioAsistenciaDelDia) {
        this.usuarioAsistenciaDelDia = usuarioAsistenciaDelDia;
    }

    public String getUsuarioKey() {
        return usuarioKey;
    }

    public void setUsuarioKey(String usuarioKey) {
        this.usuarioKey = usuarioKey;
    }

    public String getUsuarioDomicilio() {
        return usuarioDomicilio;
    }

    public void setUsuarioDomicilio(String usuarioDomicilio) {
        this.usuarioDomicilio = usuarioDomicilio;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public long getUsuarioTelefono() {
        return usuarioTelefono;
    }

    public void setUsuarioTelefono(long usuarioTelefono) {
        this.usuarioTelefono = usuarioTelefono;
    }

    public String getUsuarioTipo() {
        return usuarioTipo;
    }

    public void setUsuarioTipo(String usuarioTipo) {
        this.usuarioTipo = usuarioTipo;
    }

    public String getUsuarioTurno() {
        return usuarioTurno;
    }

    public void setUsuarioTurno(String usuarioTurno) {
        this.usuarioTurno = usuarioTurno;
    }

    public String getUsuarioZona() {
        return usuarioZona;
    }

    public void setUsuarioZona(String usuarioZona) {
        this.usuarioZona = usuarioZona;
    }

    public boolean isUsuarioDisponible() {
        return usuarioDisponible;
    }

    public void setUsuarioDisponible(boolean usuarioDisponible) {
        this.usuarioDisponible = usuarioDisponible;
    }

//    public String getUsuarioCliente() {
//        return usuarioCliente;
//    }
//
//    public void setUsuarioCliente(String usuarioCliente) {
//        this.usuarioCliente = usuarioCliente;
//    }
}
