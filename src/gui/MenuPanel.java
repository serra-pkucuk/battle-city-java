package gui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel() {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JLabel titleLabel = new JLabel("BATTLE CITY");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 70));
        add(titleLabel, gbc);
        
        gbc.gridy++;
        JButton newGameButton = createMenuButton("NEW GAME");
        add(newGameButton, gbc);
        
        gbc.gridy++;
        JButton mapEditorButton = createMenuButton("MAP EDITOR");
        add(mapEditorButton, gbc);
        
        gbc.gridy++;
        JButton optionsButton = createMenuButton("OPTIONS");
        add(optionsButton, gbc);
        
        gbc.gridy++;
        JButton highScoresButton = createMenuButton("HIGH SCORES");
        add(highScoresButton, gbc);
        
        gbc.gridy++;
        JButton exitButton = createMenuButton("EXIT");
        add(exitButton, gbc);
        
        newGameButton.addActionListener(e -> {
            GameWindow topFrame = (GameWindow) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) topFrame.startGame();
        });
        
        mapEditorButton.addActionListener(e -> {
            GameWindow topFrame = (GameWindow) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.getContentPane().removeAll();
                topFrame.add(new MapEditorPanel(), BorderLayout.CENTER);
                topFrame.revalidate();
                topFrame.repaint();
            }
        });

       
        optionsButton.addActionListener(e -> {
            GameWindow topFrame = (GameWindow) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.getContentPane().removeAll();
                topFrame.add(new OptionsPanel(), BorderLayout.CENTER);
                topFrame.revalidate();
                topFrame.repaint();
            }
        });

        highScoresButton.addActionListener(e -> {
            GameWindow topFrame = (GameWindow) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.getContentPane().removeAll();
                topFrame.add(new ScorePanel(), BorderLayout.CENTER);
                topFrame.revalidate();
                topFrame.repaint();
            }
        });
        
        exitButton.addActionListener(e -> System.exit(0));
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }
}