package me.herrlestrate.wgforge_task.SQL;

import java.sql.*;

public class SQLWorker {
    private static Connection connection;
    private static String URI = "jdbc:postgresql://isilo.db.elephantsql.com:5432/";
    private static String database = "qhxunfxk";
    private static String username = "qhxunfxk";
    private static String password = "5RIhE4CR-USVgzPcw9aL4rZxFE7tNoJ2";

    public SQLWorker(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(URI,
                            username,
                            password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet getCats(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM cats");
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addCat(String name, String type, int tail_length, int whiskers_length){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO cats VALUES ('"
                            +name+"', '"
                            +type+"', '"
                            +tail_length+"', '"
                            +whiskers_length+"');"
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getCat(String name){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM cats WHERE name = '"+name+"';");
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putCatColor(String color){
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM cat_colors_info WHERE color = '"+color+"';");
            if(!rs.next()) statement.executeUpdate("INSERT INTO cat_colors_info VALUES ('"+color+"', 1);");
                else {
                    statement.executeUpdate("UPDATE cat_colors_info SET count = '"
                            +(rs.getInt(rs.findColumn("count"))+1)+"' WHERE color = '"+color+"';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void putCatsStat(String sql){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void clearTable(String tablename){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE "+tablename+";");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
