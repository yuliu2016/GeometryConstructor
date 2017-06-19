package ca.wtcs.ics3u.gc;


/*
 * Created by Yu Liu on 2017-06-06
 * ICS3U - Culminating Project - A compass-and-straightedge construction tool
 *
 * More information at https://github.com/yuliu2016/GeometryConstructor
 */


import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A <code>GeometryConstructor</code> initiates a window with a
 * {@link GraphView} filled into it
 *
 * @author Yu Liu
 * @see GraphView
 */


public class GeometryConstructor {


    /**
     * Default dimension of the application
     */

    private static final Dimension DEFAULT_DIMENSION;

    /**
     * The title of the application shown on the window
     */

    private static final String TITLE;


    static {
        DEFAULT_DIMENSION = new Dimension(800, 600);
        TITLE = "Geometry Constructor";
    }


    /**
     * Starts the application by intiating a Frame containing a GraphView
     *
     * @param args System arguments
     */

    public static void main(String[] args) {

        Frame app = new Frame(TITLE);
        GraphView graphView = new GraphView();

        app.setSize(DEFAULT_DIMENSION);
        app.setLayout(null);
        app.setResizable(true);

        app.add(graphView);

        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                app.dispose();
                System.exit(0);
            }
        });

        app.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = app.getSize();
                graphView.setSize(newSize.width, newSize.height);
            }
        });

        app.setMinimumSize(GraphView.MINIMUM_DIMENSION);
        app.setVisible(true);
    }

}
