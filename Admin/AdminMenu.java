/*package Projekt_Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {
    private MainMenu mainMenu;

    public AdminMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Admin Menü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));

        JLabel title = new JLabel("Admin Menü");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(33, 37, 41));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);

        JButton addQuestionButton = createStyledButton("Frage hinzufügen", 400, 80);
        JButton updateQuestionButton = createStyledButton("Frage aktualisieren", 400, 80);
        JButton deleteQuestionButton = createStyledButton("Frage löschen", 400, 80);
        JButton viewUsersButton = createStyledButton("Benutzer anzeigen", 400, 80);
        JButton logoutButton = createStyledButton("Abmelden", 400, 80);

        addQuestionButton.addActionListener(e -> new AddQuestionDialog(AdminMenu.this));
        updateQuestionButton.addActionListener(e -> new UpdateQuestionDialog(AdminMenu.this));
        deleteQuestionButton.addActionListener(e -> new DeleteQuestionDialog(AdminMenu.this));
        viewUsersButton.addActionListener(e -> new ViewUsersDialog(AdminMenu.this, mainMenu.benutzerMap));
        logoutButton.addActionListener(e -> {
            dispose();
            mainMenu.zeigeLoginFenster();
        });

        buttonPanel.add(addQuestionButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(updateQuestionButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(deleteQuestionButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(viewUsersButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(logoutButton);

        JPanel centerButtonPanel = new JPanel(new GridBagLayout());
        centerButtonPanel.setBackground(Color.WHITE);
        centerButtonPanel.add(buttonPanel);

        mainPanel.add(centerButtonPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(width, height));
        button.setPreferredSize(new Dimension(width, height));
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
}
*/