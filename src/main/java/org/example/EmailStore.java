package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EmailStore {

    private final ArrayList<Email> emailList;
    private final String filename ="emails.ser";



    public EmailStore(String filename){
        this.emailList =new ArrayList<>();
        loadEmailDataFromFile(filename);
    }


    public void addEmail(String to, LocalDateTime time){


        String body = "Dear "+to+"\n This is a reminder for you booking which was is on "+time+"\n Thank you for working with our booking company\n Regards\n Car Hire Manager";
        //String body = "body";
        String subject ="Booking Reminder";
        Email email = new Email(to,subject,body,time);
        emailList.add(email);
    }

    public void displayEmails(){
        emailList.remove(0);
        for (Email e: emailList){

            System.out.println("-------------------------------------------");
            System.out.println("To: "+e.getTo());
            System.out.println("-------------------------------------------");
            System.out.println("Subject: "+e.getSubject());
            System.out.println("-------------------------------------------");
            System.out.println(e.getText());
            System.out.println("-------------------------------------------\n");

        }


    }




    public void loadEmailDataFromFile(String fileName){

        try{
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);

            Object o;

            while (true){
                try {
                    o = ois.readObject();

                    if (o instanceof Email){
                        emailList.add((Email) o);
                    }else {
                        System.err.println("Unexpected object in file");
                    }
                }catch (IOException ex){
                    break;
                }
            }

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            FileOutputStream f = new FileOutputStream(filename);
            ObjectOutputStream o = new ObjectOutputStream(f);

            for (Email e: emailList){
                o.writeObject(e);
            }
            o.close();
            f.close();

        }catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
