package org.gui.lab4;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import org.gui.lab6.TextureReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class lab4 extends JFrame implements GLEventListener
{
    // Number of textures we want to create
    private final int NO_TEXTURES = 2;

    private int texture[] = new int[NO_TEXTURES];
    TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];

    // GLU object used for mipmapping.
    private GLU glu;

    public lab4()
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


        // Create a new GLU object.
        glu = GLU.createGLU();

        // Generate a name (id) for the texture.
        // This is called once in init no matter how many textures we want to generate in the texture vector
        gl.glGenTextures(NO_TEXTURES, texture, 0);

        // Define the filters used when the texture is scaled.
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

        // Do not forget to enable texturing.
        gl.glEnable(GL.GL_TEXTURE_2D);

        // The following lines are for creating ONE texture
        // If you want TWO textures modify NO_TEXTURES=2 and copy-paste again the next lines of code
        // up until (and including) this.makeRGBTexture(...)
        // Modify texture[0] and tex[0] to texture[1] and tex[1] in the new code and that's it

        // Bind (select) the texture.
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

        // Read the texture from the image.
        try {
            tex[0] = TextureReader.readTexture("path/to/your/image/here");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Construct the texture and use mipmapping in the process.
        this.makeRGBTexture(gl, glu, tex[0], GL.GL_TEXTURE_2D, true);

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);

        // Read the texture from the image.
        try {
            tex[0] = TextureReader.readTexture("path/to/your/image/here");
            // This line reads another image that will be used to replace a part of the previous
            tex[1] = TextureReader.readTexture("path/to/your/second/image/here");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if( gl.isExtensionAvailable("GL_EXT_texture_filter_anisotropic") )
        {
            float max[] = new float[1];
            gl.glGetFloatv( GL.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, max, 0 );

            gl.glTexParameterf( GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAX_ANISOTROPY_EXT, max[0] );
        }

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    int aCircle;

    public void display(GLAutoDrawable canvas)
    {
        GL2 gl = canvas.getGL().getGL2();

        // Bind (select) the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

        // Draw a square and apply a texture on it.
        gl.glBegin(GL2.GL_QUADS);
            // Lower left corner.
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex2f(0.1f, 0.1f);

            // Lower right corner.
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex2f(0.9f, 0.1f);

            // Upper right corner.
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex2f(0.9f, 0.9f);

            // Upper left corner.
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex2f(0.1f, 0.9f);
        gl.glEnd();

        // Replace all of our texture with another one.
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]); // the pixel data for this texture is given by tex[0] in our example.
        gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, 0, 0, tex[1].getWidth(), tex[1].getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, tex[1].getPixels());

        // Draw a square and apply a texture on it.


        // Disable blending for this texture.
        gl.glDisable(GL.GL_BLEND);

        // Bind (select) the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

        // Draw a square and apply a texture on it.
        gl.glBegin(GL2.GL_QUADS);
            // Lower left corner.
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex2f(0.1f, 0.1f);

            // Lower right corner.
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex2f(0.9f, 0.1f);

            // Upper right corner.
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex2f(0.9f, 0.9f);

            // Upper left corner.
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex2f(0.1f, 0.9f);
        gl.glEnd();

        // Enable blending for this texture.
        gl.glEnable(GL.GL_BLEND);

        // Set the blend function.
        gl.glBlendFunc(GL.GL_SRC_COLOR, GL.GL_DST_ALPHA);

        // Bind (select) the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[1]);

        // Draw a square and apply a texture on it.
        gl.glBegin(GL2.GL_QUADS);
            // Lower left corner.
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex2f(0.1f, 0.1f);

            // Lower right corner.
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex2f(0.9f, 0.1f);

            // Upper right corner.
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex2f(0.9f, 0.9f);

            // Upper left corner.
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex2f(0.1f, 0.9f);
        gl.glEnd();


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

    private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
        if (mipmapped) {
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
            gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }

    private GLCanvas canvas;
    private Animator animator;
}