/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Usuario;
import dal.DaoAutenticacao;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Gabriel
 */
public class cadastro extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws java.security.NoSuchAlgorithmException

     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException, NoSuchAlgorithmException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            MessageDigest md = MessageDigest.getInstance("MD5");
    
            md.update(request.getParameter("senha").getBytes());
            byte[] digest = md.digest();

            String hashSenha = DatatypeConverter.printHexBinary(digest).toUpperCase();
            
            Usuario usuario = new Usuario(
                    request.getParameter("documento"),
                    hashSenha,
                    request.getParameter("nome"),
                    request.getParameter("email")
            );
            
            boolean success = Cadastrar(usuario);
            if(success){
                request.setAttribute("redirect-path", "login.html");
                request.setAttribute("message", "Usuario cadastrado com sucesso!");
            }
            else
            {
                request.setAttribute("redirect-path", "cadastro.html");
                request.setAttribute("message", "Falha ao cadastrar usuario. Tente novamente mais tarde.");
            }
            request.getRequestDispatcher("voltar.jsp").forward(request, response);
        }
    }

    private boolean Cadastrar(Usuario usuario) throws ClassNotFoundException, SQLException
    {
        DaoAutenticacao dao = new DaoAutenticacao();
        try{
            return (dao.Cadastro(usuario) == 1);

        } catch(Exception ex)
        {
            return false;
        }
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
        } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException ex) {
            Logger.getLogger(cadastro.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException ex) {
            Logger.getLogger(cadastro.class.getName()).log(Level.SEVERE, null, ex);
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
