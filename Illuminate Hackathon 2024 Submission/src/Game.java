import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.*;
import java.util.ArrayList;


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
    ArrayList<Block> killBlocks;

    Timer scoreboard;

    int velocityX;
    int velocityY;
    int speed;
    int count = 0;

    Random random;

    String[] problemSet;
    String[] answerSet;

    boolean gameRunning = true;

    Game(int width, int height) {
        gameWidth = width;
        gameHeight = height;
        setPreferredSize(new Dimension(gameWidth, gameHeight));
        setBackground(Color.white);
        addKeyListener(this);
        setFocusable(true);

        adventurer = new Block(10, gameHeight/2);
        killBlock = new Block(10, 10);

        random = new Random();

        killBlocks = new ArrayList<Block>();

        velocityX = 0;
        velocityY = 0;
        speed = blockSize/2;

        problemSet = new String[]{"∫kdx","∫x dx","∫x^n dx (n =/= -1)","∫1/x dx","∫e^xdx","∫sin(x)","∫cos(x)dx","∫sec^2(x)dx","∫csc(x)dx","∫sec(x)tan(x)dx","∫csc(x)cot(x)dx","∫tan(x)dx","∫cot(x)dx",
            "∫a^x dx","∫ln(x)dx","∫e^ax dx","∫1/sqrt(1-x^2)dx","∫1/sqrt(1+x^2)","∫sin^2(x)dx","∫sin(x)cos(x)dx","∫cos^2(x)dx","∫sin^-1(x)dx","∫1/(ax+b) dx"};
        answerSet = new String[]{"∫kdx","∫x dx","∫x^n dx (n =/= -1)","∫1/x dx","∫e^xdx","∫sin(x)","∫cos(x)dx","∫sec^2(x)dx","∫csc(x)dx","∫sec(x)tan(x)dx","∫csc(x)cot(x)dx","∫tan(x)dx","∫cot(x)dx",
            "∫a^x dx","∫ln(x)dx","∫e^ax dx","∫1/sqrt(1-x^2)dx","∫1/sqrt(1+x^2)","∫sin^2(x)dx","∫sin(x)cos(x)dx","∫cos^2(x)dx","∫sin^-1(x)dx","∫1/(ax+b) dx"};
        scoreboard = new Timer(100, this);
        scoreboard.start();
    }

    public void paintComponent(Graphics a) {
        super.paintComponent(a);
        draw(a);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < gameWidth; i++) {
            g.setColor(Color.black);
            g.drawLine(i*blockSize, 0, i*blockSize, gameHeight);
            g.drawLine(0, i*blockSize, gameWidth, i*blockSize);
        }

        g.setColor(Color.blue);
        g.fill3DRect(adventurer.x, adventurer.y, blockSize, blockSize, true);

        g.setColor(Color.red);
        g.fill3DRect(killBlock.x, killBlock.y, blockSize, blockSize, true);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        if (!gameRunning) {
            g.setColor(Color.red);
            g.drawString("Game Over! You're final score is " + count, blockSize, blockSize);
        } else {
            g.setColor(Color.green);
            g.drawString("Score: " + count, blockSize - 10, blockSize);
        }

        for (int f = 0; f < killBlocks.size(); f++) {
            g.setColor(Color.red);
            g.fill3DRect(killBlocks.get(f).x*blockSize, killBlocks.get(f).y*blockSize, blockSize, blockSize, true);
        }
    }

    public boolean collision(Block block1, Block block2) {
        return block1.x == block2.x && block1.y == block2.y;
    }



    public void move() {
        adventurer.x += velocityX;
        adventurer.y += velocityY;
        if (collision(adventurer, killBlock)) {
            gameRunning = false;
        }
        if (adventurer.x >= 1030) {
            gameRunning = false;
        }
        if (adventurer.x <= -30) {
            gameRunning = false;
        }
        if (adventurer.y >= 630) {
            gameRunning = false;
        }
        if (adventurer.y <= -30) {
            gameRunning = false;
        }
        count++;
        if (count % 25 == 0) {
            killBlocks.add(new Block(blockSize, blockSize));
            for (int i = 0; i < killBlocks.size(); i++) {
                int randomX = random.nextInt(gameWidth/blockSize);
                int randomY = random.nextInt(gameHeight/blockSize);
                killBlocks.get(i).x = randomX;
                killBlocks.get(i).y = randomY;
            }
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
            velocityY = -1*speed;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = speed;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1*speed;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = speed;
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