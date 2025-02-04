package ad.gestion_pedidos.hmarort.ui;

/**
 * Interfaz que define las operaciones básicas de la interfaz de usuario.
 */
public interface UI {
    /**
     * Inicia la interfaz de usuario.
     */
    void iniciar();

    /**
     * Muestra un mensaje al usuario.
     * 
     * @param mensaje El mensaje a mostrar
     * @throws Exception Si ocurre un error al mostrar el mensaje
     */
    void mostrarMensaje(String mensaje) throws Exception;

    /**
     * Muestra un mensaje de error al usuario.
     * 
     * @param mensaje El mensaje de error a mostrar
     * @throws Exception Si ocurre un error al mostrar el mensaje
     */
    void mostrarError(String mensaje) throws Exception;

    /**
     * Muestra el menú principal y retorna la opción seleccionada.
     * 
     * @return La opción seleccionada por el usuario
     * @throws Exception Si ocurre un error al mostrar el menú
     */
    int mostrarMenu() throws Exception;

    /**
     * Gestiona las operaciones relacionadas con clientes.
     * 
     * @throws Exception Si ocurre un error durante la gestión de clientes
     */
    void gestionarClientes() throws Exception;

    /**
     * Gestiona las operaciones relacionadas con pedidos.
     * 
     * @throws Exception Si ocurre un error durante la gestión de pedidos
     */
    void gestionarPedidos() throws Exception;

    /**
     * Muestra información sobre las zonas de envío disponibles.
     * 
     * @throws Exception Si ocurre un error al consultar las zonas de envío
     */
    void consultarZonasEnvio() throws Exception;

    /**
     * Consulta y muestra los pedidos de un cliente específico.
     * 
     * @throws Exception Si ocurre un error al consultar los pedidos
     */
    void consultarPedidosCliente() throws Exception;
}