using System.Collections;
using UnityEngine;
using UnityEngine.AI;
using UnityEngine.Audio;

public class BomberController : MonoBehaviour {

	public NavMeshAgent bomber;
	public Transform target;
	public float range = 10f;
	public float turnSpeed = 5.0f;
	float bomberHealth = 5f;
	Color originalColor;
	Vector3 bomberSize;
	bool coroutineCheck = false;

	public PlayerHealth healthcalc;
	public GameObject playerShield;
	public ShieldPowerup shieldUpdate;
	public AudioSource source;
	public AudioClip clip;
	public AudioSource source_player_damaged;
	public AudioClip clip_player_damaged;
	public AudioSource source_hiss;
	public AudioClip clip_hiss;

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

			bomber.SetDestination (target.position);
		}
	
		if (disToPlayer <= 3) {
			
			bomber.speed = 2f;
			gameObject.GetComponent<Renderer> ().material.color = Color.red;
			if (coroutineCheck == false)
			StartCoroutine("SelfDestruct");
		
		}
		if (disToPlayer < 100 && bomber.speed == 2f) {
			bomberSize = transform.localScale;

			bomberSize.x += Time.deltaTime;
			bomberSize.y += Time.deltaTime;
			bomberSize.z += Time.deltaTime;

			transform.localScale = bomberSize;
		}
	
	}

	IEnumerator SelfDestruct()
	{
		coroutineCheck = true;
		PlayHissSound ();
		yield return new WaitForSeconds (1f);
			Destroy (this.gameObject);
		if (target != null) {
			float disToPlayer = Vector3.Distance (transform.position, target.transform.position);

			if (disToPlayer <= 5.5f) {
				
				healthcalc.HealthCalc ();
				PlayPlayerSound ();

				if (playerShield.activeSelf == true)
					shieldUpdate.ShieldUpdate ();
		}

		}
	}
		
	void OnCollisionEnter (Collision col)
	{
		if (col.gameObject.tag == "PlayerProjectile") {
			bomberHealth -= 1f;
			gameObject.GetComponent<Renderer> ().material.color = Color.red;
			Invoke ("ResetColor", 0.1f);
			PlaySound ();
		}
		if (bomberHealth <= 0) {
			PlaySound ();
			Destroy (this.gameObject);
		}
	}

	void ResetColor ()
	{

		gameObject.GetComponent<Renderer> ().material.color = originalColor;
	}
		
	void PlaySound () {
		source.PlayOneShot (clip, 1f);
	}

	void PlayPlayerSound () {
		source_player_damaged.PlayOneShot (clip_player_damaged, 1f);
	}

	void PlayHissSound () {
			source_hiss.PlayOneShot (clip_hiss, 1f);
	}

	void OnDrawGizmos() {
		Gizmos.color = Color.red;
		Gizmos.DrawWireSphere(transform.position, 5.5f);
	}
}
