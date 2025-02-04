package ad.gestion_pedidos.hmarort.ui;

import ad.gestion_pedidos.hmarort.dao.interfaces.DAOCliente;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOPedido;
import ad.gestion_pedidos.hmarort.dao.interfaces.DAOZonaEnvio;
import ad.gestion_pedidos.hmarort.database_config.*;
import ad.gestion_pedidos.hmarort.factory.DAOFactory;
import ad.gestion_pedidos.hmarort.models.Cliente;
import ad.gestion_pedidos.hmarort.models.Pedido;
import ad.gestion_pedidos.hmarort.models.ZonaEnvio;

import java.util.List;
import java.util.Scanner;

public class UIManualImpl implements UI {
    private DAOCliente daoCliente;
    private DAOPedido daoPedido;
    private DAOZonaEnvio daoZonaEnvio;
    private Scanner scanner;
    private DatabaseConfig dbConfig;

    public UIManualImpl() {
        // Crear configuración de base de datos usando DatabaseProperties
        DatabaseProperties properties = new DatabaseProperties.Builder()
            .url("pedidos.db")
            .build();

        dbConfig = DatabaseConfigFactory.createConfig(DatabaseType.SQLITE, properties);
        
        DAOFactory factory = DAOFactory.getDAOFactory(DatabaseType.SQLITE, dbConfig);
        
        daoCliente = factory.createClienteDAO();
        daoPedido = factory.createPedidoDAO();
        daoZonaEnvio = factory.createZonaEnvioDAO();
        scanner = new Scanner(System.in);
    }

    @Override
    public void iniciar() {
        while (true) {
            int opcion = mostrarMenu();
            try {
                switch (opcion) {
                    case 1 -> gestionarClientes();
                    case 2 -> gestionarPedidos();
                    case 3 -> consultarZonasEnvio();
                    case 4 -> consultarPedidosCliente();
                    case 0 -> {
                        mostrarMensaje("Saliendo del sistema...");
                        return;
                    }
                }
            } catch (Exception e) {
                mostrarError("Error: " + e.getMessage());
            }
        }
    }

    @Override
    public int mostrarMenu() {
        System.out.println("\n--- SISTEMA DE GESTIÓN ---");
        System.out.println("1. Gestionar Clientes");
        System.out.println("2. Gestionar Pedidos");
        System.out.println("3. Consultar Zonas de Envío");
        System.out.println("4. Consultar Pedidos de Cliente");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    @Override
    public void gestionarClientes() throws Exception {
        System.out.println("\n--- GESTIÓN DE CLIENTES ---");
        System.out.println("1. Agregar Cliente");
        System.out.println("2. Modificar Cliente");
        System.out.println("3. Eliminar Cliente");
        System.out.println("4. Listar Clientes");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        switch (opcion) {
            case 1 -> agregarCliente();
            case 2 -> modificarCliente();
            case 3 -> eliminarCliente();
            case 4 -> listarClientes();
        }
    }

    private void agregarCliente() throws Exception {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("ID Zona Envío: ");
        int idZona = scanner.nextInt();

        Cliente cliente = new Cliente(0, nombre, email, telefono, idZona);
        daoCliente.agregarCliente(cliente);
        mostrarMensaje("Cliente agregado exitosamente");
    }

    private void modificarCliente() throws Exception {
        System.out.print("ID de Cliente a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Cliente cliente = daoCliente.obtenerClientePorId(id);
        if (cliente == null) {
            mostrarError("Cliente no encontrado");
            return;
        }

        System.out.print("Nuevo nombre (enter para mantener actual): ");
        String nombre = scanner.nextLine();
        if (!nombre.isEmpty()) cliente.setNombre(nombre);

        System.out.print("Nuevo email (enter para mantener actual): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) cliente.setEmail(email);

        System.out.print("Nuevo teléfono (enter para mantener actual): ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) cliente.setTelefono(telefono);

        daoCliente.actualizarInformacionCliente(cliente);
        mostrarMensaje("Cliente modificado exitosamente");
    }

    private void eliminarCliente() throws Exception {
        System.out.print("ID de Cliente a eliminar: ");
        int id = scanner.nextInt();
        daoCliente.eliminarClientePorId(id);
        mostrarMensaje("Cliente eliminado exitosamente");
    }

    private void listarClientes() throws Exception {
        List<Cliente> clientes = daoCliente.obtenerTodosLosClientes();
        clientes.forEach(c -> System.out.println(
            "ID: " + c.getId() + 
            ", Nombre: " + c.getNombre() + 
            ", Email: " + c.getEmail()
        ));
    }

    @Override
    public void gestionarPedidos() throws Exception {
        // Implementación similar a gestionarClientes()
    }

    @Override
    public void consultarZonasEnvio() throws Exception {
        List<ZonaEnvio> zonas = daoZonaEnvio.obtenerTodasLasZonas();
        zonas.forEach(z -> System.out.println(
            "ID: " + z.getId() + 
            ", Nombre: " + z.getNombre() + 
            ", Precio: " + z.getPrecio()
        ));
    }

    @Override
    public void consultarPedidosCliente() throws Exception {
        System.out.print("ID de Cliente: ");
        int idCliente = scanner.nextInt();
        
        List<Pedido> pedidos = daoPedido.obtenerPedidosPorCliente(idCliente);
        double totalFacturado = daoCliente.calcularFacturacionTotalCliente(idCliente);
        
        System.out.println("Pedidos del Cliente:");
        pedidos.forEach(p -> System.out.println(
            "ID: " + p.getId() + 
            ", Fecha: " + p.getFecha() + 
            ", Importe: " + p.getImporte()
        ));
        System.out.println("Total Facturado: " + totalFacturado);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }
}