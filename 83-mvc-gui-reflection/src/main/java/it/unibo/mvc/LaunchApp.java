package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//import it.unibo.mvc.api.DrawNumber;
import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;
//import it.unibo.mvc.view.DrawNumberStdoutView;
//import it.unibo.mvc.view.DrawNumberSwingView;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) {
        //Previous implementation
        /* */
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        /* 
        app.addView(new DrawNumberSwingView());
        app.addView(new DrawNumberSwingView());
        app.addView(new DrawNumberStdoutView());
        */
        try {
            final Class<?> cls1 = Class.forName("it.unibo.mvc.view.DrawNumberStdoutView");
            final Class<?> cls2 = Class.forName("it.unibo.mvc.view.DrawNumberSwingView");
            final Constructor<?> construct1 = cls1.getConstructor(); 
            final Constructor<?> construct2 = cls2.getConstructor();
            for(int i = 0 ; i < 3; i++) {
                app.addView( (DrawNumberView)construct1.newInstance());
                app.addView( (DrawNumberView)construct2.newInstance());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException | SecurityException e) {
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
            System.out.println(e.getMessage());
        } 
    }
}
