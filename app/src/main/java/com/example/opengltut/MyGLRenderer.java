package com.example.opengltut;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import shapes.Square;
import shapes.Triangle;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle triangle;
    private Triangle triangle2;
    private Triangle triangle3;

    private Square square;
    public Triangle[] triangles = new Triangle[32];
    private int tiles_per_height = 100;
    private int tiles_per_width = 180;
    //private float top_left[] = {};

    public Square[][] grid = new Square[tiles_per_height][tiles_per_width];

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    public int triangle_count = 0;

    float w = MyGLSurfaceView.width;
    float h = MyGLSurfaceView.height;

    void single_square(int row, int col) {
        float coords[] = {
                -w / 2 + col * w / tiles_per_width, -0.28f + row * h / tiles_per_height, 0.0f,   // top left
                -w / 2 + col * w / tiles_per_width, -0.28f + h / 10 - (row + 1) * h / tiles_per_height, 0.0f,   // bottom left
                -w / 2 - w / 100 + (col + 1) * w / tiles_per_width, -0.28f + h / 10 - (row + 1) * h / tiles_per_height, 0.0f,   // bottom right
                -w / 2 - w / 100 + (col + 1) * w / tiles_per_width, -0.28f + row * h / tiles_per_height, 0.0f}; // top right
        float c[] = {1.0f, 0.6f, 0, 1.0f};
        grid[row][col] = new Square(coords, c);
        if (true) {
            // float c[] = {0,0,1.0f,1.0f};
            grid[row][col].set_color(c);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.05f, 0.05f, 0.05f, 1f);

        float triangle_coords[] = {
                0.0f, 0.6222f, 0.0f,
                -0.5f, -0.311f, 0.0f,
                0.5f, -0.311f, 0.0f
        };
        float triangle2_coords[] = {
                -0.5f, 0.6222f, 0.0f,
                -1.0f, -0.311f, 0.0f,
                0.00f, -0.311f, 0.0f
        };
        float triangle3_coords[] = {
                -0.25f, 0.1556f, 0.0f,
                -0.75f, -0.7777f, 0.0f,
                0.25f, -0.7777f, 0.0f
        };
        try {
            square = new Square();
        } catch (Exception e) {
            System.out.println("========================================");
            System.out.println(e.getMessage());
            System.out.println("========================================");
        }

        // number of coordinates per vertex in this array

        try {

        } catch(Exception e) {
            System.out.println("=================================");
            System.out.println(e.getMessage());
            System.out.println("=================================");
        }

        /*
                    float c[] = grid[row][col].color;
                    c[0]+=row*col*0.25f;
                    c[1]+=row*col*0.15f;
                    c[2]+=row*col*0.175f;
                    grid[row][col].set_color(c);
                    System.out.println("=================================");
                    for(float rgb: grid[row][col].color) {
                        System.out.println(rgb);
                    }
                    System.out.println("=================================");

                     */

        triangle = new Triangle(triangle_coords);
        triangles[0] = triangle;
        triangle2 = new Triangle(triangle2_coords);
        triangles[1] = triangle2;
        triangle3 = new Triangle(triangle3_coords);
        triangles[2] = triangle3;
        float colors[][] = {
                {0.0f,1.0f,1.0f,1.0f},
                {0.0f,1.0f,1.0f,1.0f},
                {1.0f,1.0f,0.0f,1.0f}
        };
        triangles[2].set_color(colors[2]);
        triangle_count++;
        //single_square(0,0);
        //single_square(0,3);
        //single_square(3,0);
        //single_square(3,3);

        float grid_coords[][][] = new float[tiles_per_height][tiles_per_width][12];

        for(int row=0;row<tiles_per_height;row++) {
            for(int col=0;col<tiles_per_width;col++) {
                float coords[] = {
                        -w/2+col*w/tiles_per_width, h/2-row*h/tiles_per_height, 0.0f,   // top left
                        -w/2+col*w/tiles_per_width, 0.005f+h/2-(row+1)*h/tiles_per_height, 0.0f,   // bottom left
                        -0.005f-w/2+(col+1)*w/tiles_per_width, 0.005f+h/2-(row+1)*h/tiles_per_height, 0.0f,   // bottom right
                        -0.005f-w/2+(col+1)*w/tiles_per_width, h/2-row*h/tiles_per_height, 0.0f }; // top right
                float c[] = {1.0f,0.6f,0.0f,1.0f};
                grid_coords[row][col] = coords;
                grid[row][col] = new Square(coords);
                grid[row][col].set_color(c);
            }
        }


        float squareCoords[] = {
                -w / 2, h / 2, 0.0f,   // top left
                -w / 2, 0, 0.0f,   // bottom left
                0, 0, 0.0f,   // bottom right
                0, h / 2f, 0.0f}; // top right

        float squareCoords2[] = {
                0, h / 2, 0.0f,   // top left
                0, 0, 0.0f,   // bottom left
                w / 2, 0, 0.0f,   // bottom right
                w / 2, h / 2f, 0.0f}; // top right

        //grid[0][0] = new Square(squareCoords);
        //grid[0][1] = new Square(squareCoords2);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    private float[] rotationMatrix = new float[16];

    private void drawGrid() {
        // Draw Square

        float[] sQscratch = new float[16];

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        // Create a rotation transformation for the triangle

        Matrix.setRotateM(rotationMatrix, 0, 0, 0, 0, -1.0f);
        // Combine the rotation matrix with the projection and camera view
        // Note that the vPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(sQscratch, 0, vPMatrix, 0, rotationMatrix, 0);
        square.draw(sQscratch);

        for (Square[] row: grid) {
            for (Square square: row) {
                if (square!=null) {
                    //float[] color = {1.0f,0.8f,0.0f,1.0f};

                    //square.set_color(color);
                    square.draw(sQscratch);
                }
            }
        }
    }

    private void drawTriangles() {
        float color[] = { 0.0f, 0.5f, 1.0f, 1.0f};
        square.triangles[0].set_color(color);
        square.triangles[1].set_color(color);

        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);

        // Draw triangle
        for (Triangle t: triangles) {
            if (t!=null) {
                float[] scratch = new float[16];

                // Set the camera position (View matrix)
                Matrix.setLookAtM(viewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

                // Calculate the projection and view transformation
                Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

                time = SystemClock.uptimeMillis() % 4000L;
                angle = 0.090f * ((int) time);

                Matrix.setRotateM(rotationMatrix, 0, mAngle, 0, 0, -1.0f);

                // Combine the rotation matrix with the projection and camera view
                // Note that the vPMatrix factor *must be first* in order
                // for the matrix multiplication product to be correct.
                Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);
                String coords = "";
                for (float coord: t.triangleCoords) {
                    coords += String.format("%f\t",coord);
                }
                //System.out.println(coords);
                t.draw(scratch);
                //System.out.println("Triangle drawn");
            } else {

                //System.out.println("Slot not filled");
            }

        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        try {
            //drawGrid();
        } catch(Exception e) {
            System.out.println("=================================");
            System.out.println(e.getMessage());
            System.out.println("=================================");
        }
        try {
            drawTriangles();
        } catch(Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getStackTrace().toString());
        }


    }

    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }
}
