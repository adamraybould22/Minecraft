package Voxel.Graphics;

import Voxel.Utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader
{
    private String vertexFile;
    private String fragmentFile;

    private int vertexID;
    private int fragmentID;
    private int programID;

    public Shader(String vertexPath, String fragmentPath)
    {
        vertexFile = FileUtils.LoadAsString(vertexPath);
        fragmentFile = FileUtils.LoadAsString(fragmentPath);
    }

    /** Creates the vertex and fragment shader before combining them into a shader program. */
    public void Create()
    {
        programID = GL20.glCreateProgram();
        vertexID = CreateShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentID =  CreateShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);

        // Create the shader program and link the two shaders to it.
        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        GL20.glLinkProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
        {
            System.err.println("Program Linking: " + GL20.glGetProgramInfoLog(programID));
            return;
        }

        GL20.glValidateProgram(programID);
        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE)
        {
            System.err.println("Program Validation: " + GL20.glGetProgramInfoLog(programID));
            return;
        }

        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
    }

    public void Bind()
    {
        GL20.glUseProgram(programID);
    }

    public void Unbind()
    {
        GL20.glUseProgram(0);
    }

    public void Destroy()
    {
        GL20.glDeleteProgram(programID);
    }

    /** Creates a shader using the specified path of the specified type. IE: Vertex or Fragment. */
    private int CreateShader(String path, int type)
    {
        int ID = GL20.glCreateShader(type);
        GL20.glShaderSource(ID, path); // Attach the shader to the ID.
        GL20.glCompileShader(ID);

        // Checks to see if the shader compiled correctly. Throwing an error if it failed.
        if (GL20.glGetShaderi(ID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
        {
            System.err.println("Shader: " + GL20.glGetShaderInfoLog(ID));
            return 0;
        }

        return ID;
    }
}