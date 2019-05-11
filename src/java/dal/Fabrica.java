/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gabriel
 */
public class Fabrica {
 
    private String servidor, porta, base, login, senha;
    
    public Fabrica(){
        servidor = "localhost";
        porta = "5432";
        base = "engSoft1";
        login = "postgres";
        senha = "admin";
    }
    
    public Connection Conexao() throws ClassNotFoundException{
        try{
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://" + servidor +
                ":" + porta + "/" + base, login, senha);
        }catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
}
