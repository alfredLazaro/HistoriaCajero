/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consulta;

/**
 *
 * @author MAURICIO
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultaBD {
    public static String existeAlumno(int codSis){
        String msj = "No se encontro el alumno en la BD";
        try{
           Statement sql = ConexionSQL.getConnetion().createStatement();
           
           String consulta = "SELECT *"
                        + " FROM Umss.dbo.Estudiante"
                        + " WHERE codSis="+codSis;
           System.out.println(consulta);
           ResultSet resultado = sql.executeQuery(consulta);
           if(resultado.next()){
               msj = "Info Alumno:\n"
                       + "Nombre: " + resultado.getString(2) + "\n"
                       + "ApPat: " + resultado.getString(4) + "\n"
                       + "ApMat: " + resultado.getString(5) + "\n"
                       + "Estado: " + resultado.getString(9) + "\n";
           }
        }catch(SQLException ex){
            msj = ex.toString();
        }
        return msj;
    }
    public static boolean noPago(int codSis, String gestion){
        //Consulta para que ver que estudiante no pago en la gestion actual
        boolean resp = false;
        try{
           Statement sql = ConexionSQL.getConnetion().createStatement();
           
           String consulta = "SELECT *"
                        + " FROM Umss.dbo.Estudiante"
                        + " WHERE codSis="+codSis+" AND"
                                + " codSis NOT IN ("
                                                + "SELECT codSis"
                                                + " FROM Umss.dbo.Inscripciones"
                                                + " WHERE gestionInscrip LIKE '"+gestion+"'" 
                                                + ")";
           
           ResultSet resultado = sql.executeQuery(consulta);
           if(resultado.next()){
               resp = true;
           }
        }catch(SQLException ex){
            
        }
        return resp;
    }
    
    public static void registroPago(int codSis, String gestion,int monto){
        //Registramos en la Base de Datos el pago realizado
        
        try{
           Statement sql = ConexionSQL.getConnetion().createStatement();
           
           String consulta = "INSERT into Umss.dbo.Inscripciones "
                   + "values ("+codSis+",'"+gestion+"',"+monto+")";
           sql.execute(consulta);
           
        }catch(SQLException ex){
            System.out.println(ex.toString());
        }
    }
}
