package com.company;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.company.Main.bank;
import static java.time.temporal.ChronoUnit.DAYS;

public abstract class Account {

    //attributes
        private static final AtomicInteger count = new AtomicInteger(-1);
        private final int id;
        private int type;
        private int point;
        private int balance;
        private double rate;
        private int minValue;
        private int firstValue;
        private LocalDate openDate;
        private List<Transaction> transactionList=new ArrayList<>();
    //constructor


    public Account(int type, int balance, double rate, int minValue, int firstValue) {
        this.id = count.incrementAndGet();
        this.type = type;
        this.balance = balance;
        this.rate = rate;
        this.minValue = minValue;
        this.firstValue = firstValue;
        if(type==2){
            this.point=1;
        }else{
            this.point=0;
        }
        this.openDate = LocalDate.now();
    }

    //methods
        public void transactionFetch(){
        if(transactionList.size()<1){
            System.out.println("###########");
            System.out.println("Bu hesap ile alakalı henüz hiçbir işlem gerçekleştirmemiş.");
            System.out.println("###########");
        }else{
            int times=transactionList.size();
            if(transactionList.size()>5){
                times=5;
            }else{
                times=transactionList.size();
            }
            for(int i=0;i<times;i++){
                System.out.println("###########");
                transactionList.get(i).getTransaction();
            }
        }

        }

    public void deposit(int cash){
            balance+=cash;
            resetPoint();
            transactionList.add(new Transaction(this,cash,LocalDate.now(),"deposit"));
        }
        public void withdraw(int cash){
            if(cash>balance){
                System.out.println("Hesaptan çekmek istediğiniz tutar hesap bakiyesinden büyük olamaz\nHesap Bakiyesi:"+balance);
            }else{
                balance-=cash;
                resetPoint();
                transactionList.add(new Transaction(this,cash,LocalDate.now(),"withdraw"));
            }
        }

    public int getBalance() {
        return balance;
    }


    public int getMinValue() {
        return minValue;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void Benefit(){
        int difference= (int) DAYS.between(openDate, bank.getSysDate());
        if(difference<1){
            System.out.println("HATA: Kar alabilmek için hesabın açılışından veya son kar alımından bu yana bir günden fazla süre geçmiş olmalı.");
        }else{
            balance+=(int)balance*difference*rate;
            System.out.println("Kar hesaba eklenmiştir");
        }
    }

    public int getPoint() {
        return point;
    }

    public void resetPoint(){
        this.point=getBalance()/2000;
    }
}
