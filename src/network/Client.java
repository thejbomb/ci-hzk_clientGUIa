package network;

public class Client {
    final private ServerHandler SERVER;

    public Client(){
        SERVER = new ServerHandler("192.168.0.9",9999);
    }

    public void start(ServerHandlerInterface notifyObject){
        SERVER.startServer();
        SERVER.setNotify(notifyObject);
    }

    public ServerHandler getServer(){
        return SERVER;
    }

}
