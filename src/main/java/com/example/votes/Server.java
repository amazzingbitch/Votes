package com.example.votes;

import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.sql.Connection;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.Observable;
        import java.util.Vector;


public class Server {

    private static Server instance;
    private static int clientCount = 0;
    private static int id = 0;


    public static synchronized Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int port = 8000;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                MonoServer ech = new MonoServer(serverSocket.accept(), id++);
                // clientList.put(,ech);
                clientCount++;

                ech.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert serverSocket != null;
            stop(serverSocket);
        }
    }

    public static String getClients() {
       /* StringBuilder names = new StringBuilder();
        for (MonoServer client : clientList)
            names.append("Client ").append(client.getClientId()).append("\n");
        return names.toString();*/

        return "";
    }


    public static void stop(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class MonoServer extends Thread {
        private final Socket clientSocket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        DBHandler DBHandler = new DBHandler();

        public MonoServer(Socket socket, int id) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                ois = new ObjectInputStream(clientSocket.getInputStream());
                oos = new ObjectOutputStream(clientSocket.getOutputStream());

                while (!clientSocket.isClosed() && !clientSocket.isOutputShutdown() && !clientSocket.isInputShutdown()) {
                    Object readObject = ois.readObject();
                    if (readObject instanceof String string) {
                        //sendLeaderboard();
                    } else {
                        UserPackage userPackage = (UserPackage) readObject;
                        if (userPackage.getMessage().equals("LOGIN")) {
                            if (DBHandler.getUser(userPackage.username, userPackage.password)) {
                                oos.writeObject("correct");
                                oos.reset();
                            } else {
                                oos.writeObject("incorrect");
                                oos.reset();
                            }
                        } /*else if (userPackage.getMessage().equals("REGISTER")) {
                            System.out.println(DBHandler.isUsernameExist(userPackage.username));
                            if (DBHandler.isUsernameExist(userPackage.username)) {
                                oos.writeObject("already exist");
                                oos.reset();
                            } else {
                                DBHandler.signUpUser(userPackage.username, userPackage.password, userPackage.highScore);
                                oos.writeObject("horoshechno");
                                oos.reset();
                            }
                        }*/
                    }
                }
                oos.close();
                ois.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }

        /*private void sendLeaderboard() {
            Vector<UserPackage> table = new Vector<>();
            ResultSet resultSet = DBHandler.getTable();
            try {
                while (resultSet.next()) {
                    table.add(new UserPackage(resultSet.getString(1),
                            resultSet.getString(3),
                            resultSet.getInt(2)));
                }
                oos.writeObject(table);
                oos.reset();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }

        }*/
    }
}