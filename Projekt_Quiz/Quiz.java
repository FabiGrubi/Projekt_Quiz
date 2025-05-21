package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.Timer;


public class Quiz extends JFrame {
    private final String[][] fragen;
    private final MainMenu mainMenu;
    private int frageIndex = 0;
    private int punkteProRichtigeAntwort = 10;
    private int punkteImQuiz = 0;

    private JLabel frageLabel;
    private JButton[] antwortButtons;
    private Timer timer;
    private int zeitProFrage = 10; // Sekunden
    private JLabel timerLabel;

    private int ausgewaehlteAntwort = -1;
    private List<Integer> frageSequenz;

    public Quiz(String[][] fragen, MainMenu mainMenu) {
        this.fragen = fragen;
        this.mainMenu = mainMenu;

        setTitle("Daily Quiz");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        frageSequenz = new ArrayList<>();
        for (int i = 0; i < fragen.length; i++) {
            frageSequenz.add(i);
        }
        Collections.shuffle(frageSequenz);

        frageLabel = new JLabel("", SwingConstants.CENTER);
        frageLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        frageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(frageLabel, BorderLayout.NORTH);

        JPanel antwortPanel = new JPanel();
        antwortPanel.setLayout(new GridLayout(2, 2, 20, 20));
        antwortPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        antwortPanel.setBackground(Color.WHITE);

        antwortButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton();
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            btn.setBackground(new Color(230, 230, 230));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            final int index = i;
            btn.addActionListener(e -> {
                if (timer != null) timer.stop();
                ausgewaehlteAntwort = index;
                zeigeErgebnis();
            });
            antwortButtons[i] = btn;
            antwortPanel.add(btn);
        }
        add(antwortPanel, BorderLayout.CENTER);

        timerLabel = new JLabel("Zeit: " + zeitProFrage + "s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(timerLabel, BorderLayout.SOUTH);

        ladeFrage();
        startTimer();
    }

    private void ladeFrage() {
        if (frageIndex >= frageSequenz.size()) return;

        ausgewaehlteAntwort = -1;
        int aktuelleFrage = frageSequenz.get(frageIndex);
        String[] frageSet = fragen[aktuelleFrage];

        frageLabel.setText("<html><div style='text-align:center;'>" + frageSet[0] + "</div></html>");

        // Antworten mischen (Positionen)
        List<String> antworten = new ArrayList<>(Arrays.asList(frageSet).subList(1, 5));
        Collections.shuffle(antworten);

        for (int i = 0; i < 4; i++) {
            antwortButtons[i].setText(antworten.get(i));
            antwortButtons[i].setBackground(new Color(230, 230, 230));
            antwortButtons[i].setEnabled(true);
        }

        timerLabel.setText("Zeit: " + zeitProFrage + "s");
    }

    private void startTimer() {
        final int[] verbleibendeSekunden = {zeitProFrage};

        timer = new Timer(1000, e -> {
            verbleibendeSekunden[0]--;
            timerLabel.setText("Zeit: " + verbleibendeSekunden[0] + "s");
            if (verbleibendeSekunden[0] <= 0) {
                timer.stop();
                zeigeErgebnis();
            }
        });
        timer.start();
    }

    private void zeigeErgebnis() {
        for (JButton btn : antwortButtons) {
            btn.setEnabled(false);
        }

        int aktuelleFrage = frageSequenz.get(frageIndex);
        String[] frageSet = fragen[aktuelleFrage];
        String richtigeAntwort = frageSet[1];

        // Richtig/falsch markieren
        for (JButton btn : antwortButtons) {
            if (btn.getText().equals(richtigeAntwort)) {
                btn.setBackground(new Color(144, 238, 144)); // grün
            }
        }

        if (ausgewaehlteAntwort >= 0 && antwortButtons[ausgewaehlteAntwort].getText().equals(richtigeAntwort)) {
            punkteImQuiz += punkteProRichtigeAntwort;
            mainMenu.punkteHinzufuegen(punkteProRichtigeAntwort);
        } else if (ausgewaehlteAntwort >= 0) {
            antwortButtons[ausgewaehlteAntwort].setBackground(new Color(255, 160, 122)); // rot
        }

        new Timer(2000, e -> {
            ((Timer) e.getSource()).stop();
            frageIndex++;
            if (frageIndex < frageSequenz.size()) {
                ladeFrage();
                startTimer();
            } else {
                zeigeErgebnisFenster();
            }
        }).start();
    }

    private void zeigeErgebnisFenster() {
        this.setVisible(false);
        JFrame ergebnisFrame = new JFrame("Quiz-Ergebnis");
        ergebnisFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ergebnisFrame.setSize(400, 250);
        ergebnisFrame.setLocationRelativeTo(null);
        ergebnisFrame.setLayout(new BorderLayout());
        ergebnisFrame.getContentPane().setBackground(Color.WHITE);

        JLabel ergebnisLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<h1>Quiz beendet!</h1>" +
                "<p>Du hast im Quiz <b>" + punkteImQuiz + " Punkte</b> erreicht.</p>" +
                "</div></html>", SwingConstants.CENTER);
        ergebnisLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        ergebnisLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        ergebnisFrame.add(ergebnisLabel, BorderLayout.CENTER);

        JButton beendenButton = new JButton("Zurück zum Hauptmenü");
        beendenButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        beendenButton.setBackground(new Color(0, 123, 255));
        beendenButton.setForeground(Color.WHITE);
        beendenButton.setFocusPainted(false);
        beendenButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        beendenButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        beendenButton.addActionListener(e -> {
            ergebnisFrame.dispose();
            mainMenu.setVisible(true);
            this.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(beendenButton);
        ergebnisFrame.add(buttonPanel, BorderLayout.SOUTH);

        ergebnisFrame.setVisible(true);
    }
}
