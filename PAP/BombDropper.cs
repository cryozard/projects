using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class BombDropper : MonoBehaviour {

	public GameObject healthBar;
	public GameObject healthbar2;
	public Transform target;
	public GameObject bombPrefab;
	float bombCountdown = 3f;
	float bombRate = 1f;
	public static bool isPlayerInside = false;
	static int bossCheck = 2;
	public AudioClip clip;
	public AudioSource source;
	public AudioSource main_source;
	public AudioSource boss_source;
	public GameObject redBarrier;
	public GameObject greenBarrier;


	public void OnTriggerEnter (Collider col)
	{
		
		if (col.gameObject.tag == "Player") {
			
			isPlayerInside = true;
			healthBar.SetActive (true);
			healthbar2.SetActive (true);
			main_source.Stop();
			boss_source.Play ();
			redBarrier.SetActive (true);
		}

	}

	public void OnTriggerExit (Collider col)
	{
		
		if (col.gameObject.tag == "Player") {

			isPlayerInside = false;
			healthBar.SetActive (false);
			healthbar2.SetActive (false);
			main_source.Play ();
			boss_source.Stop ();
		} 
			
	}

	void Update () {
		if (target == null) {
			return;
		}
		if (isPlayerInside == true) {
			if (bombCountdown <= 0) {
				DropBomb ();
				bombCountdown = 1f / bombRate;
			}

			bombCountdown -= Time.deltaTime;


		}
	
	}

	void DropBomb ()
	{
		var bomb = (GameObject)Instantiate (bombPrefab, target.position + new Vector3(0, 8, 0), target.rotation);

		Destroy (bomb, 1.25f);
		Invoke ("PlaySound", 1.25f);
	}

	void PlaySound () {
		source.PlayOneShot (clip, 0.8f);

	}

	public void ZoneUpdate()
	{
		bossCheck -= 1;

		if (bossCheck <= 0) {
			Destroy (GameObject.FindGameObjectWithTag ("TowerBossZone"));

			main_source.Play ();
			boss_source.Stop ();
			greenBarrier.SetActive (false);
		}
	}
}
