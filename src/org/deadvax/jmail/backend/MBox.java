/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.deadvax.jmail.backend;

import java.util.*;
import java.io.*;
/**
 *
 * @author cfh
 */
public class MBox {
    private Vector<MailMessage> msgs = null;
    private File originalFile = null;
    private static String[] nonos = { "X-UID: ", "Xref: ", "X-UIDL: ",
        "X-Mozilla-Status:",
        "X-Mozilla-Keys:", "X-Mozilla-Status2:", "Status:",
        "X-Status:", "X-Keywords:", "X-Gnus-Newsgroup:", "Lines:",
        "X-Original-Status:", "X-Original-X-Status:", "X-Original-X-Keywords",
        "X-Original-X-UID", "X-From-Line", "X-Gnus-Mail-Source"};/*, ">From "};*/
                              
                              /*">From cfh@sam.cscc ", ">From clark@mandos ",                             
                              ">From cfh@elrond.cscc ",">From VM ", ">From cfh@shadowfax",
                              ">From ch70925@cs.cs.appstate.edu"};*/
    static String[] goodones = { "Subject", "From", "Date", "Message-ID"};


    public static MBox importMbox(String filename) {
        // TODO
        // Grab Lines: field
        // REGEX?  Count # of fields and do a total w/ printout
        //
        // Seems like the most common kind of difference is 
        // one file being just one line longer than the other.
        
        String line = null, oldLine = null;
        
        
        try {
            MBox mbox = new MBox(new File(filename));
            LineNumberReader lnr = new LineNumberReader(new FileReader(mbox.getFile()));

            line = lnr.readLine();
            
            if(!line.startsWith("From "))
                throw new Exception("Invalid MBOX File, doesn't start with FROM_");
            
            while(line != null) {
                if(line.startsWith("From ")) {
                    /* In Message */
                    boolean afterHeaders = false;
                    Vector<String> header = new Vector<String>();
                    Vector<String> body = new Vector<String>();
                    long currentStartLine = lnr.getLineNumber();

                    do
                     {
                        /* My current assumption is that there are NO
                         * blank lines in the header section.  The first 
                         * blank line indicates the beginning of the 
                         * body
                         */
                        oldLine = line;                     
                        line = lnr.readLine();
                        if(line == null
                            || line.startsWith("From ")) {
                            mbox.add(new MailMessage(currentStartLine, header, body));
                            continue;
                        } else if (line.equals("") &&
                                !afterHeaders) { /* Blank Lines can appear in messages */
                            afterHeaders = true;
                            continue;
                        } else if (afterHeaders) {
                            body.add(line);
                        } else {
                            header.add(line);
                        }
                    } while(line != null && !line.startsWith("From "));
                }
                
            }
            return mbox;
        }
        catch (Exception e) {
            System.out.println("PANIC!");
            System.out.println(e);
            System.exit(0);
        }
        
        return null;
    }

    public boolean add(MailMessage m) {
        return msgs.add(m);
    }

    public MBox() {
        msgs = new Vector<MailMessage>();
    }

    public MBox(String s) {
        msgs = new Vector<MailMessage>();
    }

    public MBox(File f) {
        msgs = new Vector<MailMessage>();
        originalFile = f;
    }

    public File getFile() {
        return originalFile;
    }

    public int size() {
        return msgs.size();
    }

    public MailMessage getMsg(int i) {
        return msgs.get(i);
    }
    
    public void sort() {
        Collections.sort(msgs);
    }
}