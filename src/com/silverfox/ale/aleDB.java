package com.silverfox.ale;

import java.sql.Connection;
import java.sql.DriverManager;

public class aleDB {



    public boolean connectToDB(){
        boolean estCon = false;
        Connection dbCon = null;
        try{
            dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ale", "Root", "oqu#$XQgHFzDj@1MGg1G8");
            estCon = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return estCon;
    }

}
