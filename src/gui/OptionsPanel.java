package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OptionsPanel extends JPanel {

    private JPanel[] levelPanels = new JPanel[3];

    public OptionsPanel() {
        this.setPreferredSize(new Dimension(950, 600));
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        // TITLE
        JLabel titleLabel = new JLabel("⚙️ GAME OPTIONS ⚙️", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // CENTER
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel infoLabel = new JLabel("Select Starting Difficulty Level:", SwingConstants.CENTER);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(infoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        levelPanels[0] = createLevelItem("LEVEL 1 (EASY)", new Color(34, 139, 34), 1);
        levelPanels[1] = createLevelItem("LEVEL 2 (MEDIUM)", new Color(204, 102, 0), 2);
        levelPanels[2] = createLevelItem("LEVEL 3 (HARD)", new Color(153, 0, 0), 3);

        for (JPanel panel : levelPanels) {
            centerPanel.add(panel);
            centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        add(centerPanel, BorderLayout.CENTER);

        // BOTTOM
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        JButton backButton = new JButton("⬅️ Save Settings & Return");
        backButton.setFont(new Font("Arial", Font.BOLD, 15));
        backButton.setPreferredSize(new Dimension(260, 45));
        backButton.addActionListener(e -> goBackToMenu());

        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        updateHighlights();
    }

    private JPanel createLevelItem(String text, Color bgColor, int levelValue) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(bgColor);
        item.setMaximumSize(new Dimension(300, 50));
        item.setPreferredSize(new Dimension(300, 50));
        item.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        item.add(label, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                GameWindow.selectedLevel = levelValue; 
                updateHighlights();
            }
        });

        return item;
    }

    private void updateHighlights() {
        for (int i = 0; i < levelPanels.length; i++) {
            if ((i + 1) == GameWindow.selectedLevel) {
                levelPanels[i].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
            } else {
                levelPanels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            }
        }
    }

    private void goBackToMenu() {
        GameWindow topFrame = (GameWindow) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.getContentPane().removeAll();
            topFrame.add(new MenuPanel(), BorderLayout.CENTER);
            topFrame.revalidate();
            topFrame.repaint();
        }
    }
}