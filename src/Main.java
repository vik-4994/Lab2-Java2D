import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class Main extends JPanel implements ActionListener {
    private Timer timer;
    private double angle = 0;
    private float alpha = 1.0f;
    private boolean fadingOut = true;

    private final int centerX = 300;
    private final int centerY = 200;

    public Main() {
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        AffineTransform old = g2.getTransform();

        int pivotX = centerX - 160;
        int pivotY = centerY - 130;

        g2.rotate(Math.toRadians(angle), pivotX, pivotY);
        drawMonster(g2, pivotX, pivotY);

        g2.setTransform(old);

        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(centerX - 200, centerY - 200, 400, 400);
        g2.setStroke(oldStroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle += 1;
        if (fadingOut) {
            alpha -= 0.01f;
            if (alpha <= 0.3f) fadingOut = false;
        } else {
            alpha += 0.01f;
            if (alpha >= 1.0f) fadingOut = true;
        }
        repaint();
    }

    private void drawMonster(Graphics2D g2, int x, int y) {
        g2.setPaint(new GradientPaint(x + 0, y + 0, Color.MAGENTA, x + 80, y + 50, Color.CYAN));
        g2.fillRect(x + 0, y + 0, 80, 50);

        g2.setColor(new Color(128, 0, 255));

        g2.fillRect(x - 35, y + 50, 150, 100);
        g2.fillRect(x - 70, y + 50, 60, 75);
        g2.fillRect(x + 115, y + 50, 60, 65);

        g2.fillRect(x - 35, y + 150, 45, 80);
        g2.fillRect(x + 75, y + 150, 40, 80);

        g2.setColor(Color.YELLOW);
        g2.fillRect(x + 25, y + 20, 8, 8);
        g2.fillRect(x + 55, y + 20, 8, 8);

        g2.setColor(new Color(128, 0, 255));
        g2.fillRect(x + 10, y + 210, 10, 20);
        g2.fillRect(x + 115, y + 210, 10, 20);

        Polygon leftHorn = new Polygon(
                new int[]{x + 2, x + 2, x - 25},
                new int[]{y, y - 30, y - 15},
                3
        );
        Polygon rightHorn = new Polygon(
                new int[]{x + 78, x + 110, x + 80},
                new int[]{y, y, y - 25},
                3
        );
        g2.setColor(Color.BLUE);
        g2.fillPolygon(leftHorn);
        g2.fillPolygon(rightHorn);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab 2 - Java2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.add(new Main());
        frame.setVisible(true);
    }
}
