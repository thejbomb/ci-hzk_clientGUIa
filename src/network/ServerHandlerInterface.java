package network;

import java.util.LinkedList;

public interface ServerHandlerInterface {

    void handleServerData(int command, LinkedList<String> data);

}
