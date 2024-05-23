package org.andersonlopez.bean;

public class EmailProveedor {
    private int codigoEmailProveedor;
    private String emailProveedor;
    private String descripcion;
    private int codigoProveedor;

    public EmailProveedor() {
    }

    public EmailProveedor(int codigoEmailProveedor, String emailProveedor, String descripcion, int codigoProveedor) {
        this.codigoEmailProveedor = codigoEmailProveedor;
        this.emailProveedor = emailProveedor;
        this.descripcion = descripcion;
        this.codigoProveedor = codigoProveedor;
    }

    public int getCodigoEmailProveedor() {
        return codigoEmailProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoEmailProveedor(int codigoEmailProveedor) {
        this.codigoEmailProveedor = codigoEmailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

}
