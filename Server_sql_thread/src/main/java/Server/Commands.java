package Server;

import Server.CollectionObjects.Route;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Класс Server.Commands хранит в себе информацию и назначения всех команд
 */
public class Commands {

    /**
     * Метод выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     *
     * @return отформатированная строка с информацией о коллекции
     */
    public static String info() {
        utils.lock.readLock().lock();
        String result = "Тип коллекции: HashSet элементов Route \n" +
                "Дата инициализации: " + utils.collection.getTime() + "\n" +
                "Количество элементов в коллекции: " + utils.collection.getSet().size();
        utils.lock.readLock().unlock();
        return result;
    }

    /**
     * Метод выводит в стандартный поток вывода все элементы коллекции в строковом представлении
     *
     * @return строка с информацией об элементах коллекции
     */
    public static String show() {
        utils.lock.readLock().lock();
        String s = "";
        utils.collection.getSet();
        Iterator<Route> elements = utils.collection.getSet().iterator();
        while (elements.hasNext())
            s = s + "\n" + elements.next().showInfo();
        utils.lock.readLock().unlock();
        return s + "\n" + "Конец информации о коллекции";
    }

    public static int getLastId(DataBase db) {
        ResultSet rs = db.executeQuery("SELECT max(id) from s288870.\"Route\"");
        try {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }


     /**
     * Метод  добавляет новый элемент в коллекцию
     *
     * @return строка с информацией об элементах коллекции
     */
    public static String add(Route newRoute) {
        utils.lock.writeLock().lock();
        DataBase db = new DataBase();
        int result = db.executeUpdate("INSERT INTO s288870.\"Route\" " +
                "(name,creation_date,distance,x,y,name_from,x_from,y_from,name_to,x_to,y_to,z_to,owner) values ('"
                + newRoute.getName() + "','"
                + newRoute.getCreationDate() + "','"
                + newRoute.getDistance()+ "','"
                + newRoute.getCoordinates().getX() + "','"
                + newRoute.getCoordinates().getY() + "','"
                + newRoute.getFrom().getName() + "','"
                + newRoute.getFrom().getX() + "','"
                + newRoute.getFrom().getY() + "','"
                + newRoute.getTo().getName() + "','"
                + newRoute.getTo().getX() + "','"
                + newRoute.getTo().getY() + "','"
                + newRoute.getTo().getZ() + "','"
                + newRoute.getOwner() + "')");
        int id = getLastId(db);
        newRoute.setId(id);
        utils.collection.getSet().add(newRoute);
        utils.lock.writeLock().unlock();
        return "Элемент успешно добавлен";
    }


    /**
     * Метод обновляет значение элемента коллекции, id которого равен заданному
     *
     * @return строка с информацией об элементах коллекции
     */
    public static String update(Container container) {
        try {
            utils.lock.writeLock().lock();
            DataBase db = new DataBase();
            Route newRoute = container.getRoute();
            Route oldRoute = null;

            Iterator<Route> elements = utils.collection.getSet().iterator();
            while (elements.hasNext()) {
                Route element = elements.next();
                if (element.getId()==newRoute.getId()) oldRoute=element;
            }
            if (oldRoute==null) {
                utils.lock.writeLock().unlock();
                return "Элемента с таким ID не найдено";
            }
            if (!oldRoute.getOwner().equalsIgnoreCase(container.getLogin())) {
                utils.lock.writeLock().unlock();
                return "Вы не являетесь хозяином данного объекта";
            }

            String sql = "UPDATE s288870.\"Route\" SET " +
                    "name = '" + newRoute.getName() + "', " +
                    "creation_date = '" + newRoute.getCreationDate() + "', " +
                    "distance = '" + newRoute.getDistance() + "', " +
                    "x = '" + newRoute.getCoordinates().getX() + "', " +
                    "y = '" + newRoute.getCoordinates().getY() + "', " +
                    "name_from = '" + newRoute.getFrom().getName() + "', " +
                    "x_from = '" + newRoute.getFrom().getX() + "', " +
                    "y_from = '" + newRoute.getFrom().getY() + "', " +
                    "name_to = '" + newRoute.getTo().getName() + "', " +
                    "x_to = '" + newRoute.getTo().getX() + "', " +
                    "y_to = '" + newRoute.getTo().getY() + "', " +
                    "z_to = '" + newRoute.getTo().getZ() + "' " +
                    "WHERE id = '" + newRoute.getId() + "';";

            int result = db.executeUpdate(sql);
            if (result>0) {
                utils.collection.getSet().remove(oldRoute);
                utils.collection.getSet().add(newRoute);
                utils.lock.writeLock().unlock();
                return "Элемент успешно обновлен";
            }
            utils.lock.writeLock().unlock();
            return "Ошибка обновления элемента";

        } catch (Exception e) {
            //e.printStackTrace();
            utils.lock.writeLock().unlock();
            return "Ошибка выполнения команды";
        }
    }

    /**
     * Метод удаляет элемент из коллекции по его id
     *
     * @return строка с информацией об элементах коллекции
     */
    public static String remove(Container container) {
        try {
            utils.lock.writeLock().lock();
            DataBase db = new DataBase();
            Route newRoute = container.getRoute();
            Route oldRoute = null;

            Iterator<Route> elements = utils.collection.getSet().iterator();
            while (elements.hasNext()) {
                Route element = elements.next();
                if (element.getId()==newRoute.getId()) oldRoute=element;
            }
            if (oldRoute==null) {
                utils.lock.writeLock().unlock();
                return "Элемента с таким ID не найдено";
            }
            if (!oldRoute.getOwner().equalsIgnoreCase(container.getLogin())) {
                utils.lock.writeLock().unlock();
                return "Вы не являетесь хозяином данного объекта";
            }

            String sql = "DELETE FROM s288870.\"Route\" " +
                    "WHERE id = '" + newRoute.getId() + "';";

            int result = db.executeUpdate(sql);
            if (result>0) {
                utils.collection.getSet().remove(oldRoute);
                utils.lock.writeLock().unlock();
                return "Элемент успешно удален";
            }
            utils.lock.writeLock().unlock();
            return "Ошибка удаления элемента";
        } catch (Exception e) {
            //e.printStackTrace();
            utils.lock.writeLock().unlock();
            return "Ошибка выполнения команды";
        }
    }

    /**
     * Метод очищает коллекцию
     *
     * @return строка с информацией об элементах коллекции
     */
    public static String clear( Container container) {

        try {
            utils.lock.writeLock().lock();
            DataBase db = new DataBase();
            String owner = container.getLogin();

            int counter = 0;

            Iterator<Route> elements = utils.collection.getSet().iterator();
            while (elements.hasNext()) {
                Route element = elements.next();
                if (element.getOwner().equalsIgnoreCase(owner)) {
                    elements.remove();
                    counter++;
                }
            }

            String sql = "DELETE FROM s288870.\"Route\" " +
                    "WHERE owner = '" + owner + "';";

            int result = db.executeUpdate(sql);
            utils.lock.writeLock().unlock();
            return "Удалено элементов: " + counter;
        } catch (Exception e) {
            //e.printStackTrace();
            utils.lock.writeLock().unlock();
            return "Ошибка выполнения команды";
        }

    }

    /**
     * Метод добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
     *
     * @return строка с информацией об элементах коллекции
     */
    public static String add_if_max(Container container) {
        utils.lock.writeLock().lock();
        Route newRoute = container.getRoute();
        double max_dist = 0;
        Iterator<Route> elements = utils.collection.getSet().iterator();
        while (elements.hasNext()) {
            Route element = elements.next();
            if (element.getDistance() > max_dist) max_dist = element.getDistance();
        }
        if (newRoute.getDistance() > max_dist) {
            DataBase db = new DataBase();
            int result = db.executeUpdate("INSERT INTO s288870.\"Route\" " +
                    "(name,creation_date,distance,x,y,name_from,x_from,y_from,name_to,x_to,y_to,z_to,owner) values ('"
                    + newRoute.getName() + "','"
                    + newRoute.getCreationDate() + "','"
                    + newRoute.getDistance()+ "','"
                    + newRoute.getCoordinates().getX() + "','"
                    + newRoute.getCoordinates().getY() + "','"
                    + newRoute.getFrom().getName() + "','"
                    + newRoute.getFrom().getX() + "','"
                    + newRoute.getFrom().getY() + "','"
                    + newRoute.getTo().getName() + "','"
                    + newRoute.getTo().getX() + "','"
                    + newRoute.getTo().getY() + "','"
                    + newRoute.getTo().getZ() + "','"
                    + newRoute.getOwner() + "')");

            int id = getLastId(db);

            newRoute.setId(id);

            utils.collection.getSet().add(newRoute);
            utils.lock.writeLock().unlock();
            return "Элемент добавлен";
        }
        utils.lock.writeLock().unlock();
        return "Элемент не добавлен";
    }

    /**
     * Метод  удаляет из коллекции все элементы, превышающие заданный
     *
     * @return строка с информацией об элементах коллекции
     */
    public static String remove_greater(Container container) {
        try {
            utils.lock.writeLock().lock();
            Double dist = container.getParametr();
            DataBase db = new DataBase();
            String owner = container.getLogin();

            int counter = 0;

            Iterator<Route> elements = utils.collection.getSet().iterator();
            while (elements.hasNext()) {
                Route element = elements.next();
                if (element.getOwner().equalsIgnoreCase(owner) && element.getDistance()>dist) {
                    elements.remove();
                    counter++;
                }
            }

            String sql = "DELETE FROM s288870.\"Route\" " +
                    "WHERE owner = '" + owner + "' AND distance > '"+ dist + "';";

            int result = db.executeUpdate(sql);
            utils.lock.writeLock().unlock();
            return "Удалено элементов: " + counter;
        } catch (Exception e) {
            //e.printStackTrace();
            utils.lock.writeLock().unlock();
            return "Ошибка выполнения команды";
        }
    }

    /**
     * Метод выводит последние 10 команд (без их аргументов)
     *
     * @return строка с информацией о командах
     */
    public static String history() {
        String s = "Список последних команд: ";
        System.out.println("Список последних команд: ");
        for (int i = 1; i < utils.LastCommands.size(); i++) {
            s = s + "\n" + i + ". " + utils.LastCommands.get(i - 1);
            System.out.println(i + ". " + utils.LastCommands.get(i - 1));
        }
        return s + "\n" + "Список команд выведен успешно";
    }

    /**
     * Метод удаляет из коллекции один элемент, значение поля distance которого эквивалентно заданному
     *
     * @return строка с информацией об элементах коллекции
     */
    public static String remove_any_by_distance(Container container) {
        try {
            utils.lock.writeLock().lock();
            Double dist = container.getParametr();
            DataBase db = new DataBase();
            String owner = container.getLogin();


            Iterator<Route> elements = utils.collection.getSet().iterator();
            while (elements.hasNext()) {
                Route element = elements.next();
                if (element.getOwner().equalsIgnoreCase(owner) && element.getDistance()==dist) {
                    String sql = "DELETE FROM s288870.\"Route\" WHERE id = '" + element.getId()+ "';";
                    int result = db.executeUpdate(sql);
                    if (result>0) {
                        elements.remove();
                        utils.lock.writeLock().unlock();
                        return "Элемент успешно удален";
                    }
                    utils.lock.writeLock().unlock();
                    return "Ошибка удаления элемента";
                }
            }
            utils.lock.writeLock().unlock();
            return "Элемент не найден";
        } catch (Exception e) {
            //e.printStackTrace();
            utils.lock.writeLock().unlock();
            return "Ошибка выполнения команды";
        }
    }

    /**
     * Метод  выводит количество элементов, значение поля distance которых равно заданному
     *
     * @return строка с информацией об элементах коллекции
     */
    public static String count_by_distance(double dist) {
        utils.lock.readLock().lock();
        int counter = 0;
        counter = (int) utils.collection.getSet().stream().filter(obj -> obj.getDistance() == dist).count();
        utils.lock.readLock().unlock();
        return "Количество элементов: " + counter;
    }

    /**
     * Метод выводит элементы, значение поля name которых содержит заданную подстроку
     *
     * @param name       значение параметра строки
     * @return строка с информацией об элементах коллекции
     */
    public static String filter_contains_name(String name) {
        utils.lock.readLock().lock();
        String s = "Элементы, содежращие фрагмент в name:";
        int counter = 0;
        Iterator<Route> elements = utils.collection.getSet().iterator();
        while (elements.hasNext()) {
            Route element = elements.next();
            if (element.getName().contains(name)) {
                s = s + "\n" + element.showInfo();
                counter++;
            }
        }
        utils.lock.readLock().unlock();;
        return s + "\n" + "Количество элементов: " + counter;
    }



    public static boolean check_login(Container container) {
        if (container.getLogin()==null) return false;

        String password = null;
        try {
            password = utils.sha384(container.getPassword());
        } catch (NoSuchAlgorithmException e) {
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        }

        DataBase db = new DataBase();
        ResultSet result = db.executeQuery("SELECT * FROM s288870.\"Registration\" " +
                "WHERE login = '"+container.getLogin()+"' " +
                "AND password = '"+password+"';");
        try {
            if (!result.next()) return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public static String register(Container container) {
        DataBase db = new DataBase();
        String password = null;
        try {
            password = utils.sha384(container.getPassword());
        } catch (NoSuchAlgorithmException e) {
            return "Ошибка выполнения запроса";
        } catch (UnsupportedEncodingException e) {
            return "Ошибка выполнения запроса";
        }
        ResultSet result = db.executeQuery("SELECT * FROM s288870.\"Registration\" " +
                "WHERE login = '"+container.getLogin()+"';");
        try {
            if (!result.next()) {
                int reg_result = db.executeUpdate("INSERT INTO s288870.\"Registration\" (login,password) " +
                        "values ('"+ container.getLogin() + "','"
                        + password + "');");
                if (reg_result>0) return "Регистрация успешна";
                return "Ошибка регистрации";
            };
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Ошибка выполнения запроса";
        }
        return "Такой пользователь уже существует!";
    }
}
