package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizMitLives extends JFrame {

    private static class Frage {
        String frageText;
        String[] antworten;
        int richtigeAntwortIndex;

        Frage(String frageText, String[] antworten, int richtigeAntwortIndex) {
            this.frageText = frageText;
            this.antworten = antworten;
            this.richtigeAntwortIndex = richtigeAntwortIndex;
        }
    }

    private List<Frage> fragen;
    private int leben = 3;
    private int punkte = 0;
    private Random random = new Random();

    // UI Komponenten
    private JLabel frageLabel;
    private JButton[] antwortButtons = new JButton[4];
    private JLabel lebenLabel;
    private JLabel punkteLabel;

    public QuizMitLives(String[][] fragenArray) {
        setTitle("Unendlichmodus");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        fragen = new ArrayList<>();
        for (String[] frageData : fragenArray) {
            String frageText = frageData[0];
            String[] antworten = {frageData[1], frageData[2], frageData[3], frageData[4]};
            int richtigeAntwortIndex = Integer.parseInt(frageData[5]);
            fragen.add(new Frage(frageText, antworten, richtigeAntwortIndex));
        }

        initUI();
        ladeFrage();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        // Oben: Leben und Punkte
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lebenLabel = new JLabel("Leben: ❤❤❤");
        lebenLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        punkteLabel = new JLabel("Punkte: 0");
        punkteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        topPanel.add(lebenLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(punkteLabel);
        add(topPanel, BorderLayout.NORTH);

        // Mitte: Frage
        frageLabel = new JLabel("Frage");
        frageLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        frageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(frageLabel, BorderLayout.CENTER);

        // Unten: Antwort-Buttons
        JPanel antwortPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton();
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            final int idx = i;
            btn.addActionListener(e -> checkAntwort(idx));
            antwortButtons[i] = btn;
            antwortPanel.add(btn);
        }
        antwortPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        add(antwortPanel, BorderLayout.SOUTH);
    }

    private void ladeFrage() {
        if (leben <= 0) {
            zeigeEndeDialog();
            return;
        }

        Frage f = fragen.get(random.nextInt(fragen.size()));
        frageLabel.setText(f.frageText);
        for (int i = 0; i < 4; i++) {
            antwortButtons[i].setText(f.antworten[i]);
            antwortButtons[i].setEnabled(true);
            antwortButtons[i].setBackground(null);
        }
    }

    private void checkAntwort(int index) {
        Frage f = fragen.get(random.nextInt(fragen.size()));
        boolean richtig = (index == f.richtigeAntwortIndex);

        if (richtig) {
            punkte += 10;
            punkteLabel.setText("Punkte: " + punkte);
            JOptionPane.showMessageDialog(this, "Richtig! +10 Punkte 🎉");
        } else {
            leben--;
            updateLebenAnzeige();
            JOptionPane.showMessageDialog(this, "Falsch! Du hast noch " + leben + " Leben.");
            if (leben <= 0) {
                zeigeEndeDialog();
                return;
            }
        }
        ladeFrage();
    }

    private void updateLebenAnzeige() {
        StringBuilder herz = new StringBuilder("Leben: ");
        for (int i = 0; i < leben; i++) {
            herz.append("❤");
        }
        lebenLabel.setText(herz.toString());
    }

    private void zeigeEndeDialog() {
        // Schließe das Quiz-Fenster und zeige das Hauptmenü wieder an
        this.dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }

}
