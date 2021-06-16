package powder;

/**
 * Heavy
 */
public class Heavy extends Particle {

  Heavy(int x, int y) {
    super(x, y);
    super.mass = 5;
    super.gravity = true;
  }

}
