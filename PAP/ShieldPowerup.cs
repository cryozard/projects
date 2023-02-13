using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ShieldPowerup : MonoBehaviour {

	GameObject player;
	public GameObject shieldUI;

	public AudioClip clip;
	public AudioSource source;

	void Start()
	{
		player = GameObject.Find ("Player");
	}

	void Update ()
	{
		transform.Rotate (Vector3.up * 45 * Time.deltaTime);
	}

	void OnTriggerEnter (Collider other)
	{
		if (other.gameObject.tag == "Player") {
			PlaySound ();
			PlayerHealth.playerHealth = PlayerHealth.playerHealth + 1;
			player.GetComponent<Renderer> ().material.color = Color.green;
			Destroy (this.gameObject);
			shieldUI.SetActive (true);

		}
	}


	public void ShieldUpdate()
	{ 
			player.GetComponent<Renderer> ().material.color = Color.white;
			shieldUI.SetActive (false);
	}

	void PlaySound() {

		source.PlayOneShot (clip, 0.8f);
	}
}
