package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
@SuppressLint("NewApi")
public class ConectionHellper {
    String userName, userPassword, ip, port, dataBase;

    public Connection connectionClass() {
        ip = "ngknn.ru";
        dataBase = "KuznecovaAndroid43P";
        userName = "33П";
        userPassword = "12357";
        port = "1433";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename=" + dataBase + ";user=" + userName + ";password=" + userPassword + ";";
            connection = DriverManager.getConnection(connectionURL);
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
        return connection;
    }
}
