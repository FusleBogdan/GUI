package org.gui.lab3;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.*;

public class lab3 extends JFrame implements GLEventListener
{
    byte mask[] = {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x03, (byte)0x80, 0x01, (byte)0xC0, 0x06, (byte)0xC0, 0x03, 0x60,
            0x04, 0x60, 0x06, 0x20, 0x04, 0x30, 0x0C, 0x20,
            0x04, 0x18, 0x18, 0x20, 0x04, 0x0C, 0x30, 0x20,
            0x04, 0x06, 0x60, 0x20, 0x44, 0x03, (byte)0xC0, 0x22,
            0x44, 0x01, (byte)0x80, 0x22, 0x44, 0x01, (byte)0x80, 0x22,
            0x44, 0x01, (byte)0x80, 0x22, 0x44, 0x01, (byte)0x80, 0x22,
            0x44, 0x01, (byte)0x80, 0x22, 0x44, 0x01, (byte)0x80, 0x22,
            0x66, 0x01, (byte)0x80, 0x66, 0x33, 0x01, (byte)0x80, (byte)0xCC,
            0x19, (byte)0x81, (byte)0x81, (byte)0x98, 0x0C, (byte)0xC1, (byte)0x83, 0x30,
            0x07, (byte)0xe1, (byte)0x87, (byte)0xe0, 0x03, 0x3f, (byte)0xfc, (byte)0xc0,
            0x03, 0x31, (byte)0x8c, (byte)0xc0, 0x03, 0x33, (byte)0xcc, (byte)0xc0,
            0x06, 0x64, 0x26, 0x60, 0x0c, (byte)0xcc, 0x33, 0x30,
            0x18, (byte)0xcc, 0x33, 0x18, 0x10, (byte)0xc4, 0x23, 0x08,
            0x10, 0x63, (byte)0xC6, 0x08, 0x10, 0x30, 0x0c, 0x08,
            0x10, 0x18, 0x18, 0x08, 0x10, 0x00, 0x00, 0x08};

    public lab3()
    {
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        // This method will be explained later
        this.initializeJogl();

        this.setVisible(true);
    }

    private void initializeJogl()
    {
        // Obtaining a reference to the default GL profile
        GLProfile glProfile = GLProfile.getDefault();
        // Creating an object to manipulate OpenGL parameters.
        GLCapabilities capabilities = new GLCapabilities(glProfile);

        // Setting some OpenGL parameters.
        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        // Creating an OpenGL display widget -- canvas.
        this.canvas = new GLCanvas();

        // Adding the canvas in the center of the frame.
        this.getContentPane().add(this.canvas);

        // Adding an OpenGL event listener to the canvas.
        this.canvas.addGLEventListener(this);

        this.animator = new Animator();

        this.animator.add(this.canvas);

        this.animator.start();
    }

    public void init(GLAutoDrawable canvas)
    {
        // Obtain the GL instance associated with the canvas.
        GL2 gl = canvas.getGL().getGL2();


        // Generate a unique ID for our list.
        aCircle = gl.glGenLists(1);
        // Generate the Display List
        gl.glNewList(aCircle, GL2.GL_COMPILE);
        drawCircle(gl, 0.5f, 0.5f, 0.4f);
        gl.glEndList();

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    int aCircle;

    public void display(GLAutoDrawable canvas)
    {
        GL2 gl = canvas.getGL().getGL2();

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);

        // Set the polygon mask.
        gl.glPolygonStipple (mask, 0);
        // Enable polygon stipple.
        gl.glEnable (GL2.GL_POLYGON_STIPPLE);

        // Define vertices in clockwise order (back-faced).
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(1.f, 0.f, 0.f);
        gl.glVertex2f(0.2f, 0.2f);
        gl.glColor3f(0.f, 1.f, 0.f);
        gl.glVertex2f(0.2f, 0.4f);
        gl.glColor3f(0.f, 0.f, 1.f);
        gl.glVertex2f(0.4f, 0.4f);
        gl.glColor3f(1.f, 1.f, 1.f);
        gl.glVertex2f(0.4f, 0.2f);
        gl.glEnd();

        // Disable polygon stipple.
        gl.glDisable (GL2.GL_POLYGON_STIPPLE);

        gl.glLineWidth(1.5f);

        gl.glColor3f(1.f, 0.f, 0.f);
        gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(0.2f, 0.2f);
            gl.glVertex2f(0.9f, 0.9f);
            gl.glEnd();

            gl.glColor3f(0.f, 1.f, 0.f);
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(0.9f, 0.2f);
            gl.glVertex2f(0.2f, 0.9f);
        gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);
            gl.glColor3f(1.f, 0.f, 0.f);
            gl.glVertex2f(0.2f, 0.2f);
            gl.glColor3f(0.f, 1.f, 0.f);
            gl.glVertex2f(0.2f, 0.4f);
            gl.glColor3f(0.f, 0.f, 1.f);
            gl.glVertex2f(0.4f, 0.4f);
            gl.glColor3f(1.f, 1.f, 1.f);
            gl.glVertex2f(0.4f, 0.2f);
        gl.glEnd();

