using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class FinishLevel : MonoBehaviour {

	void OnTriggerEnter (Collider col)
	{
		if (col.gameObject.tag == "Player")
			SceneManager.LoadScene ("FinishLevel01");
		PlayerHealth.playerHealth = 4f;
			
	}
}
