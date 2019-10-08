public class area {
    int x_min=0;
    int x_max=0;
    int y_min=0;
    int y_max=0;

    public area(int x_min_new, int x_max_new, int y_min_new, int y_max_new){
        this.x_min=x_min_new;
        this.x_max=x_max_new;
        this.y_min=y_min_new;
        this.y_max=y_max_new;
    }

    /**
     * @return the x_min
     */
    public int getX_min() {
        return x_min;
    }

    /**
     * @param x_min the x_min to set
     */
    public void setX_min(int x_min) {
        this.x_min = x_min;
    }

    /**
     * @return the x_max
     */
    public int getX_max() {
        return x_max;
    }

    /**
     * @param x_max the x_max to set
     */
    public void setX_max(int x_max) {
        this.x_max = x_max;
    }

    /**
     * @return the y_min
     */
    public int getY_min() {
        return y_min;
    }

    /**
     * @param y_min the y_min to set
     */
    public void setY_min(int y_min) {
        this.y_min = y_min;
    }

    /**
     * @return the y_max
     */
    public int getY_max() {
        return y_max;
    }

    /**
     * @param y_max the y_max to set
     */
    public void setY_max(int y_max) {
        this.y_max = y_max;
    }
    
    
}
