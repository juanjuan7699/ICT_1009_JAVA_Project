
import states.GameState;
import structs.GPosition;
import structs.GameTimer;
import structs.Player;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Main {
    //the lower the delay, the more accurate the game is but the higher cpu usage it takes.!!do not set to 0 or 100% cpu usage
    public static long tickDelay = 50; //1000 is 1 tick per second
    public GameState gameStateInstance; //change to array for multiple matches, but probably not

    private long window; //its a long, i dk

    //LWJGL things
    public static final int TARGET_FPS = 61;
    public static final int TARGET_UPS = 30;
    private GLFWErrorCallback errorCallback;
    private boolean running;
    public GameTimer timer; //our main calculator


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) { //check if all uids are unique
            Player p = new Player(); //player will auto generate a UUID
            p.tryTeleportToLocation(new GPosition(0,0));
            System.out.println(p.getUID());
        }

        Player p = new Player(); //player will auto generate a UUID
        p.setCurrentLocation(new GPosition(100, 200));
        System.out.println(p.getCurrentLocation().getRangeTo(new GPosition(200, 250)));

        //lwjgl tests
        new Main().run();


    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        timer = new GameTimer();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) { //unfortunately java does not usually do manual mem alloc but LWJGL has some helpers
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        //init game stuff
        timer.init();

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() { //game loop will be FIXED instead of based on FPS
        float delta; //the delta time for ticking
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;
        float alpha;


        GL.createCapabilities(); //create  openGL
        glClearColor(1.0f, 1.0f, 0.5f, 0.0f); //when you gl clear window it turns back to this color


        while ( !glfwWindowShouldClose(window) ) { //the game loop here TODO
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();


            delta = timer.getDelta();
            accumulator += delta;

            //handle input here

            //update the stuff here
            while (accumulator >= interval) {
                //here
                timer.updateUPS();
                accumulator -= interval;
            }

            alpha = accumulator / interval; //interpolation value
            //do a render with the alpha value as interpolation

            timer.updateFPS();
            timer.update();

            //TODO: render everything here
            //TODO: update to show next frame


        }
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // when loop() ends it will free and destroy
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
