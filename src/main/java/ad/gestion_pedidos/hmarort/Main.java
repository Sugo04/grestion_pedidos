package ad.gestion_pedidos.hmarort;

import ad.gestion_pedidos.hmarort.ui.UI;
import ad.gestion_pedidos.hmarort.ui.UIFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            logger.info("Iniciando aplicación de gestión de pedidos");
            
            // Determine UI type based on arguments
            UI ui = UIFactory.crearUI(args);
            
            logger.debug("Tipo de interfaz seleccionada: {}", 
                ui.getClass().getSimpleName());
            
            ui.iniciar();
            
            logger.info("Aplicación finalizada exitosamente");
        } catch (Exception e) {
            logger.error("Error fatal en la aplicación", e);
        }
    }
}