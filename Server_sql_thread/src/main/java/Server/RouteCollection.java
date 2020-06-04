package Server;

import Server.CollectionObjects.Route;
import Server.CollectionObjects.ZonedDateTimeAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;

@XmlRootElement
public class RouteCollection implements Serializable {


    private java.time.ZonedDateTime creation_date = null;
    private int counter = 0;
    private HashSet<Route> set = null;

    public RouteCollection(){
        set = new HashSet<Route>();
        creation_date = ZonedDateTime.now();
    }

    /**
     * Метод возвращает значение атрибуты коллекции Route
     * @return значение атрибуты set
     */
    @XmlElement
    public HashSet<Route> getSet() {
        return set;
    }
    /**
     * Метод возвращает увеличивает счетчик ID на 1 и возвращает его
     * @return увеличенная на 1 атрибута counter
     */
    public int getNextCounter() {
        return ++counter;
    }
    /**
     * Метод возвращает атрибуту creation_date
     * @return значение атрибуты creation_date
     */

    @XmlAttribute
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    public ZonedDateTime getTime() {
        return creation_date;
    }

    public void setCreation_date(ZonedDateTime creation_date) {
        this.creation_date = creation_date;
    }

    @XmlAttribute
    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
