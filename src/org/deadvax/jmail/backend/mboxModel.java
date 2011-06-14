/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.deadvax.jmail.backend;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author cfh
 */
public class mboxModel extends AbstractTableModel {
    private String[] colNames = { "Sender", "Subject", "Date" };
    private MBox mbox;
    
    public mboxModel(MBox mbox) {
        this();
        setMBox(mbox);
    }
    
    public mboxModel() {
        super();      
    }
    
    public void setMBox(MBox mbox) {
        this.mbox = mbox;
        fireTableStructureChanged();
    }
    
    public MBox getMBox() {
        return mbox;
    }
    
    public String getColumnName(int col) {
        return colNames[col].toString();
    }

    public int getRowCount() {
        return mbox.size();
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public Object getValueAt(int row, int col) {
        switch(col) {
            case 0:
                return mbox.getMsg(row).getSender();
            case 1:
                return mbox.getMsg(row).getSubject();
            case 2:
                return mbox.getMsg(row).getDate();
            default:
                return -1;
        }
     
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public MailMessage getRow(int row) {
        return mbox.getMsg(row);
    }
            
    
    public void setValueAt(Object value, int row, int col) {
        
    }
}
