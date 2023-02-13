using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class BossController : MonoBehaviour {

	public Transform target;
	float turnSpeed = 5.0f;
	public float range = 8.0f;
	public float fireRate = 1.0f;
	float fireCountdown = 3f;
	float projSpeed = 20f;
	float bossHealth = 20f;

	public Slider healthBar;
	public GameObject bulletPrefab;
	public Transform firePoint;
	Color originalColor;
	public BombDropper zoneUpdate;

	public AudioClip clip;
	public AudioSource source;

	void Start () {
		originalColor = gameObject.GetComponent<Renderer> ().material.color;
	}

	void Update () {
		healthBar.value = bossHealth;

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
		bullet.GetComponent<BossProjectile>().bulletSpeed = projSpeed;

		Destroy (bullet, 2.0f);
	}

	void OnCollisionEnter (Collision col)
	{
		if (col.gameObject.tag == "PlayerProjectile") {
			{
				bossHealth -= 1f;
				gameObject.GetComponent<Renderer> ().material.color = Color.red;
				PlaySound ();
				Invoke ("ResetColor", 0.1f);
			}
			if (bossHealth <= 0) {
				Destroy (this.gameObject);
				Destroy (GameObject.FindGameObjectWithTag ("BossShield"));
				Destroy (GameObject.FindGameObjectWithTag ("BossShieldBack"));
				Destroy (GameObject.FindGameObjectWithTag ("BossHealthBar1"));
				zoneUpdate.ZoneUpdate ();
			}
			}
	}

	void PlaySound () {

		source.PlayOneShot (clip, 1f);
	}

	void ResetColor ()
	{
		gameObject.GetComponent<Renderer> ().material.color = originalColor;
	}
}
