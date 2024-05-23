package org.andersonlopez.bean;

public class DetalleCompra {
    private int codigoDetalleCompra;
    private double costoUnitario;
    private int cantidad;
    private String codigoProducto;
    private int numeroDocumento;

    public DetalleCompra() {
    }

    public DetalleCompra(int codigoDetalleCompra, double costoUnitario, int cantidad, String codigoProducto, int numeroDocumento) {
        this.codigoDetalleCompra = codigoDetalleCompra;
        this.costoUnitario = costoUnitario;
        this.cantidad = cantidad;
        this.codigoProducto = codigoProducto;
        this.numeroDocumento = numeroDocumento;
    }

    public int getCodigoDetalleCompra() {
        return codigoDetalleCompra;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setCodigoDetalleCompra(int codigoDetalleCompra) {
        this.codigoDetalleCompra = codigoDetalleCompra;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    
}
