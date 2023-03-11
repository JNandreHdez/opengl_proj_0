package shapes;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.opengltut.MyGLRenderer;
import com.example.opengltut.MyGLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    float w = MyGLSurfaceView.width;
    float h = MyGLSurfaceView.height;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    public float squareCoords[] = {
            -w/2,  h/2, 0.0f,   // top left
            -w/2, -h/2, 0.0f,   // bottom left
            w/2, -h/2f, 0.0f,   // bottom right
            w/2,  h/2f, 0.0f }; // top right

    public float triangleCoords[][] = new float[2][12];
    public Triangle triangles[] = new Triangle[2];

    public float color[] = { 1.0f, 0.5f, 0.5f, 1.0f};

    public void setCoords(float[] coords) {
        squareCoords = coords;
    }

    public void set_color(float[] color) {
        this.color = color;
    }


    public Square() {
        int i;
        for(i=0;i<9;i++) {
            triangleCoords[0][i]=squareCoords[i];
            if (i<3) {
                triangleCoords[1][i] = squareCoords[i];
            }
        }

        for(i=6;i<12;i++) {
            triangleCoords[1][i-3]=squareCoords[i];
        }

        triangles[0] = new Triangle(triangleCoords[0]);
        triangles[1] = new Triangle(triangleCoords[1]);
    }

    public Square(float[] squareCoords) {
        this.squareCoords=squareCoords;

        int i;
        for(i=0;i<9;i++) {
            triangleCoords[0][i]=squareCoords[i];
            if (i<3) {
                triangleCoords[1][i] = squareCoords[i];
            }
        }

        for(i=6;i<12;i++) {
            triangleCoords[1][i-3]=squareCoords[i];
        }

        triangles[0] = new Triangle(triangleCoords[0]);
        triangles[1] = new Triangle(triangleCoords[1]);
    }

    public Square(float[] squareCoords, float[] color) {
        this.squareCoords=squareCoords;
        this.color=color;

        int i;
        for(i=0;i<9;i++) {
            triangleCoords[0][i]=squareCoords[i];
            if (i<3) {
                triangleCoords[1][i] = squareCoords[i];
            }
        }

        for(i=6;i<12;i++) {
            triangleCoords[1][i-3]=squareCoords[i];
        }

        triangles[0] = new Triangle(triangleCoords[0]);
        triangles[1] = new Triangle(triangleCoords[1]);
    }

    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public void draw(float[] mvpMatrix) {
        triangles[0].draw(mvpMatrix);
        triangles[1].draw(mvpMatrix);
    }
}
