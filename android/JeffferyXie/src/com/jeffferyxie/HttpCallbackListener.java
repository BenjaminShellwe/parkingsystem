package com.jeffferyxie;

/**
 * 请求回调接口
 * @author Jeffery
 *
 */
public interface HttpCallbackListener {
	void onFinish(String response);

    void onError(Exception e);
}
