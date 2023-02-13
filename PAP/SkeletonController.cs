using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class SkeletonController : MonoBehaviour {


	public NavMeshAgent skeleton;
	public Transform target;
	public float range = 10f;
	public float turnSpeed = 5.0f;
	float skeletonHealth = 10f;
	Color originalColor;

	public AudioSource source;
	public AudioClip clip;

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

			skeleton.SetDestination (target.position);
		}


		}
		
	

	void OnCollisionEnter (Collision col)
	{
		if (col.gameObject.tag == "PlayerProjectile") {
			skeletonHealth -= 1f;
			gameObject.GetComponent<Renderer> ().material.color = Color.red;
			Invoke ("ResetColor", 0.1f);
			PlaySound ();
		}
		if (skeletonHealth <= 0) {
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

}
