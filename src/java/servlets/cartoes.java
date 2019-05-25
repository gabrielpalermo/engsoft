/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dal.DaoCartoes;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Cartao;

/**
 *
 * @author Gabriel
 */
public class cartoes extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action_cartoes");
        String documento = request.getParameter("documento");
        
        // Cadastra cartão na base
        if(action.equals("cadastrar"))
        {
            Cartao cartao = new Cartao(
                documento,
                request.getParameter("codigo"),
                Double.parseDouble(request.getParameter("limite")),
                request.getParameter("bandeira")
            );

            try {
                Cadastro(cartao);
            } catch(ClassNotFoundException | SQLException ex) {}
        }
        else if(action.equals("editar"))
        {
            Cartao cartao = new Cartao(
                documento,
                request.getParameter("codigo"),
                Double.parseDouble(request.getParameter("limite")),
                request.getParameter("bandeira")
            );
            
            try {
                Editar(cartao);
            } catch (SQLException ex) {
                Logger.getLogger(cartoes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(action.equals("deletar"))
        {
            String codigo = request.getParameter("codigo");
            try {
                Excluir(codigo);
            } catch(ClassNotFoundException | SQLException ex) {
                System.out.print(ex.toString());
            }
        }
        
        // Todas os métodos atualizam a página de cartões
        request.setAttribute("documento", documento);
        request.getRequestDispatcher("cartoes.jsp").forward(request, response);
    }
    
    private void Cadastro(Cartao cartao) throws ClassNotFoundException, SQLException
    {
        DaoCartoes dao = new DaoCartoes();
        dao.Cadastro(cartao);
    }
    
     private void Editar(Cartao cartao) throws ClassNotFoundException, SQLException
    {
        DaoCartoes dao = new DaoCartoes();
        dao.Editar(cartao);
    }
    
    private void Excluir(String codigo) throws ClassNotFoundException, SQLException
    {
        DaoCartoes dao = new DaoCartoes();
        dao.Excluir(codigo);
    }

    public static String GetCartoesTable(String documento) throws ClassNotFoundException, SQLException{
        
        DaoCartoes dao = new DaoCartoes();
        ArrayList<Cartao> cartoes = dao.GetCartoes(documento);
        
        String cartoesTable = "<table>";
        // Adiciona headers da tabela
        cartoesTable += "<tr><th>Código do cartão</th><th>Limite</th><th>Bandeira</th></tr>";
        
        // Adiciona linhas
        for(int i=0; i< cartoes.size(); i++)
        {
            cartoesTable += "<tr>";
            cartoesTable += "<td>" + cartoes.get(i).Codigo + "</td>";
            cartoesTable += "<td>" + cartoes.get(i).Limite + "</td>";
            cartoesTable += "<td>" + cartoes.get(i).Bandeira + "</td>";
            cartoesTable += "</tr>";
        }
        
        // Finaliza tabela        
        cartoesTable += "</table>";
        
        return cartoesTable;
    }
    
    public static String GetCartoesEdit(String documento) throws ClassNotFoundException, SQLException{
        
        DaoCartoes dao = new DaoCartoes();
        ArrayList<Cartao> cartoes = dao.GetCartoes(documento);
        String codigo;
        
        String cartoesEdit = "<form autocomplete=\"off\" action=\"cartoes\" method=\"get\">";
        // Adiciona select de cartões
        cartoesEdit += "<select name=\"codigo\">";
        
        // Adiciona opções da combobox
        for(int i=0; i< cartoes.size(); i++)
        {
            codigo = cartoes.get(i).Codigo;
            cartoesEdit += "<option value=\"" + codigo + "\">" + codigo +"</option>";
        }
        
        cartoesEdit += "</select>";
        
        // Dado do usuario
        cartoesEdit += "<input type=\"hidden\" name=\"documento\" value=\"" + documento + "\"%>";
        
        // Campo de limite para alterar
        cartoesEdit += "<h3><input type=\"number\" name=\"limite\" placeholder=\"Novo limite\"></h3>";
        // Campo da bandeira para alterar
        cartoesEdit += "<h3><input type=\"text\" name=\"bandeira\" placeholder=\"Nova bandeira\"></h3>"; 
        
        //Botão de editar
        cartoesEdit += "<button name=\"action_cartoes\" value=\"editar\" type=\"submit\">Salvar edição</button>";
        //Botão de deletar
        cartoesEdit += "<button name=\"action_cartoes\" value=\"deletar\" type=\"submit\">Excluir</button>";
        
        // Finaliza form        
        cartoesEdit += "</form>";
        
        return cartoesEdit;
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cartoes.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cartoes.class.getName()).log(Level.SEVERE, null, ex);
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
