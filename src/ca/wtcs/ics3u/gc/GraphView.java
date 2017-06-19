package ca.wtcs.ics3u.gc;


/*
 * Created by Yu Liu on 2017-06-06
 * ICS3U - Culminating Project - A compass-and-straightedge construction tool
 *
 * More information at https://github.com/yuliu2016/GeometryConstructor
 */


import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A <code>GraphView</code>  is a canvas component designed to provide a geometric construction tool.
 * <p>
 * This component may be placed anywhere that allows a <code>java.awt.Frame</code>,
 * provided it has a minimum size specified in MINIMUM_DIMENSION;
 * or it can be created as standalone by instantiating this class.
 * The component can be resized and would update accordingly,
 * including a new default scale. See {@link ViewPort} for more details.
 * This component receives events from its window, component, keyboard, and mouse;
 * </p>
 * <p>
 * This class keeps track of objects relating to the viewport,
 * values relating to the mouse and key events, and a list of objects that implement
 * {@link Geometry} to be created, displayed, and modified by the user through
 * interactions with this component
 * The class is declared <code>final</code> because much of the code in this design
 * are not reusable for extensions
 * </p>
 *
 * @author Yu Liu
 * @see ViewPort
 * @see Geometry
 */


public final class GraphView
        extends BufferedCanvas
        implements KeyListener, MouseListener, MouseMotionListener {

    /**
     * The minimum dimension that this component can be resized to
     */

    static final Dimension MINIMUM_DIMENSION;

    /**
     * The rate of update as set by a timer object
     */

    private static final int FRAME_RATE;

    /**
     * The amount of pan applied when the viewport updates
     */

    private static final int PAN;

    /**
     * The rate of zoom applied when the viewport updates
     */

    private static final double ZOOM;


    static {

        MINIMUM_DIMENSION = new Dimension(300, 300);

        FRAME_RATE = 15;

        PAN = 15;

        ZOOM = 1.05;

    }


    /**
     * The viewport of the component, updated when view changes with resize, zoom, or pan
     */

    private ViewPort viewPort = new ViewPort();


    /**
     * The list of all the points on the graph
     */

    private LinkedList<Point> points = new LinkedList<>();

    /**
     * The list of geometric objects that have been constructed
     */

    private LinkedList<Geometry> shapes = new LinkedList<>();

    /**
     * The x and y coordinate of the mouse(if mouse is inside the component)
     */

    private int mouseX, mouseY;


    /**
     * The current tool selected with the keyboard<br>
     * tool = 0 : Selection tool<br>
     * tool = 1 : Point tool<br>
     */

    private int tool;


    /**
     * Indicates whether the component has reset the view for the first time
     */

    private boolean viewHasReset;


    /**
     * Counts the selected and hidden entities
     */

    private int selected, hidden;


    /**
     * Initializes the component and start the timer
     * <p>
     * Calls super's constructor with title<br>
     * Assigns window, component, key, mouse, and mouse motion event listeners to this component<br>
     * Sets the default and minimum bounds of the window<br>
     * Reset the viewPort<br>
     * Inserts the default point entities<br>
     * Starts the update timer<br>
     * Sets window to visible<br>
     * </p>
     */

    GraphView() {

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                viewPort.setDefaultScale(getWidth() / 8);
                viewPort.setSize(getWidth(), getHeight());
                if (!viewHasReset) {
                    viewPort.resetView();
                    viewHasReset = true;
                }
            }
        });

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        setMinimumSize(MINIMUM_DIMENSION);

        Point p1 = new Point(0.5, 0);
        Point p2 = new Point(-0.5, 0);

        points.add(p1);
        points.add(p2);

        shapes.add(new Circle(p1, p2));
        shapes.add(new Circle(p2, p1));

        selected = 0;
        hidden = 0;

        Timer timer = new Timer(true);

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                viewPort.update();
                repaint();
            }
        }, 0, FRAME_RATE);

        requestFocus();
        requestFocusInWindow();

    }


    /**
     * Gets the tool label
     *
     * @return The label to be displayed of the current tool
     */

    private String getToolLabel() {

        switch (tool) {
            case 1:
                return "Point Tool";
            case 2:
                return "Circle Tool";
            default:
                return "Selection Tool";
        }

    }


    /**
     * Gets the view label
     *
     * @return The label to be displayed of the current view,
     * consisting of the width and height of the screen
     * as well as the zoom ratio
     */

    private String getViewLabel() {
        return getWidth() + " × " + getHeight() + " : " + (int) (viewPort.getRatio() * 100) + "%";
    }


    /**
     * Counts the number of entities selected and hidden
     *
     * @return The label to be displayed of the entities selected and hidden
     */

    private String getNumericalLabel() {
        return selected + " Selected, " + hidden + " Hidden";
    }


    /**
     * Determines if the mouse is hovering on any entities
     *
     * @return if the mouse is hovering on any entities
     */

    private boolean anyEntityHovered() {

        for (Geometry entity : shapes)
            if (entity.isMouseOver(mouseX, mouseY, viewPort))
                return true;

        for (Point point : points)
            if (point.isMouseOver(mouseX, mouseY, viewPort))
                return true;

        return false;
    }


    /**
     * Determines if any entities are selected
     *
     * @return if any entities are selected
     */

    private boolean anyEntitySelected() {

        for (Geometry entity : shapes)
            if (entity.isSelected())
                return true;

        for (Point point : points)
            if (point.isSelected())
                return true;

        return false;
    }


    /**
     * Executes the select action
     * <ul>
     * <li>If the mouse is not over any entities, deselect everything</li>
     * <li>If the mouse clicked an unselected entity, add it into the selection</li>
     * <li>If the mouse clicked a selected entity, remove it from the selection</li>
     * </ul>
     */

    private void selectEntities() {

        boolean over = anyEntityHovered();
        selected = 0;

        for (Point point : points) {
            point.setSelected(over && point.isSelected() ^ point.isMouseOver(mouseX, mouseY, viewPort));
            selected += point.isSelected() ? 1 : 0;
        }

        for (Geometry entity : shapes) {
            entity.setSelected(over && entity.isSelected() ^ entity.isMouseOver(mouseX, mouseY, viewPort));
            selected += entity.isSelected() ? 1 : 0;
        }


    }

    /**
     * Adds a fixed point onto the graph
     */

    private void addFixedPoint(double x, double y) {
        points.add(new Point(x, y));
    }


    /**
     * Inserts a point by computing the absolute position from the viewport
     */

    private void insertPoint() {

        if (!anyEntityHovered())
            addFixedPoint(viewPort.computeFromX(mouseX), viewPort.computeFromY(mouseY));

    }


    /**
     * Creates a list that include all item selected to be removed,
     * then subtract it from the {@link GraphView#shapes} list
     */

    private void deleteSelected() {

        LinkedList<Geometry> toBeRemoved = new LinkedList<>();

        for (Geometry entity : shapes)
            if (entity.isSelected())
                toBeRemoved.add(entity);

        shapes.removeAll(toBeRemoved);

    }


    /**
     * Executes the hide action
     * <ul>
     * <li>If no selection is made, show everything and select the entities that are unhidden</li>
     * <li>If some selection is made, set selected to hidden</li>
     * </ul>
     */

    private void hideSelectedOrShowAll() {

        boolean anySelected = anyEntitySelected();
        boolean either;
        hidden = 0;

        for (Point point : points) {

            either = point.isSelected() || point.isHidden();

            point.setHidden(anySelected && either);
            point.setSelected(!anySelected && either);

            hidden += anySelected && either ? 1 : 0;
        }

        for (Geometry entity : shapes) {

            either = entity.isSelected() || entity.isHidden();

            entity.setHidden(anySelected && either);
            entity.setSelected(!anySelected && either);

            hidden += anySelected && either ? 1 : 0;
        }

    }


    /**
     * Receives the event when user presses a key,
     * used for pan and zoom where the program
     * continues as long as the key is held down
     *
     * @param e the event that triggered this method
     */

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key) {

            case KeyEvent.VK_LEFT:
                viewPort.setPan(-PAN, 0);
                break;
            case KeyEvent.VK_RIGHT:
                viewPort.setPan(PAN, 0);
                break;
            case KeyEvent.VK_UP:
                viewPort.setPan(0, -PAN);
                break;
            case KeyEvent.VK_DOWN:
                viewPort.setPan(0, PAN);
                break;

            case KeyEvent.VK_MINUS:
                viewPort.setZoom(1 / ZOOM);
                break;
            case KeyEvent.VK_EQUALS:
                viewPort.setZoom(ZOOM);
                break;

            default:

        }
    }


    /**
     * Receives the event when user releases a key,
     * used to release
     * the continuous functions and trigger tool changes
     *
     * @param e the event that triggered this method
     */

    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        switch (key) {

            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_LEFT:
                viewPort.setPan(0, 0);
                break;

            case KeyEvent.VK_EQUALS:
            case KeyEvent.VK_MINUS:
                viewPort.setZoom(1);
                break;

            case KeyEvent.VK_BACK_SPACE:
                deleteSelected();
                break;

            case KeyEvent.VK_0:
                viewPort.resetView();
                break;
            case KeyEvent.VK_H:
                hideSelectedOrShowAll();
                break;

            case KeyEvent.VK_S:
                tool = 0;
                break;
            case KeyEvent.VK_P:
                tool = 1;
                break;
            case KeyEvent.VK_C:
                tool = 2;

            default:
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }


    @Override
    public void mouseExited(MouseEvent e) {

    }


    /**
     * Receives the event when user presses the mouse,
     * used to perform selections.
     * Sends the event to the appropriate methods depending on the tool
     *
     * @param e the event that triggered this method
     */

    @Override
    public void mousePressed(MouseEvent e) {

        switch (tool) {
            case 1:
                insertPoint();
            default:
                selectEntities();
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }


    /**
     * Receives the event when user moves the mouse.
     * Sets the class variables mouseX and mouseY
     * to be further processed.
     *
     * @param e the event that triggered this method
     */

    @Override
    public void mouseMoved(MouseEvent e) {

        mouseX = e.getX();
        mouseY = e.getY();

    }


    /**
     * Paints each of the object by calling its paint method,
     * along with the <code>Graphics</code> object and the viewport.
     * Then paints the labels on the bottom-left corner
     *
     * @param g the graphic context to be drawn on
     */

    @Override
    public void paint(Graphics g) {

        LinkedList<Geometry> paintLast = new LinkedList<>();

        for (Geometry entity : shapes) {
            entity.setHovered(entity.isMouseOver(mouseX, mouseY, viewPort));
            if (entity.isSelected() || entity.isHovered()) paintLast.add(entity);
            else entity.paint(g, viewPort);
        }

        for (Point point : points) {
            point.setHovered(point.isMouseOver(mouseX, mouseY, viewPort));
            if (point.isSelected() || point.isHovered()) paintLast.add(point);
            point.paint(g, viewPort);
        }

        for (Geometry entity : paintLast)
            entity.paint(g, viewPort);

        g.setColor(Color.BLUE);

        g.drawString(getToolLabel(), 10, getHeight() - 50);

        g.setColor(Color.GRAY);

        g.drawString(getViewLabel(), 10, getHeight() - 30);

        g.drawString(getNumericalLabel(), 10, getHeight() - 10);
    }
}
