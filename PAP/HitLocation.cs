using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HitLocation : MonoBehaviour {

	RaycastHit hit;
	public GameObject hitLocation;

	void Start () {
		Ray ray = new Ray (transform.position, Vector3.down);
		if (Physics.Raycast (ray, out hit, 10)) {

			if (GetComponent<Collider>().gameObject.tag == "Player") {
				Physics.IgnoreCollision (GetComponent<Collider> (), GetComponent<Collider> ());
			}
			if (GetComponent<Collider>().gameObject.tag == "BossPlatform") {
				Physics.IgnoreCollision (GetComponent<Collider> (), GetComponent<Collider> ());
			}

			else {
			Instantiate (hitLocation, hit.point, transform.rotation);
			}
	}
}


		
}