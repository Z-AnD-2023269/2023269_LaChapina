package org.andersonlopez.bean;

public class DetalleFactura {
    private int codigoDetalleFactura;
    private double precioUnitario;
    private int cantidad;
    private int numeroFactura;
    private String codigoProducto;

    public DetalleFactura() {
    }

    public DetalleFactura(int codigoDetalleFactura, double precioUnitario, int cantidad, int numeroFactura, String codigoProducto) {
        this.codigoDetalleFactura = codigoDetalleFactura;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.numeroFactura = numeroFactura;
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoDetalleFactura() {
        return codigoDetalleFactura;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoDetalleFactura(int codigoDetalleFactura) {
        this.codigoDetalleFactura = codigoDetalleFactura;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
}
