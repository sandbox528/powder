package powder;

/**
 * Light
 */
public class Light extends Particle {

  Light(int x, int y) {
    super(x, y);
    super.mass = 1;
    super.gravity = true;
  }

}
