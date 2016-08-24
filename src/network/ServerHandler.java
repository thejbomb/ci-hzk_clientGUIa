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
    private Socket socket = null;

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
            socket = new Socket(HOST_NAME, PORT_NUMBER);
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
        System.out.println("CLIENT -> SERVER BEGIN TRANSMISSION");
        System.out.println("SH To Server: command = " + Integer.toHexString(command) + " | data = " + data);
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
                System.out.println("RESPONSE FROM SERVER: " + response);

                if (response != null && response.compareTo(Constants.TRANSMISSION_BEGIN) == 0) {
                    System.out.println("TO SERVER: SERV_SEND_NEXT");
                    out.println(Constants.SERVER_SEND_NEXT);
                    response = readFromServer();

                    while (response.compareTo(Constants.TRANSMISSION_END) != 0) {
                        System.out.println("From server: " + response);
                        if (inputQueue == null)
                            inputQueue = new LinkedList<>();
                        inputQueue.add(response);
                        response = in.readLine();

                    }


                    System.out.println("GOT EVERYTHING FROM SERVER");
                    System.out.println("DATA GOT FROM SERVER: " + inputQueue);
                    int command = Integer.parseInt(inputQueue.removeFirst());
                    System.out.println("DATA FROM SERVER (NO COMMAND): " + inputQueue);
                    System.out.println("Server command: " + Integer.toHexString(command));
                    final int COMMAND = command;
                    final LinkedList<String> DATA = new LinkedList<>(inputQueue);
                    inputQueue.clear();
                    switch (COMMAND) {
                        default:
                            Platform.runLater(() -> {
                                System.out.println("HANDLING COMMAND " + COMMAND + " NOW");
                                System.out.println("HANDLING DATA " + DATA + " NOW");
                                mainController.handleServerData(COMMAND, DATA);
                                System.out.println("FINISHED HANDLING COMMAND " + COMMAND + " AND DATA " + DATA);
                            });
                            break;
                    }

                } else if (response != null && response.compareTo(Constants.CLIENT_SEND_NEXT) == 0) {

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
