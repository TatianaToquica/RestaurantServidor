package co.unicauca.restaurante.servidor.domain.services;

import co.unicauca.restaurante.comunicacion.domain.User;
import co.unicauca.restaurante.servidor.acces.IUserRepository;
import co.unicauca.restaurante.comunicacion.infra.JsonError;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis Arango
 */
public class UserService {
    IUserRepository repo;
    public UserService(){}
    public UserService(IUserRepository prmRepo){
        repo = prmRepo;
    }
    public User findUser(String prmUserLoginName){
        return repo.findUser(prmUserLoginName);
    }
    public String createUser (User prmObjUser){
        List<JsonError> error = new ArrayList<>();
        if(prmObjUser.getUserLoginName().isEmpty() || prmObjUser.getUserPassword().isEmpty() || prmObjUser.getUserName().isEmpty() ||
                prmObjUser.getUserLastName().isEmpty() || prmObjUser.getUserAddres().isEmpty() || prmObjUser.getUserMobile().isEmpty()
                || prmObjUser.getUserEmail().isEmpty()){
            error.add(new JsonError("400", "BAD_REQUEST","La información marcada con * es obligatoria"));
        }
        if (!error.isEmpty()) {
            Gson gson = new Gson();
            String errorJson = gson.toJson(error);
            return errorJson;
        }
        return repo.createUser(prmObjUser);
    }
}
