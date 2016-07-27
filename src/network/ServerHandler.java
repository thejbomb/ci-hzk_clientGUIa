package network;

import javafx.application.Platform;
import tool.Constants;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerHandler implements Notify, Runnable {
    private final String HOST_NAME;
    private final int PORT_NUMBER;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private Notify notify;
    private boolean listening = false;
    private boolean initialized = false;
    private Thread handlerThread = null;

    public ServerHandler(String hostName, int portNumber) {
        HOST_NAME = hostName;
        PORT_NUMBER = portNumber;
        handlerThread = new Thread(this);

    }

    public void setNotifyObject(Notify notify) {
        this.notify = notify;
    }

    public void startServer() {
        try {
            socket = new Socket(HOST_NAME, PORT_NUMBER);
            out = new PrintWriter(socket.getOutputStream(), true);
            os = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            is = new ObjectInputStream(socket.getInputStream());
            initialized = true;
            System.out.println("Connected to server @ " + socket.getInetAddress());
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + " " + getClass());
        }
        handlerThread.start();
    }

    public boolean isInitialized() {
        return initialized;
    }

    private void writeToServer(String text) {
        out.println(text);
    }

    private void writeToServer(int num) {
        writeToServer(num);
    }


    private void writeToServer(Object object) {
        try {
            os.writeObject(object);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " + getClass());
        }
    }

    private void writeToServer(int command, Object object) {
        out.println(command);
        if (object.getClass() == Integer.class)
            out.println(object);
        else
            writeToServer(object);
    }


    private String readMessageFromServer() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println(e);
            return "-1";
        }


    }

    private int readIntFromServer() {
        try {
            return Integer.parseInt(in.readLine());
        } catch (IOException e) {
            System.out.println(e);
            return -1;
        }
    }

    @Override
    public void takeNotice() {
    }

    @Override
    public void takeNotice(int command) {

    }

    @Override
    public void takeNotice(int command, Object data) {
        writeToServer(command, data);
    }

    @Override
    public void run() {
        listening = true;
        try {
            while (listening) {
                String response = in.readLine();
                System.out.println("Server response: " + response);
                int command = Integer.parseInt(response);
                if (command > 900) {
                    switch (command) {
                        case Constants.ERROR:
                            String eData = in.readLine();
                            Platform.runLater(() -> {
                                notify.takeNotice(command, eData);
                            });
                            break;
                        default:
                            break;
                    }
                } else {
                    Platform.runLater(() -> {
                        notify.takeNotice(command);
                    });
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " + getClass());
            notify.takeNotice(Constants.ERROR, ex.getMessage());
        }
    }
}
