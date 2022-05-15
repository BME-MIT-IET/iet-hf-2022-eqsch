package UI.Components;

/**
 * a camera class for zooming and moving around the map
 */
public class Camera {
    /**
     * the camera's x coordinate of what we want to focus on
     */
    double x;
    /**
     * the camera's y coordinate of what we want to focus on
     */
    double y;
    /**
     * the zoom value of the camera
     */
    double zoom;

    public Camera(double X,double Y,double Zoom){
        x=X;
        y=Y;
        zoom=Zoom;
    }

    /**
     * getter for x
     * @return the value of x
     */
    public double getX(){
        return x;
    }

    /**
     * getter for y
     * @return the value of y
     */
    public double getY(){
        return y;
    }

    /**
     * setter for x
     * @param X the new value
     */
    public void setX(double X){
        x = X;
    }

    /**
     * setter for y
     * @param Y the new value
     */
    public void setY(double Y){
        y = Y;
    }

    /**
     * getter for zoom
     * @return the value of zoom
     */
    public double getZoom(){
        return zoom;
    }

    /**
     * setter for zoom
     * @param z the new value
     */
    public void setZoom(double z){
        zoom = z;
    }

    /**
     * shifts the given x coordinate.
     * @param X the x coordinate
     * @return the shifted value
     */
    public double TransformX(double X){
        return (X- x) * zoom;
    }

    /**
     * shifts the given y coordinate
     * @param Y the y coordinate
     * @return the shifted value
     */
    public double TransformY(double Y){
        return (Y - y) * zoom;
    }

    /**
     * transforms the given width value base on the current zoom value
     * @param w the width we want to transform
     * @return the transformed width
     */
    public double TransformWidth(double w){
        return w * zoom;
    }

    /**
     * transforms the given height value based on the current zoom value
     * @param h the height we want to transform
     * @return the transformed height
     */
    public double TransformHeight(double h){
        return h * zoom;
    }
}
