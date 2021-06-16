package powder;

/**
 * ParticleMath
 */
public final class ParticleMath {

  private ParticleMath() {
  }

  public static Vector2D updateVector(int mass) {
    return new Vector2D(0, mass * CONST.GRAVITY.getVal());
  }

}
