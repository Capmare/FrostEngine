/**
 * 18 jul. 2017
 */
package com.Engine.Utils;

/**
 * @author Boris Mulder
 *
 */
public class Constants
{

	public static final float Clamp(float var,float min,float max)
	{
		if(var < min)
			return min;
		if(var > max)
			return max;
		return var;
	}
	
	public static final int Clamp(int var,int min,int max) 
	{
		if(var < min)
			return min;
		if(var > max)
			return max;
		return var;
	}
	
}
