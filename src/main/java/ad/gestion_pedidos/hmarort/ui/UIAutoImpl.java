package ad.gestion_pedidos.hmarort.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ad.gestion_pedidos.hmarort.dao.interfaces.DAOCliente;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOPedido;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.gestion_pedidos.hmarort.database_config.DatabaseConfig;
import ad.gestion_pedidos.hmarort.database_config.DatabaseConfigFactory;
import ad.gestion_pedidos.hmarort.database_config.DatabaseProperties;
import ad.gestion_pedidos.hmarort.database_config.DatabaseType;
import ad.gestion_pedidos.hmarort.factory.DAOFactory;
import ad.gestion_pedidos.hmarort.models.Cliente;
import ad.gestion_pedidos.hmarort.models.Pedido;
import ad.gestion_pedidos.hmarort.models.ZonaEnvio;

/**
 * Implementación automática de la interfaz de usuario que genera datos de
 * prueba
 * y realiza operaciones automáticamente.
 */
public class UIAutoImpl implements UI {
    private DAOCliente daoCliente;
    private DAOPedido daoPedido;
    private DAOZonaEnvio daoZonaEnvio;
    private Random random;
    private DatabaseConfig dbConfig;

    /**
     * Constructor que inicializa la configuración de la base de datos y los DAOs.
     */
    public UIAutoImpl() {
        DatabaseProperties properties = new DatabaseProperties.Builder()
                .url("src/main/resources/pedidos.db")
                .build();

        dbConfig = DatabaseConfigFactory.createConfig(DatabaseType.SQLITE, properties);

        DAOFactory factory = DAOFactory.getDAOFactory(DatabaseType.SQLITE, dbConfig);

        daoCliente = factory.createClienteDAO();
        daoPedido = factory.createPedidoDAO();
        daoZonaEnvio = factory.createZonaEnvioDAO();
        random = new Random();
    }

    /**
     * Inicia el proceso automático de generación de datos y reportes.
     */
    @Override
    public void iniciar() {
        try {
            generarDatosAutomaticos();
            generarInformesAutomaticos();
        } catch (Exception e) {
            mostrarError("Error en proceso automático: " + e.getMessage());
        }
    }

    /**
     * Genera datos de prueba automáticamente incluyendo zonas de envío, clientes y
     * pedidos.
     * 
     * @throws Exception Si ocurre un error durante la generación de datos
     */
    private void generarDatosAutomaticos() throws Exception {
        System.out.println("\n[GENERACIÓN AUTOMÁTICA DE DATOS]");

        // Generar zonas de envío
        List<ZonaEnvio> zonas = IntStream.range(1, 4)
                .mapToObj(i -> new ZonaEnvio(0, "Zona " + i, random.nextDouble(10, 50)))
                .collect(Collectors.toList());

        for (ZonaEnvio zona : zonas) {
            daoZonaEnvio.agregarZonaEnvio(zona);
        }
        System.out.println("✓ Zonas de envío generadas");

        // Generar clientes
        List<Cliente> clientes = IntStream.range(1, 6)
                .mapToObj(i -> new Cliente(0,
                        "Cliente " + i,
                        "cliente" + i + "@example.com",
                        "123456789" + i,
                        zonas.get(random.nextInt(zonas.size())).getId()))
                .collect(Collectors.toList());

        for (Cliente cliente : clientes) {
            daoCliente.agregarCliente(cliente);
        }
        System.out.println("✓ Clientes generados");

        // Generar pedidos
        for (Cliente cliente : clientes) {
            int numeroPedidos = random.nextInt(1, 4);
            for (int j = 0; j < numeroPedidos; j++) {
                Pedido pedido = new Pedido(0,
                        LocalDate.now().minusDays(random.nextInt(30)),
                        random.nextDouble(50, 500),
                        cliente.getId());
                daoPedido.agregarPedido(pedido);
            }
        }
        System.out.println("✓ Pedidos generados");
    }

    /**
     * Genera informes automáticos mostrando resúmenes de clientes y sus pedidos.
     * 
     * @throws Exception Si ocurre un error durante la generación de informes
     */
    private void generarInformesAutomaticos() throws Exception {
        System.out.println("\n[INFORME AUTOMÁTICO]");
        List<Cliente> clientes = daoCliente.obtenerTodosLosClientes();

        for (Cliente cliente : clientes) {
            List<Pedido> pedidos = daoPedido.obtenerPedidosPorCliente(cliente.getId());
            double totalFacturado = daoCliente.calcularFacturacionTotalCliente(cliente.getId());

            System.out.println("\n--- Resumen Cliente ---");
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Total Pedidos: " + pedidos.size());
            System.out.printf("Total Facturado: %.2f €\n", totalFacturado);

            System.out.println("Detalle de Pedidos:");
            pedidos.forEach(p -> System.out.printf(
                    "  • Pedido %d: %s - %.2f €\n",
                    p.getId(), p.getFecha(), p.getImporte()));
        }
    }

    /**
     * Como no lo usamos hacemos que devuelva 0
     */
    @Override
    public int mostrarMenu() {
        return 0;
    }

    /**
     * Como no lo usamos hacemos que no haga nada
     */
    @Override
    public void gestionarClientes() {
    }

    /**
     * Como no lo usamos hacemos que no haga nada
     */
    @Override
    public void gestionarPedidos() {
    }

    /**
     * Como no lo usamos hacemos que no haga nada
     */
    @Override
    public void consultarZonasEnvio() throws Exception {
    }

    /**
     * Como no lo usamos hacemos que no haga nada
     */
    @Override
    public void consultarPedidosCliente() throws Exception {
    }

    /**
     * Muestra un mensaje en la consola.
     */
    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Muestra un mensaje de error en la consola.
     */
    @Override
    public void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }
}