/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelos.Usuario;

/**
 *
 * @author Gabriel
 */
public class DaoAutenticacao {
    
    private Connection _bdConnection;
    
    public DaoAutenticacao() throws ClassNotFoundException, SQLException
    {
     Fabrica fabrica = new Fabrica();
     _bdConnection = fabrica.Conexao();
    }
    
    public int Cadastro(Usuario usuario) throws SQLException
    {
        PreparedStatement statement = _bdConnection.prepareStatement("INSERT INTO usuario"
                    + "(documento, senha, nome, email)"
                    + "VALUES (?, ?, ?, ?)");
        
        statement.setString(1, usuario.Documento);
        statement.setString(2, usuario.Senha);
        statement.setString(3, usuario.Nome);
        statement.setString(4, usuario.Email);
        
        return statement.executeUpdate();
    }
    
    public boolean Login(String documento, String senha) throws SQLException
    {
        PreparedStatement statement = _bdConnection.prepareStatement("SELECT * FROM usuario WHERE documento = '"
                    + documento + "' AND senha = '" + senha + "'");
        ResultSet res = statement.executeQuery();
        res.next();
        String resDocumento = res.getString("documento");
        return(!resDocumento.isEmpty());
    }
    
    @Override
    @SuppressWarnings("FinalizeDeclaration")
    protected void finalize() throws SQLException, Throwable
    {
        try {
            _bdConnection.close();
        }
        finally {
            super.finalize();
        }
    }
}
