/**
 * 14 jul. 2017
 */
package com.Engine.Display;

import java.awt.Canvas;

import com.Engine.Application.Application;

/**
 * @author Boris Mulder
 *
 */
public class GameCanvas
{

	protected enum WindowAction
	{
		RESIZED, // Size has changed
		ACIVAITED, // Focused window
		OPENED, // Called one once when the window is created
		ICONFIFIED, // Put on taskbar
		DEICONFIED, // Activaited from taskbar
		CLOSING, // App is getting closed
	}

	private final Thread GameThread = new Thread(() -> run());
	private final Canvas Canvas = new Canvas();
	private final Application GameApplication;
	private boolean Playing = false;
	private int fps = 60;
	private long lastFpsTime;

	public GameCanvas(Application app)
	{
		this.GameApplication = app;
	}

	/**
	 * Game loop
	 */
	public void run()
	{
		long lastLoopTime = System.nanoTime();
		final int TARGET_FPS = 75;
		final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

		Playing = true;
		
		// keep looping round til the game ends
		while (Playing)
		{
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			float delta = updateLength / ((float) OPTIMAL_TIME);

			// update the frame counter
			lastFpsTime += updateLength;
			fps++;

			// update our FPS counter if a second has passed since
			// we last recorded
			if (lastFpsTime >= 1000000000)
			{
				GameApplication.SetFPS(fps);
				lastFpsTime = 0;
				fps = 0;
			}

			// update the game logic
			GameApplication.OnUpdate(delta);

			// draw everyting
			GameApplication.OnRender(Canvas);

			// we want each frame to take 10 milliseconds, to do this
			// we've recorded when we started the frame. We add 10 milliseconds
			// to this and then factor in the current time to give
			// us our final value to wait for
			// remember this is in ms, whereas our lastLoopTime etc. vars are in
			// ns.
			try
			{
				long sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
				Thread.sleep(sleepTime > 0 ? sleepTime : 0);
			} catch (InterruptedException e)
			{

			}
		}

	}

	public void Action(WindowAction e)
	{
		switch (e)
		{
			case ACIVAITED :
				Canvas.requestFocus();
				break;

			case CLOSING :

				Playing = !Playing;
				break;

			case DEICONFIED :
				Canvas.requestFocus();
				break;

			case ICONFIFIED :
				break;

			case OPENED :
				Canvas.requestFocus();
				break;

			default :
				break;
		}
	}

	/**
	 * Start the thread / game
	 */
	public synchronized void Start()
	{
		GameThread.start();
	}

	/**
	 * Name says it
	 * 
	 * @return
	 */
	public Canvas GetGameCanvas()
	{
		Canvas.addMouseMotionListener(((Application)GameApplication).GetMouseInput());
		Canvas.addMouseListener(((Application)GameApplication).GetMouseInput());
		Canvas.addKeyListener(((Application)GameApplication).GetKeyBoard());
		return Canvas;
	}
}
