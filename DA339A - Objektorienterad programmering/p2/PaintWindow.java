package p2;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.*;

/**
 * A PaintWindow object can display images and draw some shapes.
 * @author Rolf Axelsson
 */
public class PaintWindow extends JFrame {
    private PaintPanel panel = new PaintPanel();
    private BufferedImage bImage;
    private Graphics2D bg;
    private ImageIcon background;
    private LinkedList<IconXY> icons = new LinkedList<IconXY>();
    private LinkedList<DPShape> buffer = new LinkedList<DPShape>();

    /**
     * Constructs a PaintWindow object with white background and the size 600x400
     */
    public PaintWindow() {
        this(null);
    }

    /**
     * Constructs a PaintWindow object with the image <code>background</code> in the background and with 
     * the same size as the picture. Maximum size of the window is 800x800.
     * 
     * @param background the image in the background of the window
     */
    public PaintWindow(ImageIcon background) {
        super("Paint Window");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.background = background;
        if (background != null) {
            panel.setPreferredSize(new Dimension( Math.min(background.getIconWidth(),800), 
                                                  Math.min(background.getIconHeight(),800)));
        } else {
            panel.setPreferredSize(new Dimension(600, 400));
        }
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Sets the background image, <code>icon</code>, of the window.
     * 
     * @param icon the new image in the background of the window
     */
   public void setBackground(ImageIcon icon) {
        this.background = icon;
        bImage = null;
        repaint();
    } 

   /**
    * Displays an image, <code>icon</code>, at a specified position (<code>x</code>,<code>y</code>) 
    * of the window.
    * 
    * @param icon the image to be displayed
    * @param x x-positon of the image in the window
    * @param y y-positon of the image in the window
    */
    public void showImage(Icon icon, int x, int y) {
        int index;
        if (icon == null) {
            return;
        }
        IconXY newIcon = new IconXY(icon, x, y);
        index = icons.indexOf(newIcon);
        if (index == -1) {
            icons.add(new IconXY(icon, x, y));
        } else {
            icons.get(index).setX(x);
            icons.get(index).setY(y);
        }
        repaint();
    }
    
    /**
     * Hides an image, <code>icon</code>.
     * 
     * @param icon the image to be hidden
     */
    public void hideImage(Icon icon) {
        int index;
        if (icon == null) {
            return;
        }
        IconXY newIcon = new IconXY(icon, 0, 0);
        index = icons.indexOf(newIcon);
        if (index != -1) {
            icons.remove(index);
            repaint();
        } 
    }
        
    /**
     * Clears the background from drawn shapes
     */
    public void clear() {
        if (background != null) {
            bg.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        } else {
            bg.setPaint(Color.WHITE);
            bg.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        repaint();
    }
    
    /**
     * Returns the width of the background in the PaintWindow object
     * 
     * @return the width of the background
     */
    public int getBackgroundWidth() {
        return panel.getWidth();
    }
    
    /**
     * Returns the height of the background in the PaintWindow object
     * 
     * @return the height of the background
     */
    public int getBackgroundHeight() {
        return panel.getHeight();
    }

    /**
     * Draws a line on the background. The line will be drawn from (<code>x1</code>,<code>y1</code>)
     * to (<code>x2</code>,<code>y2</code>), with the <code>color</code> and the <code>ineWidth</code>.
     * 
     * @param x1 x-position for the first point
     * @param y1 y-position for the first point
     * @param x2 x-position for the second point
     * @param y2 y-position for the second point
     * @param color the color of the line
     * @param lineWidth the line thickness
     */
    public void line(int x1, int y1, int x2, int y2, Color color, int lineWidth) {
        buffer.add(new Line(x1, y1, x2, y2, color, lineWidth));
        repaint();
    }

    /**
     * Draws the outline of a rectangle on the background. The rectangle will be drawn from upper left 
     * corner, (<code>x</code>,<code>y</code>), and with the specified <code>width</code>, <code>height</code>, 
     * <code>color</code> and with thickness, <code>lineWidth</code>, on the line.
     * 
     * @param x the x-coordinate for the upper left corner
     * @param y the y-coordinate for the upper left corner
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param color the color of the edge
     * @param lineWidth the edge thickness
     */
    public void drawRect(int x, int y, int width, int height, Color color, int lineWidth) {
        buffer.add(new DrawRect(x, y, width, height, color, lineWidth));
        repaint();
    }

    /**
     * Draws a filled rectangle on the background. The rectangle will be drawn from upper left corner, 
     * (<code>x1</code>,<code>y1</code>), and with the specified <code>width</code>, <code>height</code> 
     * and <code>color</code>.
     * 
     * @param x the x-coordinate for the upper left corner
     * @param y the y-coordinate for the upper left corner
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param color the color of the rectangle
     */
    public void fillRect(int x, int y, int width, int height, Color color) {
        buffer.add(new FillRect(x, y, width, height, color));
        repaint();
    }

    /**
     * Draws the outline of an ellipse on the background. The result is a ellipse that fits within the 
     * rectangle specified by the <code>x</code>, <code>y</code>, <code>width</code> and 
     * <code>height</code>) arguments.
     * The ellipse will be drawn with the specified <code>color</code> and thickness, 
     * <code>lineWidth</code>, on the line.
     * 
     * @param x the x coordinate of the upper left corner of the ellipse to be drawn.
     * @param y the y coordinate of the upper left corner of the ellipse to be drawn.
     * @param width the width of the ellipse
     * @param height the height of the ellipse
     * @param color the color of the edge
     * @param lineWidth the edge thickness
     */
    public void drawOval(int x, int y, int width, int height, Color color, int lineWidth) {
        buffer.add(new DrawOval(x, y, width, height, color, lineWidth));
        repaint();
    }

    /**
     * Draws a filled ellipse on the background. The result is a ellipse that fits within the 
     * rectangle specified by the <code>x</code>, <code>y</code>, <code>width</code>, 
     * <code>height</code>) arguments. The ellipse will be filled with the <code>color</code>.
     * 
     * @param x the x coordinate of the upper left corner of the ellipse to be drawn.
     * @param y the y coordinate of the upper left corner of the ellipse to be drawn.
     * @param width the width of the ellipse
     * @param height the height of the ellipse
     * @param color the color of the ellipse
     */
    public void fillOval(int x, int y, int width, int height, Color color) {
        buffer.add(new FillOval(x, y, width, height, color));
        repaint();
    }

    /**
     * Pauses the execution for <code>ms</code> milliseconds
     * @param ms the pause in milliseconds
     */
    public static void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }
    
    private class PaintPanel extends JPanel {

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Point point;
            if (bImage == null) {
                bImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                bg = bImage.createGraphics();
                if (background != null) {
                    bg.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
                } else {
                    bg.setPaint(Color.WHITE);
                    bg.fillRect(0, 0, this.getWidth(), this.getHeight());
                }
            }
            while (buffer.size() > 0) {
                buffer.get(0).draw(bg);
                buffer.remove(0);
            }
            g.drawImage(bImage, 0, 0, null);
            for (int i = 0; i < icons.size(); i++) {
                icons.get(i).paintIcon(g);
            }
        }
    }

