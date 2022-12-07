package com.smw.contract.mupdf.view.page;

public abstract class PassClickResultVisitor {
	public abstract void visitText(PassClickResultText result);
	public abstract void visitChoice(PassClickResultChoice result);
	public abstract void visitSignature(PassClickResultSignature result);
}

