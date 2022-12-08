package com.smw.base.bean;

public class DownloadResult extends BaseBean {
    private String path;
    private float progress;
    private boolean hasDone;

    public DownloadResult(String path, float progress, boolean hasDone) {
        this.path = path;
        this.progress = progress;
        this.hasDone = hasDone;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHasDone() {
        return hasDone;
    }

    public void setHasDone(boolean hasDone) {
        this.hasDone = hasDone;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
