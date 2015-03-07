package sample;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.json.simple.JSONArray;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Madhuri
 */
@Path("/hello")
public class products {
    
    @GET
    public String getData() {
        //  return "s";  
        Boolean flag=true;
        StringWriter out = new StringWriter();
        ResultSet rs;
        int rows=0;
        JSONArray products = new JSONArray();
        // StringBuilder sb = new StringBuilder();
        try (Connection cn = connection.getConnection()) {
            PreparedStatement pstmt = cn.prepareStatement("Select * from product");
            rs = pstmt.executeQuery(); 
           // Boolean result=rs.last();
            //if(result)
            // rows=rs.getRow();
           // System.out.println(rows
            if(rs.last())
                rows=rs.getRow();
            System.out.println(rows);
            rs.beforeFirst();
            if(rows>1){
           
           
            while (rs.next()) {
                //sb.append(String.format("%s\t%s\t%s\t%s\n", rs.getInt("productId"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity"))); 
                JsonObject json = Json.createObjectBuilder()
                        .add("Product Id", rs.getInt("productId"))
                        .add("Name", rs.getString("name"))
                        .add("Description", rs.getString("description"))
                        .add("Quantity", rs.getString("quantity"))
                        .build();
                products.add(json);
                
            }
            }
            else{
                while(rs.next()){
               JsonObject json = Json.createObjectBuilder()
                        .add("Product Id", rs.getInt("productId"))
                        .add("Name", rs.getString("name"))
                        .add("Description", rs.getString("description"))
                        .add("Quantity", rs.getString("quantity"))
                        .build(); 
                        return json.toString();
                }
             //gen.writeEnd();
            //  gen.close();
            } 
        } catch (SQLException ex) {
            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products.toString();
    }


//        StringWriter out=new StringWriter();
//     JsonGeneratorFactory factory=Json.createGeneratorFactory(null);
//       JsonGenerator gen=factory.createGenerator(out);
//       gen.writeStartObject()
//               .write("Name","Madhuri")
//            .write("id","1")               
//      .writeEnd();
//       gen.close();
//      return out.toString();
//        
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
//        response.setHeader("Content-Type", "text/plain-text");
//        try (PrintWriter out = response.getWriter()) {
//            if (!request.getParameterNames().hasMoreElements()) {
//                out.println(getResults("SELECT * FROM PRODUCT"));
//            } else {
//                int id = Integer.parseInt(request.getParameter("id"));
//                out.println(getResults("SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?", String.valueOf(id)));
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    private String getResults(String query, String... params) {
//        StringBuilder sb = new StringBuilder();
//        try (Connection cn = connection.getConnection()) {
//            PreparedStatement pstmt = cn.prepareStatement(query);
//            for (int i = 1; i <= params.length; i++) {
//                pstmt.setString(i, params[i - 1]);
//            }
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                sb.append(String.format("%s\t%s\t%s\t%s\n", rs.getInt("productId"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity")));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return sb.toString();
//    }
//     protected void doPost(HttpServletRequest request, HttpServletResponse response) {
//        Set<String> keySet = request.getParameterMap().keySet();
//        try (PrintWriter out = response.getWriter()) {
//            if (keySet.contains("id") && keySet.contains("product_name") && keySet.contains("product_description") && keySet.contains("quantity")) {
//                  String id = request.getParameter("id");
//                String name = request.getParameter("product_name");
//                String description = request.getParameter("product_description");
//                String quantity = request.getParameter("quantity");
//
//                doUpdate("INSERT INTO PRODUCT (productId, name, description, quantity) VALUES (?, ?, ?, ?)", id, name, description, quantity);
//            } else {
//    
//                out.println("Not enough data to input");
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//     
//     
//    private int doUpdate(String query, String... params) {
//        int changes = 0;
//        try (Connection cn = connection.getConnection()) {
//            PreparedStatement pstmt = cn.prepareStatement(query);
//            for (int i = 1; i <= params.length; i++) {
//                pstmt.setString(i, params[i - 1]);
//            }
//            changes = pstmt.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return changes;
//    }
//     protected void doPut(HttpServletRequest request, HttpServletResponse response) {
//        Set<String> keySet = request.getParameterMap().keySet();
//        try (PrintWriter out = response.getWriter()) {
//            if (keySet.contains("id") && keySet.contains("product_name") && keySet.contains("product_description") && keySet.contains("quantity")) {
//                
//                String id = request.getParameter("product_id");
//                String name = request.getParameter("product_name");
//                String description = request.getParameter("product_description");
//                String quantity = request.getParameter("quantity");
//                doUpdate("UPDATE PRODUCT SET product_id = ?, product_name = ?, product_description = ?, quantity = ? WHERE PRODUCT_ID = ?", id, name, description, quantity, id);
//            } else {
//                out.println("Not enough data to input");
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//      protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
//        Set<String> keySet = request.getParameterMap().keySet();
//        try (PrintWriter out = response.getWriter()) {
//            if (keySet.contains("id")) {               
//                String id = request.getParameter("id");
//                doUpdate("DELETE FROM PRODUCT WHERE product_id = ?", id);
//            } else {
//                out.println("Not enough arguments");
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(products.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
