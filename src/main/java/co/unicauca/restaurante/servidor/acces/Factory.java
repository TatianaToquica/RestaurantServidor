
package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.infra.Utilities;

/**
 *
 * @author Luis Arango
 */
public class Factory {
    /**
     * Instancia de la clase Factory
     */
    private static Factory instance;
    /**
     * Constructor por defecto no parametrizado de la clase Factory
     */
    private Factory(){}
    /**
     * Clase singleton
     * @return Instancia de la clase factory
     */
    public static Factory getInstance(){
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }
    /**
     * Método que crea una instancia concreta de la jerarquia IRestaurantRepository.
     *
     * @return una clase hija de la abstracción IRestaurantRepository.
     */
    public IRestaurantRepository getRepository(){
        String type = Utilities.loadProperty("restaurant.repository");
        if(type.isEmpty()){
            type = "mysql";
        }
        IRestaurantRepository result = null;
        
        switch (type){
            case "mysql" -> result = new RestaurantRepositoryImplMysql();
        }
        return result;
    }
    /**
     * Método que crea una instancia concreta de la jerarquia IUserRepository.
     *
     * @return una clase hija de la abstracción IUserRepository.
     */
    public IUserRepository getRepositoryUser(){
        String type = Utilities.loadProperty("restaurant.repository");
        if (type.isEmpty()) {
            type = "mysql";
        }
        IUserRepository result = null;
        switch (type) {
            case "mysql" -> result = new UserRepositoryImplMysql();
        }
        return result;
    }
    /**
     * Método que crea una instancia concreta de la jerarquia IDishRepository.
     *
     * @return una clase hija de la abstracción IDishRepository.
     */
    public IDishRepository getRepositoryDish() {
        String type = Utilities.loadProperty("restaurant.repository");
        if (type.isEmpty()) {
            type = "mysql";
        }
        IDishRepository result = null;
        switch (type) {
            case "mysql":
                result = new DishRepositoryImplMysql();
                break;
        }
        return result;
    }
    /**
     * Método que crea una instancia concreta de la jerarquia IComponenteRepository.
     *
     * @return una clase hija de la abstracción IComponenteRepository.
     */
    public IComponenteRepository gerRepositoryComponente(){
        String type = Utilities.loadProperty("restaurant.repository");
        if (type.isEmpty()) {
            type = "mysql";
        }
        IComponenteRepository result = null;
        switch (type){
            case "mysql":
                result = new ComponenteRepositoryImplMysql();
                break;
        }
        return result;
    }
}
