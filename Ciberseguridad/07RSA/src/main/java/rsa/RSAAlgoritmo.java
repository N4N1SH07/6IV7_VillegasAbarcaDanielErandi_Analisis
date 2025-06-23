/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rsa;
import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class RSAAlgoritmo {
    //Vamos a definir las variables principales del agoritmo
    int tamPrimo;
    BigInteger n, p, q; //n = p * q
    BigInteger fi; //(p-1) * (q -1)
    BigInteger e, d;
    
    //Constructor
    public RSAAlgoritmo(){
        this.tamPrimo = tamPrimo;
    }
    
    //Metodo para generar primos 
    public void generarPrimos(){
        //se debe de comprobar que solo sea divisible entre si mismo y la unidad
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10,new Random());
        while(q.compareTo(p)==0);
        
    }
    
    /*
    Necesitamos generar las claves
    n = p * q
    fi = (p-1) * (q -1)
    determinar el e y d como un coprimo que sea 1 < e < n
    */
    public void generarClaves(){
        n = p.multiply(q);
        //Tenemos que utilizar el método subtract
        fi = p.subtract(BigInteger.valueOf(1));
        fi = fi.multiply(q.subtract(BigInteger.valueOf(1)));
        
        //Calcular e
        do e = new BigInteger(2*tamPrimo, new Random());
        while(
                (e.compareTo(fi) != -1) ||
                (e.gcd(fi).compareTo(BigInteger.valueOf(1)) != 0)
                );
        
        d = e.modInverse(fi);
    }
    
    //Programar el metodo para cifrar y el metodo para descifrar
    public BigInteger[] cifrar(String mensaje){
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        // formula es C = M^e mod(n)
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        for(i = 0; i < bigdigitos.length; i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        //Aplicamos la formula
        BigInteger[] cifrado = new BigInteger[bigdigitos.length];
        
        for(i = 0; i < bigdigitos.length; i++){
            cifrado[i] = bigdigitos[i].modPow(e, n);
        }  
        return cifrado;  
    }
    
    
    //Metodo para descifrar
    public String descifrar(BigInteger[] cifrado){
        BigInteger[] descifrar = new BigInteger[cifrado.length];
        //Aplico la formula para descifrar
        for(int i = 0; i < descifrar.length; i++){
            // M = C^d mod(n)
            descifrar[i] = cifrado[i].modPow(d, n);
        }
        
        //crear el nuevo arreglo
        char[] charArray = new char[descifrar.length];
        
        for(int i = 0; i < charArray.length; i++){
            charArray[i] = (char)(descifrar[i].intValue());
        }
        
        return (new String(charArray));
    }
}
