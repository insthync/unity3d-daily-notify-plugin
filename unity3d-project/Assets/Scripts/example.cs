using UnityEngine;
using System.Collections;
using genc.genpon;
using Prime31;

public class example : MonoBehaviour {

	public DailyNotifyObject dailyNotifyObject;
	void Awake () {
		if (dailyNotifyObject == null) {
			dailyNotifyObject = new GameObject ("_testObj").AddComponent<DailyNotifyObject> ();
		}
		dailyNotifyObject.Init ();
		dailyNotifyObject.SetNotifyAppearance ("Title (android only)", "Content as you wish");
	}
}
