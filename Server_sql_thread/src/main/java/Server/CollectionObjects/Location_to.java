package Server.CollectionObjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 *Клаас Location_to хранит в себе  название конца пути и значения координат Х и Y и Z
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Location_to implements Serializable {
    private long x;
    private Integer y; //Поле не может быть null
    private Integer z; //Поле не может быть null
    private String name; //Поле может быть null

    public Location_to() {
    }
    /**
     *Конструктор класса Location_to
     * @param x  значение координаты X
     * @param y  значение координаты Y
     * @param z  значение координаты Z
     * @param name  значение координаты name
     */
    public Location_to(long x, Integer y, Integer z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
    /**
     * Метод возвращает значения атрибута Х
     * @return    значение атрибуты Х
     */

    public long getX() {
        return x;
    }
    /**
     * Метод возвращает значения атрибута Y
     * @return   значение атрибуты Y
     */

    public Integer getY() {
        return y;
    }

    /**
     * Метод возвращает значения атрибута Z
     * @return     значение атрибуты Z
     */

    public Integer getZ() {
        return z;
    }

    /**
     * Метод возвращает строку со значением атрибуты name
     * @return name    значение атрибуты name
     */

    public String getName() {
        return name;
    }
    /**
     * Метод возвращает строку с атрибутами Х и Y и Z
     * @return x     возвращает значение атрибутов X в виде строки
     * @return y     возвращает значение атрибутов Y в виде строки
     * @return z     возвращает значение атрибутов Z в виде строки
     */
    public String getInfo() {
        return "Место назначения: " + name + " | X:" + x + " | Y:" + y + " | Z:" + z;
    }
}
