package powder;

public enum CONST {
  GRAVITY(0.2);

  private double val;

  CONST(double val) {
    this.val = val;
  }

  public double getVal() {
    return val;
  }

}
