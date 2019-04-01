/**
 * 14 jul. 2017
 */
package com.Engine.Application;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.Engine.Display.Graphics.Camera;
import com.Engine.Display.Graphics.RenderBatch;
import com.Engine.Display.Graphics.ResourceManager;


/**
 * @author Boris Mulder
 *
 */
public abstract class GameApplication implements Application
{

	private final ResourceManager Resource = new ResourceManager();
	
	//Render
	private BufferStrategy Buffer;
	private Graphics2D Graphics, DrawGraphics;
	private BufferedImage RenderBuffer;
	protected final Camera Camera = new Camera(0,0,this);
	private final RenderBatch Batch = new RenderBatch(Camera,Resource);
	protected float Delta;
	
	//Keyboard info
	private boolean DownKeys[] = new boolean[256];
	private boolean UpKeys[] = new boolean[256];
	
	//Info
	int FPS;
	
	public int ClientWidth,
	           ClientHeight;
	public int WorldWidth = 640,
	           WorldHeight = 480;

	public String GameName;
	public boolean ResizeAble = true;
	
	public GameApplication(String GameName)
	{
		this.GameName = GameName;
		
		RenderBuffer = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration().createCompatibleImage(WorldWidth , WorldHeight);
		
		RenderBuffer.setAccelerationPriority(1);

		Resource.LoadFont("default", "Comic Sans Ms", Font.BOLD, 14);
		LoadResources(Resource);
	}
	
	@Override
	public void SetFPS(int fps)
	{
		FPS = fps;
	}

	@Override
	public void OnResize(int width, int height)
	{
		WorldWidth = width;
		WorldHeight = height;
		
		RenderBuffer = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration().createCompatibleImage(width , height);
		
		RenderBuffer.setAccelerationPriority(1);
	}
	

	@Override
	public void OnUpdate(float delta)
	{		
		Delta = delta;
		UpdateGame();
	}

	@Override
	public void OnRender(Canvas canvas)
	{
		Buffer = canvas.getBufferStrategy();

		if (Buffer == null)
		{
			canvas.createBufferStrategy(3);
			return;
		}

		DrawGraphics = (Graphics2D) RenderBuffer.getGraphics();
		DrawGraphics.clearRect(0, 0, WorldWidth, WorldHeight);
		
		DrawGraphics.setColor(Color.black);
		DrawGraphics.fillRect(0, 0, WorldWidth, WorldHeight);

		Batch.SetGraphics(DrawGraphics);
	
		DrawGraphics.setColor(Batch.BackGroundColor);
		DrawGraphics.fillRect(0, 0, ClientWidth, ClientHeight);
		
		RenderGame(Batch);
		
		/*int x = GetMouseInput().getX();
		int y = GetMouseInput().getY();
		
		Batch.Begin();
		Batch.setColor(Color.yellow);
		Batch.DrawSystemString(String.format("FPS: %s", FPS),5, 18);
		
		Batch.setColor(Color.red);
		Batch.DrawSystemString(String.format("x:%s y:%s",x,y), 5, 38);
		Batch.End();*/
		
		Graphics = (Graphics2D) Buffer.getDrawGraphics();
		Graphics.setColor(Color.BLACK);
		Graphics.fillRect(0, 0, ClientWidth, ClientHeight);

		Graphics.drawImage(RenderBuffer, 0, 0,null);
		
		DrawGraphics.dispose();
		Graphics.dispose();
		Buffer.show();
	}

	public ResourceManager getResourceManager()
	{
		return Resource;
	}
	
	public abstract void mousePressed(ApplicationEvent button,int x,int y);
	
	public abstract void mouseReleased(ApplicationEvent button, int x,int y);
	
	protected abstract void UpdateGame();

	protected abstract void RenderGame(RenderBatch batch);
	
	public boolean isKeyDown(int key_code)
	{
		return DownKeys[key_code] && !UpKeys[key_code];
	}
	
	public boolean isKeyUp(int key_code)
	{
		return UpKeys[key_code] && !DownKeys[key_code];
	}
	
	/***
	 * Don't overide this, but if you do then good luck :D
	 * @param event
	 * @param key_code
	 */
	public void keyboardEvent(ApplicationEvent event,int key_code) {
		switch(event)
		{
			case KEYPRESSED:
				DownKeys[key_code] = true;
				UpKeys[key_code] = false;
				break;
				
			case KEYRELEASED:
				UpKeys[key_code] = true;
				DownKeys[key_code] = false;
				break;
				
			default:
				// Please don't let this happen..... java 
				DownKeys[key_code] = false;
				UpKeys[key_code] = false;
				break;
		}
	}

}
