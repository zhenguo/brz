package com.brz.listener;

/**
 * 内容播放完毕，用于通知切换节目单
 */
public interface OnCompletionListener {

	int LEVEL_PROGRAMME = 0x99;
	int LEVEL_VIDEO = 0x100;
	int LEVEL_IMAGE = 0x101;

    /**
     *
     * @return true indicates this event has been processed, false otherwise.
     */
    boolean onCompletion(int level);
}
