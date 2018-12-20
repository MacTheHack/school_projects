package p2;
import java.awt.*;
import java.awt.font.*;
import javax.swing.Icon;

/**
 * A Text object can be used to show a text in a PaintWindow object. A Text  
 * object is shown as a picture in PaintWindow and can be moved.
 *
 * @author TSROAX
 */
public class Text implements Icon {
    private String text;
    private Font font;
    private Color foreground;
    private Color background;
    private FontRenderContext frc;
    private boolean opaque;
    private int iconWidth;
    private int iconHeight;
    private int textDY;

    
    /**
     * Construction of a text object that shows <code> text </code>. The text will be displayed with:
     * font="Sans Serif", style=Font.PLAIN, size=12, text color=Color.BLACK
     *
     * @param text the text to be displayed
     */
    public Text(String text) {
        this(text,new Font("SansSerif",Font.PLAIN,12));
    }

    /**
     * Construction of a text object that displays <code> text </code> with the specified <code>font</code>. 
     * The text will be displayed with text color=Color.BLACK
     *
     * @param text the text to be displayed
     * @param font the font to be used
     */
    public Text(String text, Font font) {
        this(text,font,Color.BLACK,Color.WHITE,false);
    }

    /**
     * Construction of a text object that displays <code> text </code> with the specified <code>font</code>
     * and text color <code>foreground</code>. 
     *
     * @param text the text to be displayed
     * @param font the font to be used
     * @param foreground the color of the text
     */
    public Text(String text, Font font, Color foreground) {
        this(text,font,foreground,null,false);
    }
 
    /**
     * Construction of a text object that displays <code> text </code> with the specified <code>font</code>, 
     * text color <code>foreground</code> and background color <code>background</code>. 
     *
     * @param text the text to be displayed
     * @param font the font to be used
     * @param foreground the color of the text
     * @param background the color of the background
     */
   public Text(String text, Font font, Color foreground, Color background) {
        this(text,font,foreground,background,true);
    }

    private Text(String text, Font font, Color foreground, Color background, boolean opaque) {
        LineMetrics lm;
        this.font = font;
        this.foreground = foreground;
        this.background = background;
        this.opaque = opaque;
        setText(text);
    }

     /**
     * Sets the the text color <code>foreground</code>. 
     *
     * @param foreground the color of the text
     */
    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

     /**
     * Sets the the background color <code>background</code>. 
     *
     * @param background the color of the background
     */
    public void setBackground(Color background) {
        if(background!=null) {
            this.background = background;
            this.opaque = true;
        } else {
            this.opaque = false;
        }
    }
    
     /**
     * Sets the <code>text</code> to be displayed. 
     *
     * @param txt the text to be displayed
     */
    public void setText(String txt) {
    	this.text = txt;
    	if(frc!=null) {
    		iconWidth = (int)font.getStringBounds(text, frc).getWidth();
    		iconHeight = (int)font.getStringBounds(text, frc).getHeight();
    		LineMetrics lm = font.getLineMetrics(text, frc);
    		textDY = (int)(lm.getHeight() - lm.getDescent());
    	}
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Color oldColor = g.getColor();
        Font oldFont = g.getFont();
        if(frc==null) {
        	frc = ((Graphics2D)g).getFontRenderContext();
            iconWidth = (int)font.getStringBounds(text, frc).getWidth();
            iconHeight = (int)font.getStringBounds(text, frc).getHeight();
            LineMetrics lm = font.getLineMetrics(text, frc);
            textDY = (int)(lm.getHeight() - lm.getDescent());
        }
        if(this.opaque) {
            g.setColor(this.background);
            g.fillRect(x, y, this.iconWidth, this.iconHeight);
        }
        g.setColor(this.foreground);
        g.setFont(this.font);
        g.drawString(text, x, y+this.textDY);
        g.setColor(oldColor);
        g.setFont(oldFont);
    }

     /**
     * Returns the width of the Text object. 
     *
     * @return the width of the Text object
     */
    public int getIconWidth() {
        return iconWidth;
    }

     /**
     * Returns the height of the Text object. 
     *
     * @return the height of the Text object
     */
    public int getIconHeight() {
        return iconHeight;
    }

}
