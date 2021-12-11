package org.example;

import java.time.LocalDate;

public class Email {

    private String To;
    private String Subject;
    private String Text;
    private LocalDate date;



    public Email(String to,String subject,String text,LocalDate date){

        this.To= to;
        this.Subject=subject;
        this.Text=text;
        this.date=date;
    }
}
