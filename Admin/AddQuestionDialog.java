/*package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddQuestionDialog extends JDialog {
    private AdminMenu adminMenu;

    public AddQuestionDialog(AdminMenu adminMenu) {
        this.adminMenu = adminMenu;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Frage hinzufügen");
        setSize(500, 450);
        setLocationRelativeTo(adminMenu);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel quizTypeLabel = new JLabel("Quiz-Typ:");
        JComboBox<String> quizTypeComboBox = new JComboBox<>(new String[]{"Geo-Quiz", "Länder-Quiz", "Städte-Quiz"});

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

        JButton addButton = new JButton("Hinzufügen");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String quizType = (String) quizTypeComboBox.getSelectedItem();
                String question = questionField.getText();
                String optionA = optionAField.getText();
                String optionB = optionBField.getText();
                String optionC = optionCField.getText();
                String optionD = optionDField.getText();
                String correctAnswer = (String) correctAnswerComboBox.getSelectedItem();

                Frage neueFrage = new Frage(question, optionA, optionB, optionC, optionD, correctAnswer);

                switch (quizType) {
                    case "Geo-Quiz":
                        Fragen.addFrage(Fragen.geoFragen, neueFrage);
                        break;
                    case "Länder-Quiz":
                        Fragen.addFrage(Fragen.laenderFragen, neueFrage);
                        break;
                    case "Städte-Quiz":
                        Fragen.addFrage(Fragen.staedteFragen, neueFrage);
                        break;
                }

                JOptionPane.showMessageDialog(AddQuestionDialog.this, "Frage wurde hinzugefügt!");
                dispose();
            }
        });

        panel.add(quizTypeLabel);
        panel.add(quizTypeComboBox);
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
        panel.add(addButton);

        add(panel);
        setVisible(true);
    }
}
*/
