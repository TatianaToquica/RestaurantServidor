
package co.unicauca.restaurante.servidor.domain.services;

import co.unicauca.restaurante.comunicacion.domain.Componente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author HP
 */
public class ComponenteServiceTest {
    
        /**
     * Test of createComponente method, of class ComponenteService.
     */
    @Test
    public void testCreateComponente() {
        System.out.println("createComponente");
        Componente prmObjComponente = new Componente(11,"Lentejas",1200,"principio",null);
        ComponenteService instance = new ComponenteService();
        String expResult = "11";
        String result = instance.createComponente(prmObjComponente);
        assertEquals(expResult, result);
        
    }
    
}
