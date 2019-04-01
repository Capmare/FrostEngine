/**
 * 14 jul. 2017
 */
package com.Engine.Application;

import java.awt.Canvas;

import com.Engine.Application.Input.KeyInput;
import com.Engine.Application.Input.MouseInput;
import com.Engine.Display.Graphics.ResourceManager;


/**
 * @author Boris Mulder
 *
 */
public interface Application
{
	public enum ApplicationEvent
	{
		NULL,
		RESIZED, //Size has changed
		ACIVAITED, //Focused window
		OPENED, //Called one once when the window is created
		ICONFIFIED, //Put on taskbar
		DEICONFIED, //Activaited from taskbar
		CLOSING, //App is getting closed
		KEYPRESSED, //Input
		KEYRELEASED, //Input
		MOUSE_LEFT, // Input
		MOUSE_RIGHT, // Input
	}
	
	//Mouse
	static MouseInput MouseInput = new MouseInput();
	
	//Keyboard
	static KeyInput KeyBoardInput = new KeyInput();
	
	//Load assets
	void LoadResources(ResourceManager manager);
	
	//Set the frames per second
	void SetFPS(int fps);

	//Set the new size of the window
	void OnResize(final int width,final int height);
	
    //Update the game
	void OnUpdate(float delta);
	
	//Render the game
	void OnRender(Canvas gfx);
	
    default MouseInput GetMouseInput()
	{
    	MouseInput.SetApplication(this);
		return MouseInput;
	}
    
    default KeyInput GetKeyBoard()
    {
    	KeyBoardInput.SetApplication(this);
    	return KeyBoardInput;
    }
}
