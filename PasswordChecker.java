import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.*;

public class PasswordChecker extends JFrame {
    
    private JPasswordField passwordField;
    private JLabel strengthLabel;
    private JLabel scoreLabel;
    private JLabel suggestionsLabel;
    private final CustomProgressBar progressBar;
    private JCheckBox showPasswordCheckBox;
    private JLabel charCountLabel;
    private JTextArea requirementsArea;

    public PasswordChecker() {
        // Frame setup
        setTitle("Advanced Password Strength Checker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(createIcon());
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(15, 32, 56), 
                                                          0, getHeight(), new Color(32, 58, 89));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Input panel
        JPanel inputPanel = createInputPanel();
        
        // Score and strength panel
        JPanel scorePanel = createScorePanel();
        
        // Progress bar
        progressBar = new CustomProgressBar();
        
        // Requirements panel
        JPanel requirementsPanel = createRequirementsPanel();
        
        // Suggestions panel
        JPanel suggestionsPanel = createSuggestionsPanel();
        
        // Button panel
        JPanel buttonPanel = createButtonPanel();

        // Combine panels
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(inputPanel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(scorePanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(progressBar);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(requirementsPanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(suggestionsPanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(buttonPanel);

        // Scroll pane for center panel
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        
        // Add key listener for real-time feedback
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updatePasswordStrength();
            }
        });

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("🔐 Password Strength Analyzer");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Advanced Security Assessment Tool");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(180, 200, 220));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitleLabel);
        
        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel label = new JLabel("Enter Password:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(Color.WHITE);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        passwordField.setPreferredSize(new Dimension(0, 45));
        passwordField.setBackground(new Color(240, 245, 250));
        passwordField.setForeground(Color.BLACK);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 180, 255), 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(passwordField);
        
        return panel;
    }

    private JPanel createScorePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(new Color(25, 45, 70));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        topPanel.setOpaque(false);
        
        strengthLabel = new JLabel("No Password");
        strengthLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        strengthLabel.setForeground(new Color(150, 150, 150));
        
        scoreLabel = new JLabel("Score: 0%");
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        scoreLabel.setForeground(new Color(150, 150, 150));
        
        charCountLabel = new JLabel("Characters: 0");
        charCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        charCountLabel.setForeground(new Color(180, 200, 220));
        
        topPanel.add(strengthLabel);
        topPanel.add(scoreLabel);
        
        panel.add(topPanel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(charCountLabel);
        
        return panel;
    }

    private JPanel createRequirementsPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(new Color(32, 55, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel requiredLabel = new JLabel("✓ Requirements:");
        requiredLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        requiredLabel.setForeground(new Color(100, 200, 255));
        
        requirementsArea = new JTextArea();
        requirementsArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        requirementsArea.setEditable(false);
        requirementsArea.setLineWrap(true);
        requirementsArea.setWrapStyleWord(true);
        requirementsArea.setBackground(new Color(32, 55, 80));
        requirementsArea.setForeground(new Color(200, 220, 240));
        requirementsArea.setBorder(null);
        requirementsArea.setText("Length: ✗ | Upper: ✗ | Lower: ✗ | Digit: ✗ | Special: ✗");
        
        panel.add(requiredLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(requirementsArea);
        
        return panel;
    }

    private JPanel createSuggestionsPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(new Color(45, 65, 90));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel suggestionTitle = new JLabel("💡 Suggestions:");
        suggestionTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        suggestionTitle.setForeground(new Color(255, 200, 100));
        
        suggestionsLabel = new JLabel("Start typing to get suggestions");
        suggestionsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        suggestionsLabel.setForeground(new Color(200, 220, 240));
        
        panel.add(suggestionTitle);
        panel.add(Box.createVerticalStrut(8));
        panel.add(suggestionsLabel);
        
        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Show password checkbox
        JPanel togglePanel = new JPanel();
        togglePanel.setOpaque(false);
        togglePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setForeground(Color.WHITE);
        showPasswordCheckBox.setOpaque(false);
        showPasswordCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('●');
            }
        });
        
        togglePanel.add(showPasswordCheckBox);
        
        // Button panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton generateBtn = createButton("Generate Password", new Color(76, 175, 80));
        generateBtn.addActionListener(e -> generatePassword());
        
        JButton copyBtn = createButton("Copy to Clipboard", new Color(33, 150, 243));
        copyBtn.addActionListener(e -> copyToClipboard());
        
        JButton clearBtn = createButton("Clear", new Color(244, 67, 54));
        clearBtn.addActionListener(e -> {
            passwordField.setText("");
            updatePasswordStrength();
        });
        
        buttonsPanel.add(generateBtn);
        buttonsPanel.add(copyBtn);
        buttonsPanel.add(clearBtn);
        
        panel.add(togglePanel);
        panel.add(buttonsPanel);
        
