using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BossBomb : MonoBehaviour {

	public static bool bombCollided;

	void OnCollisionEnter (Collision col)
	{
		bombCollided = true;

		if (col.gameObject.tag == "Player") {
			Destroy (GameObject.FindGameObjectWithTag ("Player"));
			Destroy (this.gameObject);

			bombCollided = false;
		}
	
	
	}

	void OnTriggerEnter (Collider col)
	{
		if (col.gameObject.tag == "BossPlatform")
		{
			Destroy (GameObject.FindGameObjectWithTag ("BossPlatform"));
		}
	}
}
