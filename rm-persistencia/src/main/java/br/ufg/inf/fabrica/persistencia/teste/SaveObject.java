package br.ufg.inf.fabrica.persistencia.teste;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SaveObject {

    Connection conn;
    public SaveObject() throws Exception {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:rmpersistencia.sqlite3");
    }
    public Object javaObject = null;

    public void setJavaObject(Object javaObject) {
        this.javaObject = javaObject;
    }


    public void saveObject() {
        try {
            Class.forName("org.sqlite.JDBC");
            PreparedStatement ps = null;
            String sql = null;

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(javaObject);
            oos.flush();
            oos.close();
            bos.close();

            byte[] data = bos.toByteArray();


            sql = "insert into javaobject (javaObject) values(?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, data);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Object getObject() throws Exception {
        Object rmObj = null;
        String sql = "select * from javaobject where id=2";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ByteArrayInputStream bais;
            ObjectInputStream ins;
            try {
                bais = new ByteArrayInputStream(rs.getBytes("javaObject"));
                ins = new ObjectInputStream(bais);
                rmObj = ins.readObject();
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return rmObj;
    }

}
