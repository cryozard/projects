using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PlayerDeathScreen : MonoBehaviour {

	public GameObject player;

	void Update () {
		if (player == null) {
			
				SceneManager.LoadScene ("DeathScreen");
		}
	}
}
