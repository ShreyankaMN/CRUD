package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/showdata")
public class ShowUserServlet extends HttpServlet {
	private final static String query = "select id,name,email,mobile,dob,city,gender from registration";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //get PrintWriter
        PrintWriter pw = res.getWriter();
        //set content type
        res.setContentType("text/html");
        //link the bootstrap
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        pw.println("<marquee><h2 class='text-danger'>User Data</h2></marquee>");
        //load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e) {
            e.printStackTrace();
        }
        //generate the connection
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reg", "root","tiger");
                PreparedStatement ps = con.prepareStatement(query);){
            //resultSet
            ResultSet rs = ps.executeQuery();
            pw.println("<div style='margin:auto;width:900px;margin-top:100px;'>");
            pw.println("<table class='table table-hover table-bordered'>");
            pw.println("<tr class='bg-info'>");
            pw.println("<th class='text-white p-3'>ID</th>");
            pw.println("<th class='text-white p-3'>Name</th>");
            pw.println("<th class='text-white p-3'>Email</th>");
            pw.println("<th class='text-white p-3'>Mobile No</th>");
            pw.println("<th class='text-white p-3'>DOB</th>");
            pw.println("<th class='text-white p-3'>City</th>");
            pw.println("<th class='text-white p-3'>Gender</th>");
            pw.println("<th class='text-white p-3'>Edit</th>");
            pw.println("<th class='text-white p-3'>Delete</th>");
            pw.println("</tr>");
            while(rs.next()) {
                pw.println("<tr>");
                pw.println("<td>"+rs.getInt(1)+"</td>");
                pw.println("<td>"+rs.getString(2)+"</td>");
                pw.println("<td>"+rs.getString(3)+"</td>");
                pw.println("<td>"+rs.getString(4)+"</td>");
                pw.println("<td>"+rs.getString(5)+"</td>");
                pw.println("<td>"+rs.getString(6)+"</td>");
                pw.println("<td>"+rs.getString(7)+"</td>");
                pw.println("<td><a href='editurl?id="+rs.getInt(1)+"'><button class='btn bg-success text-white'>Edit</button></a></td>");
                pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'><button class='btn bg-danger text-white'>Delete</button></a></td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
        }catch(SQLException se) {
            pw.println("<h2 class='bg-danger text-light text-center'>"+se.getMessage()+"</h2>");
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        pw.println("<center><a href='home.html'><button class='btn btn-info'>Home</button></a></center>");
        pw.println("</div>");
        //close the stream
        pw.close();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req,res);
    }

}
