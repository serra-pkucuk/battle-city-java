package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MapEditorPanel extends JPanel {
    private int[][] map = new int[15][20];
    private int currentBrush = 1; 

    private JPanel[] brushPanels = new JPanel[5];

    public MapEditorPanel() {
        this.setPreferredSize(new Dimension(950, 600));
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        // TOP PANEL
        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setBackground(Color.DARK_GRAY);
        topButtonPanel.setPreferredSize(new Dimension(950, 50));
        
        JButton saveButton = new JButton("💾 Save Map (Print to Console)");
        JButton backButton = new JButton("⬅️ Back to Main Menu");
        
        saveButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setFont(new Font("Arial", Font.BOLD, 13));

        saveButton.addActionListener(e -> saveMap());
        backButton.addActionListener(e -> goBackToMenu());

        topButtonPanel.add(saveButton);
        topButtonPanel.add(backButton);
        add(topButtonPanel, BorderLayout.NORTH);

        // CENTER PANEL: Grid
        JPanel gridPanel = new JPanel(new GridLayout(15, 20));
        gridPanel.setPreferredSize(new Dimension(800, 550));
        gridPanel.setBackground(Color.BLACK);

        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 20; col++) {
                final int finalRow = row;
                final int finalCol = col;
                JPanel cell = new JPanel();
                cell.setBackground(Color.BLACK);
                cell.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                
                MouseAdapter paintAdapter = new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        paintCell(cell, finalRow, finalCol);
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            paintCell(cell, finalRow, finalCol);
                        }
                    }
                };
                
                cell.addMouseListener(paintAdapter);
                cell.addMouseMotionListener(paintAdapter);
                gridPanel.add(cell);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // RIGHT PANEL
        JPanel palettePanel = new JPanel();
        palettePanel.setLayout(new BoxLayout(palettePanel, BoxLayout.Y_AXIS));
        palettePanel.setBackground(new Color(20, 20, 20)); 
        palettePanel.setPreferredSize(new Dimension(150, 550));
        palettePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel title = new JLabel("BRUSH SELECTION");
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        palettePanel.add(title);
        palettePanel.add(Box.createRigidArea(new Dimension(0, 25)));

        brushPanels[0] = createBrushItem("EMPTY (Eraser)", Color.BLACK, Color.WHITE, 0);
        brushPanels[1] = createBrushItem("BRICK WALL", new Color(153, 51, 0), Color.WHITE, 1);
        brushPanels[2] = createBrushItem("WATER BLOCK", Color.BLUE, Color.WHITE, 2);
        brushPanels[3] = createBrushItem("STEEL WALL", Color.LIGHT_GRAY, Color.BLACK, 3);
        brushPanels[4] = createBrushItem("BUSH", new Color(34, 139, 34), Color.WHITE, 4);

        for (JPanel panel : brushPanels) {
            palettePanel.add(panel);
            palettePanel.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        add(palettePanel, BorderLayout.EAST);
        updateBrushHighlights();
    }

    private void paintCell(JPanel cell, int row, int col) {
        map[row][col] = currentBrush;
        if (currentBrush == 0) cell.setBackground(Color.BLACK);
        else if (currentBrush == 1) cell.setBackground(new Color(153, 51, 0));
        else if (currentBrush == 2) cell.setBackground(Color.BLUE);
        else if (currentBrush == 3) cell.setBackground(Color.LIGHT_GRAY);
        else if (currentBrush == 4) cell.setBackground(new Color(34, 139, 34));
    }

    private JPanel createBrushItem(String text, Color bgColor, Color textColor, int type) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(bgColor);
        item.setMaximumSize(new Dimension(135, 40));
        item.setPreferredSize(new Dimension(135, 40));
        
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.BOLD, 11));
        item.add(label, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentBrush = type;
                updateBrushHighlights();
            }
        });
        
        return item;
    }

    private void updateBrushHighlights() {
        for (int i = 0; i < brushPanels.length; i++) {
            if (i == currentBrush) {
                brushPanels[i].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            } else {
                brushPanels[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            }
        }
    }

    private void saveMap() {
        System.out.println("\n--- NEW CUSTOM MAP CODE ---");
        System.out.println("int[][] customLevel = {");
        for (int row = 0; row < 15; row++) {
            System.out.print("    {");
            for (int col = 0; col < 20; col++) {
                System.out.print(map[row][col] + (col < 19 ? "," : ""));
            }
            System.out.println("}" + (row < 14 ? "," : ""));
        }
        System.out.println("};");
        System.out.println("---------------------------\n");
        
        JOptionPane.showMessageDialog(this, 
            "Map design successfully printed to Eclipse Console (System.out)!", 
            "Map Code Ready", JOptionPane.INFORMATION_MESSAGE);
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