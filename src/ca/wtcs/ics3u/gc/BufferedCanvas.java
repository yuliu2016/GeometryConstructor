package ca.wtcs.ics3u.gc;


/*
 * Created by Yu Liu on 2017-06-15
 * ICS3U - Culminating Project - A compass-and-straightedge construction tool
 *
 * More information at https://github.com/yuliu2016/GeometryConstructor
 *
 * The code in this file is from an answer on Stack Overflow.
 * It is not written by me, however I made some modifications.
 * The source is found at https://stackoverflow.com/a/10508090
 */


import java.awt.*;


/**
 * A <code>BufferedCanvas</code> is a Canvas that
 * updates by painting onto a image
 * and then copies it onto the component.
 * This allows the program to be flicker-free
 * when it's drawing multiple objects at each frame
 */


class BufferedCanvas extends Canvas {


    /**
     * Overrides Canvas' update method.
     * Creates a buffer image from the current component,
     * then calls the component's paint method with the graphic
     * context created from the image. Finally it calls drawImage
     * on the component's graphic context to "blit" the image onto
     * the screen
     *
     * @param g the onscreen component's graphic context
     */

    @Override
    public void update(Graphics g) {

        Image bufferedImage = createImage(getWidth(), getHeight());

        Graphics bufferedGraphics = bufferedImage.getGraphics();

        bufferedGraphics.setColor(getBackground());

        bufferedGraphics.fillRect(0, 0, getWidth(), getHeight());

        paint(bufferedGraphics);

        g.drawImage(bufferedImage, 0, 0, this);

    }
}
