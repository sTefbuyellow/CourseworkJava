package view.components;

import view.frames.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class TableBuilder {

    public static JScrollPane buildTable(Object[][] data, String[] columnNames, MouseListener mouseListener){
        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(650, 500));
        table.setRowHeight(30);
        table.setAlignmentX(Component.CENTER_ALIGNMENT);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.addMouseListener(mouseListener);
        return scrollPane;
    }

}
