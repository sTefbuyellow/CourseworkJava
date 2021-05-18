package service;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>TableBuilder</code> class contains method <code>buildTable</code>
 * witch creates a object of <code>JPanel</code> class with custom parameters and
 * returns it.
 *
 * @author Kirichuk K.N.
 * @version 0.01 07.03.2021
 */
public class TableBuilder {


    /**
     * @param data 2 dimension array with table data
     * @param columnNames array with column names
     * @param mouseListener a object of <code>MouseListener</code> class to interact with the table
     * @return object of <code>JPanel</code> class with custom parameters
     */
    public static JScrollPane buildTable(Object[][] data, String[] columnNames, MouseListener mouseListener){
        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sorter.setSortKeys(sortKeys);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(650, 500));
        table.setRowHeight(30);
        table.setAlignmentX(Component.CENTER_ALIGNMENT);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.addMouseListener(mouseListener);
        return scrollPane;
    }
}
