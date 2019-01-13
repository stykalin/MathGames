package ru.alstmedia.gui;

import ru.alstmedia.MazeApplication;
import ru.alstmedia.calculations.MathSign;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class SimpleGUI extends JFrame {

    private MathSign[] signs = new MathSign[]{MathSign.PLUS, MathSign.MINUS};

    private JButton button = new JButton("Сгенерировать");
    private JLabel resultNumberLabel = new JLabel("Результат:");
    private JTextField resultNumber = new JTextField(new Random().nextInt(50) + "");
    private JLabel signLabel = new JLabel("Операция:");
    private JComboBox<MathSign> signComboBox = new JComboBox<>(signs);

    public SimpleGUI() {
        super("MathMaze");
        this.setBounds(1400, 300, 400, 150);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        EmptyBorder emptyBorder = new EmptyBorder(10, 10, 10, 10);
        LineBorder lineBorder = new LineBorder(Color.BLACK, 2);
        resultNumberLabel.setBorder(emptyBorder);
//        resultNumber.setBorder(emptyBorder);
        resultNumber.setSize(30,15);
        signLabel.setBorder(emptyBorder);
        signComboBox.setBorder(emptyBorder);
        button.setBorder(BorderFactory.createCompoundBorder(lineBorder,emptyBorder));

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4, 3));
        container.add(resultNumberLabel);
        container.add(resultNumber);
        container.add(signLabel);
        signComboBox.setEditable(true);
        container.add(signComboBox);
        container.add(new JLabel());
        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    class ButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (resultNumber.getText().isEmpty()) {
                String message = "Поле 'Результат' пустое. Введите в поле значение результата выбранной операции.";
                JOptionPane.showMessageDialog(null, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    MazeApplication.generateAndOpenMaze((MathSign) signComboBox.getSelectedItem(), Integer.parseInt(resultNumber.getText()));
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    writeTextToClipboard(ex.toString() + "\n\n" + Arrays.toString(ex.getStackTrace()));
                    JOptionPane.showMessageDialog(null, ex.toString() + "\n Сообщение скопировано в буфер.",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
                JOptionPane.showMessageDialog(null, "Лабиринт успешно сгенерирован.", "Выполнено", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void writeTextToClipboard(String s) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(s);
        clipboard.setContents(transferable, null);
    }
}
