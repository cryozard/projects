using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class DeathScreen : MonoBehaviour {
	
	void Update () {

		if (Input.GetKeyDown (KeyCode.Return))
			SceneManager.LoadScene ("Level01");
		PlayerHealth.playerHealth = 4f;
	}
}
