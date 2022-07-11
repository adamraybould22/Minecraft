package Voxel.Graphics;

import org.joml.Vector3f;

public class Vertex
{
    private Vector3f position;
    
    public Vertex(Vector3f position)
    {
    	this.position = position;
    }
    
    public Vector3f GetPosition()
    {
    	return position;
    }
}
