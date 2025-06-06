package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.Timer;

// Klasse für das Quiz-System
public class Quiz extends JFrame {
    private final String[][] fragen; // 2D-Array zur Speicherung der Fragen und Antworten
    private final MainMenu mainMenu; // Referenz auf das Hauptmenü
    private int frageIndex = 0; // Index der aktuellen Frage
    private int punkteProRichtigeAntwort = 10; // Punkte, die für jede richtige Antwort vergeben werden
    private int punkteImQuiz = 0; // Punkte, die im aktuellen Quiz erreicht wurden
    private boolean frageBeantwortet = false; // Flag, um zu überprüfen, ob die aktuelle Frage beantwortet wurde

    private JLabel frageLabel; // Label zur Anzeige der aktuellen Frage
    private JButton[] antwortButtons; // Array von Buttons für die Antwortmöglichkeiten
    private Timer timer; // Timer für die Zeitbegrenzung pro Frage
    private int zeitProFrage = 10; // Zeit in Sekunden, die für jede Frage zur Verfügung steht
    private JLabel timerLabel; // Label zur Anzeige der verbleibenden Zeit

    private int ausgewaehlteAntwort = -1; // Index der ausgewählten Antwort
    private List<Integer> frageSequenz; // Liste zur Speicherung der Reihenfolge der Fragen

    // Konstruktor für die Quiz-Klasse
    public Quiz(String[][] fragen, MainMenu mainMenu) {
        this.fragen = fragen;
        this.mainMenu = mainMenu;

        setTitle("Daily Quiz");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Initialisiert die Reihenfolge der Fragen und mischt sie
        frageSequenz = new ArrayList<>();
        for (int i = 0; i < fragen.length; i++) {
            frageSequenz.add(i);
        }
        Collections.shuffle(frageSequenz);

        // Initialisiert das Label für die Fragen
        frageLabel = new JLabel("", SwingConstants.CENTER);
        frageLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        frageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(frageLabel, BorderLayout.NORTH);

        // Initialisiert das Panel für die Antwort-Buttons
        JPanel antwortPanel = new JPanel();
        antwortPanel.setLayout(new GridLayout(2, 2, 20, 20));
        antwortPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        antwortPanel.setBackground(Color.WHITE);

        // Initialisiert die Antwort-Buttons
        antwortButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton();
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            btn.setBackground(new Color(230, 230, 230));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            int index = i;
            btn.addActionListener(e -> {
                if (timer != null) timer.stop(); // Stoppt den Timer, wenn eine Antwort ausgewählt wird
                if (frageBeantwortet) return; // Verhindert weitere Aktionen, wenn die Frage bereits beantwortet wurde
                ausgewaehlteAntwort = index;
                zeigeErgebnis(); // Zeigt das Ergebnis der Antwort an
            });
            antwortButtons[i] = btn;
            antwortPanel.add(btn);
        }
        add(antwortPanel, BorderLayout.CENTER);

        // Initialisiert das Label für den Timer
        timerLabel = new JLabel("Zeit: " + zeitProFrage + "s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(timerLabel, BorderLayout.SOUTH);

        ladeFrage(); // Lädt die erste Frage
        startTimer(); // Startet den Timer für die aktuelle Frage
    }

    // Lädt die aktuelle Frage und Antworten
    private void ladeFrage() {
        if (frageIndex >= frageSequenz.size()) return; // Beendet die Methode, wenn alle Fragen geladen wurden

        ausgewaehlteAntwort = -1; // Setzt die ausgewählte Antwort zurück
        int aktuelleFrage = frageSequenz.get(frageIndex);
        String[] frageSet = fragen[aktuelleFrage];

        frageLabel.setText("<html><div style='text-align:center;'>" + frageSet[0] + "</div></html>");

        // Antworten mischen (Positionen)
        List<String> antworten = new ArrayList<>(Arrays.asList(frageSet).subList(1, 5));
        Collections.shuffle(antworten);

        // Setzt die Antworten auf die Buttons
        for (int i = 0; i < 4; i++) {
            antwortButtons[i].setText(antworten.get(i));
            antwortButtons[i].setBackground(new Color(230, 230, 230, 68));
            antwortButtons[i].setEnabled(true);
        }

        timerLabel.setText("Zeit: " + zeitProFrage + "s"); // Setzt den Timer zurück
    }

