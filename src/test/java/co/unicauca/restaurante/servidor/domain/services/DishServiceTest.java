
package co.unicauca.restaurante.servidor.domain.services;

import co.unicauca.restaurante.comunicacion.domain.Dish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author HP
 */
public class DishServiceTest {
    
    
    /**
     * Test of CreateDish method, of class DishService.
     */
    @Test
    public void testCreateDish() {
        System.out.println("CreateDish");
        Dish parDish = new Dish(1,"Arroz chino","raicez y camarones",15000,null);
        DishService instance = new DishService();
        String expResult = "1";
        String result = instance.CreateDish(parDish);
        assertEquals(expResult, result);        
    }
    
}
