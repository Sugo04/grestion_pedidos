package ad.gestion_pedidos.hmarort.ui;

public interface UI {
    void iniciar();

    void mostrarMensaje(String mensaje);

    void mostrarError(String mensaje);

    int mostrarMenu();

    void gestionarClientes();

    void gestionarPedidos();

    void consultarZonasEnvio() throws Exception;

    void consultarPedidosCliente() throws Exception;
}