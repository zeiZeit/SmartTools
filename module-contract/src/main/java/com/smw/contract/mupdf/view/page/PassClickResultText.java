package com.smw.contract.mupdf.view.page;

public class PassClickResultText extends PassClickResult {
	public final String text;

	public PassClickResultText(boolean _changed, String _text) {
		super(_changed);
		text = _text;
	}

	@Override
	public void acceptVisitor(PassClickResultVisitor visitor) {
		visitor.visitText(this);
	}
}

