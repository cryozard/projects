using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerInputs : MonoBehaviour {

	public Transform projectileSpawn;
	public GameObject projectilePrefab;
	float fireRate = 0.5f;
	float fireNext;


	void Start () {

		//Cursor.lockState = CursorLockMode.Locked;
	}
	

	void Update () {

		if (Input.GetKeyDown (KeyCode.Mouse0) && Time.time > fireNext) {

			fireNext = Time.time + fireRate;

			Fire ();
		}
	}

	void Fire ()
	{
		if (PauseMenu.GameIsPaused == false) {
			var projectile = (GameObject)Instantiate (projectilePrefab, projectileSpawn.position, projectileSpawn.rotation);

			projectile.GetComponent<Rigidbody> ().velocity = projectile.transform.forward * 20;

			Destroy (projectile, 2.0f);
		} 
		else {
			return;
		}
	}
}
