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
import java.util.Scanner;

public class UIManualImpl implements UI {
    private DAOCliente daoCliente;
    private DAOPedido daoPedido;
    private DAOZonaEnvio daoZonaEnvio;
    private Scanner scanner;
    private DatabaseConfig dbConfig;

    public UIManualImpl() {
        DatabaseProperties properties = new DatabaseProperties.Builder()
                .url("src/main/resources/pedidos.db")
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
                        mostrarMensaje("\n📍 Saliendo del sistema...");
                        return;
                    }
                }
            } catch (Exception e) {
                mostrarError("❌ Error: " + e.getMessage());
            }
        }
    }

    @Override
    public int mostrarMenu() {
        System.out.println("\n╔═══════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN      ║");
        System.out.println("╠═══════════════════════════╣");
        System.out.println("║ 1. Gestionar Clientes     ║");
        System.out.println("║ 2. Gestionar Pedidos      ║");
        System.out.println("║ 3. Consultar Zonas Envío  ║");
        System.out.println("║ 4. Pedidos por Cliente    ║");
        System.out.println("║ 0. Salir                  ║");
        System.out.println("╚═══════════════════════════╝");
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
        if (!nombre.isEmpty())
            cliente.setNombre(nombre);

        System.out.print("Nuevo email (enter para mantener actual): ");
        String email = scanner.nextLine();
        if (!email.isEmpty())
            cliente.setEmail(email);

        System.out.print("Nuevo teléfono (enter para mantener actual): ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty())
            cliente.setTelefono(telefono);

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
                        ", Email: " + c.getEmail()));
    }

    @Override
    public void gestionarPedidos() throws Exception {
        System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
        System.out.println("1. Agregar Pedido");
        System.out.println("2. Modificar Pedido");
        System.out.println("3. Eliminar Pedido");
        System.out.println("4. Listar Pedidos");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> agregarPedido();
            case 2 -> modificarPedido();
            case 3 -> eliminarPedido();
            case 4 -> listarPedidos();
        }
    }

    private void agregarPedido() throws Exception {
        System.out.print("ID de Cliente: ");
        int idCliente = scanner.nextInt();
        System.out.print("Importe del Pedido: ");
        double importe = scanner.nextDouble();

        Pedido pedido = new Pedido(0, LocalDate.now(), importe, idCliente);
        daoPedido.agregarPedido(pedido);
        mostrarMensaje("Pedido agregado exitosamente");
    }

    private void modificarPedido() throws Exception {
        System.out.print("ID de Pedido a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        Pedido pedido = daoPedido.obtenerPedidoPorId(id);
        if (pedido == null) {
            mostrarError("Pedido no encontrado");
            return;
        }

        System.out.print("Nuevo importe (enter para mantener actual): ");
        String importeStr = scanner.nextLine();
        if (!importeStr.isEmpty()) {
            pedido.setImporte(Double.parseDouble(importeStr));
        }

        daoPedido.actualizarPedido(pedido);
        mostrarMensaje("Pedido modificado exitosamente");
    }

    private void eliminarPedido() throws Exception {
        System.out.print("ID de Pedido a eliminar: ");
        int id = scanner.nextInt();
        daoPedido.eliminarPedido(id);
        mostrarMensaje("Pedido eliminado exitosamente");
    }

    private void listarPedidos() throws Exception {
        List<Pedido> pedidos = daoPedido.obtenerTodosLosPedidos();
        pedidos.forEach(p -> System.out.println(
                "ID: " + p.getId() +
                        ", Fecha: " + p.getFecha() +
                        ", Importe: " + p.getImporte() +
                        ", Cliente ID: " + p.getIdCliente()));
    }

    @Override
    public void consultarZonasEnvio() throws Exception {
        List<ZonaEnvio> zonas = daoZonaEnvio.obtenerTodasLasZonas();
        zonas.forEach(z -> System.out.println(
                "ID: " + z.getId() +
                        ", Nombre: " + z.getNombre() +
                        ", Precio: " + z.getPrecio()));
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
                        ", Importe: " + p.getImporte()));
        System.out.println("Total Facturado: " + totalFacturado);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("✅ " + mensaje);
    }

    @Override
    public void mostrarError(String mensaje) {
        System.err.println("❌ " + mensaje);
    }
}