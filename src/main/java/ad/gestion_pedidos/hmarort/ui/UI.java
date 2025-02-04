package ad.gestion_pedidos.hmarort.ui;

public interface UI {
    void iniciar();

    void mostrarMensaje(String mensaje) throws Exception;

    void mostrarError(String mensaje) throws Exception;

    int mostrarMenu() throws Exception;

    void gestionarClientes() throws Exception;

    void gestionarPedidos() throws Exception;

    void consultarZonasEnvio() throws Exception;

    void consultarPedidosCliente() throws Exception;
}