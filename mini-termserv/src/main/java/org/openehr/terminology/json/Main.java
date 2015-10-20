/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package org.openehr.terminology.json;


/**
 * Created by Samuel Junio on 20/10/2015.
 */
public class Main {

    public static void main(String args[]){
        JsonParser jsonParser = new JsonParser();
        System.out.println(jsonParser.toJson());
        jsonParser.fromJson(jsonParser.toJson());
        System.out.println(jsonParser.toJson());
    }
}
