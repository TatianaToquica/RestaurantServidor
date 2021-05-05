package co.unicauca.restaurante.servidor.domain.services;

import co.unicauca.restaurante.comunicacion.domain.Componente;
import co.unicauca.restaurante.comunicacion.infra.JsonError;
import co.unicauca.restaurante.servidor.acces.IComponenteRepository;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Luis Arango
 */
public class ComponenteService {
    IComponenteRepository repoComponente;
    /**
    * Constructor parametrizado, Hace inyeccion de dependencias.
    * @param repoComponente
    */
    public ComponenteService(IComponenteRepository repoComponente) {
        this.repoComponente = repoComponente;
    }

    /**
     * Constructor por defecto.
     */
    public ComponenteService() {
    }
    
    public String createComponente(Componente prmObjComponente) {
        List<JsonError> errors = new ArrayList<>();
        
        if (prmObjComponente.getCompId()== 0 || prmObjComponente.getCompNombre().isEmpty() || prmObjComponente.getTipo().isEmpty() || prmObjComponente.getPrecio()== 0 || prmObjComponente.getCompImage() == null) {
        errors.add(new JsonError("400", "BAD_REQUEST", "LA INFORMACION X ES OBLIGATORIA "));
        }
        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorJson = gson.toJson(errors);
            return errorJson;
        }
        return repoComponente.createComponente(prmObjComponente);
    }
}
