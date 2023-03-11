package com.example.opengltut;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import shapes.Triangle;
import com.example.opengltut.MyGLRenderer;

public class MyGLSurfaceView extends GLSurfaceView {

    public static float width = 3.8f;
    public static float height = 1.8f;

    private final MyGLRenderer renderer;

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    public float x;
    public float y;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        x = e.getX();
        y = e.getY();

        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:
                float color[] = renderer.triangles[1].color;

                if (color[0]==1.0f||color[1]==1.0f||color[2]==1.0f) {
                    System.out.println("1.0 reached");
                    color[0]-=0.05f;
                    color[1]+=0.05f;
                    color[2]+=0.05f;
                } else {
                    color[0]-=0.05f;
                    color[1]+=0.05f;
                    color[2]+=0.05f;
                }

                renderer.triangles[1].set_color(color);
                String s = String.format("Triangle 2 RGB values:\n\tr: %f\n\tg: %f\n\tb: %f",color[0],color[1],color[2]);
                System.out.println(s);
                /*
                System.out.println("x: "+x+"\ty: "+y);
                System.out.println(renderer.triangle_count);
                if (renderer.triangle_count<32) {
                    float[] triangle_coords = {
                            0.0f + (x/2340), 0.6222f - (y/1080), 0.0f,
                            -0.5f - (x/2340), -0.311f - (y/1080), 0.0f,
                            0.5f - (x/2340), -0.311f - (y/1080), 0.0f
                    };
                    renderer.triangles[renderer.triangle_count] = new Triangle(triangle_coords);
                    float inc = 0.05f * renderer.triangle_count;
                    float color[] = { 1.0f-inc, 0.0f+inc, 0.0f+inc, 1.0f };
                    renderer.triangles[renderer.triangle_count].color = color;
                    renderer.triangle_count++;
                }*/
            case MotionEvent.ACTION_MOVE:
                color = renderer.triangles[1].color;
                float color3[] = renderer.triangles[2].color;
                color3[0]-=0.0005f;
                color3[1]-=0.0005f;
                color3[2]+=0.0005f;
                float coords[] = renderer.triangles[1].triangleCoords;
                coords[0]+=0.0005f;
                renderer.triangles[1].setCoords(coords);

                if (color[0]==1.0f||color[1]==1.0f||color[2]==1.0f) {
                    System.out.println("1.0 reached");
                    color[0]-=0.0005f;
                    color[1]-=0.0005f;
                    color[2]-=0.0005f;
                } else {
                    color[0]-=0.0005f;
                    color[1]-=0.0005f;
                    color[2]+=0.0015f;
                }

                renderer.triangles[1].set_color(color);
                renderer.triangles[2].set_color(color3);

                float dx = x - previousX;
                float dy = y - previousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                renderer.setAngle(
                        renderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        previousX = x;
        previousY = y;
        return true;
    }


    public MyGLSurfaceView(Context context){

        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        renderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
