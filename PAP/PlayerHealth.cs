using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerHealth : MonoBehaviour {

	public GameObject heart;
	public GameObject heart2;
	public GameObject heart3;
	public GameObject heart4;
	public static float playerHealth = 4f;

	public void HealthCalc()
	{
		
			PlayerHealth.playerHealth = PlayerHealth.playerHealth - 1;

			if (PlayerHealth.playerHealth <= 0) {
			heart.SetActive (false);
			heart2.SetActive (false);
			heart3.SetActive (false);
			heart4.SetActive (false);
				Destroy (this.gameObject);
			}
		if (playerHealth == 1) {
			heart.SetActive (true);
			heart2.SetActive (false);
			heart3.SetActive (false);
			heart4.SetActive (false);
		}
		if (playerHealth == 2) {
			heart.SetActive (true);
			heart2.SetActive (true);
			heart3.SetActive (false);
			heart4.SetActive (false);
		}
		if (playerHealth == 3) {
			heart.SetActive (true);
			heart2.SetActive (true);
			heart3.SetActive (true);
			heart4.SetActive (false);
		}

	}
		
}
