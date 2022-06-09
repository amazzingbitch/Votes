package com.example.votes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DBHandler extends Configs {

    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        return dbConnection;
    }

    public void signUpUser(String login, String password, String role) throws SQLException, ClassNotFoundException {

        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_LOGIN + "," + Const.USER_PASSWORD + "," + Const.USER_ROLE + ")" + "VALUES(?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, login);
        prSt.setString(2, password);
        prSt.setString(3, role);
        prSt.executeUpdate();

    }

    public void createVote(Votes vote) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE2 + "(" + Const.VOTE_TITLE + "," + Const.VOTE_A1 + "," + Const.VOTE_A2 + "," + Const.VOTE_A3 + "," + Const.VOTE_A1STAT + "," + Const.VOTE_A2STAT +"," + Const.VOTE_A3STAT + ")" + "VALUES(?,?,?,?,?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, vote.getTitle());
        prSt.setString(2, vote.getA1());
        prSt.setString(3, vote.getA2());
        prSt.setString(4, vote.getA3());
        prSt.setInt(5, vote.getA1stat());
        prSt.setInt(6, vote.getA2stat());
        prSt.setInt(7, vote.getA3stat());
        prSt.executeUpdate();
    }

    public ResultSet getVote() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE2;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resultSet = prSt.executeQuery();

        return resultSet;
    }

    public boolean getUser(String login, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, login);
        prSt.setString(2, password);
        resultSet = prSt.executeQuery();

        int k = 0;
        while (resultSet.next()) {
            k++;
        }
        return k > 0;
    }

    public ResultSet getLogin(String login) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, login);
        resultSet = prSt.executeQuery();

        return resultSet;

    }

    public ResultSet getAnswer(String title) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE2 + " WHERE " + Const.VOTE_TITLE + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, title);
        resultSet = prSt.executeQuery();

        return resultSet;

    }

    public void deleteVote(String title) throws SQLException, ClassNotFoundException {
        String select = "DELETE FROM " + Const.USER_TABLE2 + " WHERE " + Const.VOTE_TITLE + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, title);
        prSt.executeUpdate();
    }

    public void updateVote(int id, String title, String a1, String a2, String a3) throws SQLException, ClassNotFoundException {

        String select = "UPDATE " + Const.USER_TABLE2 +  " SET " + Const.VOTE_TITLE + "=?, " +  Const.VOTE_A1 + "=?, " + Const.VOTE_A2 + "=?, " + Const.VOTE_A3 + "=?" + " WHERE " + Const.VOTE_ID + "=?"  ;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, title);
        prSt.setString(2, a1);
        prSt.setString(3, a2);
        prSt.setString(4, a3);
        prSt.setInt(5, id);


        prSt.executeUpdate();


    }

    public void addUserVote(int id, String login, int n) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Const.USER_TABLE3 + "(" + Const.WHO_LOGIN + "," + Const.VOTE_ID + "," + Const.WHO_NUM + ")" + "VALUES(?,?,?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1, login);
        prSt.setInt(2, id);
        prSt.setInt(3, n);
        prSt.executeUpdate();
    }

    public void addStatVote1(int id, int stat) throws SQLException, ClassNotFoundException {
        String select = "UPDATE " + Const.USER_TABLE2 +  " SET " + Const.VOTE_A1STAT + "=?" + " WHERE " + Const.VOTE_ID + "=?"  ;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, stat);
        prSt.setInt(2, id);

        prSt.executeUpdate();


    }

    public void addStatVote2(int id, int stat) throws SQLException, ClassNotFoundException {
        String select = "UPDATE " + Const.USER_TABLE2 +  " SET " + Const.VOTE_A3STAT + "=?" + " WHERE " + Const.VOTE_ID + "=?"  ;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, stat);
        prSt.setInt(2, id);

        prSt.executeUpdate();


    }

    public void addStatVote3(int id, int stat) throws SQLException, ClassNotFoundException {
        String select = "UPDATE " + Const.USER_TABLE2 +  " SET " + Const.VOTE_A3STAT + "=?" + " WHERE " + Const.VOTE_ID + "=?"  ;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setInt(1, stat);
        prSt.setInt(2, id);

        prSt.executeUpdate();


    }

    public ResultSet voteOrNot(String login) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE3 + " WHERE " + Const.USER_LOGIN + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1, login);
        resultSet = prSt.executeQuery();

        return resultSet;

    }


}
