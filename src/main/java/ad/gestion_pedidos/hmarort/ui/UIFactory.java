package ad.gestion_pedidos.hmarort.ui;

public class UIFactory {
    public enum TipoUI {
        AUTO,
        MANUAL
    }

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

    public static UI crearUI(String[] args){
        return creaUI(args != null && args.length > 0 && args[0].equals("manual") ? TipoUI.MANUAL : TipoUI.AUTO);
    }
}
