package powder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Main
 */

public class Main extends JFrame {

  public static Vector2D updateVector(Point p) {
    return new Vector2D(0, 2);
  }

  class Panel extends JPanel {

    BufferedImage img;
    Graphics2D imgg;
    Class<? extends Point> selectedPoint;
    ArrayList<Class<? extends Point>> pointTypes = new ArrayList<>();
    int i = 0;
    public HashSet<Point> pointSet;

    public Panel() {

      setDoubleBuffered(true);
      img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_BGR);

      pointTypes.add(Heavy.class);
      pointTypes.add(Light.class);

      selectedPoint = pointTypes.get(0);
      pointSet = new HashSet<>();
      imgg = img.createGraphics();
      // https://docs.oracle.com/javase/tutorial/uiswing/painting/step3.html

      addMouseWheelListener(new MouseWheelListener() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
          i++;
          if (i >= pointTypes.size()) {
            i = 0;
          }
          selectedPoint = pointTypes.get(i);
        }
      });

      addMouseMotionListener(new MouseMotionListener() {
        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {

          try {
            Point p = (Point) selectedPoint.getDeclaredConstructors()[0].newInstance(e.getX(), e.getY());
            pointSet.add(p);
          } catch (Exception ex) {
            ex.printStackTrace();
          }

        }
      });
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img, 0, 0, null);
      g.setColor(Color.white);
      g.drawString(selectedPoint.getName(), 20, 50);
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(300, 300);
    }
  }

  Main() {

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    var p = new Panel();
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
          try {
            p.pointSet.removeIf((p) -> {
              return p.x < 0 || p.x >= 300 || p.y < 0 || p.y >= 300;
            });
            p.pointSet.iterator().forEachRemaining((point) -> {
              p.img.setRGB(point.x, point.y, Color.white.getRGB());
              var par = (Particle) point;
              var v = ParticleMath.updateVector(par.mass);
              par.setLocation(point.x + v.x, point.y + v.y);
            });
            p.repaint();
            p.img.setRGB(0, 0, 300, 300, new int[900], 0, 0);
            Thread.sleep(20);
          } catch (Exception e) {
            e.printStackTrace();
          }
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
