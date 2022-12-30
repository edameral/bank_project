package com.company;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static Bank bank=new Bank();

    public static int getAmount(){
        System.out.print("Hesap için tutar giriniz: ");
        int value = 0;
        Scanner scanner=new Scanner(System.in);
        try{
            value=scanner.nextInt();
        }catch (Exception e){
            System.out.println("HATA: Tam sayı bir değer girilmediği için herhangi bir değişiklik gerçekleşmedi.");
            scanner.next();// Move to next other wise exception
        }
        return value;
    }

    public static int getID(){
        System.out.print("İşlem yapmak istediğiniz hesap ID'sini giriniz: ");
        Scanner scanner=new Scanner(System.in);
        int value=0;
        try{
            value=scanner.nextInt();
            if(bank.getAccountbyID(value).equals(null)){
                value=-1;
            }
        }catch (Exception e){
            value=-1;
        }
        return value;
    }
    public static LocalDate getDate(){
        System.out.println("Yeni sistem tarihini giriniz:(GG-AA-YYYY şeklinde)");
        Scanner scanner= new Scanner(System.in);
        String date=scanner.next();
        String[] arrOfStr = date.split("-");
        String newDate="";
        LocalDate localDate=null;
        try{
            newDate+=arrOfStr[2]+"-"+arrOfStr[1]+"-"+arrOfStr[0];
            localDate = LocalDate.parse(newDate);
            return localDate;
        }catch (Exception e){
            return null;
        }
    }
    public static void fetchAccounts(){
        for(int i=0;i<bank.getAccountList().size();i++){
            System.out.println("\n((HESAP ID:"+bank.getAccountbyID(i).getId()+"'nin SON 5 İŞLEMİ)): ");
            bank.getAccountbyID(i).transactionFetch();
            System.out.println("__________________________________");
        }
    }
    public static void showMenu(){
        System.out.println("#######################################################");
        System.out.println("_________________________KOMUTLAR_________________________");
        System.out.println("Create_S_ID_balance: Short Term hesap oluşturmaya yarar.");
        System.out.println("Create_L_ID_balance: Long Term hesap oluşturmaya yarar.");
        System.out.println("Create_O_ID_balance: Özel hesap oluşturmaya yarar.");
        System.out.println("Create_C_ID_balance: Özel hesap oluşturmaya yarar.");
        System.out.println("Increase_ID_cash: Hesap bakiyesi arttırmaya yarar.");
        System.out.println("Decrease_ID_cash: Hesap bakiyesi azaltmaya yarar.");
        System.out.println("Set_dd_mm_yy: Sistem tarihini değiştirmeye yarar.");
        System.out.println("ShowAccount: Tüm hesap ID'leri ve son 5 işlemi görüntülemeye yarar.");
        System.out.println("ShowIDs: Tüm hesap ID'lerini görüntülemeye yarar.");
        System.out.println("Sortition: Özel hesaplar arasında çekiliş yapmaya yarar.");
        System.out.println("Benefit: Hesaba geçen süre içerisindeki karını aktarır.");
        System.out.println("help: Tüm komutları görmeye yarar.");
        System.out.println("#######################################################");
    }
    public static void helpText(){
        System.out.println("\n\n\n\n\n\n\nİşlem sonuçlandı! Diğer tüm komutları görmek için: help");
    }
    public static void fetchIDS(){
        System.out.println("TÜM HESAPLARIN ID'leri");
        for(int i=0;i<bank.getAccountList().size();i++){
            System.out.println("ID: "+bank.getAccountbyID(i).getId());
            System.out.println("__________________________________");
        }
    }
    public static void increaseBalance(int id,int amount){
        if(id==-1){
            System.out.println("HATA: Böyle bir hesap bulunamadı!");
        }else{
            bank.getAccountbyID(id).deposit(amount);
            System.out.println("HESABIN GÜNCEL BAKİYESİ:"+bank.getAccountbyID(id).getBalance());
        }
    }
    public static void decreaseBalance(int id,int amount){
        if(id==-1){
            System.out.println("HATA: Böyle bir hesap bulunamadı!");
        }else{
            if(amount>bank.getAccountbyID(id).getBalance()||bank.getAccountbyID(id).getBalance()-amount<bank.getAccountbyID(id).getMinValue()){
                System.out.println("HATA: Çekilmek istenen miktar hesabın bakiyesinden büyük veya minimum tutar sınırına takıldınız\nHesap Bakiyesi:"+bank.getAccountbyID(id).getBalance()+"\nHesap Min. Tutarı:"+bank.getAccountbyID(id).getMinValue());
            }else{
                bank.getAccountbyID(id).withdraw(amount);
                System.out.println("YENİ BAKİYE:"+bank.getAccountbyID(id).getBalance());
            }
        }
    }

    public static void createAccount(int amount,int type){
        switch (type){
            case 0: if(amount<1000){
                System.out.println("HATA: Short Term hesabı için minimum tutar 1000 TL'dir!");
            }else{bank.getAccountList().add(new ShortTerm(amount));} break;
            case 1: if(amount<1500){
                System.out.println("HATA: Long Term hesabı için minimum tutar 1500 TL'dir!");
            }else{ bank.getAccountList().add(new LongTerm(amount));} break;
            case 2: bank.getAccountList().add(new Special(amount)); break;
            case 3: bank.getAccountList().add(new Current(amount)); break;
        }
    }

    public static void main(String[] args) {
        boolean active=true;
        int a=-1;
        while(active){
            if(a<0){
                showMenu();
                a++;
            }
            Scanner scanner=new Scanner(System.in);
            String state=scanner.next();
            switch (state){
                case "Create_S_ID_balance": createAccount(getAmount(),0); helpText(); break;
                case "Create_L_ID_balance": createAccount(getAmount(),1); helpText(); break;
                case "Create_O_ID_balance": createAccount(getAmount(),2); helpText(); break;
                case "Create_C_ID_balance": createAccount(getAmount(),3); helpText(); break;
                case "Increase_ID_cash": increaseBalance(getID(),getAmount()); helpText(); break;
                case "Decrease_ID_cash": decreaseBalance(getID(),getAmount()); helpText(); break;
                case "Set_dd_mm_yy": bank.setSysDate(getDate()); helpText(); break;
                case "ShowAccount": fetchAccounts(); helpText(); break;
                case "ShowIDs": fetchIDS(); helpText(); break;
                case "Sortition": bank.sortition(); helpText(); break;
                case "Benefit": bank.getAccountbyID(getID()).Benefit(); helpText(); break;
                case "help": showMenu(); break;
            }
        }
    }


}
