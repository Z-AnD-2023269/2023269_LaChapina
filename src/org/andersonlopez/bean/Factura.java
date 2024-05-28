package org.andersonlopez.bean;

public class Factura {
    private int numeroFactura;
    private String estado;
    private double totalFactura;
    private String fechaFactura;
    private int codigoCliente;
    private int codigoEmpleado;

    public Factura() {
    }

    public Factura(int numeroFactura, String estado, double totalFactura, String fechaFactura, int codigoCliente, int codigoEmpleado) {
        this.numeroFactura = numeroFactura;
        this.estado = estado;
        this.totalFactura = totalFactura;
        this.fechaFactura = fechaFactura;
        this.codigoCliente = codigoCliente;
        this.codigoEmpleado = codigoEmpleado;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public String getEstado() {
        return estado;
    }

    public double getTotalFactura() {
        return totalFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    @Override
    public String toString() {
        return "|" +numeroFactura;
    }
    
}
