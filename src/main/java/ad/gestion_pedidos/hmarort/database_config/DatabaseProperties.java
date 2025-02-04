package ad.gestion_pedidos.hmarort.database_config;

public class DatabaseProperties {
    private final String url; 
    private final String username;
    private final String password;
    private final int maxPoolSize;
    private final int minPoolSize; 

    /**
     * Constructor privado que recibe un objeto Builder.
     * @param builder
     */
    private DatabaseProperties(Builder builder) {
        this.url = builder.url;
        this.username = builder.username;
        this.password = builder.password;
        this.maxPoolSize = builder.maxPoolSize;
        this.minPoolSize = builder.minPoolSize;
    }

    /**
     * Obtiene la URL de la base de datos.
     * @return
     */
    public String getUrl() { return url; }

    /**
     * Obtiene el nombre de usuario de la base de datos.
     * @return
     */
    public String getUsername() { return username; }

    /**
     * Obtiene la contraseña de la base de datos.
     * @return
     */
    public String getPassword() { return password; }

    /**
     *  Obtiene el tamaño máximo del pool de conexiones.
     * @return
     */
    public int getMaxPoolSize() { return maxPoolSize; }

    /**
     *  Obtiene el tamaño mínimo del pool de conexiones.
     * @return
     */
    public int getMinPoolSize() { return minPoolSize; }

    /**
     * Crea una instancia de Builder.
     * @return
     */
    public static class Builder {
        private String url;
        private String username = "";
        private String password = "";
        private int maxPoolSize = 10;
        private int minPoolSize = 1;

        /**
         * Constructor por defecto.
         *
         * @param url
         * @return
         */
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        /**
         * Constructor por defecto.
         * @param username
         * @return
         */
        public Builder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Constructor por defecto.
         * @param password
         * @return
         */
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Constructor por defecto.
         * @param maxPoolSize
         * @return
         */
        public Builder maxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        /**
         * Constructor por defecto.
         * @param minPoolSize
         * @return
         */
        public Builder minPoolSize(int minPoolSize) {
            this.minPoolSize = minPoolSize;
            return this;
        }

        /**
         * Construye una instancia de DatabaseProperties.
         * @return
         */
        public DatabaseProperties build() {
            if (url == null || url.isEmpty()) {
                throw new IllegalStateException("URL es requerida");
            }
            return new DatabaseProperties(this);
        }
    }
}
