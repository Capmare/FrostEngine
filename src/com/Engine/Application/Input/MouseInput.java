/**
 * 20 feb. 2019
 */
package com.Engine.Application.Input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.Engine.Application.Application;
import com.Engine.Application.Application.ApplicationEvent;
import com.Engine.Application.GameApplication;

/**
 * @author Boris Mulder
 *
 */
public class MouseInput extends MouseAdapter implements MouseMotionListener
{
	private static final int LEFT_BUTTON = 1;
	//private static final int SCROLL_WHEEL = 1;
	private static final int RIGHT_BUTTON = 3;
	
	private GameApplication app;
	private int X = 0;
	private int Y = 0;

	@Override
	public void mouseReleased(MouseEvent e)
	{
		X = e.getX();
		Y = e.getY();
		app.mouseReleased(getButtonEvent(e.getButton()), X, Y);
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		X = e.getX();
		Y = e.getY();
		app.mousePressed(getButtonEvent(e.getButton()), X, Y);
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		X = e.getX();
		Y = e.getY();
	}
	
	private ApplicationEvent getButtonEvent(int button)
	{
		switch(button)
		{
			case LEFT_BUTTON: return ApplicationEvent.MOUSE_LEFT;
				
			case RIGHT_BUTTON: return ApplicationEvent.MOUSE_RIGHT;
				
			default: return ApplicationEvent.NULL;
		}
	}
	
	public void SetApplication(Application app) 
	{
		this.app = (GameApplication) app;
	}
	
	public int getY()
	{
		return Y;
	}
	
	public int getX()
	{
		return X;
	}
}
