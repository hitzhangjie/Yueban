2016-08-06 11:31:39 AM

---

# 前言

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
在参加腾讯封培“新人体验站”的课程中，我们小组成员提出了“**乐伴**”这一产品，阐述了基本的设计理念、原型、技术方案，虽然只是一个设想或者草稿方案，但是毕竟是我们小组集体的劳动成果，因此在做部门培训MINI项目时，我特意征求了封培小组“**二组不二**”全体成员的同意（没有人反对）。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
在此，首先要感谢“二组不二”小组成员的集体努力，“二组不二”包括的成员包括：
-
	- **产品经理**：高威煌
	- **视觉设计**：刘均璨
	- **交互设计**：徐相凯
	- **研发人员**：张杰、尹京昱、马蒙光、曹一聪、马利麒、马建辉、吕晨、张建

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
在“二组不二”工作的基础上，我们MINI项目小组对“乐伴”相关理念、设计、目标用户群等等进行了进一步的深入思考，并对技术方案进行了更深层次地研究。下面我根据自己的理解对“乐伴”的相关设计理念、需求、设计、实现等进行了一个细致的总结。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
MINI项目小组成员包括：
-
	- **产品经理**：elaineyuan、morsonxie
	- **后台开发**：zhijiezhang、evannzhang、jorryliu、raymondcao、gavinxwang
	- **IOS开发**：zuqingxie、etundliang、looperwang
	- **测试人员**：calorieyang

---

# **目录**

