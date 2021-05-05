package co.unicauca.restaurante.servidor.app;

import co.unicauca.restaurante.servidor.infra.RestauranteServidorSocket;

/**
 *
 * @author Luis Arango
 */
public class RestaurantApplication {
        public static void main(String args[]) {
        RestauranteServidorSocket server = new RestauranteServidorSocket();
        server.startServer();
    }
}