    // Startet den Timer für die aktuelle Frage
    private void startTimer() {
        final int[] verbleibendeSekunden = {zeitProFrage};

        timer = new Timer(1000, e -> {
            verbleibendeSekunden[0]--;
            timerLabel.setText("Zeit: " + verbleibendeSekunden[0] + "s");
            if (verbleibendeSekunden[0] <= 0) {
                timer.stop(); // Stoppt den Timer, wenn die Zeit abgelaufen ist
                zeigeErgebnis(); // Zeigt das Ergebnis an, wenn die Zeit abgelaufen ist
            }
        });
        timer.start();
    }

    // Zeigt das Ergebnis der aktuellen Frage an
    private void zeigeErgebnis() {
        for (JButton btn : antwortButtons) {
            btn.setFocusable(false);
        }
        frageBeantwortet = true;
        int aktuelleFrage = frageSequenz.get(frageIndex);
        String[] frageSet = fragen[aktuelleFrage];
        String richtigeAntwort = frageSet[1];

        // Markiert die richtige Antwort grün
        for (JButton btn : antwortButtons) {
            if (btn.getText().equals(richtigeAntwort)) {
                btn.setBackground(new Color(144, 238, 144)); // Grün für die richtige Antwort
            }
        }

        // Überprüft, ob die ausgewählte Antwort richtig ist
        if (ausgewaehlteAntwort >= 0 && antwortButtons[ausgewaehlteAntwort].getText().equals(richtigeAntwort)) {
            punkteImQuiz += punkteProRichtigeAntwort;
            mainMenu.punkteHinzufuegen(punkteProRichtigeAntwort);
        } else if (ausgewaehlteAntwort >= 0) {
            antwortButtons[ausgewaehlteAntwort].setBackground(new Color(255, 160, 122)); // Rot für die falsche Antwort
        }

        // Timer für die Anzeige des Ergebnisses, bevor die nächste Frage geladen wird
        new Timer(2000, e -> {
            ((Timer) e.getSource()).stop();
            frageIndex++;
            frageBeantwortet = false;
            if (frageIndex < frageSequenz.size()) {
                for (JButton btn : antwortButtons) {
                    btn.setFocusable(false);
                }
                ladeFrage(); // Lädt die nächste Frage
                startTimer(); // Startet den Timer für die nächste Frage
            } else {
                zeigeErgebnisFenster(); // Zeigt das Ergebnis-Fenster an, wenn alle Fragen beantwortet wurden
            }
        }).start();
    }

    // Zeigt das Ergebnis-Fenster am Ende des Quiz an
    private void zeigeErgebnisFenster() {
        this.setVisible(false);
        JFrame ergebnisFrame = new JFrame("Quiz-Ergebnis");
        ergebnisFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ergebnisFrame.setSize(400, 250);
        ergebnisFrame.setLocationRelativeTo(null);
        ergebnisFrame.setLayout(new BorderLayout());
        ergebnisFrame.getContentPane().setBackground(Color.WHITE);

        // Zeigt die erreichten Punkte an
        JLabel ergebnisLabel = new JLabel("<html><div style='text-align: center;'>" +
                "<h1>Quiz beendet!</h1>" +
                "<p>Du hast im Quiz <b>" + punkteImQuiz + " Punkte</b> erreicht.</p>" +
                "</div></html>", SwingConstants.CENTER);
        ergebnisLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        ergebnisLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        ergebnisFrame.add(ergebnisLabel, BorderLayout.CENTER);

        // Button zum Zurückkehren zum Hauptmenü
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
