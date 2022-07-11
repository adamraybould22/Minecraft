package Voxel.IO;

import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window
{
    // Window ID
    private long window;

    // Window Parameters
    private int width;
    private int height;
    private String title;

    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    /** Creates an OpenGL Window with the passed in parameters */
    public void Create()
    {
        // Initializes GLFW
        if(!GLFW.glfwInit())
            throw new IllegalStateException("Unable to Initalize GLFW");

        // Configure GLFW Window Settings
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // Window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE); // Window can't be resized

        // Creates the Window
        window = GLFW.glfwCreateWindow(width, height, title, NULL, NULL);
        if(window == NULL)
            throw new RuntimeException("Failed to create the GLFW Window");

        // Centers the Window
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

        // Focuses the Window
        GLFW.glfwMakeContextCurrent(window);

        // Enable V-Sync
        GLFW.glfwSwapInterval(1);

        // Show the Window
        GLFW.glfwShowWindow(window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Lock the Cursor
        //LockMouse(true);
    }

    /** Updates the Window */
    public void PrepareWindow()
    {
        GL11.glClearColor(0, 0, 0, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GLFW.glfwPollEvents();
    }

    /** Swaps the Buffers for the Window */
    public void SwapBuffers()
    {
        GLFW.glfwSwapBuffers(window);
    }

    /** Should the Window close? */
    public boolean ShouldClose()
    {
        return GLFW.glfwWindowShouldClose(window);
    }

    /** Locks the Mouse to the Window (So you can't move it out of the Window Borders)
     * @param lock - Should the Cursor should be locked
     */
    public void LockMouse(boolean lock)
    {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
    }

    /** Destroys the Window and clears its Resources */
    public void DestroyWindow()
    {
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);
    }

    public long GetID() { return window; }
    public int GetWidth() { return width; }
    public int GetHeight() { return height; }
}
