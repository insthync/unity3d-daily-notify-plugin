using UnityEngine;
using System;
using System.Collections;
using System.Runtime.InteropServices;

public class DailyNotifyObject : MonoBehaviour {
	
	#if UNITY_IPHONE
	IntPtr notifier;
	#elif UNITY_ANDROID
	AndroidJavaObject notifier;
	#endif
	
	#if UNITY_IPHONE
	[DllImport("__Internal")]
	private static extern IntPtr _DailyNotifyPlugin_Init();
	[DllImport("__Internal")]
	private static extern void _DailyNotifyPlugin_SetNotifyAppearance(IntPtr instance, string title, string content);
	#endif
	
	public void Init()
	{
		#if UNITY_IPHONE
		notifier = _DailyNotifyPlugin_Init();
		#elif UNITY_ANDROID
		notifier = new AndroidJavaObject("com.insthync.dailynotify.DailyNotifyPlugin");
		notifier.Call("Init");
		#endif
	}
	
	public void SetNotifyAppearance(string title, string content)
	{
		#if UNITY_IPHONE
		if (notifier == IntPtr.Zero)
			return;
		_DailyNotifyPlugin_SetNotifyAppearance(notifier, title, content);
		#elif UNITY_ANDROID
		if (notifier == null)
			return;
		notifier.Call("SetNotifyAppearance", title, content);
		#endif
	}
}
