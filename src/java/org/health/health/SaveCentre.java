/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.health.health;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Apple
 */
public class SaveCentre extends HttpServlet {

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
        Connection conn = null;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SaveCentre</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaveCentre at " + request.getContextPath() + "</h1>");
            String n = request.getParameter("centrename");
            String l = request.getParameter("location");
            
            try{
                //loading driver
             Class.forName("com.mysql.jdbc.Driver");
             //out.println("Succesfull");
             //connect to DB
             try{
             conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vaccineadmintarcker", "root", "");
             //out.print("connection complete");
                          //Retrieve from DB
             Statement sql = conn.createStatement();
             ResultSet rsl =  sql.executeQuery("select * from healthcentres ");
             //showing in table
             out.println("<table>"
                     + "<tr>"
                     + "<th>ID</th>"
                     + "<th>Name</th>"
                     + "<th>Location</th>"
                     + "</tr>");
             while(rsl.next()){
                 out.println("<tr>"
                         + "<td>"+rsl.getInt("id")+"</td>"
                         + "<td>"+rsl.getString("centrename")+"</td>"
                         + "<td>"+rsl.getString("location")+"</td>"
                         + "</tr>");
             }
             out.println("</table>");
             
             try{
                 //Save to DB
             Statement sqli = conn.createStatement();
            sqli.executeUpdate("insert into healthcentres set centrename='"+n+"',location='"+l+"'");
            out.println("Saved Successfully");
            }catch(SQLException z){
                out.print("sqli exec failure:"+z.getMessage());
            } 

             }catch(SQLException e){
                 out.println("Connection failed"+e.getMessage());

             }
            }catch(ClassNotFoundException e){
                out.println("Error loading driver"+e.getMessage());
            }
            out.println("</body>");
            out.println("</html>");
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
