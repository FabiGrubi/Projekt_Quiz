package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class MainMenu extends JFrame {

    private static Map<String, Benutzer> benutzerMap = new HashMap<>();
    private static final String DATEI = "benutzer.txt";
    private int punkte = 0;
    private String benutzername = "Unbekannt";
    private String anmeldedatum = "";

    private static final String[][] geoFragen = {
            {"Welcher ist der längste Fluss der Welt?", "Nil", "Amazonas", "Jangtse", "Mississippi"},
            {"Welcher ist der größte Ozean der Erde?", "Pazifik", "Atlantik", "Indischer Ozean", "Arktischer Ozean"},
            {"Wie heißt das größte Korallenriff der Welt?", "Great Barrier Reef", "Belize Barrier Reef", "Rotes Meer Riff", "Florida Reef"},
            {"Welches Land hat die meisten Nachbarländer?", "China", "Deutschland", "Russland", "Brasilien"},
            {"Wie heißt das höchste Gebirge der Erde?", "Himalaya", "Anden", "Alpen", "Rocky Mountains"},
            {"In welchem Land liegt der Mount Everest?", "Nepal", "China", "Indien", "Pakistan"},
            {"Welche ist die größte Insel der Welt?", "Grönland", "Australien", "Neuguinea", "Borneo"},
            {"Welche Wüste ist die größte Trockenwüste der Welt?", "Sahara", "Gobi", "Kalahari", "Atacama"},
            {"Welches Land hat die längste Küstenlinie?", "Kanada", "Russland", "Indonesien", "USA"},
            {"In welchem Land befindet sich die Atacama-Wüste?", "Chile", "Argentinien", "Peru", "Bolivien"},
            {"Wie heißt der tiefste Punkt der Erde (unter Wasser)?", "Marianengraben", "Atlantischer Graben", "Java-Graben", "Puerto-Rico-Graben"},
            {"Welches Land hat die meisten aktiven Vulkane?", "Indonesien", "Island", "Japan", "Philippinen"},
            {"Welches Gebirge trennt Europa und Asien?", "Ural", "Alpen", "Kaukasus", "Himalaya"},
            {"Welches Land hat als einziges eine Flagge, die nicht rechteckig oder quadratisch ist?", "Nepal", "Schweiz", "Bhutan", "Vatikanstadt"},
            {"Welches ist das bevölkerungsreichste Land Afrikas?", "Nigeria", "Ägypten", "Südafrika", "Äthiopien"},
            {"Welches Land hat die größte Fläche?", "Russland", "Kanada", "China", "USA"},
            {"Welcher Kontinent hat die meisten Länder?", "Afrika", "Europa", "Asien", "Südamerika"},
            {"Welches Land hat die größte Bevölkerung?", "China", "Indien", "USA", "Indonesien"},
            {"Welcher See ist der größte Süßwassersee der Welt (nach Fläche)?", "Oberer See", "Victoriasee", "Baikalsee", "Michigansee"},
            {"Welcher Kontinent ist der kleinste?", "Australien", "Europa", "Antarktika", "Südamerika"},
            {"Wie heißt die Hauptstadt von Kanada?", "Ottawa", "Toronto", "Montreal", "Vancouver"},
            {"Welches Land besteht aus über 17.000 Inseln?", "Indonesien", "Philippinen", "Japan", "Maldiven"},
            {"Welches europäische Land hat die meisten Einwohner?", "Deutschland", "Frankreich", "Italien", "Vereinigtes Königreich"},
            {"Welche Stadt liegt auf zwei Kontinenten?", "Istanbul", "Kairo", "Moskau", "Athen"},
            {"Welcher Fluss fließt durch Paris?", "Seine", "Thames", "Rhein", "Donau"},
            {"Welche Insel ist die größte im Mittelmeer?", "Kreta", "Sizilien", "Zypern", "Korsika"},
            {"Welches Land hat die meisten offiziellen Amtssprachen?", "Südafrika", "Indien", "Schweiz", "Kanada"},
            {"Welches Land hat die längste Grenze zu den USA?", "Kanada", "Mexiko", "Russland", "Guatemala"},
            {"Welche Stadt ist die größte der Welt nach Fläche?", "New York", "Tokyo", "Mexico City", "Sydney"},
            {"Welches Land hat die meisten Vulkane?", "Indonesien", "Japan", "Island", "Italien"},
            {"Welches Land hat die größte Zahl an UNESCO-Weltkulturerbestätten?", "Italien", "China", "Spanien", "Frankreich"}
    };

    public MainMenu() {
        super("Quiz");

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Bilder/AppIcon.png"));
            this.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Icon konnte nicht geladen werden.");
        }

        datenLaden();
        benutzerAnmeldung();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menü");
        JMenu Bestenliste = new JMenu("Bestenliste");
        JMenu shopMenu = new JMenu("Shop");
        JMenuItem shopItem = new JMenuItem("Shop öffnen");
        shopMenu.add(shopItem);
        menuBar.add(shopMenu);

        JMenuItem punkteItem = new JMenuItem("Punkte anzeigen");
        JMenuItem profilItem = new JMenuItem("Profil ansehen");
        JMenuItem besten = new JMenuItem("Bestenliste anzeigen");

        menu.add(punkteItem);
        menu.add(profilItem);
        menuBar.add(menu);
        Bestenliste.add(besten);
        menuBar.add(Bestenliste);
        setJMenuBar(menuBar);

        shopItem.addActionListener(e -> showShopFenster());
        besten.addActionListener(e -> showBestenliste());
        punkteItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Du hast aktuell " + punkte + " Punkte."));
        profilItem.addActionListener(e -> showProfilFenster());

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel title = new JLabel("Wähle ein Quiz-Thema", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JButton laenderButton = new JButton("Länder Quiz");
        this.add(laenderButton, gbc);
        laenderButton.setPreferredSize(new Dimension(200, 50));

        gbc.gridx = 1;
        JButton staedteButton = new JButton("Städte Quiz");
        this.add(staedteButton, gbc);
        staedteButton.setPreferredSize(new Dimension(200, 50));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton DailyQuiz = new JButton("Daily Quiz");
        this.add(DailyQuiz, gbc);
        DailyQuiz.setPreferredSize(new Dimension(200, 50));

        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        DailyQuiz.addActionListener(e -> DailyQuiz());
    }

    private void benutzerAnmeldung() {
        benutzername = JOptionPane.showInputDialog(null, "Bitte gib deinen Namen ein:", "Anmeldung", JOptionPane.QUESTION_MESSAGE);
        if (benutzername == null || benutzername.trim().isEmpty()) {
            benutzername = "Unbekannt";
        }

        if (benutzerMap.containsKey(benutzername)) {
            Benutzer b = benutzerMap.get(benutzername);
            punkte = b.getPunkte();
            anmeldedatum = b.getAnmeldedatum();
        } else {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            anmeldedatum = today.format(formatter);
            benutzerMap.put(benutzername, new Benutzer(benutzername, 0, anmeldedatum, "Neuling"));
        }
    }

    private void DailyQuiz() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < geoFragen.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        int richtigeAntworten = 0;
        for (int i = 0; i < 5 && i < indices.size(); i++) {
            int index = indices.get(i);
            String[] frage = geoFragen[index];
            List<String> antworten = new ArrayList<>(Arrays.asList(frage).subList(1, 5));
            Collections.shuffle(antworten);

            int auswahl = JOptionPane.showOptionDialog(null, frage[0], "Daily Quiz", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, antworten.toArray(), antworten.get(0));
            if (auswahl >= 0 && antworten.get(auswahl).equals(frage[1])) {
                JOptionPane.showMessageDialog(null, "Richtig!");
                richtigeAntworten++;
            } else if (auswahl >= 0) {
                JOptionPane.showMessageDialog(null, "Falsch! Richtige Antwort: " + frage[1]);
            }
        }

        pruefeErfolge();
        int punkteZuwachs = richtigeAntworten * 10;
        punkte += punkteZuwachs;
        JOptionPane.showMessageDialog(null, "Du hast " + richtigeAntworten + " von 5 richtig beantwortet! +" + punkteZuwachs + " Punkte");
        benutzerMap.get(benutzername).setPunkte(punkte);
        datenSpeichern();
    }

    private void showShopFenster() {
        JFrame shopFrame = new JFrame("Shop");
        shopFrame.setSize(300, 200);
        shopFrame.setLocationRelativeTo(this);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        String[] titelOptionen = {"Quiz-Anfänger (50 Punkte)", "Quiz-Experte (250 Punkte)", "Quiz-Meister (500 Punkte)", "Quiz-LEGENDE (999 Punkte)"};
        int[] titelKosten = {50, 250, 500, 999};

        for (int i = 0; i < titelOptionen.length; i++) {
            int kosten = titelKosten[i];
            String titelName = titelOptionen[i].split(" ")[0];
            JButton button = new JButton(titelOptionen[i]);
            button.addActionListener(e -> {
                Benutzer b = benutzerMap.get(benutzername);
                if (b.getTitel().equals(titelName)) {
                    JOptionPane.showMessageDialog(null, "Du hast diesen Titel bereits!");
                } else if (punkte >= kosten) {
                    punkte -= kosten;
                    b.setPunkte(punkte);
                    b.setTitel(titelName);
                    datenSpeichern();
                    JOptionPane.showMessageDialog(null, "Titel gekauft: " + titelName);
                    shopFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Nicht genug Punkte!");
                }
            });
            panel.add(button);
        }

        shopFrame.add(panel);
        shopFrame.setVisible(true);
    }

    private void showProfilFenster() {
        JFrame profilFrame = new JFrame("Mein Profil");
        profilFrame.setSize(300, 200);
        profilFrame.setLocationRelativeTo(this);

        JLabel nameLabel = new JLabel("Name: " + benutzername);
        JLabel punkteLabel = new JLabel("Punkte: " + punkte);
        JLabel datumLabel = new JLabel("Angemeldet seit: " + anmeldedatum);
        JLabel titelLabel = new JLabel("Titel: " + benutzerMap.get(benutzername).getTitel());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(nameLabel);
        panel.add(punkteLabel);
        panel.add(datumLabel);
        panel.add(titelLabel);
        profilFrame.add(panel);
        profilFrame.setVisible(true);
    }

    private void showBestenliste() {
        JFrame besteFrame = new JFrame("Bestenliste");
        besteFrame.setSize(400, 300);
        besteFrame.setLocationRelativeTo(this);

        List<Benutzer> benutzerListe = new ArrayList<>(benutzerMap.values());
        benutzerListe.sort((b1, b2) -> Integer.compare(b2.getPunkte(), b1.getPunkte()));
        StringBuilder bestenListeText = new StringBuilder("<html><h2>Bestenliste</h2>");
        int rank = 1;
        for (Benutzer b : benutzerListe) {
            bestenListeText.append(rank).append(". ").append(b.getName()).append(" - ").append(b.getPunkte()).append(" Punkte<br>");
            rank++;
        }
        bestenListeText.append("</html>");

        JLabel bestenListeLabel = new JLabel(bestenListeText.toString());
        besteFrame.add(bestenListeLabel);
        besteFrame.setVisible(true);
    }

    private void datenSpeichern() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATEI))) {
            for (Benutzer b : benutzerMap.values()) {
                writer.println(b.getName() + ";" + b.getPunkte() + ";" + b.getAnmeldedatum() + ";" + b.getTitel());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void datenLaden() {
        File file = new File(DATEI);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                String[] teile = zeile.split(";");
                if (teile.length >= 4) {
                    String name = teile[0];
                    int punkte = Integer.parseInt(teile[1]);
                    String datum = teile[2];
                    String titel = teile[3];
                    Benutzer b = new Benutzer(name, punkte, datum, titel);
                    benutzerMap.put(name, b);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ladeErfolge() {
        // Dummy-Methode
    }

    private void speichernErfolge() {
        // Dummy-Methode
    }

    private void pruefeErfolge() {
        // Dummy-Methode
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }

    static class Benutzer {
        private String titel;
        private String name;
        private int punkte;
        private String anmeldedatum;

        public Benutzer(String name, int punkte, String anmeldedatum, String titel) {
            this.name = name;
            this.punkte = punkte;
            this.anmeldedatum = anmeldedatum;
            this.titel = titel;
        }

        public String getTitel() {
            return titel;
        }

        public void setTitel(String titel) {
            this.titel = titel;
        }

        public String getName() {
            return name;
        }

        public int getPunkte() {
            return punkte;
        }

        public void setPunkte(int punkte) {
            this.punkte = punkte;
        }

        public String getAnmeldedatum() {
            return anmeldedatum;
        }
    }
}
