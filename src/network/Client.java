package network;

public class Client {
    final private ServerHandler SERVER;

    public Client(){
        SERVER = new ServerHandler("localhost",9999);
    }

    public void start(Notify notifyObject){
        SERVER.startServer();
        SERVER.setNotifyObject(notifyObject);
    }

    public ServerHandler getServer(){
        return SERVER;
    }

}
