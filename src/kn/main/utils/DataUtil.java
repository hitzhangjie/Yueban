package kn.main.utils;

import kn.main.common.EventType;

/**
 * Created by zhangjie on 8/13/16.
 */
public class DataUtil {
	public static Object decode(String rawData) throws Exception {

		if(rawData==null) {
			throw new Exception("you're decoding invalid data ... check and try again");
		}

		Object res = null;
		String evtType_s = String.valueOf(rawData.charAt(0))+String.valueOf(rawData.charAt(1));
		Integer evtType_i = Integer.parseInt(evtType_s);

		switch(evtType_i) {
			case EventType.DJ_SHARE_START_EVT:
			case EventType.DJ_SHARE_STOP_EVT:
			case EventType.DJ_LISTEN_FOLLOW_EVT:
			case EventType.DJ_LISTEN_UNFOLLOW_EVT:
				// extract the playload and decode it as Msg, for example, decode it as DJEventMsg
				res = decodeAsDJEventMSG(evtType_i, rawData.substring(2, rawData.length()-2));
				break;
		}

		return res;
	}

	private static DJEventMsg decodeAsDJEventMSG(Integer evtType, String payload) {

		DJEventMsg msg = new DJEventMsg();
		msg.setEvent(evtType);
		if(evtType== EventType.DJ_SHARE_START_EVT) {
			int dj_uid = Integer.parseInt(payload);
			msg.setDj_uid(dj_uid);
		}

		return msg;
	}
}

/**
 * DJ事件消息
 */
class DJEventMsg {

	int event;
	int dj_uid;
	int dj_follower_uid;

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public int getDj_uid() {
		return dj_uid;
	}

	public void setDj_uid(int dj_uid) {
		this.dj_uid = dj_uid;
	}

	public int getDj_follower_uid() {
		return dj_follower_uid;
	}

	public void setDj_follower_uid(int dj_follower_uid) {
		this.dj_follower_uid = dj_follower_uid;
	}

}
