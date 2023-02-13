using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerCollisions : MonoBehaviour {

	public PlayerHealth healthCalc;
	public ShieldPowerup shieldcolor;

	public AudioClip clip;
	public AudioSource source;
	public AudioClip clip_plate;
	public AudioSource source_plate;


	void OnTriggerEnter (Collider col)
	{
		if (col.gameObject.tag == "Trap") {
			PlaySound ();
			Destroy (this.gameObject);
		}
			



		if (col.gameObject.tag == "EnemyProjectile") {
			shieldcolor.ShieldUpdate ();
			healthCalc.HealthCalc ();
			PlaySound ();
		}
	}

	void OnCollisionEnter (Collision col)
	{
		if (col.gameObject.tag == "EnemyProjectile") {
			healthCalc.HealthCalc ();
			shieldcolor.ShieldUpdate ();
			PlaySound ();
		}
		if (col.gameObject.tag == "BossBomb") {
			PlaySound ();
			Destroy (this.gameObject);
		}
	}

	void PlaySound () {
		source.PlayOneShot (clip, 1f);
	}

	void PlayPlateSound() {

		source_plate.PlayOneShot (clip_plate, 1f);
	}

}
