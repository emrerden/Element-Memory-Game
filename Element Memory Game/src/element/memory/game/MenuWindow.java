
package element.memory.game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class MenuWindow extends JFrame {
    private ArrayList<Element> allElements;

    public MenuWindow(ArrayList<Element> elements) {
        this.allElements = elements;

        setTitle("Element Memory Game - Welcome");
        setSize(450, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. ÜST PANEL: Başlık ve Alt Başlık
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(41, 128, 185)); // Şık bir mavi
        headerPanel.setBorder(new EmptyBorder(30, 10, 30, 10));

        JLabel titleLabel = new JLabel("🧪 ELEMENT MEMORY", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel subTitle = new JLabel("Test your chemistry knowledge!", SwingConstants.CENTER);
        subTitle.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        subTitle.setForeground(new Color(236, 240, 241));

        headerPanel.add(titleLabel);
        headerPanel.add(subTitle);
        add(headerPanel, BorderLayout.NORTH);

        // 2. ORTA PANEL: Zorluk Seçimi
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(236, 240, 241)); // Açık gri/beyaz
        centerPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        JLabel selectLabel = new JLabel("Choose Difficulty");
        selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        selectLabel.setForeground(new Color(44, 62, 80));
        
        centerPanel.add(selectLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Butonları oluştur
        JButton easyBtn = createMenuButton("🌱 EASY", new Color(46, 204, 113));
        JButton medBtn = createMenuButton("⚡ MEDIUM", new Color(241, 196, 15));
        JButton hardBtn = createMenuButton("🔥 HARD", new Color(231, 76, 60));

        easyBtn.addActionListener(e -> startGame(4));
        medBtn.addActionListener(e -> startGame(8));
        hardBtn.addActionListener(e -> startGame(12));

        centerPanel.add(easyBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(medBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(hardBtn);

        add(centerPanel, BorderLayout.CENTER);

        // 3. ALT PANEL: Çıkış
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(236, 240, 241));
        JButton exitBtn = new JButton("Exit Game");
        exitBtn.setFocusable(false);
        exitBtn.addActionListener(e -> System.exit(0));
        footerPanel.add(exitBtn);
        add(footerPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Modern Buton Tasarımı için yardımcı metod
    private JButton createMenuButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(250, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusable(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Kenarlıkları kaldırıp biraz gölge hissi verme
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        
        return btn;
    }

    private void startGame(int pairCount) {
        if (allElements.size() < pairCount) {
            JOptionPane.showMessageDialog(this, "Not enough elements!");
            return;
        }
        Collections.shuffle(allElements);
        ArrayList<Element> selected = new ArrayList<>();
        for(int i = 0; i < pairCount; i++) {
            selected.add(allElements.get(i));
        }
        new GameWindow(selected, allElements);
        this.dispose();
    }
}