        return panel;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bgColor.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    private void generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < 12; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        
        passwordField.setText(password.toString());
        updatePasswordStrength();
    }

    private void copyToClipboard() {
        String password = new String(passwordField.getPassword());
        if (!password.isEmpty()) {
            StringSelection stringSelection = new java.awt.datatransfer.StringSelection(password);
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            JOptionPane.showMessageDialog(this, "Password copied to clipboard!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a password first!", 
                "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updatePasswordStrength() {
        String password = new String(passwordField.getPassword());
        
        if (password.isEmpty()) {
            resetUI();
            return;
        }

        // Calculate score and check requirements
        int score = 0;
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        boolean hasConsecutive = false;

        int length = password.length();
        
        // Check character types
        for (int i = 0; i < length; i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else hasSpecial = true;
            
            // Check for consecutive characters
            if (i > 0 && password.charAt(i) == password.charAt(i - 1)) {
                hasConsecutive = true;
            }
        }

        // Score calculation
        score += Math.min(length / 2, 20); // Length score (max 20)
        if (hasUpper) score += 10;
        if (hasLower) score += 10;
        if (hasDigit) score += 10;
        if (hasSpecial) score += 20;
        if (length >= 12) score += 10;
        if (hasUpper && hasLower && hasDigit && hasSpecial) score += 10;
        
        if (hasConsecutive) score -= 5;
        score = Math.max(0, Math.min(100, score));

        // Update UI
        updateRequirements(hasUpper, hasLower, hasDigit, hasSpecial, length);
        updateStrengthLabel(score);
        updateSuggestions(hasUpper, hasLower, hasDigit, hasSpecial, length);
        
        scoreLabel.setText("Score: " + score + "%");
        charCountLabel.setText("Characters: " + length);
        progressBar.setProgress(score);
    }

    private void resetUI() {
        strengthLabel.setText("No Password");
        strengthLabel.setForeground(new Color(150, 150, 150));
        scoreLabel.setText("Score: 0%");
        scoreLabel.setForeground(new Color(150, 150, 150));
        charCountLabel.setText("Characters: 0");
        requirementsArea.setText("Length: ✗ | Upper: ✗ | Lower: ✗ | Digit: ✗ | Special: ✗");
        suggestionsLabel.setText("Start typing to get suggestions");
        progressBar.setProgress(0);
    }

    private void updateStrengthLabel(int score) {
        String strength;
        Color color;
        
        if (score >= 80) {
            strength = "🔒 Very Strong";
            color = new Color(76, 175, 80);
        } else if (score >= 60) {
            strength = "✓ Strong";
            color = new Color(56, 142, 60);
        } else if (score >= 40) {
            strength = "⚠ Medium";
            color = new Color(255, 193, 7);
        } else if (score >= 20) {
            strength = "✗ Weak";
            color = new Color(244, 67, 54);
        } else {
            strength = "✗✗ Very Weak";
            color = new Color(198, 40, 40);
        }
        
        strengthLabel.setText(strength);
        strengthLabel.setForeground(color);
        scoreLabel.setForeground(color);
    }

    private void updateRequirements(boolean hasUpper, boolean hasLower, boolean hasDigit, 
                                   boolean hasSpecial, int length) {
        String lengthStatus = length >= 8 ? "✓" : "✗";
        String upperStatus = hasUpper ? "✓" : "✗";
        String lowerStatus = hasLower ? "✓" : "✗";
        String digitStatus = hasDigit ? "✓" : "✗";
        String specialStatus = hasSpecial ? "✓" : "✗";
        
        requirementsArea.setText(String.format(
            "Length(8+): %s | Upper(A-Z): %s | Lower(a-z): %s | Digit(0-9): %s | Special(!@#): %s",
            lengthStatus, upperStatus, lowerStatus, digitStatus, specialStatus
        ));
    }

    private void updateSuggestions(boolean hasUpper, boolean hasLower, boolean hasDigit, 
                                  boolean hasSpecial, int length) {
        StringBuilder suggestions = new StringBuilder();
        
        if (length < 8) {
            suggestions.append("Add ").append(8 - length).append(" more characters | ");
        }
        if (!hasUpper) {
            suggestions.append("Include uppercase letters (A-Z) | ");
        }
        if (!hasLower) {
            suggestions.append("Include lowercase letters (a-z) | ");
        }
        if (!hasDigit) {
            suggestions.append("Include numbers (0-9) | ");
        }
        if (!hasSpecial) {
            suggestions.append("Include special characters (!@#$%^) | ");
        }
        
        if (suggestions.length() > 0) {
            suggestionsLabel.setText(suggestions.substring(0, suggestions.length() - 3));
        } else {
            suggestionsLabel.setText("✓ Great! Your password is excellent");
        }
    }

    private Image createIcon() {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(new Color(33, 150, 243));
        g2d.fillRect(0, 0, 16, 16);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.drawString("P", 3, 12);
        return img;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PasswordChecker::new);
    }

    // Custom Progress Bar
    private class CustomProgressBar extends JPanel {
        private int progress = 0;
        
        public CustomProgressBar() {
            setPreferredSize(new Dimension(0, 25));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            setOpaque(false);
        }
        
        public void setProgress(int value) {
            this.progress = Math.max(0, Math.min(100, value));
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Background
            RoundRectangle2D background = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 12, 12);
            g2d.setColor(new Color(50, 70, 95));
            g2d.fill(background);
            
            // Progress
            if (progress > 0) {
                Color color;
                if (progress >= 80) color = new Color(76, 175, 80);
                else if (progress >= 60) color = new Color(56, 142, 60);
                else if (progress >= 40) color = new Color(255, 193, 7);
                else color = new Color(244, 67, 54);
                
                double progressWidth = (double) progress / 100 * getWidth();
                RoundRectangle2D progressBar = new RoundRectangle2D.Double(0, 0, progressWidth, getHeight(), 12, 12);
                g2d.setColor(color);
                g2d.fill(progressBar);
            }
            
            // Border
            g2d.setColor(new Color(80, 100, 130));
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.draw(background);
        }
    }
}