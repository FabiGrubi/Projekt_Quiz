package Projekt_Quiz;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class MainMenu extends JFrame {

    Map<String, Benutzer> benutzerMap = new HashMap<>();
    private static final String DATEI = "benutzer.txt";
    int punkte = 0;
    String benutzername = "";
    String anmeldedatum = "";
    private List<String> gekaufteTitel = new ArrayList<>();
    private String aktuellerTitel = "";
    private ImageIcon profilBild = null;

    public MainMenu() {
        try {
            // Setze das Look-and-Feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        datenLaden();
        zeigeLoginFenster();

        try {
            Image icon = ImageIO.read(getClass().getResource("/Bilder/AppIcon.png"));
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialisiereMainUI() {
        this.setTitle("Quiz - Geo-Quiz");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.getContentPane().setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(33, 37, 41));
        menuBar.setForeground(Color.BLACK);

        JMenu menu = new JMenu("Menü");
        menu.setForeground(Color.WHITE);
        JMenu Bestenliste = new JMenu("Bestenliste");
        Bestenliste.setForeground(Color.WHITE);
        JMenu shopMenu = new JMenu("Shop");
        shopMenu.setForeground(Color.WHITE);
        JMenu benutzerMenu = new JMenu("Benutzer");
        benutzerMenu.setForeground(Color.WHITE);

        JMenuItem shopItem = new JMenuItem("Shop öffnen");
        JMenuItem punkteItem = new JMenuItem("Punkte anzeigen");
        JMenuItem profilItem = new JMenuItem("Profil ansehen");
        JMenuItem besten = new JMenuItem("Bestenliste anzeigen");

        JMenuItem kontoLoeschen = new JMenuItem("Konto löschen");
        JMenuItem abmelden = new JMenuItem("Abmelden");
        JMenuItem neuerBenutzer = new JMenuItem("Neuer Benutzer");

        shopMenu.add(shopItem);
        menu.add(punkteItem);
        menu.add(profilItem);
        Bestenliste.add(besten);
        benutzerMenu.add(neuerBenutzer);
        benutzerMenu.add(abmelden);
        benutzerMenu.add(kontoLoeschen);

        menuBar.add(menu);
        menuBar.add(shopMenu);
        menuBar.add(Bestenliste);
        menuBar.add(benutzerMenu);
        setJMenuBar(menuBar);

        shopItem.addActionListener(e -> showShopFenster());
        besten.addActionListener(e -> showBestenliste());
        punkteItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Du hast aktuell " + punkte + " Punkte."));
        profilItem.addActionListener(e -> showProfilFenster());
        kontoLoeschen.addActionListener(e -> kontoLoeschen());
        abmelden.addActionListener(e -> {
            this.dispose();
            zeigeLoginFenster();
        });
        neuerBenutzer.addActionListener(e -> {
            this.dispose();
            zeigeLoginFenster();
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));

        JLabel title = new JLabel("Wähle ein Quiz-Thema");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(33, 37, 41));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        mainPanel.add(title);

        JButton laenderButton = createStyledButton("Länder Quiz");
        JButton staedteButton = createStyledButton("Städte Quiz");
        JButton dailyQuizButton = createStyledButton("Daily Quiz");

        mainPanel.add(laenderButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(staedteButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(dailyQuizButton);

        dailyQuizButton.addActionListener(e -> {
            if (benutzername.equals("Unbekannt") || benutzername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bitte zuerst anmelden!");
                return;
            }

            Benutzer benutzer = benutzerMap.get(benutzername);
            String heute = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            if (benutzer.getLetztesDailyDatum() != null && benutzer.getLetztesDailyDatum().equals(heute)) {
                JOptionPane.showMessageDialog(this, "Du hast das Daily Quiz heute bereits gespielt.\nKomm morgen wieder!");
                return;
            }

            benutzer.setLetztesDailyDatum(heute);
            speichereBenutzer();

            DailyQuiz();
        });

        laenderButton.addActionListener(e -> {
            if (benutzername.equals("Unbekannt") || benutzername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bitte zuerst anmelden!");
                return;
            }

            Benutzer benutzer = benutzerMap.get(benutzername);
            String heute = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            speichereBenutzer();

            LänderQuiz();
        });

        staedteButton.addActionListener(e -> {
            if (benutzername.equals("Unbekannt") || benutzername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bitte zuerst anmelden!");
                return;
            }

            Benutzer benutzer = benutzerMap.get(benutzername);
            String heute = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            speichereBenutzer();

            StädteQuiz();
        });

        this.add(mainPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(320, 60));
        button.setPreferredSize(new Dimension(320, 60));
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 105, 217));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255));
            }
        });
        return button;
    }

    private void zeigeLoginFenster() {
        JFrame loginFrame = new JFrame("Anmeldung");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridBagLayout());
        loginFrame.getContentPane().setBackground(new Color(245, 245, 245));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 320));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(true);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Willkommen beim Geo-Quiz");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(33, 37, 41));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        JTextArea subtitle = new JTextArea("Bitte gib deinen Benutzernamen und dein Passwort ein:");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(80, 80, 80));
        subtitle.setEditable(false);
        subtitle.setOpaque(false);
        subtitle.setWrapStyleWord(true);
        subtitle.setLineWrap(true);
        subtitle.setFocusable(false);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setMaximumSize(new Dimension(300, 50));
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Benutzername:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(33, 37, 41));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField(20);
        nameField.setMaximumSize(new Dimension(280, 35));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameField.setBackground(new Color(245, 245, 245));
        nameField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));

        JLabel passwortLabel = new JLabel("Passwort:");
        passwortLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwortLabel.setForeground(new Color(33, 37, 41));
        passwortLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passwortField = new JPasswordField(20);
        passwortField.setMaximumSize(new Dimension(280, 35));
        passwortField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwortField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwortField.setBackground(new Color(245, 245, 245));
        passwortField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));

        inputPanel.add(nameLabel);
        inputPanel.add(Box.createVerticalStrut(8));
        inputPanel.add(nameField);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(passwortLabel);
        inputPanel.add(Box.createVerticalStrut(8));
        inputPanel.add(passwortField);

        JButton loginButton = new JButton("Anmelden");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(280, 40));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JButton passwortVergessenButton = new JButton("Passwort vergessen");
        passwortVergessenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwortVergessenButton.setMaximumSize(new Dimension(280, 40));
        passwortVergessenButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwortVergessenButton.setBackground(new Color(220, 53, 69));
        passwortVergessenButton.setForeground(Color.WHITE);
        passwortVergessenButton.setFocusPainted(false);
        passwortVergessenButton.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        passwortVergessenButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        passwortVergessenButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(loginFrame, "Bitte wende dich an den Administrator, um dein Passwort zurückzusetzen.");
        });

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 105, 217));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 123, 255));
            }
        });

        loginButton.addActionListener(e -> {
            String eingegebenerName = nameField.getText().trim();
            String eingegebenesPasswort = new String(passwortField.getPassword());

            if (eingegebenerName.isEmpty() || eingegebenesPasswort.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame, "Bitte gib einen Benutzernamen und ein Passwort ein.");
            } else {
                benutzername = eingegebenerName;
                if (!benutzerMap.containsKey(benutzername)) {
                    anmeldedatum = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    benutzerMap.put(benutzername, new Benutzer(benutzername, anmeldedatum, 0, eingegebenesPasswort));
                    speichereBenutzer();
                    punkte = 0;
                } else {
                    Benutzer benutzer = benutzerMap.get(benutzername);
                    if (benutzer.getPasswort().equals(eingegebenesPasswort)) {
                        punkte = benutzer.getPunkte();
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Falsches Passwort!");
                        return;
                    }
                }
                loginFrame.dispose();
                initialisiereMainUI();
            }
        });

        nameField.addActionListener(e -> loginButton.doClick());
        passwortField.addActionListener(e -> loginButton.doClick());

        panel.add(titleLabel);
        panel.add(subtitle);
        panel.add(inputPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passwortVergessenButton);

        loginFrame.add(panel);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private void datenLaden() {
        try (BufferedReader br = new BufferedReader(new FileReader("benutzer.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    String name = parts[0];
                    String datum = parts[1];
                    int punkte = Integer.parseInt(parts[2]);
                    String passwort = parts[3];
                    Benutzer benutzer = new Benutzer(name, datum, punkte, passwort);
                    if (parts.length == 5) {
                        benutzer.setLetztesDailyDatum(parts[4]);
                    }
                    benutzerMap.put(name, benutzer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void speichereBenutzer() {
        try (PrintWriter pw = new PrintWriter("benutzer.txt")) {
            for (Benutzer benutzer : benutzerMap.values()) {
                pw.println(benutzer.getName() + ";" + benutzer.getAnmeldedatum() + ";" +
                        benutzer.getPunkte() + ";" + benutzer.getPasswort() + ";" + benutzer.getLetztesDailyDatum());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void punkteHinzufuegen(int punkte) {
        this.punkte += punkte;
        Benutzer benutzer = benutzerMap.get(benutzername);
        if (benutzer != null) {
            benutzer.setPunkte(benutzer.getPunkte() + punkte);
            speichereBenutzer();
        }
    }

    private void kontoLoeschen() {
        if (benutzerMap.containsKey(benutzername)) {
            int antwort = JOptionPane.showConfirmDialog(this, "Willst du wirklich deinen Account löschen?", "Account löschen",
                    JOptionPane.YES_NO_OPTION);
            if (antwort == JOptionPane.YES_OPTION) {
                benutzerMap.remove(benutzername);
                speichereBenutzer();
                JOptionPane.showMessageDialog(this, "Account wurde gelöscht.");
                this.dispose();
                zeigeLoginFenster();
            }
        }
    }

    private void showShopFenster() {
        JFrame shopFrame = new JFrame("🛍️ Titel-Shop");

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        device.setFullScreenWindow(shopFrame);
        shopFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel punktLabel = new JLabel("", SwingConstants.CENTER);
        punktLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        punktLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        shopFrame.add(punktLabel, BorderLayout.NORTH);

        Runnable updatePunkteLabel = () -> {
            String punktInfo = "Verfügbare Punkte: " + punkte + " | Aktueller Titel: " + (aktuellerTitel != null ? aktuellerTitel : "Keiner");
            punktLabel.setText(punktInfo);
        };
        updatePunkteLabel.run();

        String[] titelNamen = {"Fortgeschrittener", "Meister", "CEO", "GOAT"};
        int[] titelKosten = {50, 250, 500, 999};

        JPanel inhaltPanel = new JPanel();
        inhaltPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));
        inhaltPanel.setBackground(Color.WHITE);

        for (int i = 0; i < titelNamen.length; i++) {
            String titel = titelNamen[i];
            int kosten = titelKosten[i];

            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(300, 200));
            card.setLayout(new BorderLayout());
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            card.setBackground(new Color(245, 245, 245, 255));

            JLabel titelLabel = new JLabel(titel, SwingConstants.CENTER);
            titelLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

            JButton button = new JButton();
            button.setFont(new Font("SansSerif", Font.PLAIN, 18));
            if (gekaufteTitel.contains(titel)) {
                button.setText("✔️ Auswählen");
            } else {
                button.setText("💰 Kaufen für " + kosten + " Punkte");
            }

            button.addActionListener(e -> {
                if (gekaufteTitel.contains(titel)) {
                    aktuellerTitel = titel;
                    JOptionPane.showMessageDialog(shopFrame, "Titel \"" + titel + "\" wurde ausgewählt.");
                } else {
                    if (punkte >= kosten) {
                        punkte -= kosten;
                        Benutzer benutzer = benutzerMap.get(benutzername);
                        if (benutzer != null) {
                            benutzer.setPunkte(punkte);
                            speichereBenutzer();
                        }
                        gekaufteTitel.add(titel);
                        aktuellerTitel = titel;
                        JOptionPane.showMessageDialog(shopFrame, "Titel \"" + titel + "\" wurde gekauft und ausgewählt!");

                        shopFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(shopFrame, "❌ Nicht genug Punkte!");
                    }
                }
            });

            card.add(titelLabel, BorderLayout.CENTER);
            card.add(button, BorderLayout.SOUTH);

            inhaltPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(inhaltPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        shopFrame.add(scrollPane, BorderLayout.CENTER);

        JButton zurueckButton = new JButton("Zurück");
        zurueckButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        zurueckButton.addActionListener(e -> shopFrame.dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bottomPanel.add(zurueckButton);
        shopFrame.add(bottomPanel, BorderLayout.SOUTH);

        shopFrame.setVisible(true);
    }

    private void showBestenliste() {
        StringBuilder sb = new StringBuilder();
        List<Benutzer> liste = new ArrayList<>(benutzerMap.values());
        liste.sort((b1, b2) -> Integer.compare(b2.getPunkte(), b1.getPunkte()));
        int count = 1;
        sb.append("<html><body style='font-family:Segoe UI;'>");
        sb.append("<h2>Bestenliste</h2>");
        for (Benutzer b : liste) {
            sb.append(count).append(". ").append(b.getName()).append(" - ").append(b.getPunkte()).append(" Punkte<br>");
            count++;
            if (count > 10) break;
        }
        sb.append("</body></html>");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private ImageIcon makeRoundedImage(Image img, int diameter) {
        BufferedImage rounded = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = rounded.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new Ellipse2D.Double(0, 0, diameter, diameter));
        g2.drawImage(img, 0, 0, diameter, diameter, null);
        g2.dispose();
        return new ImageIcon(rounded);
    }

    private void showProfilFenster() {
        JFrame userFrame = new JFrame("Benutzerprofil");
        Benutzer benutzer = benutzerMap.get(benutzername);
        if (benutzer == null) return;

        userFrame.setUndecorated(true);
        userFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userFrame.getContentPane().setBackground(new Color(245, 247, 250));

        JLabel profilBildLabel = new JLabel();
        profilBildLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilBildLabel.setPreferredSize(new Dimension(200, 200));
        profilBildLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        if (profilBild != null) {
            profilBildLabel.setIcon(profilBild);
        } else {
            profilBildLabel.setText("Kein Profilbild");
            profilBildLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
        }

        JLabel infoLabel = new JLabel("<html><div style='text-align:center;'>"
                + "<h1 style='font-size:32px;'>Benutzerprofil</h1>"
                + "<p style='font-size:20px;'><b>Name:</b> " + benutzer.getName() + "<br>"
                + "<b>Anmeldedatum:</b> " + benutzer.getAnmeldedatum() + "<br>"
                + "<b>Punkte:</b> " + benutzer.getPunkte() + "<br>"
                + "<b>Titel:</b> " + (aktuellerTitel == null || aktuellerTitel.isEmpty() ? "Neuling" : aktuellerTitel)
                + "</p></div></html>");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 50, 30, 50));

        JButton profilbildButton = new JButton("Profilbild ändern");
        profilbildButton.setBackground(new Color(30, 144, 255));
        profilbildButton.setForeground(Color.WHITE);
        profilbildButton.setFocusPainted(false);
        profilbildButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        profilbildButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profilbildButton.setPreferredSize(new Dimension(200, 40));

        profilbildButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Wähle ein Profilbild");
            int result = fileChooser.showOpenDialog(userFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File ausgewählteDatei = fileChooser.getSelectedFile();
                try {
                    Image img = ImageIO.read(ausgewählteDatei);
                    profilBild = makeRoundedImage(img, 200);
                    profilBildLabel.setIcon(profilBild);
                    profilBildLabel.setText(null);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(userFrame, "Fehler beim Laden des Bildes!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton closeButton = new JButton("Schließen");
        closeButton.setBackground(new Color(220, 53, 69));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new Dimension(200, 40));
        closeButton.addActionListener(e -> userFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.add(profilbildButton);
        buttonPanel.add(closeButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 247, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        contentPanel.add(Box.createVerticalStrut(80));
        contentPanel.add(profilBildLabel);
        contentPanel.add(infoLabel);
        contentPanel.add(buttonPanel);

        userFrame.add(contentPanel);
        userFrame.setVisible(true);
    }

    private void DailyQuiz() {
        Quiz quiz = new Quiz(Fragen.geoFragen, this);
        quiz.setVisible(true);
        this.setVisible(false);
    }

    private void LänderQuiz() {
        Quiz quiz = new Quiz(Fragen.länderFragen, this);
        quiz.setVisible(true);
        this.setVisible(false);
    }

    private void StädteQuiz() {
        Quiz quiz = new Quiz(Fragen.städteFragen, this);
        quiz.setVisible(true);
        this.setVisible(false);
    }

    public class Benutzer {
        private String name;
        private String anmeldedatum;
        private int punkte;
        private String passwort;
        private String letztesDailyDatum;
        private String profilbildPfad;

        public String getProfilbildPfad() {
            return profilbildPfad;
        }

        public void setProfilbildPfad(String pfad) {
            this.profilbildPfad = pfad;
        }

        public Benutzer(String name, String anmeldedatum, int punkte, String passwort) {
            this.name = name;
            this.anmeldedatum = anmeldedatum;
            this.punkte = punkte;
            this.passwort = passwort;
            this.letztesDailyDatum = "";
        }

        public String getName() {
            return name;
        }

        public String getAnmeldedatum() {
            return anmeldedatum;
        }

        public int getPunkte() {
            return punkte;
        }

        public void setPunkte(int punkte) {
            this.punkte = punkte;
        }

        public String getPasswort() {
            return passwort;
        }

        public void setPasswort(String passwort) {
            this.passwort = passwort;
        }

        public String getLetztesDailyDatum() {
            return letztesDailyDatum;
        }

        public void setLetztesDailyDatum(String datum) {
            this.letztesDailyDatum = datum;
        }
    }
}
