
import rendering.Renderer;
import states.GameState;
import structs.*;

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
    public Renderer renderer;

    /**
     * This error callback will simply print the error to
     * <code>System.err</code>.
     */
    private static GLFWErrorCallback erroCallback
            = GLFWErrorCallback.createPrint(System.err);

    /**
     * This key callback will check if ESC is pressed and will close the window
     * if it is pressed.
     */
    private static GLFWKeyCallback keyCallback = new GLFWKeyCallback() {

        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
            }
        }
    };

    public static void main(String[] args) {
//        for (int i = 0; i < 5; i++) { //check if all uids are unique
//            Player p = new Player(); //player will auto generate a UUID
//            p.tryTeleportToLocation(new GPosition(0,0));
//            System.out.println(p.getUID());
//        }
//
//        Player p = new Player(); //player will auto generate a UUID
//        p.setCurrentLocation(new GPosition(100, 200));
//        System.out.println(p.getCurrentLocation().getRangeTo(new GPosition(200, 250)));

        //lwjgl tests
        //new Main().run();
        new AnimalGame(1280,720).start();
    }

    public static void hello() {
        long window;

        /* Set the error callback */
        glfwSetErrorCallback(erroCallback);

        /* Initialize GLFW */
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        /* Create window */
        window = glfwCreateWindow(640, 480, "Simple example", NULL, NULL);
        if (window == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }

        /* Center the window on screen */
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window,
                (vidMode.width() - 640) / 2,
                (vidMode.height() - 480) / 2
        );

        /* Create OpenGL context */
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        /* Enable vertical synchronization */
        glfwSwapInterval(1);

        /* Set the key callback */
        glfwSetKeyCallback(window, keyCallback);

        /* Declare buffers for using inside the loop */
        IntBuffer width = MemoryUtil.memAllocInt(1);
        IntBuffer height = MemoryUtil.memAllocInt(1);

        /* Loop until window gets closed */
        while (!glfwWindowShouldClose(window)) {
            float ratio;

            /* Get width and height to calcualte the ratio */
            glfwGetFramebufferSize(window, width, height);
            ratio = width.get() / (float) height.get();

            /* Rewind buffers for next get */
            width.rewind();
            height.rewind();

            /* Set viewport and clear screen */
            glViewport(0, 0, width.get(), height.get());
            glClear(GL_COLOR_BUFFER_BIT);

            /* Set ortographic projection */
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(-ratio, ratio, -1f, 1f, 1f, -1f);
            glMatrixMode(GL_MODELVIEW);

            /* Rotate matrix */
            glLoadIdentity();
            glRotatef((float) glfwGetTime() * 50f, 0f, 0f, 1f);

            /* Render triangle */
            glBegin(GL_TRIANGLES);
            glColor3f(1f, 0f, 0f);
            glVertex3f(-0.6f, -0.4f, 0f);
            glColor3f(0f, 1f, 0f);
            glVertex3f(0.6f, -0.4f, 0f);
            glColor3f(0f, 0f, 1f);
            glVertex3f(0f, 0.6f, 0f);
            glEnd();

            /* Swap buffers and poll Events */
            glfwSwapBuffers(window);
            glfwPollEvents();

            /* Flip buffers for next loop */
            width.flip();
            height.flip();
        }

        /* Free buffers */
        MemoryUtil.memFree(width);
        MemoryUtil.memFree(height);

        /* Release window and its callbacks */
        glfwDestroyWindow(window);
        keyCallback.free();

        /* Terminate GLFW and release the error callback */
        glfwTerminate();
        erroCallback.free();

    }
    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        timer = new GameTimer();
        renderer = new Renderer();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

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
        try ( MemoryStack stack = stackPush() ) {
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
        }



        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities(); //create  openGL

        //init game stuff
        timer.init();
        renderer.init();
    }

    private void loop() { //game loop will be FIXED instead of based on FPS
        float delta; //the delta time for ticking
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;
        float alpha;

        //GL.createCapabilities();

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
            renderer.drawTextureRegion(50,50,100,100, 50,50,80,80, Color.RED);
            renderer.flush();
        }
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        //dispose here

        // when loop() ends it will free and destroy
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();

        glfwSetErrorCallback(null).free();
    }


}
