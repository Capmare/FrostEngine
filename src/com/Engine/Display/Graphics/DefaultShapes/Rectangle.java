/**
 * 21 feb. 2019
 */
package com.Engine.Display.Graphics.DefaultShapes;

import java.awt.Graphics2D;

import com.Engine.Display.Graphics.RenderBatch;

/**
 * @author Boris Mulder
 *
 */
public class Rectangle extends BaseShape
{
	private int width;
	private int height;
	
	public Rectangle(int x,int y,int width,int height)
	{
		super(x,y);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void Draw(Graphics2D gfx,RenderBatch bacth)
	{
		gfx.setColor(color);
		gfx.fillRect(x, y, width, height);
	}
}
