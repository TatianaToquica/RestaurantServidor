
package co.unicauca.restaurante.servidor.acces;

import co.unicauca.restaurante.comunicacion.domain.DayMenu;

/**
 *
 * @author Luis Arangp
 */
public interface IDayMenuRepository {
    public String createDayMenu(DayMenu prmObjMenuDay);
    public DayMenu findDayMenu(int prmdmenID);
    public String updateDayMenu(int prmdmenID);
    public String deleteDayMenu(int prmdemID);
}
