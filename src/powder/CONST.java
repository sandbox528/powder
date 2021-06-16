package powder;

public enum CONST {
  GRAVITY(1);

  private int val;

  CONST(int val) {
    this.val = val;
  }

  public int getVal() {
    return val;
  }

}