* [1. 乐伴 &#9835;+&#9752;](#1-乐伴-98359752)
	* [1.1. 产品简介](#11-产品简介)
	* [1.2. 产品功能](#12-产品功能)
	* [2. 需求分析说明](#2-需求分析说明)
	* [3. 技术可行性分析](#3-技术可行性分析)
	* [3. 概要设计说明](#3-概要设计说明)
		* [3.1. 概要设计](#31-概要设计)
		* [3.2. 视觉设计](#32-视觉设计)
		* [3.3. 交互设计](#33-交互设计)
		* [3.4. 产品原型](#34-产品原型)
	* [4. 详细设计说明](#4-详细设计说明)
	* [5. 数据库设计说明](#5-数据库设计说明)
	* [6. 测试用例](#6-测试用例)
	* [7. 测试计划](#7-测试计划)
	* [8. 安装手册](#8-安装手册)
	* [9. 项目阶段报告](#9-项目阶段报告)

# 1. 乐伴 &#9835;+&#9752;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
音乐已经成为人类日常文化生活中不可或缺的一部分，音乐像一股清泉融入我们的心灵和肉体，让我们可以超越种族的界限，与不同文化、肤色、语言的朋友心有灵犀；让我们可以超越年代的屏障，与时代人物举杯对饮；空间亦不再是距离，在音乐中不同的国度仿佛变成了一个整体。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
音乐的重要性驱使了一批又一批音乐播放软件的出现，从最初的基本功能需求“播放音乐”开始，到现在将音乐与社交相结合，音乐市场领域的纷争从来没有停止。

## 1.1. 产品简介

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
我们设计了一个调查问卷，对市面上的音乐播放软件进行调查研究，发现现有的音乐播放软件不能够在音乐、社交之间获得一个比较好的平衡，顾此失彼，根据统计结果显示，网友们更倾向于“***以轻量级的社交元素丰富音乐生活***”这样的一个理念。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
针对这一理念，我们提出了“乐伴”这样一款产品，其**核心思想是“音乐跟随”**，即某些用户可以变身成DJ，实时分享自己喜欢的音乐，而其他用户可以跟随自己喜欢的DJ，同步收听其正在播放的音乐，并且DJ负责控制音乐的一切播放控制，例如切歌、音效等等。

## 1.2. 产品功能

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
围绕着乐伴“音乐跟随”的核心思想，我们对产品的功能进行了简要概括。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
当前主要包括如下几个核心功能点：

- 
	- 我来当DJ
	- 选择心情
	- 选择喜欢的DJ
	- 跟随DJ
	- 与DJ、伴友的互动
		- 抛鲜花、丢板鞋
		- 简单的文字弹幕
		- 文字评论

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
下面将针对上述核心功能进行深入的分析，挖掘出潜在的功能需求。

# 2. 需求分析说明

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
为了更好地将音乐、社交相结合的同时，能够在二者之间获得一个更好的平衡，我们设计了一个调查问卷，并对统计结果进行了分析，发现大部分用户希望能够秉持“以轻量级的社交元素丰富音乐生活”的这样一个理念来进行音乐产品的选择。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
我们总结了用户可能感兴趣的、当下比较流行的社交元素，同时还提出了一种新颖的“**音乐跟随**”效果，并在调查问卷中进行了调查，统计结果显示，大家对“音乐跟随”这一效果的支持率最高，同时部分网友希望通过“抛鲜花、丢板鞋”这样生动活泼的方式来进行交互，少量用户也选择了“文本评论”的方式，极少数的人选择了“弹幕”、“弹幕”的交互方式。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
综上，我们选择了“音乐跟随”作为核心社交元素，辅之以“抛鲜花、丢板鞋”、“文本评论”。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
由于时间有限，没有足够多的时间撰写需求分析文档，在此寥寥数语介绍了基本的需求，希望参考此文档的相关人员能够谅解。

# 3. 技术可行性分析

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
前面对“乐伴”的核心设计理念和核心功能点进行了描述，这里对核心功能点中涉及到的一些技术细节进行一下研究、总结，为后续概要设计、详细设计、数据库设计提供参考。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
下面根据各个核心功能点进行逐一的技术可行性分析。

## 3.1. 用户操作模式

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
用户可以只是简单的听音乐，也可以选择自己做DJ进行音乐分享、与其他用户互动，这就涉及到用户当前所处的一个状态，包括：
-   
	- 普通模式，即normal mode  
		>用户独立选择音乐，并播放，与一个使用最最简单的音乐播放器下的场景相一致；

	- DJ分享模式，即DJ active mode 
		>用户选择自己做DJ，主动将音乐分享出去，其他用户可以跟随该用户，该用户全权控制音乐的播放控制、音效的调节等；

	- DJ收听模式，即DJ passive mode
		>用户选择某个DJ然后进行跟随，音乐的播放、音效的调节完全由DJ来控制，当前用户可以收听；

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
用户在不同状态下，可以执行的操作不同，简要总结如下：

| 用户模式  | 独立播放音乐 | 我要当DJ | 跟随DJ   | 取消跟随 |抛鲜花/丢板鞋 | 发送弹幕 | 文本评论 |
|:---------:|:------------:|:--------:|:--------:|:--------:|:------------:|:--------:|:--------:|  
|Normal     | &#10003;     | &#10003; | &#10003; | &#10005; | &#10005;     | &#10005; | &#10003; |
|DJ\_Active | &#10003;     | &#10005; | &#10005; | &#10005; | &#10005;     | &#10005; | &#10005; |
|DJ\_Passive| &#10005;     | &#10005; | &#10005; | &#10003; | &#10003;     | &#10003; | &#10003; |

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
用户在进行状态切换时，例如选择“我要当DJ”、“我要跟随DJ”、“独立播放音乐”时，必须能够对用户当前的状态进行设置，并及时通知到服务器，服务器需要对将该用户的状态信息及时通知到其他的用户端，以变其他用户知晓其状态。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
因此在此需要一个C/S架构的控制模块，C端负责向S端上报用户当前所处的操作模式，S端负责向其他的C端进行通告。


## 3.2. 音乐播放控制

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
当用户处于DJ分享模式，该用户对音乐的播放控制、音效调节等应及时同步到其他跟随了该DJ分享者的DJ收听者，以实现音乐在DJ分享者、DJ收听者之间的同步。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
为了实现音乐在二者之间的同步，当DJ分享者在执行音乐播放控制、音效调节控制操作，**播放器提供某种机制来回调**我们提供的一个“**操作控制上报函数**”，以便S端及时接收DJ分享者（C端）上报的操作命令，并及时将该命令通知到其他的DJ收听者（C端），然后DJ收听者一端再对播放器执行对应的控制操作，以实现音乐在DJ分享者和DJ收听者之间的同步。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
只要搞清楚这一个问题，问题就解决了大半，这个问题就是：**当我们对播放器（标准协议提供的组件、浏览器插件、android/ios提供的原生组件）进行控制操作时，使用的播放器是否能够回调我们指定的函数？**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
只要搞清楚了这一个问题，基本上所有的问题就都迎刃而解了。我对Android系统中的播放器组件MediaPlayer进行了学习，发现基于MediaPlayer实现的播放器，界面上的操作会被映射为对MediaPlayer的操作，也就是说不管DJ端进行何种操作，UI线程都是可以捕获到相应的事件的，当然也就可以在事件处理函数中执行任何需要的处理操作，包括向服务器上报操作事件（当然了UI线程不适合执行这种耗时的操作，可以递交给后台线程去处理）。

## 3.3. 用户开启DJ分享模式

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
当用户开启DJ分享模式时，首先DJ端对应的App会向服务器上报“**开启DJ分享模式**”这一事件，服务器收到后会将这一事件发送给所有的在线用户（不包括已经“跟随”了该DJ的 用户），以告知他们当前有一个新用户开启了DJ分享模式，以便于他们可以选择跟随或者不跟随，对这部分用户的App的影响就是，界面中会多出一个新的“气泡”，该气泡代表的是DJ，也可以理解为DJ房间，用户可以点选择跟随。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
服务器端可以维护一个DJ列表，例如通过一个**HashMap<K,V> map**，其中的K即主播个人的uid，V即主播开启DJ分享模式时跟随他的用户列表。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
当有一个用户开启DJ分享模式时，DJ端App上报这一事件给服务器，服务器会在处理线程中将开启DJ分享模式的用户的uid存储到HashMap中，并将该用户的uid发送给所有未包含在map.get(K)中的用户，以通知他们有新DJ出现了。

## 3.4. 用户开启DJ收听模式

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
与3.3节类似，当有新用户跟随DJ，用户端的App也会将“**跟随DJ**”这一事件上报给服务器，上报的参数包括了当前用户的uid，主播的uid_dj，服务器会执行操作map.get(uid_dj).add(uid)，从而完成跟随者与DJ之间的跟随关系的构建。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
以后所有的DJ的操作，都会向map.get(uid_dj)中的uid进行通告，以完成“音乐跟随”的效果。

## 3.5. 用户互动操作

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
乐友之间可以基于一些轻量级的社交元素进行交互，增加趣味性，即以简单的社交元素来丰富音乐内容本身。我们做了一个调查问卷，**进过统计显示，“DJ分享”、“DJ跟随”是用户最期望的一种交互方式，此外按照投票率从高到低，还依次包括如下交互方式**。

### 3.5.1. 抛鲜花&丢板鞋

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
当用户跟随DJ时，如果听到不喜欢的音乐，可以丢个板鞋，听到喜欢的音乐时择可以抛个鲜花。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
我们在听音乐的时候，是希望能安静下心来听音乐，用户可以一遍听音乐一边做其他事情，而不是像看视频一样会一直盯着屏幕，当然了弹幕、文本评论等就显得有些多余，但是也有相当一部分用户喜欢一边听音乐一边发评论、弹幕进行交互。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
抛鲜花、丢板鞋，只是两种不同的事件操作，直接上报给服务器，上报参数包括了当前的DJ房间号或者跟随的DJ的uid，这样服务器收到之后直接将事件传送给map.get(uid)中的所有uid对应的App就可以了。

### 3.5.2. 发送弹幕

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
处理方式与3.5.1几乎完全相同。

### 3.5.3. 文本评论

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
处理方式与3.5.1几乎完全相同。

## 3.6. 选择心情

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
根据心情来进行推荐其实是个复杂的工作，我们小组在这么短时间内、没有大量用户数据积累，是不可能做出一个高质量的推荐系统的。我们可以采取一个折衷的办法，即DJ在进行音乐分享时可以对此时播放的音乐歌单或者当前阶段的心情添加一个标签。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
这样当其他用户选择心情时，选择心情事件会被上报给服务器，服务器在用户选择的心情与各个DJ设定的心情（或者设定的歌单的心情）之间进行一个匹配，筛选出心情最佳匹配的DJ返回给用户，以便用户选择DJ并进行跟随操作。

## 3.7. 其他

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
其他的一些用户操作，比如App请求一些静态资源的url，这里的url需要由服务器事先下发给App。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
这类url相关的请求操作，包括App请求DJ及其他用户的头像、音乐文件等。

# 4. 概要设计说明
## 4.1. 概要设计
## 4.2. 视觉设计 
## 4.3. 交互设计
## 4.4. 产品原型

# 5. 详细设计说明

## 5.1. 配置说明

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
服务器监听端口：**20000/tcp**。

## 5.2. 应用协议

### 5.2.1. 事件类型定义
```
class EventType {

	// dj相关的事件类型
	public static const int DJ_SHARE_START_EVT = 0;
	public static const int DJ_SHARE_STOP_EVT = 1;
	public static const int DJ_LISTEN_FOLLOW_EVT = 2;
	public static const int DJ_LISTEN_UNFOLLOW_EVT = 3;
	
	// 音乐播放控制相关的事件类型
	public static const int MUSIC_SWITCH_NEXT_EVT = 4;
	public static const int MUSIC_SWITCH_PREV_EVT = 5;
	public static const int MUSIC_FORWARD_EVT = 6;
	public static const int MUSIC_BACKWARD_EVT = 7;
	public static const int MUSIC_START_EVT = 8;
	public static const int mUSIC_STOP_EVT = 9;
	public static const int MUSIC_RESUME_EVT = 10;
	public static const int MUSIC_INC_VOLUME_EVT = 11;
	public static const int MUSIC_DEC_VOLUME_EVT = 12;
	
	// 交互相关的事件类型
	public static const int LIKE_EVT = 13;
	public static const int DISLIKE_EVT = 14;
	public static const int BULLET_SCREEN_START_EVT = 15;
	public static const int BULLET_SCREEN_ADD_EVT = 16;
	public static const int BULLET_SCREEN_NEXT5_EVT = 17;
	public static const int BULLET_SCREEN_STOP_EVT = 18;

	// 文本评论（不支持嵌套评论）
	public static const int EVAL_ADD_EVT = 19;
	public static const int EVAL_DEL_EVT = 20;

	// 歌单创建相关的事件
	// ...
}
```
### 5.2.2. 事件上报报文格式

|  cmd/eventtype  | data1 | data2 | data3 | data4 | ...... | datan | endflag |
|:---------------:|:-----:|:-----:|:-----:|:-----:|:------:|:-----:|:-------:|
|    上报事件     |数据项1|数据项2|数据项3|数据项4| ...... |数据项n|   @ @   |
|     2 bytes     | 数据1 |  ...  |  ...  |  ...  |  ...   |  ...  | 2 bytes |

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
说明：
- tcp是面向流的、无边界的协议，因此需要一个特殊的标记@@来标记每次请求数据的结尾标记，实际上如果发送的应用层报文比较短，之间的交互时间时间足够长，也不会有大的影响，但是总归是不靠谱的。因此应该明确地标记出流边界，并在后台程序中予以处理，app中也应该做相同的处理;
- 有的上报事件包括不止一个数据项，例如“跟随事件”DJ_LISTEN_FOLLOW_EVT，其需要包括一个当前用户的uid以及一个DJ用户的uid（将uid在服务器中记录下来也可以），这种的话可以考虑使用一个分界符进行分隔，例如","、"/"、";"，服务器再解析请求数据的时候直接skip这个char就可以，也无需关心到底是什么分界符；

### 5.2.3. 事件/数据下发报文格式

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
请参考5.2.2节。

## 5.3. 后台详细设计

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
- Server.java
```
class Server {
	// 维护一个dj-跟随者关系，K即dj的uid（app端可以上报qq的uin，如果能获取到的话），
	// V是一个LinkedList<UID_TPYE>，维护跟随当前dj的所有跟随者
	private HashMap<K,V> map;

	// 维护一个conn_socket，记录所有成功建立的连接
	private LinkedList<Socket>;

	public static void main() {

		// listen on *:20000

		while(true) {
			Socket socket = accept();
			
			// 对socket进行处理可以有两种方式

			// 方法1：直接一个线程对一个socket进行处理
			// 优缺点：实现简单，
			//         但是可能会创建大量线程，最终可能导致栈溢出，
			//         大量的线程之间的同步可能会带来比较明显的性能问题；
			new ProcessThread(socket);

			// 方法2：一个线程对一个socket，只负责解析数据并构建一个Callable任务，
			//        然后将该任务提交至线程池，由线程池负责调度执行；
			// 优缺点：应该比方法1有更快的响应速度，单位时间内处理的操作事件更多，
			//         但是没有解决线程创建过多的问题；

			// 方法3：socket+channel实现，类似于libev，其底层是基于epoll(linux)；
			// 优缺点：可以使用有限的线程数量来异步处理众多socket，性能应该比较好；

			// 总结：鉴于时间有限以及目前分工的问题，可以暂时只考虑使用方法1来进行实现
		}
	}

}
```

- ProcessThread.java
```
class ProcessThread extends Thead {
	Socket socket;

	@Override
	void run() throws Exception {

		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		if(inputStream==null || outputStream==null)
			throw new Exception("...");

		while(!socket.isClosed) {
			// read event
			new_evt = inputStream.read();

			switch(new_evt) {
				// dj相关事件
				case EventType.xxx1:
				case EventType.xxx2:
				case EventType.xxx3:
					processThisKindEvents(evt);
					break;
				// 音乐播放相关事件
				case EventType.xxx4:
				case EventType.xxx5:
					processThatKindEvents(evt);
					break;
				// 未知事件
				default:
					throw new Exception("nknown event");
			}
		}
	}
}
```

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
我们做后台的开发人员可以按照待处理的事件类型进行分工、协作开发，最后整合起来。

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
建议在写Server代码的时候也把Client代码写了，这样也方便自己测试。

- 其他
还包括其他的一些，比如HeartBeat的处理线程。

## 5.2. iOS-App详细设计

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
略。

## 5.3. Android-App详细设计

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
略。

## 6. 数据库设计说明

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
关于数据库的一些设计，目前着重要考虑的是：
- 用户列表以及与其关联的歌单；
- 歌单以及与其关联的歌曲；
- 歌单以及与其关联的标签，例如心情标签、所处环境标签等；
- 歌曲以及与其关联的相关信息，包括歌手、头像、文件uri；

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
关于数据库中数据的一些读取、写入：
- 为了减轻数据库的访问压力，可以将较热的DJ的歌单缓存到memcached中；
- 为了保存用户的在线数据，例如follow过哪些dj，应该在处理事件时有相应的写db操作；

## 7. 测试用例
## 8. 测试计划
## 9. 安装手册
## 10. 项目阶段报告

