package Server;

import java.io.Serializable;

public class Answer implements Serializable {
    private String result;

    public Answer(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }


}
