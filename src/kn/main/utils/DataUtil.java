/**
 * 数据处理工具类
 */
package kn.main.utils;

import kn.main.common.EventType;

/**
 * Created by zhangjie on 8/13/16.
 */

/**
 * DataUtil
 *
 * 数据处理工具类，
 */
public class DataUtil {

	/**
	 * 将App上报的请求进行解包处理
	 *
	 * 根据App上报的事件类型，对请求数据包中的payload进行解包，并将其封装到一个对应的数据对象中,
	 * 即将tcp流中读取的数据请求rawData，根据其中的rawData[0-1]判断出事件类型，然后根据此事件类型，
	 * 对其需要的数据项进行提取。
	 * 整个操作被封装到一个消息对象中，编写具体事件处理逻辑的开发人员需要明确返回的消息对象类型。
	 *
	 * @param rawData 原始tcp流中读取的数据请求信息
	 * @return 返回与具体事件类型一致的消息对象
	 * @throws Exception
	 */
	public static Object decodeAsMsg(String rawData) throws Exception {

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

	// 已经确定事件类型为DJ相关的事件，该事件可以继续细分，根据细分后的类型进一步确定数据项
	private static DJEventMsg decodeAsDJEventMSG(Integer evtType, String payload) {

		DJEventMsg msg = new DJEventMsg();
		msg.setEvent(evtType);

		// according to value of `evtType`, prepare the data field `dj_uid`,`dj_follower_uid`:
		// 1. only dj_uid is reported
		//    - dj_share_start_evt
		//    - dj_share_stop_evt
		if(evtType==EventType.DJ_SHARE_START_EVT || evtType==EventType.DJ_SHARE_STOP_EVT) {
			int dj_uid = Integer.parseInt(payload);
			msg.setDj_uid(dj_uid);
		}
		// 2. both dj_uid and dj_follower_uid are reported
		//    - dj_listen_follow_evt
		//    - dj_listen_unfollow_evt
		else if(evtType==EventType.DJ_LISTEN_FOLLOW_EVT || evtType==EventType.DJ_LISTEN_UNFOLLOW_EVT) {
			String [] uids = payload.split("|");
			int dj_uid = Integer.parseInt(uids[0]);
			int dj_follower_uid = Integer.parseInt(uids[1]);
			msg.setDj_uid(dj_uid);
			msg.setDj_follower_uid(dj_follower_uid);
		}

		return msg;
	}
}

/**
 * DJ事件消息
 *
 * @see kn.main.common.EventType
 */
class DJEventMsg {

	// 上报的事件类型
	private int event;
	// dj的uid
	private int dj_uid;
	// dj跟随者的uid
	private int dj_follower_uid;

	// getters & setters
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

/**
 * 音乐控制事件消息
 *
 * @see kn.main.common.EventType
 */
class MusicCtrlEventMsg {

}

/**
 * dj、跟随者交互事件消息
 *
 * @see kn.main.common.EventType
 */
class InteractEventMsg {

}

/**
 * 其他通用事件消息，例如歌单的创建、歌曲的添加等等
 *
 * @see kn.main.common.EventType
 */
class CommonEventMsg {

}
