using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SkeletonBlade : MonoBehaviour {

	public AudioClip clip_player_damaged;
	public AudioSource source_player_damaged;
	public PlayerHealth healthcalc;


	void Update () {
		transform.Rotate (Vector3.up * 360f * Time.deltaTime);
	}

	void OnTriggerEnter (Collider col)
	{
		if (col.gameObject.tag == "Player") {
			
			healthcalc.HealthCalc ();
			PlaySound ();
		}
	}

	void PlaySound () {

		source_player_damaged.PlayOneShot (clip_player_damaged, 1f);
	}
}
