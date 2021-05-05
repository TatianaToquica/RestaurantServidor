
package co.unicauca.restaurante.servidor.domain.services;
import co.unicauca.restaurante.comunicacion.domain.Dish;
import co.unicauca.restaurante.comunicacion.infra.JsonError;
import co.unicauca.restaurante.servidor.acces.IDishRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Usuario
 */
public class DishService {
    IDishRepository repoDish;
    /**
    * Constructor parametrizado, Hace inyeccion de dependencias.
    * @param repoDish 
    */
    public DishService(IDishRepository repoDish) {
        this.repoDish = repoDish;
    }

    /**
     * Constructor por defecto.
     */
    public DishService() {
    }
    
    public String CreateDish(Dish parDish) {
        List<JsonError> errors = new ArrayList<>();
        
        if (parDish.getDishID() == 0 || parDish.getDishName().isEmpty() || parDish.getDishDescription().isEmpty() || parDish.getDishPrice() == 0) {
        errors.add(new JsonError("400", "BAD_REQUEST", "LA INFORMACION X ES OBLIGATORIA "));
        }
        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorJson = gson.toJson(errors);
            return errorJson;
        }
        return repoDish.createDish(parDish);
    }
}
