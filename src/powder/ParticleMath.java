package powder;

import java.awt.*;

/**
 * ParticleMath
 */
public final class ParticleMath {

  private ParticleMath() {
  }

  public static Vector2D updateVector(int mass, Vector2D velocity) {
    return new Vector2D(velocity.x, velocity.y + mass * CONST.GRAVITY.getVal());
  }

  public static boolean vectorsCross(Point p1, Vector2D v1, Point p2, Vector2D v2) {

    // TODO be smarter & make this method
    return false;
  }

}
