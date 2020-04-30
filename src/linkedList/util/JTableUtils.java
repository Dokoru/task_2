package linkedList.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;

public class JTableUtils {
    
    public static class JTableUtilsException extends ParseException {
        public JTableUtilsException(String s) {
            super(s, 0);
        }
    }

    public static final int DEFAULT_COLUMN_WIDTH = 40;
    private static final Map<JTable, Integer> tableColumnWidths = new HashMap<>();

    private static <T extends JComponent> T setFixedSize(T comp, int width, int height) {
        Dimension d = new Dimension(width, height);
        comp.setMaximumSize(d);
        comp.setMinimumSize(d);
        comp.setPreferredSize(d);
        comp.setSize(d);
        return comp;
    }
    
    private static int getColumnWidth(JTable table) {
        Integer columnWidth = tableColumnWidths.get(table);
        if (columnWidth == null) {
            if (table.getColumnCount() > 0) {
                columnWidth = table.getWidth() / table.getColumnCount();
            } else {
                columnWidth = DEFAULT_COLUMN_WIDTH;
            }
        }
        return columnWidth;
    }
    
    private static void recalcJTableSize(JTable table) {
        int width = getColumnWidth(table) * table.getColumnCount();
        int height = 0, rowCount = table.getRowCount();
        for (int r = 0; r < rowCount; r++)
            height += table.getRowHeight(height);
        setFixedSize(table, width, height);
        
        if (table.getParent() instanceof JViewport && table.getParent().getParent() instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) table.getParent().getParent();
            if (scrollPane.getRowHeader() != null) {
                Component rowHeaderView = scrollPane.getRowHeader().getView();
                if (rowHeaderView instanceof JList) {
                    ((JList) rowHeaderView).setFixedCellHeight(table.getRowHeight());
                }
                scrollPane.getRowHeader().repaint();
            }
        }
    }

    private static void writeArrayToJTable(JTable table, Object array, String itemFormat) {
        if (!array.getClass().isArray()) {
            return;
        }
        if (!(table.getModel() instanceof DefaultTableModel)) {
            return;
        }
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
                
        tableColumnWidths.put(table, getColumnWidth(table));
        
        if (itemFormat == null || itemFormat.length() == 0) {
            itemFormat = "%s";
        }
        int rank = 1;
        int len1 = Array.getLength(array), len2 = -1;
        if (len1 > 0) {
            for (int i = 0; i < len1; i++) {
                Object item = Array.get(array, i);
                if (item != null && item.getClass().isArray()) {
                    rank = 2;
                    len2 = Math.max(Array.getLength(item), len2);
                }
            }
        }
        tableModel.setRowCount(rank == 1 ? 1 : len1);
        tableModel.setColumnCount(rank == 1 ? len1 : len2);
        for (int i = 0; i < len1; i++) {
            if (rank == 1) {
                tableModel.setValueAt(String.format(itemFormat, Array.get(array, i)), 0, i);
            } else {
                Object line = Array.get(array, i);
                if (line != null) {
                    if (line.getClass().isArray()) {
                        int lineLen = Array.getLength(line);
                        for (int j = 0; j < lineLen; j++) {
                            tableModel.setValueAt(String.format(itemFormat, Array.get(line, j)), i, j);
                        }
                    } else {
                        tableModel.setValueAt(String.format(itemFormat, Array.get(array, i)), 0, i);
                    }
                }
            }
        }
        recalcJTableSize(table);
    }

    public static void writeArrayToJTable(JTable table, int[] array) {
        writeArrayToJTable(table, array, "%d");
    }

    public static <T> T[][] readMatrixFromJTable(
        JTable table, Class<T> clazz, Function<String, ? extends T> converter,
        boolean errorIfEmptyCell, T emptyCellValue
    ) throws JTableUtilsException {
        TableModel tableModel = table.getModel();
        int rowCount = tableModel.getRowCount(), colCount = tableModel.getColumnCount();
        T[][] matrix = (T[][]) Array.newInstance(clazz, rowCount, colCount);
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                T value = null;
                Object obj = tableModel.getValueAt(r, c);
                if (obj == null || obj instanceof String && ((String) obj).trim().length() == 0) {
                    if (errorIfEmptyCell) {
                        throw new JTableUtilsException(String.format("Empty value on (%d, %d) cell", r, c));
                    } else {
                        value = emptyCellValue;
                    }
                } else {
                    value = converter.apply(obj.toString());
                }
                matrix[r][c] = value;
            }
        }
        return matrix;
    }

    public static <T> T[] readArrayFromJTable(
        JTable table, Class<T> clazz, Function<String, ? extends T> converter,
        boolean errorIfEmptyCell, T emptyCellValue
    ) throws JTableUtilsException {
        T[][] matrix = readMatrixFromJTable(table, clazz, converter, errorIfEmptyCell, emptyCellValue);
        return matrix[0];
    }

    public static int[][] readIntMatrixFromJTable(JTable table) throws ParseException {
        try {
            Integer[][] matrix = readMatrixFromJTable(table, Integer.class, Integer::parseInt, false, 0);
            return (int[][]) Arrays.stream(matrix).map(ArrayUtils::toPrimitive).toArray((n) -> new int[n][]);
        } catch (JTableUtilsException impossible) {
        }
        return null;
    }

    public static int[] readIntArrayFromJTable(JTable table) throws ParseException {
        try {
            return ArrayUtils.toPrimitive(readArrayFromJTable(table, Integer.class, Integer::parseInt, false, 0));
        } catch (JTableUtilsException impossible) {
        }
        return null;
    }
}
