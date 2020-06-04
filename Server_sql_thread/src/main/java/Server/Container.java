package Server;

import Server.CollectionObjects.Route;

import java.io.Serializable;

public class Container implements Serializable {
    private String command;
    private int id;
    private Route route;
    private double parametr;
    private String parametrString;
    private String login=null;
    private String password=null;

    public Container(String command) {
        this.command = command;
    }

    public Container(String command, int id) {
        this.command = command;
        this.id = id;

    }
    public Container(String command, Route route) {
        this.command = command;
        this.route = route;

    }

    public Container(String command, double parametr) {
        this.command = command;
        this.parametr = parametr;

    }

    public Container(String command, String parametrString) {
        this.command = command;
        this.parametrString = parametrString;
    }

    public Container(String command, String login, String password) {
        this.command = command;
        this.parametrString = parametrString;
        this.login = login;
        this.password = password;
    }


    public String getCommand() {
        return command;
    }

    public Route getRoute() {
        return route;
    }

    public double getParametr() {
        return parametr;
    }

    public void setParametr(double parametr) {
        this.parametr = parametr;
    }

    public String getParametrString() {
        return parametrString;
    }

    public void setParametrString(String parametrString) {
        this.parametrString = parametrString;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
