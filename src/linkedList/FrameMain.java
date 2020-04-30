package linkedList;

import linkedList.util.ArrayUtils;
import linkedList.util.JTableUtils;
import linkedList.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static java.lang.Integer.parseInt;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTable table;
    private JButton buttonLoadFromFile;
    private JButton buttonSaveToFile;
    private JButton buttonAddLast;
    private JButton buttonDeleteFirst;
    private JButton buttonGetAtIndex;
    private JButton buttonClear;
    private JButton buttonReplace;
    private JButton buttonSet;
    private JTextField textFieldElement;
    private JTextField textFieldPosition;
    private JTextField textFieldAnswer;

    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;

    LinkedList list = new LinkedList();

    public FrameMain() {
        this.setTitle("Linked list");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        buttonLoadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[] listArr = ArrayUtils.readIntArrayFromFile(fileChooserOpen.getSelectedFile().getPath());
                        JTableUtils.writeArrayToJTable(table, listArr);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        buttonSaveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        int[][] matrix = JTableUtils.readIntMatrixFromJTable(table);
                        String file = fileChooserSave.getSelectedFile().getPath();
                        if (!file.toLowerCase().endsWith(".txt")) {
                            file += ".txt";
                        }
                        ArrayUtils.writeArrayToFile(file, matrix);
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        buttonReplace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int[] listArrIn = JTableUtils.readIntArrayFromJTable(table);
                    list.toLinkedList(listArrIn);
                    list.replaceFirstMinLastMax();
                    int[] listArrOut = list.toArray();
                    JTableUtils.writeArrayToJTable(table, listArrOut);
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int[] listArrIn = JTableUtils.readIntArrayFromJTable(table);
                    list.toLinkedList(listArrIn);
                    list.clear();
                    int[] listArrOut = list.toArray();
                    JTableUtils.writeArrayToJTable(table, listArrOut);
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        buttonSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int value = parseInt(textFieldElement.getText());
                    int index = parseInt(textFieldPosition.getText()) - 1;
                    list.set(value, index);
                    int[] listArrOut = list.toArray();
                    JTableUtils.writeArrayToJTable(table, listArrOut);
                    textFieldElement.setText(null);
                    textFieldPosition.setText(null);
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        buttonGetAtIndex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int index = parseInt(textFieldPosition.getText()) - 1;
                    int value = list.getAtIndex(index);
                    textFieldAnswer.setText(String.valueOf(value));
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        buttonDeleteFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int value = parseInt(textFieldElement.getText());
                    list.deleteFirst(value);
                    int[] listArrOut = list.toArray();
                    JTableUtils.writeArrayToJTable(table, listArrOut);
                    textFieldElement.setText(null);
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        buttonAddLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int value = parseInt(textFieldElement.getText());
                    list.addLast(value);
                    int[] listArrOut = list.toArray();
                    JTableUtils.writeArrayToJTable(table, listArrOut);
                    textFieldElement.setText(null);
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });
    }
}