/*package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ViewUsersDialog extends JDialog {
    private AdminMenu adminMenu;
    private Map<String, MainMenu.Benutzer> benutzerMap;

    public ViewUsersDialog(AdminMenu adminMenu, Map<String, MainMenu.Benutzer> benutzerMap) {
        this.adminMenu = adminMenu;
        this.benutzerMap = benutzerMap;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Benutzer anzeigen");
        setSize(600, 400);
        setLocationRelativeTo(adminMenu);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea usersArea = new JTextArea();
        usersArea.setEditable(false);
        usersArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        StringBuilder sb = new StringBuilder();
        for (MainMenu.Benutzer benutzer : benutzerMap.values()) {
            sb.append("Name: ").append(benutzer.getName()).append("\n");
            sb.append("Anmeldedatum: ").append(benutzer.getAnmeldedatum()).append("\n");
            sb.append("Punkte: ").append(benutzer.getPunkte()).append("\n");
            sb.append("------------------------------\n");
        }

        usersArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(usersArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
*/