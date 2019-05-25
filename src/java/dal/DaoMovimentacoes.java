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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import modelos.Categoria;
import modelos.Movimentacao;

/**
 *
 * @author Gabriel
 */
public class DaoMovimentacoes {
    
    private Connection _bdConnection;
    
    public DaoMovimentacoes() throws ClassNotFoundException, SQLException
    {
     Fabrica fabrica = new Fabrica();
     _bdConnection = fabrica.Conexao();
    }
    
    public int Cadastro(Movimentacao movimentacao) throws SQLException
    {
        PreparedStatement statement = _bdConnection.prepareStatement("INSERT INTO movimentacao"
                    + "(valor, data, documento, categoria, io, conta)"
                    + "VALUES (?, ?, ?, ?, ?, ?)");
        
        statement.setDouble(1, movimentacao.Valor);
        statement.setDate(2, new java.sql.Date(movimentacao.Data.getTime()));
        statement.setString(3, movimentacao.Documento);
        statement.setString(4, movimentacao.Categoria);
        statement.setInt(5, movimentacao.IO);
        statement.setString(6, movimentacao.Conta);
        
        return statement.executeUpdate();
    }
    
    public ArrayList<Movimentacao> GetMovimentacoes(String documento) throws SQLException
    {
        PreparedStatement statement = _bdConnection.prepareStatement("SELECT * FROM movimentacao WHERE documento = '"
                    + documento + "'");
        ResultSet res = statement.executeQuery();
        
        ArrayList<Movimentacao> movimentacoes = new ArrayList<>();
        
        while (res.next()) {
            Movimentacao movimentacao = new Movimentacao(
              res.getDate("data"),
              Double.parseDouble(res.getString("valor")),
              res.getString("categoria"),
              res.getString("conta"),
              res.getInt("io"),
              res.getString("documento")
            );
            movimentacao.Id = res.getInt("id");
            
            movimentacoes.add(movimentacao);
        }
        return(movimentacoes);
    }
    
