package structs;


import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.*;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Font.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    //do rendering stuff here
    private VertexArrayObject vao;
    private VertexBufferObject vbo;
    private ShaderProgram program;

    private FloatBuffer vertices;
    private int numVertices;
    private boolean drawing;

    private FontRender font;

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * Begin rendering.
     */
    public void begin() {
        if (drawing) {
            throw new IllegalStateException("Renderer is already drawing!");
        }
        drawing = true;
        numVertices = 0;
    }

    /**
     * End rendering.
     */
    public void end() {
        if (!drawing) {
            throw new IllegalStateException("Renderer isn't drawing!");
        }
        drawing = false;
        flush();
    }

    /**
     * Flushes the data to the GPU to let it get rendered.
     */
    public void flush() {
        if (numVertices > 0) {
            vertices.flip();

            if (vao != null) {
                vao.bind();
            } else {
                vbo.bind(GL_ARRAY_BUFFER);
                specifyVertexAttributes();
            }
            program.use();

            /* Upload the new vertex data */
            vbo.bind(GL_ARRAY_BUFFER);
            vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);

            /* Draw batch */
            glDrawArrays(GL_TRIANGLES, 0, numVertices);

            /* Clear vertex data for next batch */
            vertices.clear();
            numVertices = 0;
        }
    }

    private void specifyVertexAttributes() {
        /* Specify Vertex Pointer */
        int posAttrib = program.getAttributeLocation("position");
        program.enableVertexAttribute(posAttrib);
        program.pointVertexAttribute(posAttrib, 2, 8 * Float.BYTES, 0);

        /* Specify Color Pointer */
        int colAttrib = program.getAttributeLocation("color");
        program.enableVertexAttribute(colAttrib);
        program.pointVertexAttribute(colAttrib, 4, 8 * Float.BYTES, 2 * Float.BYTES);

        /* Specify Texture Pointer */
        int texAttrib = program.getAttributeLocation("texcoord");
        program.enableVertexAttribute(texAttrib);
        program.pointVertexAttribute(texAttrib, 2, 8 * Float.BYTES, 6 * Float.BYTES);
    }

    //TODO: Color must be in range of 0f to 1f, make a custom object
    public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth, float regHeight, Color c) {
        /* Vertex positions */
        float x2 = x + regWidth;
        float y2 = y + regHeight;

        /* Texture coordinates */
        float s1 = regX / texture.getWidth();
        float t1 = regY / texture.getHeight();
        float s2 = (regX + regWidth) / texture.getWidth();
        float t2 = (regY + regHeight) / texture.getHeight();

        drawTextureRegion(x, y, x2, y2, s1, t1, s2, t2, c);
    }

    public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, Color c) {
        if (vertices.remaining() < 8 * 6) {
            /* We need more space in the buffer, so flush it */
            flush();
        }

        float r = c.getRed();
        float g = c.getGreen();
        float b = c.getBlue();
        float a = c.getAlpha();

        vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(x1).put(y2).put(r).put(g).put(b).put(a).put(s1).put(t2);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);

        vertices.put(x1).put(y1).put(r).put(g).put(b).put(a).put(s1).put(t1);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(a).put(s2).put(t2);
        vertices.put(x2).put(y1).put(r).put(g).put(b).put(a).put(s2).put(t1);

        numVertices += 6;
    }

    public void renderText(CharSequence text, GPosition pos, int size) { //simple text rendering, Graphics2D
        font.drawText(this, text, pos.getX(), pos.getY());
    }

    private void createFontTexture() { //generate the font texture to display on screen
        int imageWidth = 0;
        int imageHeight = 0;
    }

}

class FontRender {

    private final Map<Character, Glyph> glyphs; //save the font glyphs
    private final Texture texture;
    private int fontHeight;

    public FontRender(int size, boolean antiAlias) {
        glyphs = new HashMap<>();
        texture = createFontTexture(new java.awt.Font(MONOSPACED, PLAIN, size), antiAlias);
    }

