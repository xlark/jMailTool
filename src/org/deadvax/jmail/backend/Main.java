///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package org.deadvax.jmail.backend;
//
//import java.io.*;
//import java.util.*;
//
///**
// *
// * @author cfh
// */
//public class Main {
//
//    /**
//     * @param args the command line arguments
//     */
//    static String prefix="/Users/cfh/mboxconv.out/";
//    static HashSet<Set<MailMessage>> dupsSet = new HashSet<Set<MailMessage>>();
//    static Vector<MailMessage> msg_info = new Vector<MailMessage>();
//
//    public static void main(String[] args) {
//        String line, oldLine=null;
//        int messageCount = 0;
//        int clWarn = 0;
//        try {
//            File f = new File("/Users/cfh/Desktop/NewSent");
//            LineNumberReader lnr = new LineNumberReader(new FileReader(f));
//
//            line = lnr.readLine();
//            if(!line.startsWith("From "))
//                throw new Exception("Invalid MBOX File, doesn't start with FROM_");
//	    line = "From cfh@galadriel Tue Apr 13 21:18:38 EDT 2010";
//            boolean printNextFrom = false;
//            while(line != null) {
//                File msg_file = new File(prefix+msg_info.size());
//                PrintWriter out = new PrintWriter(new FileWriter(msg_file));
//
//                if(line.startsWith("From ")) {
//                    /* In Message */
//                    boolean afterHeaders = false;
//                    MailMessage newMessage = new MailMessage();
//                    newMessage.setFile(msg_file);
//                    newMessage.setBeginLine(lnr.getLineNumber());
//                    if(printNextFrom) {
//
//                        System.out.println(line);
//                        printNextFrom = false;
//                    }
//                    do {
//                        /* My current assumption is that there are NO
//                         * blank lines in the header section.  The first
//                         * blank line indicates the beginning of the
//                         * body
//                         */
//                        oldLine = line;
//                        out.println(oldLine);
//                        line = lnr.readLine();
//
//                        if(afterHeaders==false && line != null && oldLine.equals("") && !line.startsWith("From ")) {
//                           afterHeaders=true;
//                           // out.close();
//                           //out = new PrintWriter(new FileWriter(new File(prefix+msg_info.size()+".body")));
//                        }
//                        if(line != null && !afterHeaders) {
//                            if (line.startsWith("From: ")) {
//                                newMessage.setSender(line.substring(6));
//                            }
//                            if (line.startsWith("Date: ")) {
//                                newMessage.setDate(line.substring(6));
//                            }
//                            if (line.startsWith("Subject: ")) {
//                                newMessage.setTitle(line.substring(9));
//                            }
//                            if (line.startsWith("Content-Length: ")) {
//                                clWarn++;
//                                printNextFrom=true;
//                                System.out.println("CL WARNING!!!" + msg_file.getName());
//                            }
//                        }
//                    } while(!(oldLine.equals("") &&
//                            (line == null || line.startsWith("From ")))
//                            );
//
//                    msg_info.add(newMessage);
//                    findDuplicates(newMessage);
//                    out.close();
//                }
//
//            }
//
//            System.out.println("Total Message Count:  " + msg_info.size());
//            System.out.println("CL WARNINGS:  " + clWarn);
//            /*System.out.println("DUPLICATES:");
//            printDupSets();*/
//        }
//        catch(Exception e) {
//            System.out.println("PANIC!");
//            System.out.println(e);
//        }
//    }
//
//    public static void findDuplicates(MailMessage newMessage) {
//        Iterator it = msg_info.iterator();
//        MailMessage m;
//        while(it.hasNext()) {
//            m = (MailMessage)it.next();
//            if(newMessage != m && newMessage.mightEqual(m)) {
//                Set<MailMessage> vec = m.getDuplicates();
//                vec.add(newMessage);
//                newMessage.setDuplicates(vec);
//            }
//        }
//    }
//
//    public static void printDupSets() {
//        Iterator it = msg_info.iterator();
//        dupsSet = new HashSet<Set<MailMessage>>();
//        int count = 0;
//        while(it.hasNext()) {
//            dupsSet.add(((MailMessage)it.next()).getDuplicates());
//        }
//        it = dupsSet.iterator();
//        while(it.hasNext()) {
//
//            System.out.print(count + ":  ");
//            Set temp_msg = (Set) it.next();
//            Iterator iter = temp_msg.iterator();
//            while(iter.hasNext()) {
//                MailMessage msg = (MailMessage)iter.next();
//                System.out.print(msg.getFile().getName() + " ");
//            }
//            System.out.println();
//            count++;
//        }
//    }
//}
