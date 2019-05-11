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
import java.util.ArrayList;
import modelos.Cartao;

/**
 *
 * @author Gabriel
 */
public class DaoCartoes {
    
    private Connection _bdConnection;
    
    public DaoCartoes() throws ClassNotFoundException, SQLException
    {
     Fabrica fabrica = new Fabrica();
     _bdConnection = fabrica.Conexao();
    }
    
    public int Cadastro(Cartao cartao) throws SQLException
    {
        PreparedStatement statement = _bdConnection.prepareStatement("INSERT INTO cartao"
                    + "(codigo, documento, limite, bandeira)"
                    + "VALUES (?, ?, ?, ?)");
        
        statement.setString(1, cartao.Codigo);
        statement.setString(2, cartao.Documento);
        statement.setDouble(3, cartao.Limite);
        statement.setString(4, cartao.Bandeira);
        
        return statement.executeUpdate();
    }
    
    public ArrayList<Cartao> GetCartoes(String documento) throws SQLException
    {
        PreparedStatement statement = _bdConnection.prepareStatement("SELECT * FROM cartao WHERE documento = '"
                    + documento + "'");
        ResultSet res = statement.executeQuery();
        
        ArrayList<Cartao> cartoes = new ArrayList<>();
        
        while (res.next()) {
            Cartao cartao = new Cartao(
              res.getString("documento"),
              res.getString("codigo"),
              Double.parseDouble(res.getString("limite")),
              res.getString("bandeira")
            );
            
            cartoes.add(cartao);
        }
        return(cartoes);
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
