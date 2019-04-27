/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmealplanner;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author matth
 */
public class FoodRenderer extends JLabel implements ListCellRenderer 
{

    private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

    public FoodRenderer() 
    {
        setOpaque(true);
        setIconTextGap(12);
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) 
    {
        Food entry = (Food) value;
        setText(entry.toString());

        if (isSelected) 
        {
            setBackground(HIGHLIGHT_COLOR);
            setForeground(Color.white);
        } 
        else 
        {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return this;
    }
}
