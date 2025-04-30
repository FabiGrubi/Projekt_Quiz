package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Quiz extends JFrame {
    private ArrayList<Quiz_Projekt.Frage> fragen;
    private int aktuelleFrage = 0;
    private int punkte = 0;
    private String quizTitel;

    private JLabel frageLabel;
    private JButton[] antwortButtons;

    public Quiz(ArrayList<Quiz_Projekt.Frage> fragen, String quizTitel) {
        this.fragen = fragen;
        this.quizTitel = quizTitel;

        setTitle(quizTitel);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        frageLabel = new JLabel("", SwingConstants.CENTER);
        frageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(frageLabel, BorderLayout.NORTH);

        JPanel antwortPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        antwortButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            final int index = i;
            antwortButtons[i] = new JButton();
            antwortButtons[i].addActionListener(e -> antwortAusgewählt(index));
            antwortPanel.add(antwortButtons[i]);
        }
        add(antwortPanel, BorderLayout.CENTER);

        frageAnzeigen();
        setVisible(true);
    }

    private void frageAnzeigen() {
        if (aktuelleFrage < fragen.size()) {
            Quiz_Projekt.Frage frage = fragen.get(aktuelleFrage);
            frageLabel.setText(frage.getFrageText());
            String[] antworten = frage.getAntworten();
            for (int i = 0; i < antwortButtons.length; i++) {
                antwortButtons[i].setText(antworten[i]);
                antwortButtons[i].setEnabled(true);
            }
        } else {
            ergebnisAnzeigen();
        }
    }

    private void antwortAusgewählt(int index) {
        if (fragen.get(aktuelleFrage).istRichtig(index)) {
            punkte++;
        }
        aktuelleFrage++;
        frageAnzeigen();
    }

    private void ergebnisAnzeigen() {
        dispose();

        JFrame ergebnis = new JFrame("Ergebnis");
        ergebnis.setSize(300, 200);
        ergebnis.setLocationRelativeTo(null);
        ergebnis.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ergebnis.setLayout(new BorderLayout());

        JLabel titel = new JLabel("Quiz beendet: " + quizTitel, SwingConstants.CENTER);
        titel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel label = new JLabel("Du hast " + punkte + " von " + fragen.size() + " Punkten erreicht.", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            ergebnis.dispose();
            new Quiz_Projekt.MainMenu();
        });

        ergebnis.add(titel, BorderLayout.NORTH);
        ergebnis.add(label, BorderLayout.CENTER);
        ergebnis.add(okButton, BorderLayout.SOUTH);

        ergebnis.setVisible(true);
    }
}
