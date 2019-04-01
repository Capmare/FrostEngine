/**
 * 14 jul. 2017
 */
package com.Engine.Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.Engine.Application.GameApplication;
import com.Engine.Display.GameCanvas.WindowAction;;

/**
 * @author Boris Mulder
 *
 */
public class GameClient
{
	static
	{
		System.setProperty("sun.java2d.opengl", "true");
		System.setProperty("sun.java2d.translaccel", "true");
		System.setProperty("sun.java2d.accthreshold", "1");
		System.setProperty("sun.java2d.ddscale", "true");
		System.setProperty("sun.java2d.ddforcevram", "true");
	}

	public static int ClientWidth;
	public static int ClientHeight;
	

	private final String clientName;
	private final Dimension windowSize;
	private final JFrame window;
	private final GameCanvas gameCanvas;
	private final int minWidth,minHeight;

	public GameClient(final GameApplication app, final int ClientWidth, final int ClientHeight)
	{
		clientName = app.GameName;
		
		GameClient.ClientWidth = ClientWidth;
		GameClient.ClientHeight = ClientHeight;
		
		windowSize = new Dimension(ClientWidth, ClientHeight);
		
		window = new JFrame();
		gameCanvas = new GameCanvas(app);
		minWidth = 640;
		minHeight = 480;

		Init(app);
	}
	
	public GameClient(final GameApplication app, final int ClientWidth, final int ClientHeight,final int minWidth,final int minHeight)
	{
		clientName = app.GameName;
		
		GameClient.ClientWidth = ClientWidth;
		GameClient.ClientHeight = ClientHeight;
		
		windowSize = new Dimension(ClientWidth, ClientHeight);
		
		window = new JFrame();
		gameCanvas = new GameCanvas(app);
		this.minWidth = minWidth;
		this.minHeight = minHeight;

		Init(app);
	}

	/**
	 * Create the display
	 */
	private void Init(GameApplication app)
	{
		window.setTitle(clientName+" - FrostEngine");
		window.setSize(windowSize);

		window.setMinimumSize(new Dimension(minWidth, minHeight)); // Atleast this size
		window.setBackground(Color.BLACK);

		window.setLocationRelativeTo(null);
		window.setResizable(app.ResizeAble);

		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		window.add(gameCanvas.GetGameCanvas());

		window.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentShown(ComponentEvent e)
			{
				gameCanvas.Start();
			}
			public void componentResized(ComponentEvent e)
			{
				ClientWidth = window.getWidth();
				ClientHeight = window.getHeight();
				
				app.OnResize(ClientWidth, ClientHeight);
			}

		});

		window.addWindowListener(new WindowAdapter()
		{

			public void windowOpened(WindowEvent e)
			{
				gameCanvas.Action(WindowAction.OPENED);
			}

			public void windowIconified(WindowEvent e)
			{
				gameCanvas.Action(WindowAction.ICONFIFIED);
			}

			public void windowDeiconified(WindowEvent e)
			{
				gameCanvas.Action(WindowAction.DEICONFIED);
			}

			public void windowClosing(WindowEvent e)
			{
				gameCanvas.Action(WindowAction.CLOSING);
			}

			public void windowActivated(WindowEvent e)
			{
				gameCanvas.Action(WindowAction.ACIVAITED);
			}
		});

		window.setVisible(true);
	}
}
