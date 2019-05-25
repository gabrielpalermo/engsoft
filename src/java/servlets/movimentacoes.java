/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dal.DaoCartoes;
import dal.DaoMovimentacoes;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Cartao;
import modelos.Movimentacao;

/**
 *
 * @author Gabriel
 */
public class movimentacoes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ClassNotFoundException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action_movimentacoes");
        String documento = request.getParameter("documento");
        
        // Cadastra cartão na base
        if(action.equals("cadastrar"))
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date data = (Date) df.parse(request.getParameter("data"));
            Movimentacao movimentacao = new Movimentacao(
                data,
                Double.parseDouble(request.getParameter("valor")),
                request.getParameter("categoria"),
                request.getParameter("conta"),
                Integer.parseInt(request.getParameter("io")),
                documento
            );

            try {
                int res = Cadastro(movimentacao);
            } catch(ClassNotFoundException | SQLException ex) {
                System.out.print(ex.toString());
            }
        }
        else if(action.equals("deletar"))
        {
            String codigo = request.getParameter("id");
            try {
                Excluir(codigo);
            } catch(ClassNotFoundException | SQLException ex) {
                System.out.print(ex.toString());
            }
        }
        
        // Todas os métodos atualizam a página de cartões
        request.setAttribute("documento", documento);
        request.getRequestDispatcher("movimentacoes.jsp").forward(request, response);
    }
    
    private int Cadastro(Movimentacao movimentacao) throws ClassNotFoundException, SQLException
    {
        String documento = movimentacao.Documento;
        DaoMovimentacoes dao = new DaoMovimentacoes();
        ArrayList<Movimentacao> movimentacoes = dao.GetMovimentacoes(documento);
        String conta = movimentacao.Conta;
        double soma = movimentacao.IO * movimentacao.Valor;
        
        // Verifica o balanço para a conta a ser alterada
        for(int i=0; i< movimentacoes.size(); i++)
        {
            if(movimentacoes.get(i).Conta.equals(conta))
            {
                soma += movimentacoes.get(i).IO * movimentacoes.get(i).Valor;
            }
        }
        
        if(conta.equals("conta_corrente"))
        {
            if(soma >= 0)
            {
                // Balanço da C.C. positivo, pode incluir movimentação
                dao.Cadastro(movimentacao);
            } else {
                return (-1);
            }
        } else {
            double limite = dao.GetLimiteConta(conta, documento);
            if(soma >= -1 * limite)
            {
                dao.Cadastro(movimentacao);
            } else {
                return (1);
            }
        }
        return (0);
    }
    
    private void Excluir(String id) throws ClassNotFoundException, SQLException
    {
        DaoMovimentacoes dao = new DaoMovimentacoes();
        dao.Excluir(id);
    }

    public static String GetMovimentacoesTable(String documento) throws ClassNotFoundException, SQLException{
        
        DaoMovimentacoes dao = new DaoMovimentacoes();
        ArrayList<Movimentacao> movimentacoes = dao.GetMovimentacoes(documento);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        String movimentacoesTable = "<table>";
        // Adiciona headers da tabela
        movimentacoesTable += "<tr><th>Data</th><th>Valor</th><th>Categoria</th><th>Tipo</th><th>Conta/Cartao</th><th>Id</th></tr>";
        
        // Adiciona linhas
        for(int i=0; i< movimentacoes.size(); i++)
        {
            movimentacoesTable += "<tr>";
            movimentacoesTable += "<td>" + simpleDateFormat.format(movimentacoes.get(i).Data) + "</td>";
            movimentacoesTable += "<td>" + movimentacoes.get(i).Valor + "</td>";
            movimentacoesTable += "<td>" + movimentacoes.get(i).Categoria + "</td>";
            if(movimentacoes.get(i).IO == 1)
            {
                movimentacoesTable += "<td>Receita</td>";
            } else {
                movimentacoesTable += "<td>Despesa</td>";
            }
            if(movimentacoes.get(i).Conta.equals("conta_corrente"))
            {
                movimentacoesTable += "<td>Conta Corrente</td>";
            } else {
                movimentacoesTable += "<td>" + movimentacoes.get(i).Conta + "</td>";
            }
            
            movimentacoesTable += "<td>" + movimentacoes.get(i).Id + "</td>";
            movimentacoesTable += "</tr>";
        }
        
        // Finaliza tabela        
        movimentacoesTable += "</table>";
        
        return movimentacoesTable;
    }
    
    public static String GetMovimentacoesDelete(String documento) throws ClassNotFoundException, SQLException{
        
        DaoMovimentacoes dao = new DaoMovimentacoes();
        ArrayList<Movimentacao> movimentacoes = dao.GetMovimentacoes(documento);
        Integer id;
        
        String selectMovimentacoes = "<form autocomplete=\"off\" action=\"movimentacoes\" method=\"get\">";
        // Adiciona select de cartões
        selectMovimentacoes += "<select name=\"id\">";
        
        // Adiciona opções da combobox
        for(int i=0; i< movimentacoes.size(); i++)
        {
            id = movimentacoes.get(i).Id;
            selectMovimentacoes += "<option value=\"" + id + "\">" + id +"</option>";
        }
        
        selectMovimentacoes += "</select>";
        
        // Dado do usuario
        selectMovimentacoes += "<input type=\"hidden\" name=\"documento\" value=\"" + documento + "\"%>";
        
        selectMovimentacoes += "<br/><br/>";
        
        selectMovimentacoes += "<button name=\"action_movimentacoes\" value=\"deletar\" type=\"submit\">Excluir</button>";
        // Finaliza form        
        selectMovimentacoes += "</form>";
        
        return selectMovimentacoes;
    }
    public static String GetContaCartoesSelect(String documento) throws ClassNotFoundException, SQLException{
        
        DaoCartoes dao = new DaoCartoes();
        ArrayList<Cartao> cartoes = dao.GetCartoes(documento);
        String codigo;
        
        String contaSelect = "<select name=\"conta\">";
        // Adiciona select de cartões
        contaSelect += "<option value=\"conta_corrente\">Conta corrente</option>";
        
        // Adiciona opções da combobox
        for(int i=0; i< cartoes.size(); i++)
        {
            codigo = cartoes.get(i).Codigo;
            contaSelect += "<option value=\"" + codigo + "\">" + codigo +"</option>";
        }
        
        contaSelect += "</select>";
        
        return contaSelect;
    }
    
    public static String GetBalancoMensal(String documento) throws ClassNotFoundException, SQLException
    {
        DaoMovimentacoes dao = new DaoMovimentacoes();
        double balanco = dao.GetBalancoMensal(documento);
        return ("R$ " + balanco);
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | ParseException ex) {
            Logger.getLogger(movimentacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | ParseException ex) {
            Logger.getLogger(movimentacoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
