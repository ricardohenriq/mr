/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package org.openehr.terminology.json;


import java.util.Base64;

/**
 * Created by Samuel Junio on 20/10/2015.
 */
public class Main {

    public static void main(String args[]){
        JsonParser jsonParser = new JsonParser();
        System.out.println(jsonParser.toJson());
        jsonParser.fromJson(jsonParser.toJson());

        String str = "qualquer coisa aabbaa";
        System.out.println(jsonParser.arrayBytesToBase64(str.getBytes()));
        System.out.println(new String(jsonParser.base64ToArrayBytes(jsonParser.arrayBytesToBase64(str.getBytes()))));

        System.out.println("DONE!!!");
    }
}
