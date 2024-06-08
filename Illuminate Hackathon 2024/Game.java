import javax.swing.*;
import java.awt.event.*;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;


public class Game extends JPanel implements ActionListener, KeyListener{
    private class Block {
        int x;
        int y;

        Block(int xvalue, int yvalue) {
            x = xvalue;
            y = yvalue;
        }
    }
    int gameHeight;
    int gameWidth;
    int blockSize = 40;

    Block adventurer;
    Block killBlock;

    Timer scoreboard;

    int velocityX;
    int velocityY;
    int levelNumber;

    Random random;

    boolean gameRunning = true;

    Game(int width, int height) {
        gameWidth = width;
        gameHeight = height;
        setPreferredSize(new Dimension(gameWidth, gameHeight));
        setBackground(Color.white);
        addKeyListener(this);
        setFocusable(true);

        adventurer = new Block(10, 10);
        killBlock = new Block(15, 15);

        random = new Random();

        velocityX = 0;
        velocityY = 0;
        levelNumber = 1;

        scoreboard = new Timer(100, this);
        scoreboard.start();
    }

    public void paintComponent(Graphics a) {
        super.paintComponent(a);
        draw(a);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < gameWidth/gameHeight; i++) {
            g.drawLine(i*blockSize, 0, i*blockSize, gameHeight);
            g.drawLine(0, i*blockSize, gameWidth, i*blockSize);
        }

        g.setColor(Color.blue);
        g.fill3DRect(adventurer.x, adventurer.y, blockSize, blockSize, true);

        g.setColor(Color.red);
        g.fill3DRect(killBlock.x, killBlock.y, blockSize, blockSize, true);

        if (!gameRunning) {
            g.setColor(Color.red);
            g.drawString("Game Over! You made it to level " + levelNumber, blockSize - 25, blockSize);
        } else {
            g.drawString("Level: " + levelNumber, blockSize - 25, blockSize);
        }
    
    }

    public boolean collision(Block block1, Block block2) {
        return block1.x == block2.x && block1.y == block2.y;
    }



    public void move() {
        if (collision(adventurer, killBlock)) {
            gameRunning = false;
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (!gameRunning) {
            scoreboard.stop();
        }
    }
    



}
