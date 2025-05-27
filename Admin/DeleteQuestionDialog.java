/*package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteQuestionDialog extends JDialog {
    private AdminMenu adminMenu;

    public DeleteQuestionDialog(AdminMenu adminMenu) {
        this.adminMenu = adminMenu;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Frage löschen");
        setSize(500, 200);
        setLocationRelativeTo(adminMenu);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel quizTypeLabel = new JLabel("Quiz-Typ:");
        JComboBox<String> quizTypeComboBox = new JComboBox<>(new String[]{"Geo-Quiz", "Länder-Quiz", "Städte-Quiz"});

        JLabel indexLabel = new JLabel("Index der Frage:");
        JTextField indexField = new JTextField();

        JButton deleteButton = new JButton("Löschen");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        deleteButton.setBackground(new Color(0, 123, 255));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String quizType = (String) quizTypeComboBox.getSelectedItem();
                int index = Integer.parseInt(indexField.getText());

                switch (quizType) {
                    case "Geo-Quiz":
                        Fragen.deleteFrage(Fragen.geoFragen, index);
                        break;
                    case "Länder-Quiz":
                        Fragen.deleteFrage(Fragen.laenderFragen, index);
                        break;
                    case "Städte-Quiz":
                        Fragen.deleteFrage(Fragen.staedteFragen, index);
                        break;
                }

                JOptionPane.showMessageDialog(DeleteQuestionDialog.this, "Frage wurde gelöscht!");
                dispose();
            }
        });

        panel.add(quizTypeLabel);
        panel.add(quizTypeComboBox);
        panel.add(indexLabel);
        panel.add(indexField);
        panel.add(new JLabel());
        panel.add(deleteButton);

        add(panel);
        setVisible(true);
    }
}
*/
