package Voxel;

import Voxel.Graphics.Mesh;
import Voxel.Graphics.Renderer;
import Voxel.Graphics.Shader;
import Voxel.Graphics.Vertex;
import Voxel.IO.Window;

import org.joml.Vector3f;

import org.lwjgl.Version;

public class Main 
{
	private Window window;
	
	private Shader shader;
	private Renderer renderer;
	
    private Mesh mesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f))
    }, new int[] {
            0, 1, 2,
            0, 3, 2
    });
	
	public void InitailizeApplication()
	{
		window = new Window(1080, 768, "Minecraft - LWJGL " + Version.getVersion());
		window.Create();
		
		shader = new Shader("Resources/Shaders/MainVertex.glsl", "Resources/Shaders/MainFragment.glsl");
        shader.Create();
		
		renderer = new Renderer(shader);
		mesh.Create();
		
	
		// Start Game Loop.
		Tick();
	}
	
	private void Tick()
	{
		long lastTime = System.currentTimeMillis();
		int frames = 0;
		while(!window.ShouldClose())
		{
			Long now = System.nanoTime();
			frames++;
			if(System.currentTimeMillis() - lastTime > 1000)
			{
				lastTime += 1000;
				System.out.println("Frames: " + frames);
				now = System.currentTimeMillis();
				frames = 0;
			}

			Update(lastTime);
			Render();
		}
	}
	
	private void Update(float dt)
	{
		window.PrepareWindow();
	}
	
	private void Render()
	{
		renderer.RenderMesh(mesh);
		window.SwapBuffers();
	}
	
	public static void main(String[] args)
	{
		new Main().InitailizeApplication();
	}
}
