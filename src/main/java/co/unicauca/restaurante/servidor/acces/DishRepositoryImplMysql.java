package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.domain.Dish;
import co.unicauca.restaurante.comunicacion.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa la interfaz repositorio de Dish
 * @author Usuario
 */
public class DishRepositoryImplMysql implements IDishRepository{
    
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
            Logger.getLogger(DishRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar la tabla Dish de la base de datos", ex);
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
            Logger.getLogger(DishRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }
//</editor-fold>
    /**
     * Crea un Dish (Plato escpecial)
     * @param prmObjDish Objeto Dish a ser insertado en la base de datos
     * @return ID del Dish creado o una excepción en caso de fallar
     */
    @Override
    public String createDish(Dish prmObjDish) {      
        try {
            this.connect();
            String sql = "INSERT INTO Dish (dishID, dishName, dishDescription, dishPrice, dishImage) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, prmObjDish.getDishID());
            pstmt.setString(2, prmObjDish.getDishName());
            pstmt.setString(3, prmObjDish.getDishDescription());
            pstmt.setDouble(4, prmObjDish.getDishPrice());
            pstmt.setBytes(5, prmObjDish.getDishImage());
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(DishRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al insertar el ObjDish", ex);
        }
        return Integer.toString(prmObjDish.getDishID());
    }

    @Override
    public Dish findDish(int prmID) {
        //TODO: implementar
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteDish(int prmID) {
        //TODO: implementar
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String updateDish(int prmID) {
        //TODO: implementar
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
