/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.deadvax.jmail.backend;

import java.util.*;
/**
 *
 * @author cfh
 */
public class MailMessage {
    private long beginLine = 0;
    private Vector <String> header = null;
    private Vector <String> body = null;
    
    private String subject = "";
    private String sender = "";
    private String date = "";

    public MailMessage(long beginLine, Vector<String> header, Vector<String> body) {
        this.beginLine = beginLine;
        this.header = header;
        this.body = body;

        populateData();
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

    public String getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }
}