package org.andersonlopez.bean;

public class TelefonoProveedor {
    private int codigoTelefonoProveedor;
    private String numeroPrincipal;
    private String numeroSecundario;
    private String observaciones;
    private int codigoProveedor;

    public TelefonoProveedor() {
    }

    public TelefonoProveedor(int codigoTelefonoProveedor, String numeroPrincipal, String numeroSecundario, String observaciones, int codigoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
        this.numeroPrincipal = numeroPrincipal;
        this.numeroSecundario = numeroSecundario;
        this.observaciones = observaciones;
        this.codigoProveedor = codigoProveedor;
    }

    public int getCodigoTelefonoProveedor() {
        return codigoTelefonoProveedor;
    }

    public String getNumeroPrincipal() {
        return numeroPrincipal;
    }

    public String getNumeroSecundario() {
        return numeroSecundario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoTelefonoProveedor(int codigoTelefonoProveedor) {
        this.codigoTelefonoProveedor = codigoTelefonoProveedor;
    }

    public void setNumeroPrincipal(String numeroPrincipal) {
        this.numeroPrincipal = numeroPrincipal;
    }

    public void setNumeroSecundario(String numeroSecundario) {
        this.numeroSecundario = numeroSecundario;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    
}
