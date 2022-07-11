package Voxel.Graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh 
{
	private Vertex[] vertices;
	private int[] indices;
	
	private int VAO; // Vertex Array
	private int PBO; // Position Buffer
	private int IBO; // Indices Buffer
	
	public Mesh(Vertex[] vertices, int[] indices)
	{
		this.vertices = vertices;
		this.indices = indices;
	}
	
	public void Create()
	{
		 // Create a Vertex Array to store information about a mesh.
        VAO = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(VAO);

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; i++)
        {
            positionData[i * 3] = vertices[i].GetPosition().x;
            positionData[i * 3 + 1] = vertices[i].GetPosition().y;
            positionData[i * 3 + 2] = vertices[i].GetPosition().z;
        }
        positionBuffer.put(positionData).flip();

        PBO = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, PBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // Unbind the buffer

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();

        IBO = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, IBO);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); // Unbind the buffer
	}
	
	public int GetVAO() { return VAO; }
	public int GetIBO() { return IBO; }
	
	public int GetIndicesLength() { return indices.length; }
}