    private class IconXY {

        private Icon icon;
        private int x;
        private int y;

        public IconXY(Icon icon, int x, int y) {
            this.icon = icon;
            this.x = x;
            this.y = y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public void paintIcon(Graphics g) {
            icon.paintIcon(PaintWindow.this, g, this.x, this.y);
        }
        
        public boolean equals(Object obj) {
            IconXY iconXY = (IconXY)obj;
            return this.icon.equals(iconXY.icon);
        }
    }

    private abstract class DPShape {

        public abstract void draw(Graphics2D g);
    }

    private class Line extends DPShape {

        private int x1,  y1,  x2,  y2,  lineWidth;
        private Color color;

        public Line(int x1, int y1, int x2, int y2, Color color, int lineWidth) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
            this.lineWidth = lineWidth;
        }

        public void draw(Graphics2D g) {
            g.setStroke(new BasicStroke(lineWidth));
            g.setPaint(color);
            g.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    private class DrawRect extends DPShape {

        private int x,  y,  width,  height,  lineWidth;
        private Color color;

        public DrawRect(int x, int y, int width, int height, Color color, int lineWidth) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.lineWidth = lineWidth;
        }

        public void draw(Graphics2D g) {
            g.setStroke(new BasicStroke(lineWidth));
            g.setPaint(color);
            g.draw(new Rectangle2D.Double(x, y, width, height));
        }
    }

    private class FillRect extends DPShape {

        private int x,  y,  width,  height;
        private Color color;

        public FillRect(int x, int y, int width, int height, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
        }

        public void draw(Graphics2D g) {
            g.setPaint(color);
            g.fill(new Rectangle2D.Double(x, y, width, height));
        }
    }

    private class DrawOval extends DPShape {

        private int x,  y,  width,  height,  lineWidth;
        private Color color;

        public DrawOval(int x, int y, int width, int height, Color color, int lineWidth) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.lineWidth = lineWidth;
        }

        public void draw(Graphics2D g) {
            g.setStroke(new BasicStroke(lineWidth));
            g.setPaint(color);
            g.draw(new Ellipse2D.Double(x, y, width, height));
        }
    }

    private class FillOval extends DPShape {

        private int x,  y,  width,  height;
        private Color color;

        public FillOval(int x, int y, int width, int height, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
        }

        public void draw(Graphics2D g) {
            g.setPaint(color);
            g.fill(new Ellipse2D.Double(x, y, width, height));
        }
    }
}
