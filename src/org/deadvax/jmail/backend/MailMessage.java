/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.deadvax.jmail.backend;

import java.util.*;
import java.io.File;
/**
 *
 * @author cfh
 */
public class MailMessage {

    private HashMap<String,String> headers = new HashMap<String,String>();
    private File file;
    private Integer beginLine;
    private Integer endLine;    
    private Set<MailMessage> duplicates = null;
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    

    public Set<MailMessage> getDuplicates() {
        if(duplicates == null) {
            duplicates = new HashSet<MailMessage>();
            duplicates.add(this);
        }
        return duplicates;
    }

    public void setDuplicates(Set<MailMessage> duplicates) {
        this.duplicates = duplicates;
    }
    
    public boolean mightEqual(MailMessage m) {
        if(getTitle().equals(m.getTitle()) &&
           getSender().equals(m.getSender()) &&
           getDate().equals(m.getDate()))
            return true;
        else
            return false;
    }

    public MailMessage() { 
   
    }

    public String getHeader(String key) {
        if (key == null) {
            return "";
        }
        key.toLowerCase();
        String toReturn = headers.get(key);
        if (toReturn == null)
            return "";
        else 
            return toReturn;
    }
    
    public void setHeader(String h, String i) {
        headers.put(h.toLowerCase(), i);
    }
    
    public Integer getBeginLine() {
        return beginLine;
    }

    public void setBeginLine(Integer beginLine) {
        this.beginLine = beginLine;
    }

    public String getDate() {
        return getHeader("date");
    }

    public void setDate(String date) {
        setHeader("date",date);
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public String getSender() {
        return getHeader("from");
    }

    public void setSender(String sender) {
        setHeader("from", sender);
    }

    public String getTitle() {
        return getHeader("subject");
    }

    public void setTitle(String title) {
        setHeader("subject", title);
    }
    
    
}