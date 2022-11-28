import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NBody extends JPanel implements ActionListener
{
    public int n;
    public double x;
    public double y;
    public double r;
    public int size;
    public double dt;
    public double maxVel;
    public double maxMass;
    public double[] xPoints;
    public double[] yPoints;
    public double[] mass;
    public int[] red;
    public int[] green;
    public int[] blue;
    public double[] xVelocity;
    public double[] yVelocity;
    public double dv;
    public int gravity;

    public void init(int n)
    {
        // Your initialization code here:
        this.n = n;
        xPoints = new double[this.n];
        yPoints = new double[this.n];
        mass = new double[this.n];
        red = new int[this.n];
        green = new int[this.n];
        blue = new int[this.n];
        xVelocity = new double[this.n];
        yVelocity = new double[this.n];
        for (int i = 0; i < n; i++) {
            double rx = Math.random();
            double ry = Math.random();
            xPoints[i] = (int) (800 * rx);
            yPoints[i] = (int) (800 * ry);
            double rand = Math.random();
            mass[i] = (int) (10 * rand);
            rand = Math.random();
            red[i] = (int) (128 * rand) + 128;
            rand = Math.random();
            green[i] = (int) (128 * rand) + 128;
            rand = Math.random();
            blue[i] = (int) (128 * rand) + 128;
            rand = Math.random();
            xVelocity[i] = (20 * rand) - maxVel;
            rand = Math.random();
            yVelocity[i] = (20 * rand) - maxVel;
        }
    }

    // Draw a circle centered at (x, y) with radius r
    public void drawCircle(Graphics g, int x, int y, int r)
    {
        int d = 2*r;
        g.fillOval(x - r, y - r, d, d);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Your drawing code here:
        for (int i = 0; i < n; i++) {
            g.setColor(new Color (red[i], green[i], blue[i]));
            drawCircle(g, (int) xPoints[i], (int) yPoints[i], (int) mass[i]/2);
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        // Your update code here:
        // change in position & change in velocity
        for (int i = 0; i < n; i++) {
            xPoints[i] += (xVelocity[i] * dt);
            yPoints[i] += (yVelocity[i] * dt);
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double x1 = xPoints[i];
                    double x2 = xPoints[j];
                    double y1 = yPoints[i];
                    double y2 = yPoints[j];
                    double xDist = x2 - x1;
                    double yDist = y2 - y1;
                    double r = Math.sqrt(xDist * xDist + yDist * yDist);
                    if (r < 5)
                        r = 5;
                    dv = (gravity * mass[j]* dt)/(r * r);
                    if (xDist > 0)
                        xVelocity[i] += dv;
                    else
                        xVelocity[i] -= dv;
                    if (yDist > 0)
                        yVelocity[i] += dv;
                    else
                        yVelocity[i] -= dv; 
                }
            }
        }
        // Repaint the screen
        repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int n = Integer.parseInt(args[0]);

        NBody nbody = new NBody();
        nbody.setBackground(Color.BLACK);
        nbody.size = 800;
        nbody.maxVel = 10;
        nbody.maxMass = 10;
        nbody.dt = 0.1;
        nbody.gravity = 100;
        nbody.setPreferredSize(new Dimension(nbody.size, nbody.size));
        nbody.init(n);

        frame.add(nbody);
        frame.pack();

        Timer timer = new Timer(16, nbody);
        timer.start();

        frame.setVisible(true);
    }
}