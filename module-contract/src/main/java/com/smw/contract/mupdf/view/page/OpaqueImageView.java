package com.smw.contract.mupdf.view.page;

import android.content.Context;

// Make our ImageViews opaque to optimize redraw
class OpaqueImageView extends androidx.appcompat.widget.AppCompatImageView {

	public OpaqueImageView(Context context) {
		super(context);
	}

	@Override
	public boolean isOpaque() {
		return true;
	}
}

