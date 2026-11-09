package gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import engine.GamePanel;

public class GameWindow extends JFrame {

    private GamePanel gamePanel;
    public static int selectedLevel = 1; 

    public GameWindow() {
        setTitle("Battle City - Tank 1990");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        
        setJMenuBar(createMenuBar());
        
        MenuPanel menuPanel = new MenuPanel();
        menuPanel.setPreferredSize(new Dimension(950, 600));
        add(menuPanel, BorderLayout.CENTER);
        
        pack(); 
        setLocationRelativeTo(null);
    }

    public void startGame() {
        getContentPane().removeAll();
        
        gamePanel = new GamePanel(); 
        add(gamePanel, BorderLayout.CENTER);
        
        pack(); 
        setLocationRelativeTo(null);
        revalidate();
        repaint();
        
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
    }

    private void showHelp() {
        String helpText = "🎮 HOW TO PLAY? 🎮\n\n"
                + "• Movement: WASD or Arrow Keys.\n"
                + "• Shoot: SPACE bar.\n"
                + "• Main Goal: Protect the Eagle Base at the bottom!\n"
                + "• Win Condition: Destroy all enemy tanks.\n"
                + "• Lose Condition: Run out of lives or the Eagle is destroyed.";
        JOptionPane.showMessageDialog(this, helpText, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        String aboutText = "ℹ️ ABOUT THE APP ℹ️\n\n"
                + "Project Name: Battle City (Tank 1990) Clone\n"
                + "Course: CSE 212 — Software Development Methodologies\n"
                + "University: Yeditepe University\n\n"
                + "Developer Info:\n"
                + "• Name Surname: Serra Pehlivanküçük\n"
                + "• Student ID: 2024070211\n"
                + "• E-mail: serra.pehlivankucuk@std.yeditepe.edu.tr";
        JOptionPane.showMessageDialog(this, aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Menu");
        
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> startGame());
        
        JMenuItem pauseItem = new JMenuItem("Pause / Resume");
        pauseItem.addActionListener(e -> {
            if (gamePanel != null) {
                gamePanel.isPaused = !gamePanel.isPaused; 
            }
        });
        
        JMenuItem mapEditorItem = new JMenuItem("Map Editor");
        mapEditorItem.addActionListener(e -> {
            getContentPane().removeAll();
            add(new MapEditorPanel(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        
        JMenuItem optionsItem = new JMenuItem("Options");
        optionsItem.addActionListener(e -> {
            getContentPane().removeAll();
            add(new OptionsPanel(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        
        JMenuItem highScoresItem = new JMenuItem("High Scores");
        highScoresItem.addActionListener(e -> {
            getContentPane().removeAll();
            add(new ScorePanel(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(e -> showHelp());
        
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        
        gameMenu.add(newGameItem);
        gameMenu.add(pauseItem); 
        gameMenu.add(mapEditorItem);
        gameMenu.add(optionsItem);
        gameMenu.add(highScoresItem);
        gameMenu.add(helpItem);
        gameMenu.add(aboutItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        
        menuBar.add(gameMenu);
        return menuBar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}