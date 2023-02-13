using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BossShield : MonoBehaviour {

	float orbitSpeed = 90f;
	float shieldHealth = 10f;
	public Transform Boss;
	Color originalColor;

	void Start () {

		originalColor = gameObject.GetComponent<Renderer> ().material.color;
	}
	

	void Update () {
		transform.RotateAround(Boss.transform.position, Boss.transform.up, orbitSpeed * Time.deltaTime);
	}

	void OnCollisionEnter (Collision col)
	{
		if (col.gameObject.tag == "PlayerProjectile") 
		{
			shieldHealth -= 1f;
			gameObject.GetComponent<Renderer> ().material.color = Color.red;
			Invoke ("ResetColor", 0.1f);
		}
		if (shieldHealth <= 0)
			Destroy (this.gameObject);
		
	}

	void ResetColor ()
	{
		gameObject.GetComponent<Renderer> ().material.color = originalColor;
	}

}
