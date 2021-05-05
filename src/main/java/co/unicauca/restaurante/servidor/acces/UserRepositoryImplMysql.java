package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.domain.User;
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
public class UserRepositoryImplMysql implements IUserRepository {
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    /**
     * Objeto de tipo conexión
     */
    private Connection conn;
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Métodos Sobre-Escritos">
    /**
     * Metodo que sobreescribe la interfaz de repositorio de usuario para encontrar un usuario
     * @param prmUserLoginName Nombre de usuario a buscar.
     * @return Objeto de tipo User
     */
    @Override
    public User findUser(String prmUserLoginName) {
        User varUser = null;
        this.connect();
        try {
            String sql = "SELECT * FROM User where userLoginName=? ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmUserLoginName);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                varUser = new User();
                varUser.setUserLoginName(res.getString("userLoginName"));
                varUser.setUserPassword(res.getString("userPassword"));
                varUser.setUserName(res.getString("userName"));
                varUser.setUserLastName(res.getString("userLastName"));
                varUser.setUserAddres(res.getString("userAddress"));
                varUser.setUserMobile(res.getString("userMobile"));
                varUser.setUserEmail(res.getString("userEmail"));
            }
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(UserRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar el Uusuario de la base de datos", ex);
        }
        return varUser;
    }
    /**
     * Metodo que sobreescribe la interfaz de repositorio de usuario y cre un usuario
     * @param prmObjUser Objeto de tipo User
     * @return String con el valor retornado por gerUserLoginName
     */
    @Override
    public String createUser(User prmObjUser) {
        try {
            this.connect();
            String sql = "INSERT INTO User(userLoginName, userPassword, userName, userLastName, userAddress, userMobile, userEmail) "
                    + "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prmObjUser.getUserLoginName());
            pstmt.setString(2, prmObjUser.getUserPassword());
            pstmt.setString(3, prmObjUser.getUserName());
            pstmt.setString(4, prmObjUser.getUserLastName());
            pstmt.setString(5, prmObjUser.getUserAddres());
            pstmt.setString(6, prmObjUser.getUserMobile());
            pstmt.setString(7, prmObjUser.getUserEmail());
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(IUserRepository.class.getName()).log(Level.SEVERE, "Error al insertar el registro", ex);
        }
        return prmObjUser.getUserLoginName();
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Metodos de conexión">
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
            Logger.getLogger(UserRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar usuario en la base de datos", ex);
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
            Logger.getLogger(UserRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }
//</editor-fold>
}
