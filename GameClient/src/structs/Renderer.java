package structs;


import sun.font.TrueTypeFont;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    //do rendering stuff here
    private Font font;

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void renderText(CharSequence text, GPosition pos, int size) { //simple text rendering, Graphics2D

    }

    private void createFontTexture() { //generate the font texture to display on screen
        int imageWidth = 0;
        int imageHeight = 0;
    }


}
