package com.example.votes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Vector;


public class Client {
    private Socket clientSocket;
    boolean isConnected = false;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;


    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            isConnected = true;
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<UserPackage> getLeaderBoard() {
        Vector<UserPackage> userPackages;
        try {
            oos.writeObject("LEAD.BOARD");
            oos.reset();

            userPackages = (Vector<UserPackage>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return userPackages;
    }

    public static void sendObj(UserPackage userPackage) {
        try {
            oos.writeObject(userPackage);
            oos.reset();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getResponse() {
        String tmp = null;
        try {
            tmp = (String) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return tmp;
    }


    public void stopConnection() {
        try {
            ois.close();
            oos.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loginUser(String login, String password) {
        UserPackage user = new UserPackage(login, password, "LOGIN");
        sendObj(user);
    }


}