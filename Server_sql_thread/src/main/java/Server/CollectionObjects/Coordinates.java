package Server.CollectionObjects;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 *Клаас  Coordinates хранит в себе значения координат Х и Y и отображает текущие координаты в пути
 */
@XmlType(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates implements Serializable {
    private int x; //Максимальное значение поля: 896, Поле не может быть null
    private int y; //Поле не может быть null

    public Coordinates() {
    }

    /**
     *Конструктор класса Coordinates
     * @param x  значение координаты X
     * @param y  значение координаты Y
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Метод возвращает значения атрибута Х
     * @return     значение атрибуты Х
     */

    public int getX() {
        return x;
    }
    /**
     * Метод устанавливает значение атрибута Х
     * @param x значение координаты Х
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Метод возвращает значения атрибута Y
     * @return    значение атрибуты Y
     */

    public int getY() {
        return y;
    }
    /**
     * Метод устанавливает значения атрибута Y
     * @param y значение координаты Y
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Метод возвращает строку с атрибутами Х и Y
     * @return      отформатированная строка с координатами
     */
    public String getInfo() {
        return "Координаты | X:" + x + " | Y:" + y;
    }
}
