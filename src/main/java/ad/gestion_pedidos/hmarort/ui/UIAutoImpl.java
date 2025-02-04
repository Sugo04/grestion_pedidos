package ad.gestion_pedidos.hmarort.ui;

import ad.gestion_pedidos.hmarort.dao.interfaces.DAOCliente;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOPedido;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.gestion_pedidos.hmarort.database_config.*;
import ad.gestion_pedidos.hmarort.factory.DAOFactory;
import ad.gestion_pedidos.hmarort.models.Cliente;
import ad.gestion_pedidos.hmarort.models.Pedido;
import ad.gestion_pedidos.hmarort.models.ZonaEnvio;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UIAutoImpl implements UI {
    private DAOCliente daoCliente;
    private DAOPedido daoPedido;
    private DAOZonaEnvio daoZonaEnvio;
    private Random random;
    private DatabaseConfig dbConfig;

    public UIAutoImpl() {
        // Crear configuración de base de datos usando DatabaseProperties
        DatabaseProperties properties = new DatabaseProperties.Builder()
            .url("pedidos.db")
            .build();

        dbConfig = DatabaseConfigFactory.createConfig(DatabaseType.SQLITE, properties);
        
        DAOFactory factory = DAOFactory.getDAOFactory(DatabaseType.SQLITE, dbConfig);
        
        daoCliente = factory.createClienteDAO();
        daoPedido = factory.createPedidoDAO();
        daoZonaEnvio = factory.createZonaEnvioDAO();
        random = new Random();
    }

    @Override
    public void iniciar() {
        try {
            generarDatosAutomaticos();
            generarInformesAutomaticos();
        } catch (Exception e) {
            mostrarError("Error en proceso automático: " + e.getMessage());
        }
    }

    private void generarDatosAutomaticos() throws Exception {
        // Generar zonas de envío
        List<ZonaEnvio> zonas = IntStream.range(1, 4)
            .mapToObj(i -> new ZonaEnvio(0, "Zona " + i, random.nextDouble(10, 50)))
            .collect(Collectors.toList());
        
        for (ZonaEnvio zona : zonas) {
            daoZonaEnvio.agregarZonaEnvio(zona);
        }

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
    }

    private void generarInformesAutomaticos() throws Exception {
        mostrarMensaje("\n--- INFORME AUTOMÁTICO DE CLIENTES ---");
        List<Cliente> clientes = daoCliente.obtenerTodosLosClientes();
        
        for (Cliente cliente : clientes) {
            List<Pedido> pedidos = daoPedido.obtenerPedidosPorCliente(cliente.getId());
            double totalFacturado = daoCliente.calcularFacturacionTotalCliente(cliente.getId());
            
            System.out.println("\nCliente: " + cliente.getNombre());
            System.out.println("Total Pedidos: " + pedidos.size());
            System.out.println("Total Facturado: " + totalFacturado);
            
            pedidos.forEach(p -> System.out.println(
                "- Pedido " + p.getId() + 
                ", Fecha: " + p.getFecha() + 
                ", Importe: " + p.getImporte()
            ));
        }
    }

    @Override
    public int mostrarMenu() {
        return 0; // No necesario en modo automático
    }

    @Override
    public void gestionarClientes() {}
    @Override
    public void gestionarPedidos() {}
    @Override
    public void consultarZonasEnvio() throws Exception {}
    @Override
    public void consultarPedidosCliente() throws Exception {}

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }
}