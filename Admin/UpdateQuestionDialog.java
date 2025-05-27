/*package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateQuestionDialog extends JDialog {
    private AdminMenu adminMenu;

    public UpdateQuestionDialog(AdminMenu adminMenu) {
        this.adminMenu = adminMenu;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Frage aktualisieren");
        setSize(500, 450);
        setLocationRelativeTo(adminMenu);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel quizTypeLabel = new JLabel("Quiz-Typ:");
        JComboBox<String> quizTypeComboBox = new JComboBox<>(new String[]{"Geo-Quiz", "Länder-Quiz", "Städte-Quiz"});

        JLabel indexLabel = new JLabel("Index der Frage:");
        JTextField indexField = new JTextField();
        JLabel questionLabel = new JLabel("Frage:");
        JTextField questionField = new JTextField();
        JLabel optionALabel = new JLabel("Option A:");
        JTextField optionAField = new JTextField();
        JLabel optionBLabel = new JLabel("Option B:");
        JTextField optionBField = new JTextField();
        JLabel optionCLabel = new JLabel("Option C:");
        JTextField optionCField = new JTextField();
        JLabel optionDLabel = new JLabel("Option D:");
        JTextField optionDField = new JTextField();
        JLabel correctAnswerLabel = new JLabel("Richtige Antwort:");
        JComboBox<String> correctAnswerComboBox = new JComboBox<>(new String[]{"A", "B", "C", "D"});

        JButton updateButton = new JButton("Aktualisieren");
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        updateButton.setBackground(new Color(0, 123, 255));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String quizType = (String) quizTypeComboBox.getSelectedItem();
                int index = Integer.parseInt(indexField.getText());
                String question = questionField.getText();
                String optionA = optionAField.getText();
                String optionB = optionBField.getText();
                String optionC = optionCField.getText();
                String optionD = optionDField.getText();
                String correctAnswer = (String) correctAnswerComboBox.getSelectedItem();

                Frage aktualisierteFrage = new Frage(question, optionA, optionB, optionC, optionD, correctAnswer);

                switch (quizType) {
                    case "Geo-Quiz":
                        Fragen.updateFrage(Fragen.geoFragen, index, aktualisierteFrage);
                        break;
                    case "Länder-Quiz":
                        Fragen.updateFrage(Fragen.laenderFragen, index, aktualisierteFrage);
                        break;
                    case "Städte-Quiz":
                        Fragen.updateFrage(Fragen.staedteFragen, index, aktualisierteFrage);
                        break;
                }

                JOptionPane.showMessageDialog(UpdateQuestionDialog.this, "Frage wurde aktualisiert!");
                dispose();
            }
        });

        panel.add(quizTypeLabel);
        panel.add(quizTypeComboBox);
        panel.add(indexLabel);
        panel.add(indexField);
        panel.add(questionLabel);
        panel.add(questionField);
        panel.add(optionALabel);
        panel.add(optionAField);
        panel.add(optionBLabel);
        panel.add(optionBField);
        panel.add(optionCLabel);
        panel.add(optionCField);
        panel.add(optionDLabel);
        panel.add(optionDField);
        panel.add(correctAnswerLabel);
        panel.add(correctAnswerComboBox);
        panel.add(new JLabel());
        panel.add(updateButton);

        add(panel);
        setVisible(true);
    }
}
*/