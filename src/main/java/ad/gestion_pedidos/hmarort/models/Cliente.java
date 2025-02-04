package ad.gestion_pedidos.hmarort.models;

public class Cliente {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private int idZonaEnvio;

    /**
     * Constructor por defecto.
     */
    public Cliente() {
    }

    /**
     * Constructor con parámetros.
     * @param id
     * @param nombre
     * @param email
     * @param telefono
     * @param idZonaEnvio
     */
    public Cliente(int id, String nombre, String email, String telefono, int idZonaEnvio) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.idZonaEnvio = idZonaEnvio;
    }

    /**
     * Devuelve el id del cliente.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el id del cliente.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del cliente.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del cliente.
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el email del cliente.
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del cliente.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve el teléfono del cliente.
     * @return
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve el id de la zona de envío del cliente.
     * @return
     */
    public int getIdZonaEnvio() {
        return idZonaEnvio;
    }

    /**
     * Establece el id de la zona de envío del cliente.
     * @param idZonaEnvio
     */
    public void setIdZonaEnvio(int idZonaEnvio) {
        this.idZonaEnvio = idZonaEnvio;
    }    

}
