package powder;

import java.awt.*;

/**
 * Particle
 */
abstract public class Particle extends Point {

  Particle(int x, int y) {
    super(x, y);
  }

  public int mass = 0;
  public boolean gravity = false;

}
