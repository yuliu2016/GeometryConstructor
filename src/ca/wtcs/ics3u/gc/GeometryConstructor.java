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
        DEFAULT_DIMENSION = new Dimension(600, 450);
        TITLE = "Geometry Constructor";
    }


    /**
     * Starts the application by intiating a Frame containing a GraphView
     *
     * @param args System arguments
     */

    public static void main(String[] args) {

        Frame application = new Frame(TITLE);
        GraphView graphView = new GraphView();

        application.setSize(DEFAULT_DIMENSION);
        application.setLayout(null);
        application.setResizable(true);

        application.add(graphView);

        application.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                application.dispose();
                System.exit(0);
            }
        });

        application.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension newSize = application.getSize();
                graphView.setSize(newSize.width, newSize.height);
            }
        });

        application.setMinimumSize(GraphView.MINIMUM_DIMENSION);
        application.setVisible(true);
    }

}
