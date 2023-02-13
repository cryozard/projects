using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy_ProjectileCollisions : MonoBehaviour {

	void OnCollisionEnter (Collision col)
	{
		if (col.gameObject.tag == "Player")
			Destroy (this.gameObject);


		if (col.gameObject.tag == "BossShield") {
			Physics.IgnoreCollision (col.collider , GetComponent<Collider> ());
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
		else if (col.gameObject.tag == "TowerBossZone") {
			Physics.IgnoreCollision (col.collider, GetComponent<Collider> ());
		} 
		else {
			Destroy (this.gameObject);
		}
	
	}
}
