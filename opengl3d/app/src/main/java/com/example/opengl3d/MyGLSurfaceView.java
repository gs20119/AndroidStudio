package com.example.opengl3d;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer renderer; // OpenGL View needs renderer

    public MyGLSurfaceView(Context context){
        super(context);
        setEGLContextClientVersion(2); // uses opengl ES 2.0
        renderer = new MyGLRenderer(); // new Renderer object
        setRenderer(renderer); // set Renderer to my renderer
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); // draw only on request call
    }

}
