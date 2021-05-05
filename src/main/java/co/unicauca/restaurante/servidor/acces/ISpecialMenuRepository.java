package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.domain.SpecialMenu;

/**
 *
 * @author Luis Arango 
 */
public interface ISpecialMenuRepository {
    public String createSpecialMenu(SpecialMenu prmObjSpecialMenu);
    public SpecialMenu findSpecialMenu(int prmsmenID);
}
