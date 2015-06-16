package br.ufg.inf.fabrica.persistencia.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DBHelper {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:rmpersistencia.sqlite3");
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return conn;
    }

    public static void executeInsert(String sql) {
        try {
            getConnection().prepareStatement(sql).executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public static Integer executeInsert(String sql, String tableName) {
        try {
            getConnection().prepareStatement(sql).executeUpdate();
            String selectId = "select max(id) from " + tableName;
            ResultSet resultSet = executeSelect(selectId);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

    public static ResultSet executeSelect(String sql) {
        try {
            return conn.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

}
