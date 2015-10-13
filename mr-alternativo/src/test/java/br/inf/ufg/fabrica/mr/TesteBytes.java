/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufg.fabrica.mr;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author Beatriz
 */
public class TesteBytes {
    /**
     * o byte varia de -127 a 127, portanto é preciso
     * convertê-lo para int para pegar o valor sem sinal
     */
    public static int getByte(byte b) {
        return b & 0xFF;
    }
    
    
    /**
     * O int é convertido para um array de byte
     * com o menor tamanho possivel.
     * O tamanho do array é guardado nos dois primeiros
     * bits, assim, por exemplo, um valor de ate 2^6 é guardado em
     * 1 byte. Os dois primeiros bits são setados
     * conforme o tamanho do array: 00 indica que é utilizado
     * apenas 1 byte; 01 para 2 byte; 10 para 3 bytes;
     * 11 para 4 bytes.
     */
    public static byte[] intToByteArray(int value) {
        if(value < 0 || value >= (Math.pow(2, 30))){
            System.out.println(value +" unsupported");
            return null;
        }
        if (value < Math.pow(2, 6)){
            return new byte[]{ 
                (byte)value
            };   
        }
        
        if(value < Math.pow(2, 14)){
            
            return new byte[] {
                (byte)((value >>> 8) | (2 << 5)),
                (byte)value
            };
            
        }
        
        if(value < Math.pow(2, 22)){
            return new byte[] {
                (byte)((value >>> 16) | (2 << 6)),
                (byte)(value >>> 8),
                (byte)value
            };
        }
        
        return new byte[] {
            (byte)((value >>> 24) | (3 << 6)),
            (byte)(value >>> 16),
            (byte)(value >>> 8),
            (byte)value
        };
    }
    
    /**
     * Converte o array de bytes em um int.
     */
    public static int byteArrayToInt(byte[] b){
        int x = 0;
        for (int i = 0; i < b.length; i++){
            x = (x << 8) | getByte(b[i]);
        }
        return x;
    }
    
    /**
     * Pega o proximo inteiro em um buffer.
     * Os primeiros dois bits informam o tamanho
     * do array de bytes que formam um int.
     * Assim o int é formado pelo primeiro byte 
     * (com exceção dos 2 primeiros bits) agregado
     * ao restante, conforme o tamanho descoberto.
     */
    public static int nextInt(ByteArrayInputStream buf) {
        int b = buf.read();
        byte i = (byte)(b >>> 6);
        byte ini = (byte)(b & 0x3F);
        
        switch(i){
            case 0:
                return ini;
            case 1:
                byte[] temp = {ini, (byte)buf.read()};
                return byteArrayToInt(temp);
            case 2:
                byte[] temp2 = {ini, (byte)buf.read(), (byte)buf.read()};
                return byteArrayToInt(temp2);
            case 3:
                byte[] temp3 = {ini, (byte)buf.read(),
                     (byte)buf.read(),  (byte)buf.read()};
                return byteArrayToInt(temp3);
                
        }
        return 0;
    }
    
    /**
     * @param args the command line arguments
     * 
     * Testes.
     */
    public static void main(String[] args) throws IOException {
        int a = 32; //0001 0000
	byte[] x1 = intToByteArray(a);
	System.out.println(a + ", tamanho " + x1.length + ", bytes = "+getByte(x1[0]));
        ByteArrayInputStream buf1 = new ByteArrayInputStream(x1);
        System.out.println("Inteiro lido: "+nextInt(buf1));
        
	a = 511; //0000 0001 1111 1111
	byte[] x2 = intToByteArray(a);
	System.out.println("\n"+ a + ", tamanho " + x2.length + ", bytes = " 
                + getByte(x2[0]) + " "+ getByte(x2[1]));
        ByteArrayInputStream buf2 = new ByteArrayInputStream(x2);
        System.out.println("Inteiro lido: "+nextInt(buf2));
        
	a = 1048576; //0001 0000 0000 0000 0000 0000
	byte[] x3 = intToByteArray(a);
	System.out.println("\n"+ a + ", tamanho " + x3.length + ", bytes = " + getByte(x3[0]) + " "
                + getByte(x3[1]) + " " + getByte(x3[2]));
	ByteArrayInputStream buf3 = new ByteArrayInputStream(x3);
        System.out.println("Inteiro lido: "+nextInt(buf3));
        
	a = 1073741823; //0011 1111 1111 1111 1111 1111 1111 1111
	byte[] x4 = intToByteArray(a);
	System.out.println("\n"+ a + ", tamanho " + x4.length + ", bytes = " + getByte(x4[0]) + " "
                + getByte(x4[1]) +" "+ getByte(x4[2]) + " "+ getByte(x4[3]));
        ByteArrayInputStream buf4 = new ByteArrayInputStream(x4);
        System.out.println("Inteiro lido: "+nextInt(buf4));
        
        /*
         * Inserção e leitura de um unico buffer.
         */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(x1);
        out.write(x2);
        out.write(x3);
        out.write(x4);
        
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        System.out.print("\nLeitura de buffer unico: ");
        while(in.available() != 0){
            System.out.print(nextInt(in)+" -- ");
        }
        System.out.println();
        
    }
    
}

