package org.andersonlopez.bean;

public class Compras {
    private int numeroDocumento;
    private String fechaDocumento;
    private String descripcion;
    private String totalDocumento;

    public Compras() {
    }

    public Compras(int numeroDocumento, String fechaDocumento, String descripcion, String totalDocumento) {
        this.numeroDocumento = numeroDocumento;
        this.fechaDocumento = fechaDocumento;
        this.descripcion = descripcion;
        this.totalDocumento = totalDocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTotalDocumento() {
        return totalDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTotalDocumento(String totalDocumento) {
        this.totalDocumento = totalDocumento;
    }

}
