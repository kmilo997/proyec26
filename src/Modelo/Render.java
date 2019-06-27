/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Carlos
 */
public class Render extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1, int i, int i1)
    {
        if(value instanceof JButton){
            JButton btn = (JButton)value;
            return btn;
        }
        
        return super.getTableCellRendererComponent(jtable, value, bln, bln1, i, i1); //To change body of generated methods, choose Tools | Templates.
    }
    
}
