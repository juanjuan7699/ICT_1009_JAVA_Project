import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import rendering.Texture;
import structs.AnimalGame;
import structs.Color;
import structs.Player;

import static org.lwjgl.glfw.GLFW.*;


public class Main {
    public static AnimalGame animalGame;

    private static GLFWErrorCallback erroCallback
            = GLFWErrorCallback.createPrint(System.err);

    private static GLFWKeyCallback keyCallback = new GLFWKeyCallback() {

        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
            }
        }
    };

    public static void main(String[] args) {

        new AnimalGame(1280,720).start();
    }

}
