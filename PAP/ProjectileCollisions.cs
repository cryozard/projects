using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ProjectileCollisions : MonoBehaviour {

	void OnCollisionEnter (Collision col)
	{
		if (col.gameObject.tag == "Player") {
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
		else if (col.gameObject.tag == "BossPlatform") {
			Physics.IgnoreCollision (col.collider, GetComponent<Collider> ());
		}
		else {
		Destroy (this.gameObject);
		}
	
	}
}
