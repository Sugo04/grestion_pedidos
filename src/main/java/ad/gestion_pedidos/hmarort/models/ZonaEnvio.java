package ad.gestion_pedidos.hmarort.models;

public class ZonaEnvio {
    private int id;
    private String nombre;
    private double precio;

    /**
     * Constructor por defecto.
     */
    public ZonaEnvio() {
    }

    /**
     * Constructor con parámetros.
     * @param id
     * @param nombre
     * @param precio
     */
    public ZonaEnvio(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Devuelve el id de la zona de envío.
     * @return
     */
    public int getId() {
        return id;
    }
    
    /**
     * Establece el id de la zona de envío.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la zona de envío.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la zona de envío.
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el precio de la zona de envío.
     * @return
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la zona de envío.
     * @param precio
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
