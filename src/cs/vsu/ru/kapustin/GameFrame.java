package cs.vsu.ru.kapustin;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.HashMap;

public class GameFrame extends JFrame {
    private JTable gameTable;
    private JPanel gamePanel;
    private final int CELL_SIZE = 140;

    private final HashMap<Integer, Color> colorMap = new HashMap<>() {{
        put(0, new Color(191, 177, 160));
        put(2, new Color(220, 205, 183));
        put(4, new Color(163, 176, 138));
        put(8, new Color(255, 248, 159));
        put(16, new Color(220, 255, 165));
        put(32, new Color(255, 200, 115));
        put(64, new Color(255, 147, 135));
        put(128, new Color(255, 255, 0));
        put(256, new Color(255, 117, 247));
        put(512, new Color(0, 211, 255));
        put(1024, new Color(131, 59, 176));
        put(2048, new Color(48, 115, 137));
        put(4096, new Color(79, 0, 176));
        put(8192, new Color(26, 176, 0));
        put(16384, new Color(52, 159, 106));
        put(32768, new Color(0, 151, 255));
        put(65536, new Color(56, 65, 255));
        put(131072, new Color(176, 0, 176));
    }};

    private final Game game = new Game();
    private boolean isGameEnd = false;
    private boolean shouldContinueGame;

    public GameFrame() {
        this.setTitle("2048");
        this.setContentPane(gamePanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(672, 250, CELL_SIZE * 4, CELL_SIZE * 4);
        this.pack();

        DefaultTableModel tableModel = new DefaultTableModel(4, 4);
        gameTable.setModel(tableModel);
        gameTable.setRowHeight(CELL_SIZE);
        gameTable.setShowGrid(false);
        gameTable.getTableHeader().setPreferredSize(new Dimension(0, 0));

        game.generateStartingArray();
        int[][] arr = game.getTileValues();

        gameTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final class DrawComponent extends Component {
                private int row = 0, column = 0;

                @Override
                public void paint(Graphics gr) {
                    Graphics2D g2d = (Graphics2D) gr;
                    int width = getWidth() - 2;
                    int height = getHeight() - 2;
                    paintTile(row, column, g2d, width, height, arr);
                }
            }

            final DrawComponent comp = new DrawComponent();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                comp.row = row;
                comp.column = column;
                return comp;
            }
        });

        gameTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int[][] oldArr = Utils.copyIntMatrix(game.getTileValues());

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    game.moveTilesUp(null);
                    game.mergeAfterUpShift(null);
                    game.moveTilesUp(null);
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    game.moveTilesDown(null);
                    game.mergeAfterDownShift(null);
                    game.moveTilesDown(null);
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.moveTilesRight(null);
                    game.mergeAfterRightShift(null);
                    game.moveTilesRight(null);
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.moveTilesLeft(null);
                    game.mergeAfterLeftShift(null);
                    game.moveTilesLeft(null);
                }
                updateGame(oldArr);
            }
        });
    }

    private void paintTile(int row, int column, Graphics2D g2d, int tileWidth, int tileHeight, int[][] arr) {
        Color color = colorMap.get(arr[row][column]);

        int tileSize = Math.min(tileWidth, tileHeight);
        int bound = (int) Math.round(tileSize * 0.1) / 2;

        g2d.setColor(color);
        g2d.fillRect(bound, bound, tileSize - 2 * bound, tileSize - 2 * bound);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(bound, bound, tileSize - 2 * bound, tileSize - 2 * bound);

        int tileValue = arr[row][column];
        if (tileValue != 0) {
            int size;

            if (tileValue < 1024) {
                size = 60;
            } else if (tileValue < 16384) {
                size = 50;
            } else if (tileValue < 131072) {
                size = 40;
            } else {
                size = 35;
            }

            FontMetrics fm = g2d.getFontMetrics(new Font("Dialog", Font.BOLD, size));
            Rectangle2D rect = fm.getStringBounds(String.valueOf(tileValue), g2d);

            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Dialog", Font.BOLD, size));

            int x = (int) ((tileWidth - rect.getWidth()) / 2);
            int y = (int) ((tileHeight - rect.getHeight()) / 2);

            g2d.drawString(String.valueOf(tileValue), x, y + size);
        }
    }

    private void updateView() {
        gameTable.repaint();
    }

    private void updateGame(int[][] oldArr) {
        updateView();

        if (game.isWin(2048) && !shouldContinueGame) {
            isGameEnd = true;
            shouldContinueGame = Messages.shouldContinueGame();
        }

        if (game.isWin(131072)) {
            Messages.showMessage(2);
            dispose();
        }

        if (isGameEnd && !shouldContinueGame) {
            dispose();
        }

        if (!Arrays.deepEquals(oldArr, game.getTileValues())) {
            game.addTile();
            updateView();
        }

        if (game.isLose()) {
            Messages.showMessage(1);
            dispose();
        }
    }
}
