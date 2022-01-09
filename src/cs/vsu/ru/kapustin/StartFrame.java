package cs.vsu.ru.kapustin;

import javax.swing.*;

public class StartFrame extends JFrame {
    private JButton startButton;
    private JButton rulesButton;
    private JButton exitButton;
    private JPanel startPanel;

    public StartFrame() {
        this.setTitle("Game");
        this.setContentPane(startPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(700, 250, 400, 350);
        this.setResizable(false);
        this.pack();

        initButtons(new JButton[] {startButton, rulesButton, exitButton});

        startButton.addActionListener(e -> {
            dispose();
            new GameFrame().setVisible(true);
        });

        rulesButton.addActionListener(e -> Messages.showMessage(0));

        exitButton.addActionListener(e -> dispose());
    }

    private void initButtons(JButton[] buttons) {
        for (JButton button : buttons) {
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
        }
    }
}
