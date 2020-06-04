package Server;

import java.util.PriorityQueue;
import java.util.Queue;

public class History {
    Queue<String> scriptPaths = new PriorityQueue<>();

    public boolean addScript(String scriptPath){
        if(!scriptPath.equals("")&&!scriptPaths.contains(scriptPath)){
            if(scriptPaths.offer(scriptPath)){
                return true;
            } else return false;
        } else return false;
    }
History history=new History();
    public void popScript(String scriptPath){
        scriptPaths.removeIf(scriptPath_n -> scriptPath_n.equalsIgnoreCase(scriptPath));
    }
}
