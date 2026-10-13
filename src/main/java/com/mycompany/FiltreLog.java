package com.mycompany;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FiltreLog {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int contador = 0;
        boolean hayTexto = false;
        String linea;
        
        try {
            while ((linea = br.readLine()) != null) {
                if (!linea.isEmpty()) {
                    hayTexto = true;
                }
                if (args.length!=0){
                    if (linea.contains(args[0])) {
                        contador++;
                    }       
                }else if (linea.contains("ERROR")){
                    contador++;
                }
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        if (!hayTexto) {
            System.err.println("Error: Text buit");
            System.exit(1);
        }else{
            System.out.println(contador);
            System.exit(0);
        }
    }
}
