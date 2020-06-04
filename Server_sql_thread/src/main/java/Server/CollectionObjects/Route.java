package Server.CollectionObjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.ZonedDateTime;
/**
 *Клаас Route хранит в себе Id, название маршрута, значения координат, дату создания маршрута, названия и координаты  начала и конца пути, дистанцию всего пути
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Route implements Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    @XmlElement(name="coordinates")
    private Coordinates coordinates; //Поле не может быть null
    @XmlElement(name="from")
    private Location_from from; //Поле может быть null
    @XmlElement(name="to")
    private Location_to to; //Поле может быть null
    private double distance; //Значение поля должно быть больше 1
    private String owner;

    public Route() {
    }

    /**
     * Конструктор класса Route
     * @param id  значение id элемента
     */
    public Route(int id){
        this.id = id;
    }
    /**
     *Конструктор класса Route
     * @param id  значение id элемента
     * @param name  название маршрута
     * @param coordinates  значение координат X Y Z
     * @param creationDate  дата создания пути
     * @param from  значение координат и название начала пути
     * @param to  значение координат и название конца пути
     */
    public Route(int id, String name, Coordinates coordinates, ZonedDateTime creationDate, Location_from from, Location_to to, String owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = Math.abs(Math.sqrt(Math.pow(from.getX()-to.getX(),2) + Math.pow(from.getY()-to.getY(),2)) + Math.pow(to.getZ(),2));
        this.owner = owner;
    }
    /**
     * Метод возвращает значение атрибуты ID
     * @return    значение атрибуту ID
     */

    public int getId() {
        return id;
    }
    /**
     * Метод устанавливает значение атрибуты ID
     *  @param id   значение ID элемента
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Метод возвращает значение атрибуты Distance
     * @return    значение  атрибуты Distance
     */

    public double getDistance() {
        return distance;
    }
    /**
     * Метод возвращает строку с информацией о пути с Id, названием, датой, дистанцией, координатами
     * @return  отформатированная строка с информацией о пути для вывода на экран
     */
    public String showInfo() {
        return "ID: " + Integer.toString(id) + "| Name: " + name + "| creationDate: " + creationDate.toString() + "| Distance: " + distance + "\n"
                + coordinates.getInfo() + "\n" + from.getInfo() + "\n" + to.getInfo() + "\n" + "Автор: " + owner;
    }
    /**
     * Метод возвращает значение атрибута Name
     * @return name   возвращает значение Name
     */

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        Route r = (Route) o;
        return (r.getId() == this.id);
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Location_from getFrom() {
        return from;
    }

    public Location_to getTo() {
        return to;
    }

    public String getOwner() {
        return owner;
    }

}
