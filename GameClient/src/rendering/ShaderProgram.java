package rendering;

import maths.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

public class ShaderProgram {

    private final int id;

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

    public void attachShader(Shader shader) {
        glAttachShader(id, shader.getID());
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
            value.toBuffer(buffer);
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
