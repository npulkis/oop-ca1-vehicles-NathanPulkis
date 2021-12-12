package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Email implements Serializable {

    private String To;
    private String Subject;
    private String Text;
    private LocalDateTime date;


    public Email(String to, String subject, String text, LocalDateTime date) {

        this.To = to;
        this.Subject = subject;
        this.Text = text;
        this.date = date;
    }


    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getSubject() {
        return Subject;
    }


    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Email{" +
                "To='" + To + '\'' +
                ", Subject='" + Subject + '\'' +
                ", Text='" + Text + '\'' +
                ", date=" + date +
                '}';
    }
}
