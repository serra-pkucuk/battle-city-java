package gui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScorePanel extends JPanel {

    public ScorePanel() {
        this.setPreferredSize(new Dimension(950, 600));
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("🏆 TOP 10 HIGH SCORES 🏆", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 45));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel scoresContainer = new JPanel();
        scoresContainer.setBackground(Color.BLACK);
        scoresContainer.setLayout(new BoxLayout(scoresContainer, BoxLayout.Y_AXIS));

        JPanel headerPanel = new JPanel(new GridLayout(1, 4));
        headerPanel.setBackground(Color.DARK_GRAY);
        headerPanel.setMaximumSize(new Dimension(800, 40));
        headerPanel.add(createLabel("NAME", Color.WHITE, true));
        headerPanel.add(createLabel("DATE", Color.WHITE, true));
        headerPanel.add(createLabel("TIME", Color.WHITE, true));
        headerPanel.add(createLabel("SCORE", Color.WHITE, true));
        scoresContainer.add(headerPanel);
        scoresContainer.add(Box.createRigidArea(new Dimension(0, 15)));

        List<ScoreRecord> scoreList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("scores.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    scoreList.add(new ScoreRecord(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) { }

        Collections.sort(scoreList, (s1, s2) -> Integer.compare(s2.score, s1.score));

        int limit = Math.min(scoreList.size(), 10);
        if (limit == 0) {
            JLabel noScoreLabel = new JLabel("No saved scores found yet.", SwingConstants.CENTER);
            noScoreLabel.setForeground(Color.LIGHT_GRAY);
            noScoreLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
            scoresContainer.add(noScoreLabel);
        } else {
            for (int i = 0; i < limit; i++) {
                ScoreRecord r = scoreList.get(i);
                JPanel rowPanel = new JPanel(new GridLayout(1, 4));
                rowPanel.setBackground(Color.BLACK);
                rowPanel.setMaximumSize(new Dimension(800, 35));
                
                rowPanel.add(createLabel(r.name, Color.LIGHT_GRAY, false));
                rowPanel.add(createLabel(r.date, Color.LIGHT_GRAY, false));
                rowPanel.add(createLabel(r.time, Color.LIGHT_GRAY, false));
                rowPanel.add(createLabel(r.score + " Pts", Color.GREEN, false));
                
                scoresContainer.add(rowPanel);
            }
        }

        add(scoresContainer, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        JButton backButton = new JButton("⬅️ Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 15));
        backButton.setPreferredSize(new Dimension(250, 45));
        backButton.addActionListener(e -> goBackToMenu());
        
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text, Color color, boolean isHeader) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(new Font("Monospaced", isHeader ? Font.BOLD : Font.PLAIN, 20));
        return label;
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

    private static class ScoreRecord {
        String name, date, time;
        int score;
        public ScoreRecord(String name, String date, String time, int score) {
            this.name = name; this.date = date; this.time = time; this.score = score;
        }
    }
}