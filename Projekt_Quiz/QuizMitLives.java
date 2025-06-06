package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Klasse für das Quiz mit Leben
public class QuizMitLives extends JFrame {

    // Innere Klasse zur Darstellung einer Frage
    private static class Frage {
        String frageText; // Der Text der Frage
        String[] antworten; // Die möglichen Antworten
        int richtigeAntwortIndex; // Der Index der richtigen Antwort

        // Konstruktor für die Frage
        Frage(String frageText, String[] antworten, int richtigeAntwortIndex) {
            this.frageText = frageText;
            this.antworten = antworten;
            this.richtigeAntwortIndex = richtigeAntwortIndex;
        }
    }

    private List<Frage> fragen; // Liste aller Fragen
    private int leben = 3; // Anzahl der Leben
    private int punkte = 0; // Aktuelle Punkte
    private Random random = new Random(); // Zufallsgenerator

    // UI Komponenten
    private JLabel frageLabel; // Label zur Anzeige der aktuellen Frage
    private JButton[] antwortButtons = new JButton[4]; // Buttons für die Antwortmöglichkeiten
    private JLabel lebenLabel; // Label zur Anzeige der verbleibenden Leben
    private JLabel punkteLabel; // Label zur Anzeige der aktuellen Punkte

    // Konstruktor für das Quiz mit Leben
    public QuizMitLives(String[][] fragenArray) {
        setTitle("Unendlichmodus"); // Titel des Fensters
        setSize(600, 400); // Größe des Fensters
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Schließt die Anwendung beim Schließen des Fensters
        setLocationRelativeTo(null); // Zentriert das Fenster auf dem Bildschirm

        // Initialisiert die Liste der Fragen aus dem übergebenen Array
        fragen = new ArrayList<>();
        for (String[] frageData : fragenArray) {
            String frageText = frageData[0];
            String[] antworten = {frageData[1], frageData[2], frageData[3], frageData[4]};
            int richtigeAntwortIndex = Integer.parseInt(frageData[5]);
            fragen.add(new Frage(frageText, antworten, richtigeAntwortIndex));
        }

        initUI(); // Initialisiert die Benutzeroberfläche
        ladeFrage(); // Lädt die erste Frage
    }

    // Methode zur Initialisierung der Benutzeroberfläche
    private void initUI() {
        setLayout(new BorderLayout(10, 10)); // Setzt das Layout des Hauptfensters

        // Oben: Panel für Leben und Punkte
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lebenLabel = new JLabel("Leben: ❤❤❤");
        lebenLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        punkteLabel = new JLabel("Punkte: 0");
        punkteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        topPanel.add(lebenLabel);
        topPanel.add(Box.createHorizontalStrut(20)); // Fügt horizontalen Abstand hinzu
        topPanel.add(punkteLabel);
        add(topPanel, BorderLayout.NORTH);

        // Mitte: Label zur Anzeige der aktuellen Frage
        frageLabel = new JLabel("Frage");
        frageLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        frageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frageLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Fügt einen Rand hinzu
        add(frageLabel, BorderLayout.CENTER);

        // Unten: Panel für die Antwort-Buttons
        JPanel antwortPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // 2x2 GridLayout für die Antwort-Buttons
        for (int i = 0; i < 4; i++) {
            JButton btn = new JButton();
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            final int idx = i;
            btn.addActionListener(e -> checkAntwort(idx)); // Fügt einen ActionListener hinzu, um die Antwort zu überprüfen
            antwortButtons[i] = btn;
            antwortPanel.add(btn);
        }
        antwortPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); // Fügt einen Rand hinzu
        add(antwortPanel, BorderLayout.SOUTH);
    }

    // Methode zum Laden einer zufälligen Frage
    private void ladeFrage() {
        if (leben <= 0) {
            zeigeEndeDialog(); // Zeigt den End-Dialog an, wenn keine Leben mehr übrig sind
            return;
        }

        Frage f = fragen.get(random.nextInt(fragen.size())); // Wählt eine zufällige Frage aus
        frageLabel.setText(f.frageText); // Setzt den Fragetext
        for (int i = 0; i < 4; i++) {
            antwortButtons[i].setText(f.antworten[i]); // Setzt die Antworttexte
            antwortButtons[i].setEnabled(true); // Aktiviert die Antwort-Buttons
            antwortButtons[i].setBackground(null); // Setzt den Hintergrund der Buttons zurück
        }
    }

    // Methode zur Überprüfung der Antwort
    private void checkAntwort(int index) {
        Frage f = fragen.get(random.nextInt(fragen.size())); // Wählt eine zufällige Frage aus
        boolean richtig = (index == f.richtigeAntwortIndex); // Überprüft, ob die Antwort richtig ist

        if (richtig) {
            punkte += 10; // Erhöht die Punkte bei richtiger Antwort
            punkteLabel.setText("Punkte: " + punkte); // Aktualisiert die Punkte-Anzeige
            JOptionPane.showMessageDialog(this, "Richtig! +10 Punkte 🎉"); // Zeigt eine Erfolgsmeldung an
        } else {
            leben--; // Verringert die Leben bei falscher Antwort
            updateLebenAnzeige(); // Aktualisiert die Leben-Anzeige
            JOptionPane.showMessageDialog(this, "Falsch! Du hast noch " + leben + " Leben."); // Zeigt eine Fehlermeldung an
            if (leben <= 0) {
                zeigeEndeDialog(); // Zeigt den End-Dialog an, wenn keine Leben mehr übrig sind
                return;
            }
        }
        ladeFrage(); // Lädt die nächste Frage
    }

    // Methode zur Aktualisierung der Leben-Anzeige
    private void updateLebenAnzeige() {
        StringBuilder herz = new StringBuilder("Leben: ");
        for (int i = 0; i < leben; i++) {
            herz.append("❤"); // Fügt ein Herz für jedes Leben hinzu
        }
        lebenLabel.setText(herz.toString()); // Aktualisiert das Label
    }

    // Methode zur Anzeige des End-Dialogs
    private void zeigeEndeDialog() {
        // Schließt das Quiz-Fenster und zeigt das Hauptmenü wieder an
        this.dispose();
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
    }
}
