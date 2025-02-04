package ad.gestion_pedidos.hmarort.models;

import java.time.LocalDate;

public class Pedido {
    private int id;
    private LocalDate fecha;
    private double importe;
    private int idCliente;

    public Pedido() {
    }

    public Pedido(int id, LocalDate fecha, double importe, int idCliente) {
        this.id = id;
        this.fecha = fecha;
        this.importe = importe;
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

}
