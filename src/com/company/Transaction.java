package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Transaction {
    private Account account;
    private int amount;
    private LocalDate date;
    private String type;

    public Transaction(Account account, int amount,LocalDate date, String type) {
        this.account = account;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }
    public void getTransaction(){
        String plusminus;
        if(type=="withdraw"){
            plusminus="-";
        }else{
            plusminus="+";
        }
        System.out.println("İşlem Tutarı: "+plusminus+amount+" TRY");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println("İşlem Tarihi: "+date.format(formatter));
        System.out.println("İşlem Türü: "+type);
    }
}
