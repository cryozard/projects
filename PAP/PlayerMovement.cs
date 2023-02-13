using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerMovement : MonoBehaviour {

	[SerializeField] private Transform target;
	public CharacterController charController;
	public float speed = 8.0f;
	public float gravity = 9.8f;
	float verticalVelocity;

	void Update () {

		Vector3 movement = Vector3.zero;
		float hAxis = Input.GetAxisRaw ("Horizontal");
		float vAxis = Input.GetAxisRaw ("Vertical");

		if (hAxis != 0 || vAxis != 0) {
			movement.x = hAxis * speed;
			movement.z = vAxis * speed;
			movement = Vector3.ClampMagnitude (movement, speed);
		}
			
		movement = Vector3.ClampMagnitude (movement, speed);
		movement = movement * Time.deltaTime;

		verticalVelocity = verticalVelocity - gravity * Time.deltaTime;
		movement.y = verticalVelocity * Time.deltaTime;
	
		CollisionFlags flags = charController.Move (movement);

		if ((flags & CollisionFlags.Below) != 0) 
		{
			verticalVelocity = -5f;
		} 
			
		Plane playerPlane = new Plane (Vector3.up, transform.position);

		Ray ray = Camera.main.ScreenPointToRay (Input.mousePosition);

		float hitdist = 0f;

		if (playerPlane.Raycast (ray, out hitdist)) {

			Vector3 targetPoint = ray.GetPoint (hitdist);

			Quaternion targetRotation = Quaternion.LookRotation (targetPoint - transform.position);

			transform.rotation = Quaternion.Slerp (transform.rotation, targetRotation, speed * Time.deltaTime);
		}
	}
}