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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        
        String documento = request.getParameter("documento");
        
        Cartao cartao = new Cartao(
            documento,
            request.getParameter("codigo"),
            Double.parseDouble(request.getParameter("limite")),
            request.getParameter("bandeira")
        );

        try {
            Cadastro(cartao);

        } catch(ClassNotFoundException | SQLException ex) {
             response.sendRedirect("cartoes.jsp");
        }

        request.setAttribute("documento", documento);
        request.getRequestDispatcher("cartoes.jsp").forward(request, response);
    }
    
    private boolean Cadastro(Cartao cartao) throws ClassNotFoundException, SQLException
    {
        DaoCartoes dao = new DaoCartoes();
        
        return (dao.Cadastro(cartao) == 1);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
