package com.smw.contract.mupdf.view.page;

import android.graphics.RectF;

public class TextChar extends RectF {
	public char c;

	public TextChar(float x0, float y0, float x1, float y1, char _c) {
		super(x0, y0, x1, y1);
		c = _c;
	}
}