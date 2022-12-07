package com.smw.contract.mupdf.task;

public interface CancellableTaskDefinition<Params, Result>
{
	Result doInBackground(Params... params);
	void doCancel();
	void doCleanup();
}
