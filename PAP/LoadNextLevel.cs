using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LoadNextLevel : MonoBehaviour {


	void Update () {

		if (Input.GetKeyDown (KeyCode.Escape))
			SceneManager.LoadScene ("Menu");
		PlayerHealth.playerHealth = 4f;
	}
}
