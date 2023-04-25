package org.gui.lab6;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.*;
import java.nio.FloatBuffer;
import java.util.Queue;

public class lab6 extends JFrame implements GLEventListener{
    private GLU glu;
    private TextureHandler texture1, texture2;
    public lab6(){
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        // This method will be explained later
        this.initializeJogl();

        this.setVisible(true);
    }

    private void initializeJogl(){
        this.glu = new GLU();
    }

    public void init(GLAutoDrawable canvas){
        GL2 gl = canvas.getGL().getGL2();

        texture1 = new TextureHandler(gl, glu, "Texturi/texture1.jpg", true);
        texture2 = new TextureHandler(gl, glu, "Texturi/texture2.jpg", true);


        // Choose the shading model.
        gl.glShadeModel(GL2.GL_SMOOTH);

        // Activate the depth test and set the depth function.
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LESS);

        // Set The Texture Generation Mode For S To Sphere Mapping (NEW)
        gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);
        // Set The Texture Generation Mode For T To Sphere Mapping (NEW)
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_SPHERE_MAP);


        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_LIGHT1);


        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable){

    }

    public void display(GLAutoDrawable canvas){
        GL2 gl = canvas.getGL().getGL2();

        // Draw the object you wish to apply the first texture to.
        texture1.bind();
        texture1.enable();

        // Draw the object you wish to apply the second texture to.
        texture2.bind();
        texture2.enable();


        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        // Clear the depth buffer.
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);

        GLUquadric sun = glu.gluNewQuadric();
        glu.gluSphere(sun, 0.3, 32, 32);
        glu.gluDeleteQuadric(sun);

        /*
        GLUquadric sphere = glu.gluNewQuadric();
        // Enabling texturing on the quadric.
        glu.gluQuadricTexture(sphere, true);
        glu.gluSphere(sphere, 0.5, 64, 64);
        glu.gluDeleteQuadric(sphere);
       */

        // Enable Texture Coord Generation For S (NEW)
        gl.glEnable(GL2.GL_TEXTURE_GEN_S);
        // Enable Texture Coord Generation For T (NEW)
        gl.glEnable(GL2.GL_TEXTURE_GEN_T);
        //draw the object


        gl.glLightf( GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, 60.0F );
        gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION, FloatBuffer.wrap(new float[]{0, 0, 1, 0}));


        // The vector arguments represent the R, G, B, A values.
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, new float [] {0.2f, 0.0f, 0.0f, 1f}, 0);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, new float [] {0.9f, 0.9f, 0.9f, 1f}, 0);
        // The vector arguments represent the x, y, z, w values of the position.
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float [] {-10, 0, 0, 1f}, 0);


        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_AMBIENT, new float [] {0.8f, 0.8f, 0.0f, 1f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_DIFFUSE, new float [] {0.8f, 0.8f, 0.0f, 1f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_SPECULAR, new float [] {0.8f, 0.8f, 0.0f, 1f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, new float [] {0.5f, 0.5f, 0f, 1f}, 0);

        sun = glu.gluNewQuadric();
        glu.gluSphere(sun, 0.3, 32, 32);
        glu.gluDeleteQuadric(sun);


        float sun_glow[] = {1.7f, 1.5f, 1.0f, 1.0f};
        gl.glPushAttrib(GL2.GL_CURRENT_BIT);
        gl.glMaterialfv(GL.GL_FRONT, GL2.GL_EMISSION, sun_glow, 0);
        GLUquadric q = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(q, GLU.GLU_FILL);
        glu.gluQuadricNormals(q, GLU.GLU_SMOOTH);
        glu.gluSphere(q, 34, 40, 40);
        glu.gluDeleteQuadric(q);
        gl.glPopAttrib();


        gl.glFlush();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height){
        GL2 gl = canvas.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        double ratio = (double) width / (double) height;
        glu.gluPerspective (38, ratio, 0.1, 100);

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    private GLCanvas canvas;
    private Animator animator;
}