package Server.CollectionObjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 *Клаас Location_from хранит в себе  название начала пути и значения координат Х и Y
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Location_from implements Serializable {
    private Double x; //Поле не может быть null
    private long y;
    private String name; //Строка не может быть пустой, Поле не может быть null

    public Location_from() {
    }
    /**
     *Конструктор класса Location_from
     * @param x  значение координаты X
     * @param y  значение координаты Y
     * @param name  значение координаты name
     */
    public Location_from(Double x, long y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
    /**
     * Метод возвращает значения атрибута Х
     * @return    возвращает значение атрибуты Х
     */
    public Double getX() {
        return x;
    }
    /**
     * Метод возвращает значения атрибута Y
     * @return     значение атрибуты Y
     */
    public long getY() {
        return y;
    }
    /**
     * Метод возвращает строку со значением атрибуты name
     * @return  значение атрибуты Х
     */
    public String getName() {
        return name;
    }
    /**
     * Метод возвращает строку с атрибутами Х и Y
     * @return      значения атрибутов X и Yв виде строки
     */
    public String getInfo() {
        return "Начало пути: " + name + " | X:" + x + " | Y:" + y;
    }
}
