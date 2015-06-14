package br.ufg.inf.fabrica.persistencia.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:rmpersistencia.sqlite3");
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return conn;
    }

}
