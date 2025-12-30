
package element.memory.game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GameWindow extends JFrame {
    private GameEngine engine;
    private JLabel scoreLabel;
    private ElementButton firstClicked = null;
    private ElementButton secondClicked = null;
    private ArrayList<Element> currentElements; // Seçili elementler
    private ArrayList<Element> fullList; // Ana liste (Geri dönüş için)
    private Timer gameTimer; // Zamanlayıcı nesnesi
    private int secondsPassed = 0; // Geçen saniye
    private JLabel timerLabel; // Süreyi ekranda gösterecek etiket

    public GameWindow(ArrayList<Element> selected, ArrayList<Element> mainList) {
        this.currentElements = selected;
        this.fullList = mainList;
        this.engine = new GameEngine(selected);
        setupUI();
    }

    private void setupUI() {
        setTitle("Element Memory Game");
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // topPanel'i 1 satır 3 sütun yapıyoruz (Geri Dön, Hamle, Zaman)
JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 0)); 
topPanel.setBackground(new Color(41, 128, 185)); // Menüyle uyumlu mavi
topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

// 1. Geri Dön Butonu
JButton backBtn = new JButton("↩ Menu");
backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
backBtn.setFocusable(false);
backBtn.setBackground(new Color(236, 240, 241));
backBtn.addActionListener(e -> {
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Oyunun kaydedilmeyecek. Menüye dönmek istiyor musun?", 
        "Oyundan Çık", JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        if (gameTimer != null) gameTimer.stop(); // Zamanlayıcıyı durdur
        this.dispose(); // Mevcut pencereyi kapat
        new MenuWindow(fullList); // Menüye geri dön
    }
});

        // 2. Skor ve Zaman Etiketleri
        scoreLabel = new JLabel("Moves: 0", SwingConstants.CENTER);
        timerLabel = new JLabel("Time: 0s", SwingConstants.CENTER);

        scoreLabel.setForeground(Color.WHITE);
        timerLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        // Panelin içine ekle
        topPanel.add(backBtn);
        topPanel.add(scoreLabel);
        topPanel.add(timerLabel);

        add(topPanel, BorderLayout.NORTH);

        // GameWindow içindeki ilgili kısım:
        int count = currentElements.size() * 2;
        int rows, cols;

        if (count == 8) { // Easy: 2x4
        rows = 2; cols = 4;
        } else if (count == 16) { // Medium: 4x4
        rows = 4; cols = 4;
        } else if (count == 24) { // Hard: 4x6
        rows = 4; cols = 6;
        } else {
        rows = 4; cols = count / 4;
        }

        JPanel gamePanel = new JPanel(new GridLayout(rows, cols, 10, 10));

        
        ArrayList<String> labels = new ArrayList<>();
        for (Element e : currentElements) {
            labels.add(e.getName());
            labels.add(e.getSymbol());
        }
        Collections.shuffle(labels);

        for (String s : labels) {
        ElementButton btn = new ElementButton(s);
        btn.setBackground(getRandomColor()); // HER BUTONA RASTGELE PASTEL RENK
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // İnce kenarlık
        btn.addActionListener(e -> handlePress(btn));
        gamePanel.add(btn);
}

        add(gamePanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        
        // Her 1000 milisaniyede (1 saniye) bir çalışacak
        gameTimer = new Timer(1000, e -> {
            secondsPassed++;
            timerLabel.setText("Time: " + secondsPassed + "s");
        });
        gameTimer.start(); // Oyuna girer girmez süreyi başlat

        setVisible(true);
    }

    private void handlePress(ElementButton btn) {
        if (secondClicked != null || btn == firstClicked) return;

        if (firstClicked == null) {
            firstClicked = btn;
            btn.setBackground(Color.CYAN);
        } else {
            secondClicked = btn;
            btn.setBackground(Color.CYAN);

            if (engine.isMatch(firstClicked.getValue(), secondClicked.getValue())) {
                firstClicked.setBackground(Color.GREEN);
                secondClicked.setBackground(Color.GREEN);

                String fact = "";
                for (Element e : currentElements) {
                    if (e.getName().equals(firstClicked.getValue()) || e.getSymbol().equals(firstClicked.getValue())) {
                        fact = e.getFact();
                        break;
                    }
                }

                JOptionPane.showMessageDialog(this, "Match! Fact: " + fact);
                firstClicked.setVisible(false);
                secondClicked.setVisible(false);
                resetSelection();
                checkGameOver();
            } else {
                firstClicked.setBackground(Color.RED);
                secondClicked.setBackground(Color.RED);
                Timer t = new Timer(700, e -> {
                    if (firstClicked != null && secondClicked != null) {
                        firstClicked.setBackground(null);
                        secondClicked.setBackground(null);
                        resetSelection();
                    }
                });
                t.setRepeats(false);
                t.start();
            }
            scoreLabel.setText("Moves: " + engine.getMoves());
        }
    }

    private void resetSelection() {
        firstClicked = null;
        secondClicked = null;
    }

    private void checkGameOver() {
    if (engine.getPairsFound() == engine.getTotalPairs()) {
        gameTimer.stop(); // SÜREYİ DURDUR

        int response = JOptionPane.showConfirmDialog(this, 
            "Congratulations!\n" +
            "Moves: " + engine.getMoves() + "\n" +
            "Time: " + secondsPassed + " seconds\n\n" +
            "Back to Menu?", 
            "Victory!", JOptionPane.YES_NO_OPTION);
        
        if (response == JOptionPane.YES_OPTION) {
            this.dispose();
            new MenuWindow(fullList);
        } else {
            System.exit(0);
        }
    }
}
    
    private Color getRandomColor() {
    // Gözü yormayan pastel tonlar için (200-255 arası)
    int r = (int) (Math.random() * 55) + 200;
    int g = (int) (Math.random() * 55) + 200;
    int b = (int) (Math.random() * 55) + 200;
    return new Color(r, g, b);
}
}