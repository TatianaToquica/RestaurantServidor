package co.unicauca.restaurante.servidor.domain.services;
import co.unicauca.restaurante.comunicacion.domain.Restaurant;
import co.unicauca.restaurante.comunicacion.infra.JsonError;
import co.unicauca.restaurante.servidor.acces.IRestaurantRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Juan Jose
 */
public class RestaurantService {
       /**
     * repositorio de Restaurante, Objeto de tipo IRestauranRepository.
     */
    IRestaurantRepository repo;

    /**
     * Constructor por defecto.
     */
    public RestaurantService() {
    }

    /**
     * Constructor parametrizado, Hace inyeccion de dependencias.
     *
     * @param repo repositorio de tipo IRestaurantRepository.
     */
    public RestaurantService(IRestaurantRepository repo) {
        this.repo = repo;
    }
     /**
     * Metodo encargado buscar un restaurante usando la interfaz
     * IRestaurantRepository.
     *
     * @param parResId cadena de texto con la cual se buscara un restaurante.
     * @return objeto tipo Restaurante
     */
    public Restaurant findRestaurant(String parResId) {
        return repo.findRestaurant(parResId);
    }

    /**
     * Crea un nuevo restaurante, Aplica validaciones de negocio.
     *
     * @param parRest Objeto de tipo Restaurant.
     * @return llamado al metodo createRestaurant.
     */
    public String CreateRestaurant(Restaurant parRest) {
        List<JsonError> errors = new ArrayList<>();
        if (parRest.getResID().isEmpty() || parRest.getResName().isEmpty() || parRest.getResAddress().isEmpty() || parRest.getResDescFood().isEmpty()) {
            errors.add(new JsonError("400", "BAD_REQUEST", "LA INFORMACION X ES OBLIGATORIA "));
        }
        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorJson = gson.toJson(errors);
            return errorJson;
        }

        return repo.createRestaurant(parRest);
    }

    /**
     * Metodo encargado de obtener una lista de todos los restaurantes
     * existentes.
     *
     * @return llamado a metodo findAllRestaurant.
     */
    public List<Restaurant> ListRestaurant() {
        List<JsonError> errors = new ArrayList<>();
        if (!repo.findAllRestaurant().isEmpty()) {
            if (!errors.isEmpty()) {
                errors.add(new JsonError("400", "BAD_REQUEST", "ERROR AL GENERAR PEDIDO SQL"));
            }
        }
        return repo.findAllRestaurant();
    } 
}