    private Texture createFontTexture(java.awt.Font font, boolean antiAlias) { //generate the fonts
        int imageWidth = 0;
        int imageHeight = 0;
        int x = 0;

        //generate all fonts after 32 to 256 via ASCII
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                //skip control code
                continue;
            }
            char c = (char) i;
            BufferedImage ch = createCharImage(font, c, antiAlias);
            if (ch == null) {
                //if the char doesnt exist then it doesnt exist in the font file
                continue;
            }

            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        fontHeight = imageHeight;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        //same loop but for glyphs
        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                continue;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null) {
                continue;
            }

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            Glyph ch = new Glyph(charWidth, charHeight, x, image.getHeight() - charHeight, 0f);
            g.drawImage(charImage, x, 0, null);
            x += ch.width;
            glyphs.put(c, ch);
        }

        /* Flip image Horizontal to get the origin to bottom left */
        AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
        transform.translate(0, -image.getHeight());
        AffineTransformOp operation = new AffineTransformOp(transform,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = operation.filter(image, null);

        /* Get charWidth and charHeight of image */
        int width = image.getWidth();
        int height = image.getHeight();

        /* Get pixel data of image */
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        /* Put pixel data into a ByteBuffer */
        ByteBuffer buffer = MemoryUtil.memAlloc(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                /* Pixel as RGBA: 0xAARRGGBB */
                int pixel = pixels[i * width + j];
                /* Red component 0xAARRGGBB >> 16 = 0x0000AARR */
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                /* Green component 0xAARRGGBB >> 8 = 0x00AARRGG */
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                buffer.put((byte) (pixel & 0xFF));
                /* Alpha component 0xAARRGGBB >> 24 = 0x000000AA */
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();

        Texture fontTexture = Texture.createTexture(width, height, buffer);
        MemoryUtil.memFree(buffer);
        return fontTexture;
    }

    private BufferedImage createCharImage(java.awt.Font font, char c, boolean antiAlias) {
        /* Creating temporary image to extract character size */
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        /* Get char charWidth and charHeight */
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        /* Check if charWidth is 0 */
        if (charWidth == 0) {
            return null;
        }

        /* Create image for holding the char */
        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(java.awt.Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    public int getWidth(CharSequence text) {
        int width = 0;
        int lineWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                width = Math.max(width, lineWidth);
                lineWidth = 0;
                continue;
            }
            if (c == '\r') {
                continue;
            }
            Glyph g = glyphs.get(c);
            lineWidth += g.width;
        }
        width = Math.max(width, lineWidth);
        return width;
    }

    public int getHeight(CharSequence text) {
        int height = 0;
        int lineHeight = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                /* Line end, add line height to stored height */
                height += lineHeight;
                lineHeight = 0;
                continue;
            }
            if (c == '\r') {
                /* Carriage return, just skip it */
                continue;
            }
            Glyph g = glyphs.get(c);
            lineHeight = Math.max(lineHeight, g.height);
        }
        height += lineHeight;
        return height;
    }

    public void drawText(Renderer renderer, CharSequence text, float x, float y, Color c) {
        int textHeight = getHeight(text);

        float drawX = x;
        float drawY = y;
        if (textHeight > fontHeight) {
            drawY += textHeight - fontHeight;
        }

        texture.bind();
        renderer.begin();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                /* Line feed, set x and y to draw at the next line */
                drawY -= fontHeight;
                drawX = x;
                continue;
            }
            if (ch == '\r') {
                /* Carriage return, just skip it */
                continue;
            }
            Glyph g = glyphs.get(ch);
            renderer.drawTextureRegion(texture, drawX, drawY, g.x, g.y, g.width, g.height, c);
            drawX += g.width;
        }
        renderer.end();
    }

    public void drawText(Renderer renderer, CharSequence text, float x, float y) { //overload
        drawText(renderer, text, x, y, Color.WHITE);
    }

    public void dispose() {
        texture.delete();
    }
}

