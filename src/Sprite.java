/**
 * Defines an interface for the Sprite. It is an entity that will store its image along with its coordinates and
 * other behaviours typical of an enemy.
 */

import java.awt.Image;

public class Sprite {

    protected boolean visible;
    private Image image;
    protected int x;
    protected int y;
    protected boolean dying;
    protected int dx;
    protected int dy;

    public Sprite() {
        visible = true;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;

    }
}
