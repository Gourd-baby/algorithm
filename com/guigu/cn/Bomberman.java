package com.guigu.cn;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class Bomberman extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final int WIDTH = 480;
    private final int HEIGHT = 480;
    private final int BLOCK_SIZE = 32;
    private final int ROWS = HEIGHT / BLOCK_SIZE;
    private final int COLS = WIDTH / BLOCK_SIZE;
    private final int MAX_BOMBS = 10;
    private final int BOMB_RANGE = 2;
    private final int PLAYER_SPEED = 4;
    private int[][] map = new int[ROWS][COLS];
    private int[][] bombs = new int[MAX_BOMBS][3];
    private int playerRow = 1;
    private int playerCol = 1;
    private int playerDir = 0;
    private int numBombs = 0;
    private boolean gameOver = false;
    private JButton[][] blocks = new JButton[ROWS][COLS];
    private Random rand = new Random();
    private Timer timer = new Timer(50, this);
    public Bomberman() {
        setTitle("Bomberman");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        initMap();
        initBlocks();
        initPlayer();
        timer.start();
    }
    private void initMap() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i == 0 || j == 0 || i == ROWS - 1 || j == COLS - 1) {
                    map[i][j] = 1;
                } else if (i % 2 == 0 && j % 2 == 0) {
                    map[i][j] = 1;
                } else {
                    map[i][j] = rand.nextInt(3);
                }
            }
        }
    }
    private void initBlocks() {
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(ROWS, COLS));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                blocks[i][j] = new JButton();
                blocks[i][j].setPreferredSize(new Dimension(BLOCK_SIZE, BLOCK_SIZE));
                blocks[i][j].addActionListener(this);
                updateBlock(i, j);
                pane.add(blocks[i][j]);
            }
        }
    }
    private void initPlayer() {
        playerRow = 1;
        playerCol = 1;
        playerDir = 0;
        updateBlock(playerRow, playerCol);
    }
    private void updateBlock(int row, int col) {
        switch (map[row][col]) {
            case 0:
                blocks[row][col].setIcon(null);
                blocks[row][col].setBackground(Color.WHITE);
                break;
            case 1:
                blocks[row][col].setIcon(new ImageIcon("wall.png"));
                blocks[row][col].setBackground(Color.BLACK);
                break;
            case 2:
                blocks[row][col].setIcon(new ImageIcon("crate.png"));
                blocks[row][col].setBackground(Color.WHITE);
                break;
        }
    }
    private void movePlayer(int dir) {
        int newRow = playerRow;
        int newCol = playerCol;
        switch (dir) {
            case 0:
                newRow--;
                break;
            case 1:
                newCol++;
                break;
            case 2:
                newRow++;
                break;
            case 3:
                newCol--;
                break;
        }
        if (map[newRow][newCol] == 0) {
            playerRow = newRow;
            playerCol = newCol;
            playerDir = dir;
            updateBlock(playerRow, playerCol);
            updateBlock(playerRow - (playerDir == 2 ? -1 : playerDir == 0 ? 1 : 0), playerCol - (playerDir == 1 ? -1 : playerDir == 3 ? 1 : 0));
        }
    }
    private void placeBomb() {
        if (numBombs < MAX_BOMBS && map[playerRow][playerCol] != 1) {
            bombs[numBombs][0] = playerRow;
            bombs[numBombs][1] = playerCol;
            bombs[numBombs][2] = BOMB_RANGE;
            numBombs++;
            updateBlock(playerRow, playerCol);
        }
    }
    private void explodeBomb(int bomb) {
        for (int i = bombs[bomb][0] - bombs[bomb][2]; i <= bombs[bomb][0] + bombs[bomb][2]; i++) {
            if (i >= 0 && i < ROWS && map[i][bombs[bomb][1]] != 1) {
                map[i][bombs[bomb][1]] = 0;
                updateBlock(i, bombs[bomb][1]);
            }
        }
        for (int j = bombs[bomb][1] - bombs[bomb][2]; j <= bombs[bomb][1] + bombs[bomb][2]; j++) {
            if (j >= 0 && j < COLS && map[bombs[bomb][0]][j] != 1) {
                map[bombs[bomb][0]][j] = 0;
                updateBlock(bombs[bomb][0], j);
            }
        }
        if (bombs[bomb][0] == playerRow && bombs[bomb][1] == playerCol) {
            gameOver = true;
        }
        for (int i = bomb; i < numBombs - 1; i++) {
            bombs[i][0] = bombs[i + 1][0];
            bombs[i][1] = bombs[i + 1][1];
            bombs[i][2] = bombs[i + 1][2];
        }
        numBombs--;
    }
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            for (int i = 0; i < numBombs; i++) {
                bombs[i][2]--;
                if (bombs[i][2] < 0) {
                    explodeBomb(i);
                }
            }
            if (e.getSource() instanceof JButton) {
                JButton block = (JButton) e.getSource();
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        if (block == blocks[i][j]) {
                            if (i == playerRow && j == playerCol) {
                                placeBomb();
                            } else {
                                movePlayer(i < playerRow ? 0 : i > playerRow ? 2 : j > playerCol ? 1 : 3);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if (map[i][j] == 1) {
                        blocks[i][j].setIcon(new ImageIcon("wall.png"));
                    } else if (map[i][j] == 2) {
                        blocks[i][j].setIcon(new ImageIcon("crate.png"));
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Game Over!", "Bomberman", JOptionPane.INFORMATION_MESSAGE);
            timer.stop();
        }
    }
    public static void main(String[] args) {
        Bomberman game = new Bomberman();
        game.setVisible(true);
    }
}