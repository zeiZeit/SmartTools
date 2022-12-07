package com.smw.contract.mupdf.view.page;

interface TextProcessor {
	void onStartLine();
	void onWord(TextWord word);
	void onEndLine();
}

