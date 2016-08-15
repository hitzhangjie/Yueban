package kn.main.common;

/**
 * App需要上报、服务器需要处理的事件类型
 */
public class EventType {

	// dj相关的事件类型
	public static final int DJ_SHARE_START_EVT = 0;
	public static final int DJ_SHARE_STOP_EVT = 1;
	public static final int DJ_LISTEN_FOLLOW_EVT = 2;
	public static final int DJ_LISTEN_UNFOLLOW_EVT = 3;

	// 音乐播放控制相关的事件类型
	public static final int MUSIC_SWITCH_NEXT_EVT = 4;
	public static final int MUSIC_SWITCH_PREV_EVT = 5;
	public static final int MUSIC_FORWARD_EVT = 6;
	public static final int MUSIC_BACKWARD_EVT = 7;
	public static final int MUSIC_START_EVT = 8;
	public static final int MUSIC_STOP_EVT = 9;
	public static final int MUSIC_RESUME_EVT = 10;
	public static final int MUSIC_INC_VOLUME_EVT = 11;
	public static final int MUSIC_DEC_VOLUME_EVT = 12;

	// 交互相关的事件类型
	public static final int LIKE_EVT = 13;
	public static final int DISLIKE_EVT = 14;
	public static final int BULLET_SCREEN_START_EVT = 15;
	public static final int BULLET_SCREEN_ADD_EVT = 16;
	public static final int BULLET_SCREEN_NEXT5_EVT = 17;
	public static final int BULLET_SCREEN_STOP_EVT = 18;

	// 文本评论（不支持嵌套评论）
	public static final int EVAL_ADD_EVT = 19;
	public static final int EVAL_DEL_EVT = 20;

	// 歌单创建相关的事件
	// ...
	public static final int MUSIC_RECORD_CREATE_EVT = 21;
	public static final int MUSIC_RECORD_PULL_EVT = 22;
	public static final int MUSIC_ALL_PULL_EVT    = 23;


	//
	public static final int HEARTBEAT_EVENT = 99;
}
