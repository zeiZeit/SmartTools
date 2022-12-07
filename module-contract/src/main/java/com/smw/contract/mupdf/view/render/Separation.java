package com.smw.contract.mupdf.view.render;

public class Separation
{
	String name;
	int rgba;
	int cmyk;

	public Separation(String name, int rgba, int cmyk)
	{
		this.name = name;
		this.rgba = rgba;
		this.cmyk = cmyk;
	}
}
