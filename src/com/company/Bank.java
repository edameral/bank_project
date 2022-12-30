package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accountList=new ArrayList<>();
    private LocalDate sysDate=LocalDate.of(2022,02,14);

    public Bank() {
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void getAccount(){
        for(int i=0;i<accountList.size();i++){
            System.out.println(accountList.get(i));
        }
    }
    public Account getAccountbyID(int id){
        Account value=null;
        if(accountList.get(id)!=null){
            value=accountList.get(id);
        }
        return value;
    }

    public LocalDate getSysDate() {
        return sysDate;
    }
    public String convertedSysDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return getSysDate().format(formatter);
    }

    public void setSysDate(LocalDate sysDate) {
        if(sysDate==null){
            System.out.println("HATA: Bir sorun oluştu.\nLütfen girdiğiniz tarihin GG-AA-YYYY biçiminde olduğuna dikkat ederek tekrar deneyiniz.");
        }else{
            System.out.println("Eski sistem tarihi: "+convertedSysDate());
            this.sysDate = sysDate;
            System.out.println("Yeni sistem tarihi: "+convertedSysDate());
        }
    }

    public void sortition(){
        List<Account> chooseList=new ArrayList<>();
        for(int i=0;i<accountList.size();i++){
            if(accountList.get(i).getType()==2){
                for(int j=0;j<accountList.get(i).getPoint();j++){
                    chooseList.add(accountList.get(i));
                }
            }
        }
        if(chooseList.size()!=0){
            int index=(int) (Math.random()*chooseList.size());
            System.out.println("Çekilişi kazanan hesap ID'si: "+chooseList.get(index).getId());
            getAccountbyID(chooseList.get(index).getId()).deposit(10000);
            System.out.println("Hesaba 10.000 TL aktarıldı: "+chooseList.get(index).getId());
        }else{
            System.out.println("HATA: Sistemde henüz bir özel tür hesap açılmamış.");
        }
    }
}
