using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyTower : MonoBehaviour {

	public Transform target;
	float turnSpeed = 5.0f;
	public float range = 8.0f;
	public float fireRate = 1.0f;
	float fireCountdown = 1f;
	float bulletSpeed = 20f;
	float towerHealth = 3f;

	public GameObject bulletPrefab;
	public Transform firePoint;
	Color originalColor;

	public AudioClip clip;
	public AudioSource source;
	public AudioClip clip_damaged;
	public AudioSource source_damaged;

	void Start () {
		originalColor = gameObject.GetComponent<Renderer> ().material.color;
	}

	void Update () {
		if (target == null) {
			return;
		}
		float disToPlayer = Vector3.Distance (transform.position, target.transform.position);

		if (disToPlayer <= range) {
			Vector3 dir = target.position - transform.position;
			Quaternion lookRotation = Quaternion.LookRotation (dir);
			Vector3 rotation = Quaternion.Lerp (transform.rotation, lookRotation, Time.deltaTime * turnSpeed).eulerAngles;
			transform.rotation = Quaternion.Euler (0f, rotation.y, 0f);

			if (fireCountdown <= 0) {
				Shoot ();
				fireCountdown = 1f / fireRate;
			}
			fireCountdown -= Time.deltaTime;
		}
	


	}

	void Shoot () {
		
		var bullet = (GameObject)Instantiate (bulletPrefab, firePoint.position, firePoint.rotation);

		bullet.GetComponent<Rigidbody> ().velocity = bullet.transform.forward * bulletSpeed;
		PlaySound ();

		Destroy (bullet, 2.0f);
	}

	void OnCollisionEnter (Collision col)
	{
		if (col.gameObject.tag == "PlayerProjectile") {
			towerHealth -= 1f;
			gameObject.GetComponent<Renderer> ().material.color = Color.red;
			PlayDamagedSound ();
			Invoke ("ResetColor", 0.1f);
		}
		if (towerHealth <= 0)
			Destroy (this.gameObject);
	
	}

	void ResetColor ()
	{
		gameObject.GetComponent<Renderer> ().material.color = originalColor;
	}

	void PlaySound () {
		source.PlayOneShot (clip, 1f);
	}

	void PlayDamagedSound() {
		source_damaged.PlayOneShot (clip_damaged, 1f);
	}
}
