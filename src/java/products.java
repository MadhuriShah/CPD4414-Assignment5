
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Madhuri
 */
@WebServlet("/hello")
public class products extends HttpServlet{
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Type", "text/plain-text");
        try (PrintWriter out = response.getWriter()) {
            if (!request.getParameterNames().hasMoreElements()) {
                out.println(getResults("SELECT * FROM PRODUCT"));
            } else {
                int id = Integer.parseInt(request.getParameter("id"));
                out.println(getResults("SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?", String.valueOf(id)));
            }
        } catch (IOException ex) {
            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getResults(String query, String... params) {
        StringBuilder sb = new StringBuilder();
        try (Connection cn = connection.getConnection()) {
            PreparedStatement pstmt = cn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sb.append(String.format("%s\t%s\t%s\t%s\n", rs.getInt("product_id"), rs.getString("product_name"), rs.getString("product_description"), rs.getInt("quantity")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }
     protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Set<String> keySet = request.getParameterMap().keySet();
        try (PrintWriter out = response.getWriter()) {
            if (keySet.contains("id") && keySet.contains("product_name") && keySet.contains("product_description") && keySet.contains("quantity")) {
                // There are some parameters     
                String id = request.getParameter("id");
                String name = request.getParameter("product_name");
                String description = request.getParameter("product_description");
                String quantity = request.getParameter("quantity");

                doUpdate("INSERT INTO PRODUCT (product_id, product_name, product_description, quantity) VALUES (?, ?, ?, ?)", id, name, description, quantity);
            } else {
                // There are no parameters at all
                out.println("Error: Not enough data to input. Please use a URL of the form /servlet?name=XXX&age=XXX");
            }
        } catch (IOException ex) {
            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     
    private int doUpdate(String query, String... params) {
        int changes = 0;
        try (Connection cn = connection.getConnection()) {
            PreparedStatement pstmt = cn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            changes = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return changes;
    }
   
}

