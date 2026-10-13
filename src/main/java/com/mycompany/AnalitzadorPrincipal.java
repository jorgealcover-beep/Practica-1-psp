package com.mycompany;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AnalitzadorPrincipal{
    public static void main(String[] args){
        String classpath = System.getProperty("java.class.path");
        ProcessBuilder pb1 = new ProcessBuilder("java","-cp",classpath, "com.mycompany.FiltreLog", "ERROR");
        ProcessBuilder pb2 = new ProcessBuilder("java","-cp",classpath, "com.mycompany.FiltreLog", "WARNING");
        pb1.redirectError(new File("errors_filtre1.log"));
        pb2.redirectError(new File("errors_filtre2.log"));
        try{
            Process p1 = pb1.start();
            Process p2 = pb2.start();

            BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(p1.getOutputStream()));
            BufferedReader in1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(p2.getOutputStream()));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(p2.getInputStream()));
            String[] lineas = {
                    "ERROR: Fallo de conexion",
                    "WARNING: Disco casi lleno",
                    "ERROR: Acceso denegado",
                    "WARNING: Uso elevado de CPU",
                    "ERROR: Error de autenticacion",
                    "WARNING: Memoria casi llena"
            };
            for (String linea : lineas) {
                out1.write(linea);
                out1.newLine();
                out2.write(linea);
                out2.newLine();
            }
            out1.flush();
            out2.flush();
            out1.close();
            out2.close();
            
            String respuesta1 = in1.readLine();
            String respuesta2 = in2.readLine();
            
            
            int codigo1 = p1.waitFor();
            int codigo2 = p2.waitFor();
            
            
            
            if (codigo1==codigo2 && codigo1==0){
                System.out.println("RESULTAT: ERRORS="+ respuesta1 + "| WARNINGS=" + respuesta2 + "| EXIT_CODE="+ codigo1);
            }else{
                System.out.print("Ha habido un error");
            }

        }catch(IOException | InterruptedException e){
            System.err.println(e);
        }
    }
}
