/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.domain.Restaurant;
import co.unicauca.restaurante.comunicacion.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la interfaz de Restaurante
 * @author Luis Arango
 */
public class RestaurantRepositoryImplMysql implements IRestaurantRepository{

    public Connection conn;
    
    //<editor-fold defaultstate="collapsed" desc="Metodos de conexion">
    /**
     * Método encargado de realizar la conexión a la base de datos
     * @return 1 si la conexión fue exitosa, -1 de lo contrario
     */
    public int connect() {
        try {
            Class.forName(Utilities.loadProperty("server.db.driver"));
            //crea una instancia de la controlador de la base de datos
            String url = Utilities.loadProperty("server.db.url");
            String username = Utilities.loadProperty("server.db.username");
            String pwd = Utilities.loadProperty("server.db.password");
            conn = DriverManager.getConnection(url, username, pwd);
            return 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar la tabla Dish de la base de datos", ex);
        }
        return -1;
    }
    
    /**
     * Metodo encargado de desconectar la aplicacion de la base de datos.
     */
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }
//</editor-fold>
    /**
     * Encuentra un restaurante de la base de datos por el ID
     * @param prmresID ID del restaurante a buscar
     * @return Retorna Objeto de tipo Restaurante encontrado o null de lo contrario
     */
    @Override
    public Restaurant findRestaurant(String prmresID) {
        Restaurant varObjRestaurant = null;
        try {
            this.connect();
            String sql = "SELECT * FROM Restaurant WHERE resID=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmresID);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                varObjRestaurant = new Restaurant();
                varObjRestaurant.setResID(res.getString("resID"));
                varObjRestaurant.setResName(res.getString("resNombre"));
                varObjRestaurant.setResAddress(res.getString("resAddress"));
                varObjRestaurant.setResDescFood(res.getString("resDescFood"));
            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar el restaurante de la base de datos", ex);
        }
        return varObjRestaurant;
    }

    @Override
    public String createRestaurant(Restaurant prmObjRestaurant) {
        try {
            this.connect();
            String sql = "INSERT INTO Restaurant (resID, resName, resAddress, resDescFood) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmObjRestaurant.getResID());
            pstmt.setString(2, prmObjRestaurant.getResName());
            pstmt.setString(3, prmObjRestaurant.getResAddress());
            pstmt.setString(4, prmObjRestaurant.getResDescFood());
        } catch (SQLException ex) {
            Logger.getLogger(IRestaurantRepository.class.getName()).log(Level.SEVERE, "Error al insertar el Restaurante", ex);
        }
        return prmObjRestaurant.getResID();
    }

    @Override
    public List<Restaurant> findAllRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String updateRestaurant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
