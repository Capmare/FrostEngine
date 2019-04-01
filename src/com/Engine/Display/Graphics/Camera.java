/**
 * 15 jul. 2017
 */
package com.Engine.Display.Graphics;

import com.Engine.Application.GameApplication;

/**
 * @author Boris Mulder
 *
 */
public class Camera
{

	private final GameApplication App;

	private float xOffset, yOffset;

	public Camera(float x, float y, GameApplication app)
	{
		App = app;
		xOffset = x;;
		yOffset = y;
	}

	public void CenterOn(float x, float y, int width, int height)
	{
		//TODO: Camera bug 
		//When moving the camera to the far left side it renders like 95% of the last tile instead off the full tile
		//might have something to do with rendering on a volitiale image
		
		xOffset = (x - (App.ClientWidth / 2) + (width / 2));
		
		yOffset = (y - (App.ClientHeight / 2) + (height / 2));
		
		if(xOffset < 0) 
		{
			xOffset = 0;
		}
		
		else if(xOffset + App.ClientWidth > App.WorldWidth)
		{
			//xOffset = (App.WorldWidth);
		}
		
		if(yOffset < 0) 
		{
			yOffset = 0;
		}
		
	}

	public void move(float xAmount, float yAmount)
	{
		xOffset += xAmount;
		yOffset += yAmount;
	}

	public boolean InView(float x, float y, int width, int height)
	{
		boolean xAxis = (x + (width / 2) >= (xOffset)) && (x + (width - width / 2) <= (xOffset + App.ClientWidth));
		boolean yAxis = (y + (height / 2) >= (yOffset)) && (y + (height - height / 2) <= (yOffset + App.ClientHeight));
		return (xAxis && yAxis);
	}

	public float getxOffset()
	{
		return xOffset;
	}

	public float getyOffset()
	{
		return yOffset;
	}

}
