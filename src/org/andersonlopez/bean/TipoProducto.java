package org.andersonlopez.bean;

public class TipoProducto {
    private int codigoTipoProducto;
    private String descripcion;

    public TipoProducto() {
    }

    public TipoProducto(int codigoTipoProducto, String descripcion) {
        this.codigoTipoProducto = codigoTipoProducto;
        this.descripcion = descripcion;
    }

    public int getCodigoTipoProducto() {
        return codigoTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCodigoTipoProducto(int codigoTipoProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}