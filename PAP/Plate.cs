using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Plate : MonoBehaviour {

	public GameObject laser;
	public AudioClip clip;
	public AudioSource source;

	bool plateActivated = false;

	void OnTriggerEnter (Collider col)
	{
		if (col.gameObject.tag == "Player") {
			if (plateActivated == false) {
				Destroy (laser);
				PlaySound ();
				plateActivated = true;
			}
		}
	}

	void PlaySound ()
	{
		source.PlayOneShot (clip, 1f);
	}
}
