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
    public boolean existeAlumno(int codSis){
        boolean resp = false;
        try{
           Statement sql = ConexionSQL.getConnetion().createStatement();
           
           String consulta = "SELECT *"
                        + "FROM Umss.dbo.Estudiante"
                        + "WHERE codSis="+codSis;
           
           ResultSet resultado = sql.executeQuery(consulta);
           if(resultado.next()){
               resp = true;
           }
        }catch(SQLException ex){
            
        }
        return resp;
    }
    public boolean noPago(int codSis, String gestion){
        boolean resp = false;
        try{
           Statement sql = ConexionSQL.getConnetion().createStatement();
           
           String consulta = "SELECT *"
                        + "FROM Umss.dbo.Estudiante"
                        + "WHERE codSis="+codSis+" AND"
                                + "codSis NOT IN ("
                                                + "SELECT codSis"
                                                + "FROM Umss.dbo.Inscripciones"
                                                + "WHERE gestionInscrip LIKE '"+gestion+"'" 
                                                + ")";
           
           ResultSet resultado = sql.executeQuery(consulta);
           if(resultado.next()){
               resp = true;
           }
        }catch(SQLException ex){
            
        }
        return resp;
    }
}
