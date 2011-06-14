/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.deadvax.jmail.backend;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 *
 * @author cfh
 */


public class MailMessage implements Comparable{
    private long beginLine = 0;
    private Vector <String> header = null;
    private Vector <String> body = null;
    
    private String subject = "";
    private String sender = "";
    private String date = "";
    private String messageID = "";

    public MailMessage(long beginLine, Vector<String> header, Vector<String> body) {
        this.beginLine = beginLine;
        this.header = header;
        this.body = body;

        populateData();
    }

    private void trimMessageEnd() {
        
        
    }
    
    private void populateData() {
        Iterator it = header.iterator();

        while(it.hasNext()) {
            String s = (String)it.next();

            if(s.startsWith("Subject")) {
                subject = s.substring(8);
            }
        }

        it = header.iterator();
        while (it.hasNext()) {
            String s = (String) it.next();

            if (s.startsWith("Date")) {
                date = s.substring(6);
            }
        }

        it = header.iterator();
        while (it.hasNext()) {
            String s = (String) it.next();

            if(s.startsWith("From")) {
                sender = s.substring(6);
            }
        }
    }

    public String getBodyText() {
        Iterator it = body.iterator();
        String toReturn = "";
        
        while(it.hasNext()) {
            toReturn += (String)it.next() + "\n";
        }
        
        return toReturn;
    }
    
    public String getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDateObject() {
        try {
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    public int compareTo(Object o) {
        if(o instanceof MailMessage) {
            return ((MailMessage)o).getDateObject().compareTo(getDateObject());
        } else {
            return -1;
        }
            
    }

    private String getMessageID() {
        return messageID;
    }
}