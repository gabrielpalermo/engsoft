/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dal.DaoMovimentacoes;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Categoria;

/**
 *
 * @author Gabriel
 */
public class acompanhamento extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet acompanhamento</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet acompanhamento at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

     public static String GetValoresCategoriaMes(String documento) throws SQLException, ClassNotFoundException {
        DaoMovimentacoes dao = new DaoMovimentacoes();
        ArrayList<Categoria> categorias =  dao.GetValoresCategoriaMes(documento); 
        String row;
        String myData = "[";
        for(int i=0; i < categorias.size(); i++)
        {
            row = "['" + categorias.get(i).Nome + "', Number('" + categorias.get(i).Valor + "')]";
            myData += row;
            
            if(i != categorias.size() -1)
            {
                myData += ",";
            }
        }
        
        myData += "]";
        
        return(myData);
    }
    
    public static String GetValoresCategorias(String documento) throws SQLException, ClassNotFoundException {
        DaoMovimentacoes dao = new DaoMovimentacoes();
        ArrayList<Categoria> categorias =  dao.GetValoresCategorias(documento); 
        String row;
        String myData = "[";
        for(int i=0; i < categorias.size(); i++)
        {
            row = "['" + categorias.get(i).Nome + "', Number('" + categorias.get(i).Valor + "')]";
            myData += row;
            
            if(i != categorias.size() -1)
            {
                myData += ",";
            }
        }
        
        myData += "]";
        
        return(myData);
    }
    
    public static String GetLiquidoSeisMeses(String documento) throws SQLException, ClassNotFoundException {
        DaoMovimentacoes dao = new DaoMovimentacoes();
        ArrayList<Categoria> categorias =  dao.GetLiquidoSeisMeses(documento); 
        String row;
        String myData = "[";
        for(int i=0; i < categorias.size(); i++)
        {
            row = "['" + categorias.get(i).Nome + "', Number('" + categorias.get(i).Valor + "')]";
            myData += row;
            
            if(i != categorias.size() -1)
            {
                myData += ",";
            }
        }
        
        myData += "]";
        
        return(myData);
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
