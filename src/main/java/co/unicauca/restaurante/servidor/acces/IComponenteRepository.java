package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.domain.Componente;

/**
 * Interfaz para el componente 
 * @author Luis Arango
 */
public interface IComponenteRepository {
    public String createComponente(Componente prmObjComponente);
    public Componente findComponente(int prmcompID);
    public String deleteComponente(int prmcompID);
    public String updateComponente(int prmcompID);
}