        // Do not render front-faced polygons.
        gl.glCullFace(GL.GL_FRONT);
        // Culling must be enabled in order to work.
        gl.glEnable(GL.GL_CULL_FACE);

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);

        // Define vertices in clockwise order (back-faced)
        gl.glBegin(GL2.GL_POLYGON);
            gl.glColor3f(1.f, 0.f, 0.f);
            gl.glVertex2f(0.2f, 0.2f);
            gl.glColor3f(0.f, 1.f, 0.f);
            gl.glVertex2f(0.2f, 0.4f);
            gl.glColor3f(0.f, 0.f, 1.f);
            gl.glVertex2f(0.4f, 0.4f);
            gl.glColor3f(1.f, 1.f, 1.f);
            gl.glVertex2f(0.4f, 0.2f);
        gl.glEnd();


        // Do not render front-faced polygons.
        gl.glCullFace(GL.GL_FRONT);
        // Culling must be enabled in order to work.
        gl.glEnable(GL.GL_CULL_FACE);

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);

        // Define vertices in clockwise order (back-faced).
        gl.glBegin(GL2.GL_POLYGON);
            // Define normal for vertex 1
            gl.glNormal3f(0.f, 0.f, 1.f);
            gl.glColor3f(1.f, 0.f, 0.f);
            gl.glVertex2f(0.2f, 0.2f);

            // Define normal for vertex 2
            gl.glNormal3f(0.f, 0.f, 1.f);
            gl.glColor3f(0.f, 1.f, 0.f);
            gl.glVertex2f(0.2f, 0.4f);

            // Define normal for vertex 3
            gl.glNormal3f(0.f, 0.f, 1.f);
            gl.glColor3f(0.f, 0.f, 1.f);
            gl.glVertex2f(0.4f, 0.4f);

            // Define normal for vertex 4
            gl.glNormal3f(0.f, 0.f, 1.f);
            gl.glColor3f(1.f, 1.f, 1.f);
            gl.glVertex2f(0.4f, 0.2f);
        gl.glEnd();

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        // Call the Display List i.e. call the commands stored in it.
        gl.glCallList(aCircle);

        gl.glFlush();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height)
    {
        GL2 gl = canvas.getGL().getGL2();

        // Select the viewport -- the display area -- to be the entire widget.
        gl.glViewport(0, 0, width, height);

        // Determine the width to height ratio of the widget.
        double ratio = (double) width / (double) height;

        // Select the Projection matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl.glLoadIdentity();

        // Select the view volume to be x in the range of 0 to 1, y from 0 to 1 and z from -1 to 1.
        // We are careful to keep the aspect ratio and enlarging the width or the height.
        if (ratio < 1)
            gl.glOrtho(0, 1, 0, 1 / ratio, -1, 1);
        else
            gl.glOrtho(0, 1 * ratio, 0, 1, -1, 1);

        // Return to the Modelview matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
    }

    // Here we define the function for building a circle from line segments.
    private void drawCircle(GL2 gl, float xCenter, float yCenter, float radius) {
        double x, y, angle;
        gl.glBegin(GL2.GL_LINE_LOOP);
        for (int i = 0; i < 360; i++) {
            angle = Math.toRadians(i);
            x = radius * Math.cos(angle);
            y = radius * Math.sin(angle);
            gl.glVertex2d(xCenter + x, yCenter + y);
        }
        gl.glEnd();
    }

    private GLCanvas canvas;
    private Animator animator;
}