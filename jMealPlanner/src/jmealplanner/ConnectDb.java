/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmealplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author matth
 */
public class ConnectDb 
{
    
    //initilize the connection
    public static Connection setupConnection()
    {
        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        String jdbcUrl = "jdbc:oracle:thin:@cscwinserv.eku.edu:1521:cscdb";  // URL for the database including the protocol (jdbc), the vendor (oracle), the driver (thin), the server (csshrpt.eku.edu), the port number (1521), database instance name (cscdb)
        //jdbc:oracle:thin:@localhost:1521:orcl
        
        /*
        AT HOME:
            system
            Fantasy14(Desktop) or Flawless14!(Laptop)
        
        AT SCHOOL:
            Abernathy5452019@cscdb  (Will)
            2061                    (Will)
        */
        String username = "Abernathy5452019";    
        String password = "2061";        
        
        try
        {
            // Load jdbc driver.            
            Class.forName(jdbcDriver);
            
            // Connect to the Oracle database
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            return conn;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    //basic connection
    static void close(Connection conn) 
    {
        if(conn != null) 
        {
            try
            {
                conn.close();
            }
            catch(Throwable whatever)
            {
            
            }
        }
    }

    //sql prepared statement (select, alter, etc)
    static void close(OraclePreparedStatement st)
    {
        if(st != null)
        {
            try
            {
                st.close();
            }
            catch(Throwable whatever)
            {
            
            }
        }
    }

    //result set (3rd)
    static void close(OracleResultSet rs)
    {
        if(rs != null)
        {
            try
            {
                rs.close();
            }
            catch(Throwable whatever)
            {
            
            }
        }
    }
    
}
