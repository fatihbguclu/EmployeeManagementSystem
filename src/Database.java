/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fatih
 */
public class Database {
    private final String username = "root";
    private final String pw       = "1234";
    private final String db_name  = "node-app";
    private final String host     = "127.0.0.1";
    private final String port     = "3306";

    public String getUsername() {
        return username;
    }

    public String getPw() {
        return pw;
    }

    public String getDb_name() {
        return db_name;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
    
    
}
