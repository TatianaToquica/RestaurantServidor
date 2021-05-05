/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.domain.Restaurant;
import java.util.List;

/**
 * Interfaz repositorio de la clase restaurante
 * @author Usuario
 */
public interface IRestaurantRepository {
    public Restaurant findRestaurant(String prmresID);
    public String createRestaurant(Restaurant prmObjRestaurant);
    public List<Restaurant> findAllRestaurant();
    public String deleteRestaurant();
    public String updateRestaurant();
}
