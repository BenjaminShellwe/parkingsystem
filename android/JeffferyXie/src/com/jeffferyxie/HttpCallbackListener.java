package com.jeffferyxie;

/**
 * ����ص��ӿ�
 * @author Jeffery
 *
 */
public interface HttpCallbackListener {
	void onFinish(String response);

    void onError(Exception e);
}
