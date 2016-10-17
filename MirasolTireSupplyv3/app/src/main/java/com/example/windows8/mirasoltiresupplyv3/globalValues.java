package com.example.windows8.mirasoltiresupplyv3;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by WINDOWS 8 on 9/29/2016.
 */

public class globalValues extends Activity {
    public static String user_Id="0", user_FName="", user_LName="",
            user_Email="", user_Address="", user_Gender="",
            user_Contact="";
    public static Boolean lastStatus = false, wait =false;
    public static ArrayList<Transaction> transactions = new ArrayList<Transaction>();

    public static void setAccountValues(String Id, String FName, String LName,
                                        String Email, String Address, String Gender, String Contact){
        user_Id = Id;
        user_FName = FName;
        user_LName = LName;
        user_Email = Email;
        user_Address = Address;
        user_Gender = Gender;
        user_Contact = Contact;

    }
    public static void addTransaction(Transaction t){
        transactions.add(t);
    }

    public static void clearTransactions(){
        transactions.clear();
    }

    public static void clear(){
        user_Id="0";
        user_FName="";
        user_LName="";
        user_Email="";
        user_Address="";
        user_Gender="";
        user_Contact="";
    }
}
