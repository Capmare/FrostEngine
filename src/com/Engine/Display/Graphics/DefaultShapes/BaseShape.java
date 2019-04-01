/**
 * 21 feb. 2019
 */
package com.Engine.Display.Graphics.DefaultShapes;

import java.awt.Color;
import java.awt.Graphics2D;

import com.Engine.Display.Graphics.RenderBatch;


/**
 * @author Boris Mulder
 *
 */
public abstract class BaseShape
{
	protected int x = 0;
	protected int y = 0;
	public Color color = Color.red;

	public BaseShape(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public abstract void Draw(Graphics2D gfx,RenderBatch batch);
}
