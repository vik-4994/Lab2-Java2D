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

    public Main() {
        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Graphics2D для більшої гнучкості
        Graphics2D g2 = (Graphics2D) g;

        // Згладжування (антиаліасінг)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Прозорість
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Збереження початкової трансформації
        AffineTransform old = g2.getTransform();

        // Обертання навколо кута (верхній лівий — 50, 50)
        g2.rotate(Math.toRadians(angle), 50, 50);

        // Тимчасовий "монстрик" — просто прямокутник
        g2.setColor(Color.PINK);
        g2.fillRect(50, 50, 100, 150);

        // Відновлення трансформації
        g2.setTransform(old);

        // Рамка з JOIN.ROUND
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(200, 50, 200, 200);
        g2.setStroke(oldStroke);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Обертання за годинниковою
        angle += 1;

        // Зміна прозорості (блимання)
        if (fadingOut) {
            alpha -= 0.01f;
            if (alpha <= 0.3f) fadingOut = false;
        } else {
            alpha += 0.01f;
            if (alpha >= 1.0f) fadingOut = true;
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab 2 - Java2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.add(new Main());
        frame.setVisible(true);
    }
}
