package co.unicauca.restaurante.servidor.infra;
import co.unicauca.serversockettemplate.infra.ServerSocketTemplate;
import co.unicauca.restaurante.comunicacion.domain.Componente;
import co.unicauca.restaurante.servidor.acces.Factory;
import co.unicauca.restaurante.comunicacion.domain.Dish;
import co.unicauca.restaurante.comunicacion.domain.Restaurant;
import co.unicauca.restaurante.comunicacion.domain.User;
import co.unicauca.restaurante.comunicacion.infra.Protocol;
import co.unicauca.restaurante.comunicacion.infra.Utilities;
import co.unicauca.restaurante.servidor.acces.IComponenteRepository;
import co.unicauca.restaurante.servidor.acces.IDishRepository;
import co.unicauca.restaurante.servidor.acces.IRestaurantRepository;
import co.unicauca.restaurante.servidor.acces.IUserRepository;
import co.unicauca.restaurante.servidor.domain.services.ComponenteService;
import co.unicauca.restaurante.servidor.domain.services.DishService;
import co.unicauca.restaurante.servidor.domain.services.RestaurantService;
import co.unicauca.restaurante.servidor.domain.services.UserService;
import co.unicauca.serversockettemplate.helpers.JsonError;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author JuanJose y Tatiana
 */
public class RestauranteServidorSocket extends ServerSocketTemplate{
    
     /**
     * Objeto de tipo RestaurantService.
     */
    private RestaurantService service;

    private DishService serviceDish;
    
    private ComponenteService serviceComponente;

    /**
     * Objeto de tipo UserService.
     */
    private final UserService service2;

    /**
     * Constructor parametrizado, se encarga de inyectar las dependencias a las
     * variables service y service2.
     */
    public RestauranteServidorSocket() {
        // Se hace la inyección de dependencia
        IRestaurantRepository repository = Factory.getInstance().getRepository();
        service = new RestaurantService(repository);

        IUserRepository repository1 = Factory.getInstance().getRepositoryUser();
        service2 = new UserService(repository1);

        IDishRepository repositoryDish = Factory.getInstance().getRepositoryDish();
        serviceDish = new DishService(repositoryDish);
        
        IComponenteRepository repositoryComponente = Factory.getInstance().gerRepositoryComponente();
        serviceComponente = new ComponenteService(repositoryComponente);
    }

