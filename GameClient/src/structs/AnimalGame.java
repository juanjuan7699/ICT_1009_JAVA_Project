package structs;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import rendering.Renderer;
import rendering.Window;
import states.GameState;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.*;

public class AnimalGame {
    public static final int TARGET_FPS = 144;
    public static final int TARGET_UPS = 30;

    private GLFWErrorCallback errorCallback;

    protected boolean running;

    protected Window window;
    protected GameTimer timer;
    protected Renderer renderer;

    protected GameState state;

    private int width; //for initial use only
    private int height;

    /**
     * Default contructor for the game.
     */
    public AnimalGame(int width, int height) {
        timer = new GameTimer();
        renderer = new Renderer();
        state = new GameState(renderer);
        this.width = width;
        this.height = height;
    }

    //init, then loop, at end of loop dispose
    public void start() {
        init();
        gameLoop();
        dispose();
    }

    /**
     * Releases resources that where used by the game.
     */
    public void dispose() {
        //renderer.dispose();
        window.destroy();
        glfwTerminate();
        errorCallback.free();
    }

    public void init() {
        errorCallback = GLFWErrorCallback.createPrint();
        glfwSetErrorCallback(errorCallback);

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        window = new Window(width, height, "Animal Game", true);

        /* Initialize stuff */
        timer.init();
        renderer.init();
        initStates();

        running = true;
    }

    /**
     * Initializes the states.
     */
    public void initStates() {
        state.exit();
        state.enter(); //re init

        //debug
        state.testAddPlayer();
    }

    public void gameLoop() {
        float delta;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;
        float alpha;

        while (running) {
            /* Check if game should close */
            if (window.isClosing()) {
                running = false;
            }

            /* Get delta time and update the accumulator */
            delta = timer.getDelta();
            accumulator += delta;

            /* Handle input */
            input();

            /* Update game and timer UPS if enough time has passed */
            while (accumulator >= interval) {
                update();
                timer.updateUPS();
                accumulator -= interval;
            }

            /* Calculate alpha value for interpolation */
            alpha = accumulator / interval;

            /* Render game and update timer FPS */
            render(alpha);
            timer.updateFPS();
            timer.update();

            //TODO debug stuff
            renderer.drawText("FPS: " + timer.getFPS() + " | Tickrate: " + timer.getUPS(), 5, 25);
            renderer.drawText("Context: " + (AnimalGame.isDefaultContext() ? "3.2 core" : "2.1"), 5, 5);

            /* Update window to show the new screen */
            window.update();

            /* Synchronize if v-sync is disabled */
            if (!window.isVSyncEnabled()) {
                sync(TARGET_FPS);
            }
        }
    }

    public void input() {
        state.input();
    }

    public void update() {
        state.update();
    }

    //direct render
    public void render() {
        state.render();
    }

    //render with interpolation alpha
    public void render(float alpha) {
        state.render(alpha);
    }


    public void sync(int fps) { //for vsync
        double lastLoopTime = timer.getLastLoopTime();
        double now = timer.getTime();
        float targetTime = 1f / fps;

        while (now - lastLoopTime < targetTime) {
            Thread.yield();
            try {
                Thread.sleep(1); //no sleep means cpu at 100% always
            } catch (InterruptedException ex) {
                System.out.println("Error sleeping: " + ex.getMessage());
            }

            now = timer.getTime();
        }
    }

    // check opengl 32
    public static boolean isDefaultContext() {
        return GL.getCapabilities().OpenGL32;
    }

}
