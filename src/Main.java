package powder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

/**
 * Main
 */

public class Main extends JFrame {

  class Panel extends JPanel {

    BufferedImage img;
    Graphics2D imgg;

    public HashSet<Point> pointSet;

    public Panel() {

      setDoubleBuffered(true);
      img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_BGR);
      pointSet = new HashSet<>();
      imgg = img.createGraphics();
      // https://docs.oracle.com/javase/tutorial/uiswing/painting/step3.html
      addMouseMotionListener(new MouseMotionListener() {
        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
          pointSet.add(new Point(e.getX(), e.getY()));
        }
      });
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(300, 300);
    }
  }

  Main() {

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    Panel p = new Panel();
    getContentPane().add(p);
    pack();
    setVisible(true);
    setLocationRelativeTo(null);
    // https://docs.oracle.com/javase/tutorial/uiswing/concurrency/worker.html
    SwingWorker<Void, Void> worker = new SwingWorker<>() {
      @Override
      protected Void doInBackground() throws Exception {
        // https://docs.oracle.com/javase/tutorial/uiswing/concurrency/interim.html
        while (!isCancelled()) {
          p.pointSet.removeIf((p) -> {
            return p.x < 0 || p.x >= 300 || p.y < 0 || p.y >= 300;
          });

          p.pointSet.iterator().forEachRemaining((point) -> {
            p.img.setRGB(point.x, point.y, Color.white.getRGB());
            // Possible entry point for vector calculations
            point.setLocation(point.x, point.y + 1);
          });

          p.repaint();
          p.img.setRGB(0, 0, 300, 300, new int[900], 0, 0);
          Thread.sleep(20);
        }
        return null;
      }

    };

    worker.execute();

  }

  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        new Main();
      }
    });
  }

}
