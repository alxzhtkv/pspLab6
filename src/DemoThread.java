import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.lang.InterruptedException;


public class DemoThread extends JFrame {
    private static Image background;
    private static Image zombie;
    private static Image blood;
    int zombieX = 200;
    int zombieY = 200;
    int bloodX = 100;
    int bloodY = 100;
    int bloodHeight = 100;
    int bloodWidth = 0;
    int zombieWidth = 0;


    JButton shotButton;
    JButton startButton;
    JButton stopButton;

    private  boolean mFinish = true;;

    private static class Background extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                background = ImageIO.read(new File("images/background2.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(background, 0, 0, null);
        }
    }


    public DemoThread() {
        setTitle("Demo app");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(new Background()); // панель
        Container content = getContentPane(); //теперь
        shotButton = new JButton("Стреляться!");
        startButton = new JButton("Начать");
        stopButton = new JButton("Стоп");
        shotButton.setBounds(400, 500, 100, 50);
        startButton.setBounds(100, 500, 100, 50);
        startButton.setBounds(100, 500, 100, 50);
        startButton.setBounds(100, 500, 100, 50);

        content.add(shotButton);
        content.add(startButton);
        content.add(stopButton);

        content.add(new ZombieImage());

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mFinish = true;
                startButton.setVisible(false);
                Thread target = new Thread(new TargetThreader());
                target.start();
            }

        });
        shotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread bloodThreader = new Thread(new BloodThreader());
                bloodThreader.start();


            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setVisible(true);
                mFinish = false;
            }
        });

    }

    private class ZombieImage extends JPanel {
        public ZombieImage() {
            setOpaque(false);
            setPreferredSize(new Dimension(1000, 600));
            try {
                zombie = ImageIO.read(new File("images/zombieTarget.png"));
                blood = ImageIO.read(new File("images/Blood.png"));

            } catch (IOException ignored) {
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2d = (Graphics2D) g;
            graphics2d.drawImage(zombie, zombieX, zombieY, zombieWidth, 100, this);
            graphics2d.drawImage(blood, bloodX, bloodY, bloodWidth, bloodHeight, this);
        }
    }

    public class TargetThreader extends Thread implements Runnable {
        @Override
        public void run() {

            while (mFinish) {
                zombieWidth = 120;
                zombieX = (int) (Math.random() * 860);
                zombieY = (int) (Math.random() * 480);
                repaint();

                try {
                    Thread.sleep(1500);
                } catch (Exception ignored) {
                }

            }
            if (!mFinish) {
                zombieWidth = 0;
                repaint();
            }
        }
    }

    public class BloodThreader extends Thread implements Runnable {
        @Override
        public void run() {
            bloodWidth = 120;
            bloodX = zombieX;
            bloodY = zombieY;
            repaint();

            try {
                Thread.sleep(500);
                bloodWidth = 0;
                repaint();
            } catch (Exception exc) {
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new DemoThread().setVisible(true);
    }
}
