/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.domain.Dish;

/**
 *
 * @author Luis Arango
 */
public interface IDishRepository {
    public String createDish(Dish prmObjDish);
    public Dish findDish(int prmID);
    public String deleteDish(int prmID);
    public String updateDish(int prmID);
}
