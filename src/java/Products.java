/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Da_Rich
 */
public class Products extends HttpServlet {

    protected Enumeration myParam = null;

//    @Override
//  public void init(ServletConfig servletConfig) throws ServletException{
//    this.myParam = servletConfig.getInitParameterNames();
//  }
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
        try ( PrintWriter out = response.getWriter()) {
            /* Load the initial paramaters from the web descriptor */
            Enumeration params = this.getInitParameterNames();

            int max = parseInt(this.getInitParameter("max"));
            int offset = 0; //For random generation of products
            //First get the cookies to be sure whether there is an existing cookie or not
            Cookie[] cookies = request.getCookies();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Products</title>");
            out.println("</head>");
            out.println("<body>");
            int cookiePresent = 0;
            if (!(cookies == null)) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie c = cookies[i];
                    //Only manipulate our cookie
                    
                    if (c.getName().equals("visits")) {
                        cookiePresent = 1;
                    
                        //Increment the cookie value by 1
                        c.setValue(String.valueOf(parseInt(c.getValue()) + 1));
                        c.setMaxAge(24 * 60 * 60);
                        response.addCookie(c);
                    }

                }
//                       Cookie c = new Cookie("visits"+System.currentTimeMillis(), "0");
//            c.setMaxAge(24 * 60 * 60);
//             response.addCookie(c);
            }
            //In case it's not there i.e the first visit, we add it
            if (cookiePresent == 0) {
                Cookie c = new Cookie("visits", "1");
                c.setMaxAge(24 * 60 * 60);
                response.addCookie(c);

            }

            out.println("<h1>Servlet Products " + request.getContextPath() + "</h1>");
            out.println("<form method=\"post\" action=\"./ShoppingCart\">");
            int counter = 0;
            int disp = 0;
            while (params.hasMoreElements()) {
                if (offset <= counter) {
                    if (disp >= max) {
                        break;
                    }
                    String product = (String) params.nextElement();
                    if (product == "max") {//max is not among the products
                        continue;
                    }
                    out.println("<p>");

                    out.println("<input type=\"checkbox\" value=\"" + product + "\""
                            + "name=\"" + product + "\""
                            + "id=\"" + product + "\""
                            + ">");
                    out.println("<label for=\"" + product + "\">" + product + "</label>");
                    out.println("</p>");
                    disp++;
                }

                counter++;
            }
            out.println("<input type=\"submit\" value=\"Buy\"");
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
