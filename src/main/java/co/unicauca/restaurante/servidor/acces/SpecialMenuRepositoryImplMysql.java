package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.domain.SpecialMenu;
import co.unicauca.restaurante.comunicacion.infra.Utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Arango
 */
public class SpecialMenuRepositoryImplMysql implements ISpecialMenuRepository{

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
            Logger.getLogger(SpecialMenuRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar la tabla SpecialMenu de la base de datos", ex);
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
            Logger.getLogger(SpecialMenuRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }
//</editor-fold>
    /**
     * Crea un menu especial por paramatro del tipo del objeto
     * @param prmObjSpecialMenu Nuevo objeto menu a ser creado
     * @return El ID del objeto si fue creado o de lo contrario una expeción
     */
    @Override
    public String createSpecialMenu(SpecialMenu prmObjSpecialMenu) {
        try {
            this.connect();
            String sql = "INSERT INTO SpecialMenu (smenID,smenDay,resID) "
                    + "VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, prmObjSpecialMenu.getSmenID());
            pstmt.setString(2, prmObjSpecialMenu.getSmenDay());
            pstmt.setString(3, prmObjSpecialMenu.getResID());
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(DishRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al insertar el ObjSpecialMenu", ex);
        }
        return Integer.toString(prmObjSpecialMenu.getSmenID());
    }
    /**
     * Encuentra un menu por su ID
     * @param prmsmenID ID del menu especial a buscar
     * @return Objeto SpecialMenu si se encuentra en la base o null de lo contrario
     */
    @Override
    public SpecialMenu findSpecialMenu(int prmsmenID) {
        SpecialMenu varSpecialMenu = null;
        try {
            this.connect();
            String sql = "SELECT * FROM SpecialMenu WHERE smenID=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,prmsmenID);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                varSpecialMenu = new SpecialMenu();
                varSpecialMenu.setSmenID(res.getInt("smenID"));
                varSpecialMenu.setSmenDay(res.getString("smenDay"));
                varSpecialMenu.setResID(res.getString("resID"));
            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(SpecialMenuRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar el menu especial de la base de datos", ex);
        }
        return varSpecialMenu;
    }
    
}
