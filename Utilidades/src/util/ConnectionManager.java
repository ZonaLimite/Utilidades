package util;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

//import org.jboss.logging.Logger;


public class ConnectionManager {

   static Connection con;
   static String url;
   static Logger log = Logger.getLogger("ConnectionDatabase");
   
   //Obtencion de conexion con JNDI y marcado del datasource como un recurso//Esto no funciona
   @Resource(lookup = "java:jboss/datasources/MySQLDS2")
   private static javax.sql.DataSource datasource ;

   static Connection getConnection(){
 
	   String DATASOURCE_CONTEXT = "java:jboss/datasources/MySQLDS2";//Probando2

	    Connection result = null;
	    try {
	      Context initialContext = new InitialContext();

	      datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
	      if (datasource != null) {
	        result = datasource.getConnection();
	      }
	      else {
	        log.info("Failed to lookup datasource.");
	      }
	    }
	    catch ( NamingException ex ) {
	      log.info("Cannot get connection: "+"("+ DATASOURCE_CONTEXT+":"+ ex);
	    }
	    catch(SQLException ex){
	      log.info("Cannot get connection: " + ex);
	    }
	    return result;
    }
   static Connection getConnectionUcanAccess(){
	   
	   String DATASOURCE_CONTEXT = "java:jboss/blastindex";//Acces a blastindex (en MIS)mediante ucanaccess

	    Connection result = null;
	    try {
	      Context initialContext = new InitialContext();

	      datasource = (DataSource)initialContext.lookup(DATASOURCE_CONTEXT);
	      if (datasource != null) {
	        result = datasource.getConnection();
	        log.info("UcanAccess conexion realizada");
	      }
	      else {
	        log.info("Failed to lookup datasource.");
	      }
	    }
	    catch ( NamingException ex ) {
	      log.info("Cannot get connection: "+"("+ DATASOURCE_CONTEXT+":"+ ex);
	    }
	    catch(SQLException ex){
	      log.info("Cannot get connection: " + ex);
	    }
	    return result;
    }

    public static Connection getConnection(String ipServer,String user,String pwd, String schema ){
    	Connection myConnection=null;
    	 myConnection = getConexionMySQL(ipServer,user,pwd, schema);
    	return myConnection;
    }
   
    public static Connection getConexionMySQL(String ipServer,String user,String pwd, String schema ){
    Connection conSQL = null;
    Properties connectionProps = new Properties();//
    connectionProps.put("user", user);
    connectionProps.put("password", pwd);

       try {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch ( ClassNotFoundException ex) {
            System.err.println (ex.getMessage());
        } catch (InstantiationException e) {
        	System.err.println (e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.err.println (e.getMessage());
			e.printStackTrace();
		}
           String cadena = "jdbc:mysql://"+ipServer+":3306/"+schema;
            System.out.println("Cadena conexion:"+ cadena);
           conSQL = DriverManager.getConnection(cadena,connectionProps);
           System.out.println("Connected to database");
       } catch (SQLException ex) {
           Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
       }
  
    
    return conSQL;
    }
    
    //Conexion a origen de datos tipo ODBC
    public Connection getConexionODBC(String origenDatosODBC,String user,String pwd ){
        Connection conSQL = null;
        Properties connectionProps = new Properties();//
        //connectionProps.put("user", user);
        //connectionProps.put("password", pwd);

          try {
               try {
            	   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            	   //java.sql.DriverManager.setLogStream(java.lang.System.out);
               } catch (ClassNotFoundException e) {
            	   System.err.println(e.getMessage());
            	   e.printStackTrace();
               }
            
               String cadena = "jdbc:odbc:"+origenDatosODBC;
               System.out.println("Cadena conexion: " + cadena);
               //conSQL = DriverManager.getConnection(cadena,connectionProps);
               conSQL = DriverManager.getConnection(cadena);
               System.out.println("Connected to database ODBC "+ origenDatosODBC );
           } catch (SQLException ex) {
               Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
           }
      
        
        return conSQL;
        } 
    public static void main (String [] args) {
    	ConnectionManager conManager =  new ConnectionManager();
    	Connection con = conManager.getConexionODBC(args[0],args[1],args[2]);
    }
}
