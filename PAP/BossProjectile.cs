using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BossProjectile : MonoBehaviour {

	public float bulletSpeed;

	void Update () {
		transform.position += transform.forward * bulletSpeed*Time.deltaTime;
	}


	void OnTriggerEnter (Collider col)
	{
		if (col.gameObject.tag == "Player")
			Destroy (this.gameObject);
	}

	void OnCollisionEnter (Collision col)
	{


		if (col.gameObject.tag == "BossShield") {
			Physics.IgnoreCollision (col.collider, GetComponent<Collider> ());
		} 
		else if (col.gameObject.tag == "Enemy") {
			Physics.IgnoreCollision (col.collider, GetComponent<Collider> ());
		} 
		else if (col.gameObject.tag == "PlayerProjectile") {
			Physics.IgnoreCollision (col.collider, GetComponent<Collider> ());
		} 
		else if (col.gameObject.tag == "EnemyProjectile") {
			Physics.IgnoreCollision (col.collider, GetComponent<Collider> ());
		} 
		else if (col.gameObject.tag == "BossBomb") {
			Physics.IgnoreCollision (col.collider, GetComponent<Collider> ());
		}
		else {
			Destroy (this.gameObject);
		}
}
}