    // Grafico A
    public ArrayList<Categoria> GetValoresCategoriaMes(String documento) throws SQLException
    {
        String categoria;
        double valor;
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String todayStr = df.format(today);
        LocalDate localDatePast = LocalDate.now().minusDays(30);
        Date datePast = Date.from(localDatePast.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String pastStr = df.format(datePast);
        
        PreparedStatement statement = _bdConnection.prepareStatement("SELECT * FROM movimentacao WHERE documento = '"
                    + documento + "' AND data between '" +pastStr + "' and '" + todayStr + "'");        
        ResultSet res = statement.executeQuery();
        
        Categoria salario = new Categoria("Salario", 0);
        Categoria alimentacao = new Categoria("Alimentacao", 0);
        Categoria transporte = new Categoria("Transporte", 0);
        Categoria lazer = new Categoria("Lazer", 0);
        Categoria outros = new Categoria("Outros", 0);
        
        while (res.next()) {
            categoria =  res.getString("categoria");
            valor = Double.parseDouble(res.getString("valor")) * res.getInt("io");
            switch(categoria)
            {
                case "Salario":
                    salario.Valor += valor;
                    break;
                case "Alimentacao":
                    alimentacao.Valor += valor;
                    break;
                case "Transporte":
                    transporte.Valor += valor;
                    break;
                case "Lazer":
                    lazer.Valor += valor;
                    break;
                case "Outros":
                    outros.Valor += valor;
                    break;
            }
        }
        
        ArrayList<Categoria> categorias = new ArrayList<>();
        categorias.add(salario);
        categorias.add(alimentacao);
        categorias.add(transporte);
        categorias.add(lazer);
        categorias.add(outros);
        
        return(categorias);
    }
    // Gráfico B
    public ArrayList<Categoria> GetValoresCategorias(String documento) throws SQLException
    {
        String categoria;
        double valor;
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String todayStr = df.format(today);
        LocalDate localDatePast = LocalDate.now().minusDays(180);
        Date datePast = Date.from(localDatePast.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String pastStr = df.format(datePast);
        
        PreparedStatement statement = _bdConnection.prepareStatement("SELECT * FROM movimentacao WHERE documento = '"
                    + documento + "' AND data between '" +pastStr + "' and '" + todayStr + "'");        
        ResultSet res = statement.executeQuery();
        
        Categoria salario = new Categoria("Salario", 0);
        Categoria alimentacao = new Categoria("Alimentacao", 0);
        Categoria transporte = new Categoria("Transporte", 0);
        Categoria lazer = new Categoria("Lazer", 0);
        Categoria outros = new Categoria("Outros", 0);
        
        while (res.next()) {
            categoria =  res.getString("categoria");
            valor = Double.parseDouble(res.getString("valor")) * res.getInt("io");
            switch(categoria)
            {
                case "Salario":
                    salario.Valor += valor;
                    break;
                case "Alimentacao":
                    alimentacao.Valor += valor;
                    break;
                case "Transporte":
                    transporte.Valor += valor;
                    break;
                case "Lazer":
                    lazer.Valor += valor;
                    break;
                case "Outros":
                    outros.Valor += valor;
                    break;
            }
        }
        
        ArrayList<Categoria> categorias = new ArrayList<>();
        categorias.add(salario);
        categorias.add(alimentacao);
        categorias.add(transporte);
        categorias.add(lazer);
        categorias.add(outros);
        
        return(categorias);
    }
    
    // Gráfico C
    public ArrayList<Categoria> GetLiquidoSeisMeses(String documento) throws SQLException
    {
        int categoria;
        double valor;
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String todayStr = df.format(today);
        LocalDate localDatePast = LocalDate.now().minusDays(180);
        Date datePast = Date.from(localDatePast.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String pastStr = df.format(datePast);
        
        PreparedStatement statement = _bdConnection.prepareStatement("SELECT * FROM movimentacao WHERE documento = '"
                    + documento + "' AND data between '" +pastStr + "' and '" + todayStr + "'");        
        ResultSet res = statement.executeQuery();
        
        Categoria receitas = new Categoria("Receitas", 0);
        Categoria despesas = new Categoria("Despesas", 0);
        
        while (res.next()) {
            categoria =  res.getInt("io");
            valor = Double.parseDouble(res.getString("valor"));
            switch(categoria)
            {
                case 1:
                    receitas.Valor += valor;
                    break;
                case -1:
                    despesas.Valor += valor;
                    break;
            }
        }
        
        ArrayList<Categoria> categorias = new ArrayList<>();
        categorias.add(receitas);
        categorias.add(despesas);
        
        return(categorias);
    }
    
    public double GetLimiteConta(String conta, String documento) throws SQLException
    {
        PreparedStatement statement = _bdConnection.prepareStatement("SELECT limite FROM cartao WHERE codigo = '"
                    + conta + "' AND documento ='" + documento +"'");
        ResultSet res = statement.executeQuery();
        
        double gastos = 0;
        
        while (res.next()) {

            gastos += Double.parseDouble(res.getString("limite"));
        }
        return(gastos);
    }
    public double GetBalancoMensal(String documento) throws SQLException
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String todayStr = df.format(today);
        LocalDate localDatePast = LocalDate.now().minusDays(30);
        Date datePast = Date.from(localDatePast.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String pastStr = df.format(datePast);
        
        
        PreparedStatement statement = _bdConnection.prepareStatement("select valor, io from movimentacao where documento = '" +
                documento + "' AND data between '" +pastStr + "' and '" + todayStr + "'");        

        ResultSet res = statement.executeQuery();
        
        double balanco = 0;
        
        while (res.next()) {

            balanco += Double.parseDouble(res.getString("valor")) * Double.parseDouble(res.getString("io"));
        }
        return(balanco);
    }
    
    public void Excluir(String id) throws SQLException
    {
        PreparedStatement statement = _bdConnection.prepareStatement("DELETE FROM movimentacao WHERE id ='" + id +"'");
        statement.executeUpdate();
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
