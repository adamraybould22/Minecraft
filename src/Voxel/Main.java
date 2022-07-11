package Voxel;

import org.lwjgl.Version;

import Voxel.IO.Window;

public class Main 
{
	private Window window;
	
	public void InitailizeApplication()
	{
		window = new Window(1080, 768, "Minecraft - LWJGL " + Version.getVersion());
		window.Create();
		
	
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
		window.SwapBuffers();
	}
	
	public static void main(String[] args)
	{
		new Main().InitailizeApplication();
	}
}
