/**
 * 18 jul. 2017
 */
package com.Engine.Display.Graphics;

import static java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_COLOR_RENDERING;
import static java.awt.RenderingHints.KEY_RENDERING;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.Engine.Display.Graphics.DefaultShapes.BaseShape;
import com.Engine.Utils.Constants;

/**
 * @author Boris Mulder
 *
 */
public class RenderBatch
{

	private final Camera camera;
	private final ResourceManager manager;

	Graphics2D graphics;
	Composite defaultComposite;
	public Color BackGroundColor = Color.black;

	boolean started = false;
	boolean ended = true;
	float delta;
	

	public RenderBatch(Camera cam, ResourceManager man)
	{
		camera = cam;
		manager = man;
	}
	
	public void SetGraphics(Graphics2D gfx)
	{
		graphics = gfx;
		defaultComposite = graphics.getComposite();
	}
	
	// Start a new render loop
	public void Begin()
	{
		if (ended)
		{
			started = true;
			ended = false;
			
			graphics.setRenderingHint(KEY_ANTIALIASING,VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);
			graphics.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
			graphics.setRenderingHint(KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_QUALITY);
			graphics.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_QUALITY);
		}
		else
		{
			Exception("Must call 'End' before 'Begin'");
		}
	}
	
	// Stop the current active render loop
	public void End()
	{
		if (started)
		{
			graphics.setComposite(defaultComposite);
			graphics.setColor(Color.BLACK);
			started = false;
			ended = true;
		}
		else
		{
			Exception("Must call 'Begin' before 'End'");
		}
	}

	// Set the current color for a render target
	public void setColor(Color color)
	{
		if(!started) {
			Exception("Must call 'Begin' before rendering");
			return;
		}
		graphics.setColor(color);
	}
	
	// 
	public void setAlpha(float v)
	{
		if(!started) {
			Exception("Must call 'Begin' before rendering");
			return;
		}
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Constants.Clamp(v, 0.0f, 1.0f)));
	}

	public void DrawImage(BufferedImage img, float x, float y)
	{
		if(!started) {
			Exception("Must call 'Begin' before rendering");
			return;
		}
		
		int width = img.getWidth();
		int height = img.getHeight();

		if (camera.InView(x, y, width, height))
		{
			float xpos = (x - camera.getxOffset());
			float ypos = (y - camera.getyOffset());
			
			graphics.drawImage(img, Math.round(xpos), Math.round(ypos), width, height, null);
		}
	}

	public void DrawString(String msg, float x, float y)
	{
		if(!started) {
			Exception("Must call 'Begin' before rendering");
			return;
		}
		
		float xpos = (x - camera.getxOffset());
		float ypos = (y - camera.getyOffset());
		graphics.setFont(manager.GetFont("default"));
		graphics.drawString(msg, xpos, ypos);
	}
	
	public void DrawString(String msg, float x, float y, String font)
	{
		if(!started) {
			Exception("Must call 'Begin' before rendering");
			return;
		}
		
		float xpos = (x - camera.getxOffset());
		float ypos = (y - camera.getyOffset());
		graphics.setFont(manager.GetFont(font));
		graphics.drawString(msg, xpos, ypos);
	}
	
	public void DrawSystemString(String msg, float x, float y)
	{
		if(!started) {
			Exception("Must call 'Begin' before rendering");
			return;
		}
		
		float xpos = (x);
		float ypos = (y);
		graphics.setFont(manager.GetFont("default"));
		graphics.drawString(msg, xpos, ypos);
	}
	
	public void DrawGrid(float startX,float startY,int width,int height,int gridsize)
	{
		DrawGrid((int)startX, (int)startY, width, height, gridsize);
	}
	
	public void DrawGrid(int startX,int startY,int width,int height,int gridsize)
	{
		if(!started) {
			Exception("Must call 'Begin' before rendering");
			return;
		}
		
		graphics.setColor(Color.GREEN);
		
		for(int xpos = startX; xpos < startX + width; xpos+= gridsize)
		{
			for(int ypos = startY; ypos < startY + height; ypos+= gridsize)
			{
				int x = (int) (xpos - camera.getxOffset());
				int y = (int) (ypos - camera.getyOffset());
				
				if(camera.InView(x, y, width, height))
				{
					graphics.drawRect(x, y, width, height);
				}
			}
		}	
	}
	
	public int GetStringWidth(String fontName,String text)
	{
		graphics.setFont(manager.GetFont(fontName));
		return graphics.getFontMetrics().stringWidth(text);
	}
	
	public void DrawShape(BaseShape shape)
	{
		shape.Draw(graphics,this);
	}
	
	private void Exception(String msg)
	{
		throw new IllegalStateException(msg);
	}
}
