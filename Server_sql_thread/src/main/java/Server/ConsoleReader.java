package Server;

import java.util.Scanner;

public class ConsoleReader extends Thread {

    RouteCollection collection;

    public ConsoleReader(RouteCollection collection){
        this.collection = collection;
    }

    public void run(){
        Scanner input = new Scanner(System.in);
        while(true){
            try {
                String command = input.nextLine();
                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Завершение работы программы");
                    System.exit(0);
                } /*else if (command.equalsIgnoreCase("save")) {
                    System.out.println(Server.Commands.save(collection, Server.utils.input_file));
                }*/
            } catch (Exception e){
                System.out.println("Произошла ошибка при выполнении команды, пожалуйста повторите ввод");
            }
        }

    }

}
