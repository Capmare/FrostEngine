/**
 * 21 feb. 2019
 */
package com.Engine.Application.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.Engine.Application.Application;
import com.Engine.Application.Application.ApplicationEvent;
import com.Engine.Application.GameApplication;

/**
 * @author Boris Mulder
 *
 */
public class KeyInput extends KeyAdapter
{
	public static int LEFT = KeyEvent.VK_LEFT;
	public static int RIGHT = KeyEvent.VK_RIGHT;
	public static int UP = KeyEvent.VK_UP;
	public static int DOWN = KeyEvent.VK_DOWN;
	public static int SPACE = KeyEvent.VK_SPACE;
	public static int ESCAPE = KeyEvent.VK_ESCAPE;

	private GameApplication app;
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		app.keyboardEvent(ApplicationEvent.KEYPRESSED, e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		app.keyboardEvent(ApplicationEvent.KEYRELEASED, e.getKeyCode());
	}
	
	public void SetApplication(Application app)
	{
		this.app = (GameApplication) app;
	}
	
}
