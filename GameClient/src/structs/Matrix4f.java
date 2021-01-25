package structs;

public class Matrix4f { //this weird sht allows u to rotate and transform the graphics in the game screen

    //its a matrix, so its a matrix 4x4


    public float[] matrix = new float[16];

    //this is an identity matrix
    //1 0 0 0
    //0 1 0 0
    //0 0 1 0
    //0 0 0 1
    public static Matrix4f identity() {
        Matrix4f res = new Matrix4f();

        for (int x = 0; x < 16; x++) {
            res.matrix[x] = 0.f; //init 16 zeroes
        }

        res.matrix[0] = 1f;
        res.matrix[5] = 1f;
        res.matrix[10] = 1f;
        res.matrix[15] = 1f;
        return res;
    }

    public Matrix4f multiply(Matrix4f matrix) { //multiply self by the other matrix
        Matrix4f res = new Matrix4f();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                float sum = 0.f;
                for (int e = 0; e < 4; e++) {
                    sum += this.matrix[e + y * 4] * matrix.matrix[x + e * 4];
                }
                res.matrix[x + y * 4] = sum;
            }
        }
        return res;
    }

    public static Matrix4f translate(GPosition pos) {
        Matrix4f res = identity();

        res.matrix[12] = (float)pos.getPosX();
        res.matrix[13] = (float)pos.getPosY();
        res.matrix[14] = 0f; //(float)pos.getPosZ(); we dont have a Z pos right now

        return res;
    }

    //its magic idk, only rotating on Z axis
    public static Matrix4f rotate(float angle) {
        Matrix4f res = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        res.matrix[0] = cos;
        res.matrix[1] = sin;

        res.matrix[4] = -sin;
        res.matrix[5] = cos;

        return res;
    }

    //orthographic graphics for 2d, no perspective
    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f res = identity();

        res.matrix[0] = 2.f / (right - left);
        res.matrix[5] = 2.f / (top - bottom);
        res.matrix[10] = 2.f / (near - far);
        res.matrix[12] = (left + right) / (left - right);
        res.matrix[13] = (bottom + top) / (bottom - top);
        res.matrix[14] = (near + far) / (far - near);

        return res;
    }
}
