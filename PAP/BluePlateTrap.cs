using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BluePlateTrap : MonoBehaviour {

	bool plateActivated = false;

	void Start () {
		BladeRotate2.bladeSpeed2 = 360f;
	}

	void OnTriggerEnter (Collider col)
	{
		if (col.gameObject.tag == "Player") {
			if (plateActivated == false) {
				BladeRotate2.bladeSpeed2 = 60f;
			}
			plateActivated = true;
		}
		}

	void Update () {


	}
}
