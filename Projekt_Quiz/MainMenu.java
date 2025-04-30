package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Quiz Auswahl");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel info = new JLabel("Wähle ein Quiz-Thema", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.BOLD, 18));
        add(info);

        JButton scrumButton = new JButton("Scrum Quiz");
        scrumButton.addActionListener(e -> {
            dispose();
            new Quiz(getScrumFragen(), "Scrum Quiz");
        });
        add(scrumButton);

        JButton kanbanButton = new JButton("Kanban Quiz");
        kanbanButton.addActionListener(e -> {
            dispose();
            new Quiz(getKanbanFragen(), "Kanban Quiz");
        });
        add(kanbanButton);

        JButton agileButton = new JButton("Agile Methoden Quiz");
        agileButton.addActionListener(e -> {
            dispose();
            new Quiz(getAgileFragen(), "Agile Methoden Quiz");
        });
        add(agileButton);

        setVisible(true);
    }

    private ArrayList<Projekt_Quiz.Frage> getScrumFragen() {
        ArrayList<Projekt_Quiz.Frage> fragen = new ArrayList<>();
        fragen.add(new Projekt_Quiz.Frage(
                "Wie lange dauert ein Sprint maximal?",
                new String[]{"1 Woche", "2 Wochen", "4 Wochen", "6 Wochen"},
                2,
                "Projekt_Quiz/Bild.jpg"
        ));

        fragen.add(new Projekt_Quiz.Frage("Wer ist für das Product Backlog verantwortlich?", new String[]{"Scrum Master", "Product Owner", "Team", "Manager"}, 1));
        fragen.add(new Projekt_Quiz.Frage("Was ist ein Scrum-Artefakt?", new String[]{"Sprint Goal", "Burnout", "Scrum Master", "Retrospektive"}, 0));
        return fragen;
    }

    private ArrayList<Projekt_Quiz.Frage> getKanbanFragen() {
        ArrayList<Projekt_Quiz.Frage> fragen = new ArrayList<>();
        fragen.add(new Projekt_Quiz.Frage("Was bedeutet WIP?", new String[]{"Work in Progress", "Workflow in Planung", "Weekly Iteration Plan", "Wissenschaft in Projekten"}, 0));
        fragen.add(new Projekt_Quiz.Frage("Wofür steht ein Kanban-Board?", new String[]{"Rollen", "Workflow", "Zeitplanung", "Risiken"}, 1));
        fragen.add(new Projekt_Quiz.Frage("Was ist kein Kanban-Prinzip?", new String[]{"WIP-Limit", "Flow steuern", "Feste Rollen", "Visualisieren"}, 2));
        return fragen;
    }

    private ArrayList<Projekt_Quiz.Frage> getAgileFragen() {
        ArrayList<Projekt_Quiz.Frage> fragen = new ArrayList<>();
        fragen.add(new Projekt_Quiz.Frage("Was betont das agile Manifest?", new String[]{"Verträge", "Individuen", "Prozesse", "Hierarchien"}, 1));
        fragen.add(new Projekt_Quiz.Frage("Welche Methode ist agil?", new String[]{"Scrum", "Wasserfall", "V-Modell", "Stage Gate"}, 0));
        fragen.add(new Projekt_Quiz.Frage("Wie reagieren agile Methoden auf Veränderungen?", new String[]{"Ignorieren sie", "Verzögern sie", "Planen sie", "Begrüßen sie"}, 3));
        return fragen;
    }
}
