import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

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
    int blockSize = 20;

    Scanner scan = new Scanner(System.in);

    Block adventurer;

    ArrayList<Block> killBlocks;

    Timer scoreboard;
    int prevSize;

    int velocityX;
    int velocityY;
    int speed;
    int count = 1;
    int countT = 120;
    int r;

    Random random;

    String[] problemSetTwo;
    String[] answerSetTwo;

    boolean gameRunning = true;

    Game(int width, int height) {
        gameWidth = width;
        gameHeight = height;
        setPreferredSize(new Dimension(gameWidth, gameHeight));
        setBackground(Color.white);
        addKeyListener(this);
        setFocusable(true);

        adventurer = new Block(blockSize, blockSize);
        random = new Random();

        killBlocks = new ArrayList<Block>();

        velocityX = 0;
        velocityY = 0;
        speed = 20;

        problemSetTwo = new String[]{"∫kdx","∫x dx","∫x^n dx (n =/= -1)","∫1/x dx","∫e^xdx","∫sin(x)","∫cos(x)dx","∫sec^2(x)dx","∫csc(x)dx","∫sec(x)tan(x)dx","∫csc(x)cot(x)dx","∫tan(x)dx","∫cot(x)dx",
            "∫a^x dx","∫ln(x)dx","∫e^ax dx","∫1/sqrt(1-x^2)dx","∫1/sqrt(1+x^2)","∫sin^2(x)dx","∫sin(x)cos(x)dx","∫cos^2(x)dx","∫sin^-1(x)dx","∫1/(ax+b) dx"};
        answerSetTwo = new String[]{"kx","(1/2)x^2","(1/n+1)x^(n+1)","ln(x)","e^x","-cos(x)","sin(x)","tan(x)","-cot(x)","sec(x)","-csc(x)","-ln(cos(x))","ln(sin(x))",
            "(a^x)/ln(a)","xln(x)-x","(1/a)e^ax","sin^-1(x)","tan^-1(x)","x/2 - (sin(2x)/4)","(1/2)sin^2(x)","(1/2)x+(1/4)sin(2x)","xsin^-1(x)+sqrt(1-x^2)","(1/a)ln(ax+b)"};
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

        g.setColor(Color.red);
        g.fillRect(0, 0, 1000, 5);
        g.fillRect(0,00, 5, 600);
        g.fillRect(995,0,05,600);
        g.fillRect(0,595,1000,05);

        g.setColor(Color.blue);
        g.fill3DRect(adventurer.x, adventurer.y, blockSize, blockSize, true);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        if (!gameRunning) {
            g.setColor(Color.magenta);
            g.drawString("Game Over! You're final score is " + count, blockSize+5, blockSize+10);
        } else {
            g.setColor(Color.green);
            g.drawString("Score: " + count, blockSize - 10, blockSize+10);
            g.drawString("Time Left: " + (countT) + " seconds", 680, 590);
        }

        for (int f = 0; f < killBlocks.size(); f++) {
            g.setColor(Color.red);
            g.fill3DRect(killBlocks.get(f).x*blockSize, killBlocks.get(f).y*blockSize, blockSize, blockSize, false);
        }

        /**if (count % 79 == 0) {
            int randomIndex = random.nextInt(23);
            r = randomIndex;
            g.setColor(Color.magenta);
            g.drawString(problemSet[r], gameWidth/2, gameHeight/2);
            String x = JOptionPane.showInputDialog("Evaluate: " + problemSet[r]);
            if (x.equals(answerSet[r])) {
                killBlocks.remove(killBlocks.size()-1);
            }
            scoreboard.start();
            //System.out.println("Evaluate the problem on the screen: ");
        }**/
    }

    public boolean collision(Block block1, Block block2) {
        return block1.x/speed == block2.x && block1.y/speed == block2.y;
    }

    public void placeKillBlock() {
        killBlocks.add(new Block(blockSize, blockSize));
        for (int i = 0; i < killBlocks.size(); i++) {
            int randomX = random.nextInt(gameWidth/blockSize);
            int randomY = random.nextInt(gameHeight/blockSize);
            killBlocks.get(i).x = randomX;
            killBlocks.get(i).y = randomY;
        }
    }



    public void move() {

        System.out.println(adventurer.x + " " + adventurer.y);
        for (int b = 0; b < killBlocks.size(); b++) {
            System.out.println(killBlocks.get(b).x + " " + killBlocks.get(b).y);
        }
        System.out.println(" ");
        if (countT == 0) {
            gameRunning = false;
        }
        for (int c = 0; c < killBlocks.size(); c++) {
            if (collision(adventurer, killBlocks.get(c))) {
                killBlocks.remove(c);
                count -= 20;
                c++;
            }
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
        if (count%10 == 0) {
            countT--;
        }

        adventurer.x += velocityX;
        adventurer.y += velocityY;

        if (count % 20 == 0 || count == 0) {
            placeKillBlock();
        }

        if (count % 80 == 0) {
            int randomIndex = random.nextInt(22);
            String x = JOptionPane.showInputDialog("Evaluate: " + problemSetTwo[randomIndex]);
            scoreboard.stop();
            if (x.equals(answerSetTwo[randomIndex])) {
                JOptionPane.showMessageDialog(null, "You got it right!");
                killBlocks.remove(killBlocks.size()-1);
                count += 100;
            }else {
                JOptionPane.showMessageDialog(null, "You got it wrong! It was " + answerSetTwo[randomIndex] + ". Better luck next time!");
                for (int i = 0; i < killBlocks.size()/2; i++) {
                    killBlocks.add(new Block(blockSize, blockSize));
                }
            }
            scoreboard.start();
            //System.out.println("Evaluate the problem on the screen: ");
        }

        //System.out.println("Evaluate the problem on the screen: ");


        /**if (count % 80 == 0) {
            scoreboard.stop();
            if (scan.nextLine().equals(answerSet[r])) {
                killBlocks.remove(killBlocks.size()-1);
            }
            scoreboard.start();
        }
        **/
        count++;
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