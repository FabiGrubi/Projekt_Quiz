package Projekt_Quiz;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Quiz Auswahl");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        // App-Icon setzen
        URL imageURL = MainMenu.class.getResource("Bilder/Bild.jpg");
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            setIconImage(icon.getImage());
        } else {
            System.err.println("Couldn't find file: Bild.jpg");
        }

        JLabel info = new JLabel("Wähle ein Quiz-Thema", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.BOLD, 18));
        add(info);

        JButton clashRoyaleButton = new JButton("Clash Royale Quiz");
        clashRoyaleButton.addActionListener(e -> {
            dispose();
            new Quiz(getClashRoyaleFragen(), "Clash Royale Quiz");
        });
        add(clashRoyaleButton);

        JButton fortniteButton = new JButton("Fortnite Quiz");
        fortniteButton.addActionListener(e -> {
            dispose();
            new Quiz(getFortniteFragen(), "Fortnite Quiz");
        });
        add(fortniteButton);

        JButton minecraftButton = new JButton("Minecraft Quiz");
        minecraftButton.addActionListener(e -> {
            dispose();
            new Quiz(getMinecraftFragen(), "Minecraft Quiz");
        });
        add(minecraftButton);

        setVisible(true);
    }

    private ArrayList<Projekt_Quiz.Frage> getClashRoyaleFragen() {
        ArrayList<Projekt_Quiz.Frage> fragen = new ArrayList<>();

        try {
            BufferedImage myPicture = ImageIO.read(new File("Projekt_Quiz/Bilder/Bild.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setHorizontalAlignment(JLabel.CENTER);
            add(picLabel);
                setSize(600, 500);
        } catch (IOException e) {
            System.out.println("File not found!");
        }


        fragen.add(new Frage(
                "Wer ist für das Product Backlog verantwortlich?",
                new String[]{"Scrum Master", "Product Owner", "Team", "Manager"},
                1


        ));

        fragen.add(new Frage(
                "Was ist ein Scrum-Artefakt?",
                new String[]{"Sprint Goal", "Burnout", "Scrum Master", "Retrospektive"},
                0

        ));

        return fragen;
    }

    private ArrayList<Projekt_Quiz.Frage> getFortniteFragen() {
        ArrayList<Projekt_Quiz.Frage> fragen = new ArrayList<>();

        fragen.add(new Frage(
                "Was bedeutet WIP?",
                new String[]{"Work in Progress", "Workflow in Planung", "Weekly Iteration Plan", "Wissenschaft in Projekten"},
                0

        ));

        fragen.add(new Frage(
                "Wofür steht ein Kanban-Board?",
                new String[]{"Rollen", "Workflow", "Zeitplanung", "Risiken"},
                1
        ));

        fragen.add(new Frage(
                "Was ist kein Kanban-Prinzip?",
                new String[]{"WIP-Limit", "Flow steuern", "Feste Rollen", "Visualisieren"},
                2

        ));

        return fragen;
    }

    private ArrayList<Projekt_Quiz.Frage> getMinecraftFragen() {
        ArrayList<Projekt_Quiz.Frage> fragen = new ArrayList<>();

        fragen.add(new Frage(
                "Was betont das agile Manifest?",
                new String[]{"Verträge", "Individuen", "Prozesse", "Hierarchien"},
                1

        ));

        fragen.add(new Frage(
                "Welche Methode ist agil?",
                new String[]{"Scrum", "Wasserfall", "V-Modell", "Stage Gate"},
                0

        ));

        fragen.add(new Frage(
                "Wie reagieren agile Methoden auf Veränderungen?",
                new String[]{"Ignorieren sie", "Verzögern sie", "Planen sie", "Begrüßen sie"},
                3

        ));

        return fragen;
    }

    private ImageIcon ladeBild(String pfad) {
        URL bildURL = MainMenu.class.getResource(pfad);
        if (bildURL != null) {
            return new ImageIcon(bildURL);
        } else {
            System.err.println("Bild nicht gefunden: " + pfad);
            return null;
        }
    }
}
