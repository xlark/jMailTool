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
    Vector<MailMessage> msg = new Vector<MailMessage>(); 
    static String[] nonos = { "X-UID: ", "Xref: ", "X-UIDL: ", "X-Mozilla-Status:",
                              "X-Mozilla-Keys:", "X-Mozilla-Status2:", "Status:",
                              "X-Status:", "X-Keywords:", "X-Gnus-Newsgroup:", "Lines:",
                              "X-Original-Status:", "X-Original-X-Status:","X-Original-X-Keywords",
                              "X-Original-X-UID", "X-From-Line","X-Gnus-Mail-Source"};/*, ">From "};*/
                              
                              /*">From cfh@sam.cscc ", ">From clark@mandos ",                             
                              ">From cfh@elrond.cscc ",">From VM ", ">From cfh@shadowfax",
                              ">From ch70925@cs.cs.appstate.edu"};*/
    static String[] goodones = { "Subject", "From", "Date", "Message-ID"};
    public static MBox importMbox(String filename, String outputPath) {
        // TODO
        // Grab Lines: field
        // REGEX?  Count # of fields and do a total w/ printout
        //
        // Seems like the most common kind of difference is 
        // one file being just one line longer than the other.
        
        String line, oldLine = null;
        MBox mbox = new MBox(outputPath); 
        
        try {
            long count = 1;
            HashMap<String, TreeSet<Long>> mids = new HashMap<String, TreeSet<Long>>();
            File f = new File(filename);
            LineNumberReader lnr = new LineNumberReader(new FileReader(f));
            PrintWriter sf = new PrintWriter(new FileWriter("/home/cfh/strange_headers"));
            PrintWriter thrown_out = new PrintWriter(new FileWriter("/home/cfh/thrown_out"));
            line = lnr.readLine(); 
            if(!line.startsWith("From "))
                throw new Exception("Invalid MBOX File, doesn't start with FROM_");
            
            while(line != null) {
                File msg_file = new File(outputPath+count);

                PrintWriter out = new PrintWriter(new FileWriter(msg_file));
                boolean printThisLine = true;
                
                if(line.startsWith("From ")) {
                    /* In Message */
                    boolean afterHeaders = false;
                    MailMessage newMessage = new MailMessage();
                    newMessage.setFile(msg_file);
                    newMessage.setBeginLine(lnr.getLineNumber());
                    
                    /* Create new From_ header */
                    
                    line = "From cfh@shadowfax";
                   /* printThisLine=false;*/
                    do
                     {
                        /* My current assumption is that there are NO
                         * blank lines in the header section.  The first 
                         * blank line indicates the beginning of the 
                         * body
                         */
                       
                        oldLine = line;
                        if(printThisLine) {
                            out.println(oldLine);
                            if (afterHeaders == false &&
                                    line.startsWith(">From ")) {
                                sf.println(count + ":" + line);
                            }
                        }
                        else {
                            if (afterHeaders == false &&
                                    line.startsWith(">From ")) {
                                thrown_out.println(count + ":" + line);
                            }

                            printThisLine=true;
                        }
                        
                        line = lnr.readLine();
                        
                        
                        
                        if(afterHeaders==false && 
                                line != null && 
                                oldLine.equals("") && 
                                !line.startsWith("From ")) {
                           afterHeaders=true;
                        }
                        if(line != null && !afterHeaders) {
                            for (int i = 0; i < nonos.length; i++) {
                                if(line.startsWith(nonos[i])) {
                                    printThisLine=false;
                                    break;
                                }
                            }
                            if(line.toUpperCase().startsWith("MESSAGE-ID:")) {
                                TreeSet<Long> l = mids.get(line.substring(11).trim());
                                //System.out.println(line.substring(11).trim());
                                if(null == l) {
                                    l = new TreeSet<Long>();
                                }
                                
                                l.add(count);
                                
                                mids.put(line.substring(11).trim(), l);
                            }
                            
                            for(int i = 0; i < goodones.length && printThisLine; i++) {
                                if(line.startsWith(goodones[i])) {
                                    int sub = goodones[i].length()+1;
                                    if(sub < 0) {
                                        System.out.println(goodones[i]);
                                        System.out.println(newMessage.getTitle());
                                    }
                                    if(line.substring(sub).equals(""))
                                        newMessage.setHeader(goodones[i], line.substring(sub));
                                    else
                                        newMessage.setHeader(goodones[i], line.substring(sub+1));
                                    break;  
                                }
                            }
                            

                        }
                    } while(!((line == null || line.startsWith("From "))));

                    
                    count++;
                    out.close();
                }
                
            }
            // Generate SCRIPT
            PrintWriter script = new PrintWriter(new FileWriter("/home/cfh/mv_script"));
            script.println("#! /bin/sh");
            Iterator it = mids.keySet().iterator();
            int c=1;
            while(it.hasNext()) {
                TreeSet<Long> t = mids.get((String)it.next());
                if(t.size() == 1) {
                    String output = "#echo ONLYONE:" + t.first();
                    script.println(output);
                }
                else {
                    Iterator iter = t.iterator();
                    String output = "mkdir " + c + "d;" + "mv ";
                    while (iter.hasNext()) {
                        Long temmp = (Long) iter.next();
                        output += temmp + " ";
                    }
                    output += c + "d";
                    script.println(output);
                    c++;
                }
            }
            script.close();
            sf.close();
            thrown_out.close();
            return mbox;
            
        }
        catch (Exception e) {
            System.out.println("PANIC!");
            System.out.println(e);
            System.exit(0);
        }
        
        return null;
    }
    
    public MBox(String path) {
        
    }
    
    public MailMessage getMsg(int i) {
        return msg.get(i);
    }
    
    protected void add(MailMessage m){
        msg.add(m);
    }
    public int size() {
        return msg.size();
    }
}