    /**
     * Metodo encargado de procesar una peticion proveniente del cliente.
     *
     * @param requestJson Peticion que proviene del socket del cliente en
     * formato json.
     */
    @Override
    protected void processRequest(String requestJson) {
        // Convertir la solicitud a objeto Protocol para poderlo procesar
        Gson gson = new Gson();
        Protocol protocolRequest = gson.fromJson(requestJson, Protocol.class);

        switch (protocolRequest.getResource()) {
            case "Restaurante":

                if (protocolRequest.getAction().equals("get")) {
                    // Consultar un customer
                    processGetRestaurant(protocolRequest);
                }

                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un customer    
                    processPostRestaurant(protocolRequest);
                }
                if (protocolRequest.getAction().equals("gets")) {
                    //(consutlar todos los restaurantes
                    processGetListRestaurant();
                }
                break;
            case "Usuario":

                if (protocolRequest.getAction().equals("get")) {
                    // Consultar un customer
                    processGetUser(protocolRequest);
                    // processGetListRestaurant(protocolRequest);
                }
                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un customer    
                    processPostUser(protocolRequest);
                }
                break;
            case "Dish":
                if (protocolRequest.getAction().equals("post")) {
                    processPostDish(protocolRequest);
                }
            case "Componente":
                if (protocolRequest.getAction().equals("post")) {
                    processPostComponente(protocolRequest);
                }
        }

    }

    /**
     * Procesa la solicitud de consultar un restaurante en especifico.
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private void processGetRestaurant(Protocol protocolRequest) {
        // Extraer la cedula del primer parámetro
        String id = protocolRequest.getParameters().get(0).getValue();
        Restaurant customer = service.findRestaurant((id));
        if (customer == null) {
            String errorJson = generateNotFoundErrorJson();
            respond(errorJson);
        } else {
            respond(objectToJSON(customer));
        }
    }

    /**
     * Procesa la solicitud de consultar un usuario.
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    public void processGetUser(Protocol protocolRequest) {
        // Extraer la cedula del primer parámetro
        String userName = protocolRequest.getParameters().get(0).getValue();
        User user = service2.findUser((userName));
        if (user == null) {
            String errorJson = generateNotFoundErrorJson();
            respond(errorJson);
        } else {
            respond(objectToJSON(user));
        }
    }

    /**
     * Procesa la solicitud para consultar todos los restaurantes.
     *
     * @param protocolRequest
     */
    private void processGetListRestaurant() {
        List<Restaurant> listaRestaurant = service.ListRestaurant();
        if (!listaRestaurant.isEmpty()) {
            respond(ArrayToJSON(listaRestaurant));
        } else {
            String errorJson = generateNotFoundErrorJson();
            respond(errorJson);
        }

    }

    /**
     * Procesa la solicitud de agregar un Restaurante
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private void processPostRestaurant(Protocol protocolRequest) {

        Restaurant varRestaurant = new Restaurant();
        // Reconstruir el restaurante a partid de lo que viene en los parámetros
        varRestaurant.setResID(((protocolRequest.getParameters().get(0).getValue())));
        varRestaurant.setResName((protocolRequest.getParameters().get(1).getValue()));
        varRestaurant.setResAddress(protocolRequest.getParameters().get(2).getValue());
        varRestaurant.setResDescFood(protocolRequest.getParameters().get(3).getValue());
        String response = service.CreateRestaurant(varRestaurant);
        respond(response);
    }

    /**
     * Procesa la solicitud de agregar un plato.
     */
    private void processPostDish(Protocol protocolRequest) {
        Dish objDish = new Dish();

        objDish.setDishID(Integer.parseInt(protocolRequest.getParameters().get(0).getValue()));
        objDish.setDishName(protocolRequest.getParameters().get(1).getValue());
        objDish.setDishDescription(protocolRequest.getParameters().get(2).getValue());
        objDish.setDishPrice(Double.parseDouble(protocolRequest.getParameters().get(3).getValue()));
        // TODO: Revisar objDish.setDishImage(Base64.Decoder(protocolRequest.getParameters().get(4).getValue()));
        String response = serviceDish.CreateDish(objDish);
        respond(response);
    }
    
    private void processPostComponente(Protocol protocolRequest){
        Componente objComponente = new Componente();
        
        objComponente.setCompId(Integer.parseInt(protocolRequest.getParameters().get(0).getValue()));
        objComponente.setCompNombre(protocolRequest.getParameters().get(1).getValue());
        objComponente.setTipo(protocolRequest.getParameters().get(2).getValue());
        objComponente.setPrecio(Integer.parseInt(protocolRequest.getParameters().get(3).getValue()));
        String response = serviceComponente.createComponente(objComponente);
        respond(response);
    }

    /**
     * Metodo encargado de procesar la solicitud de crear un usuario.
     *
     * @param protocolRequest
     */
    public void processPostUser(Protocol protocolRequest) {
        User varUser = new User();
        varUser.setUserLoginName(protocolRequest.getParameters().get(0).getValue());
        varUser.setUserPassword(protocolRequest.getParameters().get(1).getValue());
        varUser.setUserName(protocolRequest.getParameters().get(2).getValue());
        varUser.setUserLastName(protocolRequest.getParameters().get(3).getValue());
        varUser.setUserAddres(protocolRequest.getParameters().get(4).getValue());
        varUser.setUserMobile(protocolRequest.getParameters().get(5).getValue());
        varUser.setUserEmail(protocolRequest.getParameters().get(6).getValue());
        String response = service2.createUser(varUser);
        respond(response);
    }

    /**
     * Convierte Una lista de Restaurante a json para que el servidor lo envie
     * como respuesta al socket.
     *
     * @param parLista Lista de tipo Restaurant.
     * @return Lista de restaurant en formato json (String).
     */
    private String ArrayToJSON(List<Restaurant> parLista) {
        Gson gson = new Gson();
        String strObject = gson.toJson(parLista);
        return strObject;
    }

    /**
     * Genera un ErrorJson de cliente cuando este no se encuentra.
     *
     * @return error en formato json
     */
    private String generateNotFoundErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("404");
        error.setError("NOT_FOUND");
        error.setMessage("Cliente no encontrado. Cédula no existe");
        errors.add(error);

        Gson gson = new Gson();
        String errorsJson = gson.toJson(errors);

        return errorsJson;
    }
    
    @Override
    protected ServerSocketTemplate init() {
        PORT = Integer.parseInt(Utilities.loadProperty("server.port"));
         // Se hace la inyección de dependencia
        IRestaurantRepository repository = Factory.getInstance().getRepository();
        this.setService(new RestaurantService(repository));
        return this;
    }
    
    /**
     * @return the service
     */
    public RestaurantService getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(RestaurantService service) {
        this.service = service;
    }
    
}