class Glyph { //font glyph for rendering in the renderer
    public final int width;
    public final int height;
    public final int x;
    public final int y;
    public final float advance;

    public Glyph(int width, int height, int x, int y, float advance) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.advance = advance;
    }
}

class Texture { //texture object for lwjgl to render in the Renderer

    private final int id;
    private int width;
    private int height;

    public Texture() {
        id = glGenTextures();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void setParameter(int name, int value) {
        glTexParameteri(GL_TEXTURE_2D, name, value);
    }

    public void uploadData(int width, int height, ByteBuffer data) { //overload for upload data
        uploadData(GL_RGBA8, width, height, GL_RGBA, data);
    }

    public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL_UNSIGNED_BYTE, data);
    }

    public void delete() {
        glDeleteTextures(id);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }

    public static Texture createTexture(int width, int height, ByteBuffer data) {
        Texture texture = new Texture();
        texture.setWidth(width);
        texture.setHeight(height);

        texture.bind();

        texture.setParameter(GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        texture.setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture.setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        texture.uploadData(GL_RGBA8, width, height, GL_RGBA, data);

        return texture;
    }

    public static Texture loadTexture(String path) { //to load the texture
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            /* Prepare image buffers */
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            /* Load image */
            stbi_set_flip_vertically_on_load(true);
            image = stbi_load(path, w, h, comp, 4);
            if (image == null) {
                return null;
            }

            /* Get width and height of image */
            width = w.get();
            height = h.get();
        }

        return createTexture(width, height, image);
    }
}

class VertexArrayObject {
    private final int id;

    public VertexArrayObject() {
        id = glGenVertexArrays();
    }

    public void bind() {
        glBindVertexArray(id);
    }

    public void delete() {
        glDeleteVertexArrays(id);
    }

    public int getID() {
        return id;
    }

}

class VertexBufferObject {

    private final int id;

    public VertexBufferObject() {
        id = glGenBuffers();
    }

    public void bind(int target) {
        glBindBuffer(target, id);
    }

    public void uploadData(int target, FloatBuffer data, int usage) {
        glBufferData(target, data, usage);
    }

    public void uploadData(int target, long size, int usage) {
        glBufferData(target, size, usage);
    }

    public void uploadSubData(int target, long offset, FloatBuffer data) {
        glBufferSubData(target, offset, data);
    }

    public void uploadData(int target, IntBuffer data, int usage) {
        glBufferData(target, data, usage);
    }

    public void delete() {
        glDeleteBuffers(id);
    }

    public int getID() {
        return id;
    }

}

class ShaderProgram {

    /**
     * Stores the handle of the program.
     */
    private final int id;

    /**
     * Creates a shader program.
     */
    public ShaderProgram() {
        id = glCreateProgram();
    }

    public void bindFragmentDataLocation(int number, CharSequence name) {
        glBindFragDataLocation(id, number, name);
    }

    public void link() {
        glLinkProgram(id);
        checkStatus();
    }

    public int getAttributeLocation(CharSequence name) {
        return glGetAttribLocation(id, name);
    }

    public void enableVertexAttribute(int location) {
        glEnableVertexAttribArray(location);
    }

    public void disableVertexAttribute(int location) {
        glDisableVertexAttribArray(location);
    }

    public void pointVertexAttribute(int location, int size, int stride, int offset) {
        glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset);
    }

    public int getUniformLocation(CharSequence name) {
        return glGetUniformLocation(id, name);
    }

    public void setUniform(int location, int value) {
        glUniform1i(location, value);
    }

    public void setUniform(int location, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4 * 4);
            //value.toBuffer(buffer);
            glUniformMatrix4fv(location, false, buffer);
        }
    }

    public void use() {
        glUseProgram(id);
    }

    public void checkStatus() {
        int status = glGetProgrami(id, GL_LINK_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(id));
        }
    }

    public void delete() {
        glDeleteProgram(id);
    }

}