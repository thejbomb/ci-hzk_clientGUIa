package network;

import javafx.application.Platform;
import tool.Constants;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ServerHandler implements ServerInteractionInterface, Runnable {
    private final String HOST_NAME;
    private final int PORT_NUMBER;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private ServerHandlerInterface mainController;
    private Thread handlerThread = null;


    private LinkedList<String> outputQueue = null;
    private LinkedList<String> inputQueue = null;

    public ServerHandler(String hostName, int portNumber) {
        HOST_NAME = hostName;
        PORT_NUMBER = portNumber;
        handlerThread = new Thread(this);

    }

    public void setNotify(ServerHandlerInterface notify) {
        mainController = notify;
    }

    public void startServer() {
        try {
            Socket socket = new Socket(HOST_NAME, PORT_NUMBER);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server @ " + socket.getInetAddress());
        } catch (IOException ex) {
            System.out.println(ex.getMessage() + " " + getClass());
        }
        handlerThread.start();
    }

    @Override
    public void writeToServer(int command) {
        writeToServer(command, new LinkedList<>());
    }

    @Override
    public void writeToServer(int command, LinkedList<String> data) {
        data.addFirst(Integer.toString(command));
        outputQueue = data;
        out.println(Constants.TRANSMISSION_BEGIN);
    }


    private String readFromServer() throws IOException {
        return in.readLine();
    }

    private LinkedList<String> packageStringData(String data) {
        LinkedList<String> result = new LinkedList<>();
        result.add(data);

        return result;
    }

    @Override
    public void run() {
        boolean listening = true;
        try {
            while (listening) {
                String response = readFromServer();
                if (Integer.parseInt(response) == Constants.TRANSMISSION_BEGIN) {
                    out.println(Constants.SERVER_SEND_NEXT);
                    response = readFromServer();
                    while (Integer.parseInt(response) != Constants.TRANSMISSION_END) {
                        System.out.println("From server: " + response);
                        if (inputQueue == null)
                            inputQueue = new LinkedList<>();
                        inputQueue.add(response);
                        response = in.readLine();
                    }

                    int command = Integer.parseInt(inputQueue.getFirst());
                    inputQueue.removeFirst();
                    switch (command) {
                        default:
                            Platform.runLater(() -> {
                                mainController.handleServerData(command, inputQueue);
                                inputQueue = null;
                            });
                            break;
                    }

                } else if (Integer.parseInt(response) == Constants.CLIENT_SEND_NEXT) {

                    while (!outputQueue.isEmpty()) {
                        System.out.println("To server: " + outputQueue.getFirst());
                        out.println(outputQueue.getFirst());
                        outputQueue.removeFirst();
                        if (outputQueue.isEmpty())
                            out.println(Constants.TRANSMISSION_END);

                    }
                    outputQueue = null;
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " + getClass());
            mainController.handleServerData(Constants.ERROR_CONNECTION_LOST, packageStringData(ex.getMessage()));
        }
    }
}
