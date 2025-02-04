package ad.gestion_pedidos.hmarort.ui;

public class UIFactory {
    /**
     * Enumeración que define los tipos de interfaz de usuario disponibles.
     */
    public enum TipoUI {
        AUTO,
        MANUAL
    }

    /**
     * Crea una instancia de UI según el tipo especificado.
     * 
     * @param tipo El tipo de interfaz de usuario a crear
     * @return La instancia de UI creada
     */
    public static UI creaUI(TipoUI tipo) {
        switch (tipo) {
            case MANUAL:
                return new UIManualImpl();
            case AUTO:
                return new UIAutoImpl();
            default:
                return null;
        }
    }

    /**
     * Crea una instancia de UI basada en los argumentos de línea de comandos.
     * 
     * @param args Argumentos de línea de comandos
     * @return La instancia de UI creada (manual si args contiene "manual",
     *         automática en caso contrario)
     */
    public static UI crearUI(String[] args) {
        return creaUI(args != null && args.length > 0 && args[0].equals("manual") ? TipoUI.MANUAL : TipoUI.AUTO);
    }
}
