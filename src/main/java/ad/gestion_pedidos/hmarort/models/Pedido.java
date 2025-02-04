package ad.gestion_pedidos.hmarort.models;

import java.time.LocalDate;

public class Pedido {
    private int id;
    private LocalDate fecha;
    private double importe;
    private int idCliente;

    /**
     * Constructor por defecto.
     */
    public Pedido() {
    }

    /**
     *  Constructor con parámetros.
     * @param id
     * @param fecha
     * @param importe
     * @param idCliente
     */
    public Pedido(int id, LocalDate fecha, double importe, int idCliente) {
        this.id = id;
        this.fecha = fecha;
        this.importe = importe;
        this.idCliente = idCliente;
    }

    /**
     * Devuelve el id del pedido.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el id del pedido.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve la fecha del pedido.
     * @return
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del pedido.
     * @param fecha
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve el importe del pedido.
     * @return
     */
    public double getImporte() {
        return importe;
    }

    /**
     * Establece el importe del pedido.
     * @param importe
     */
    public void setImporte(double importe) {
        this.importe = importe;
    }
    
    /**
     * Devuelve el id del cliente asociado al pedido.
     * @return
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el id del cliente asociado al pedido.
     * @param idCliente
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

}
