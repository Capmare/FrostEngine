/**
 * 18 jul. 2017
 */
package com.Engine.Display.Graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * @author Boris Mulder
 *
 */
public class ResourceManager
{

	// Fonts
	private Map<String, Font> FontList = new HashMap<String, Font>();
	
	// Images
	private Map<String, BufferedImage> ImageList = new HashMap<String, BufferedImage>();

	// Loads a font
	public void LoadFont(String name, String fontName, int fontStyle, int fontSize)
	{
		FontList.put(name, new Font(fontName, fontStyle, fontSize));
	}
	
	// Get a loaded font by its name
	public Font GetFont(String name) { return FontList.get(name); };

	// Loads an image
	public void LoadImage(String name, String imagePath)
	{
		try
		{
			ImageList.put(name, ImageIO.read(this.getClass().getResourceAsStream(imagePath)));
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	// Get an loaded image by its name
	public BufferedImage GetImage(String name) { return ImageList.get(name); };
}
