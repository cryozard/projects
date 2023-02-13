﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PauseMenu : MonoBehaviour {

	public static bool GameIsPaused = false;
	public GameObject pauseMenuUI;
	public GameObject helpPanel;
	public GameObject deathScreen;

	void Update () {
		if (Input.GetKeyDown (KeyCode.Escape))
		{
			if (GameIsPaused) 
			{
				Resume ();
			} 
			else 
			{
				Pause();
			}
		}
	}

	public void Resume() {

		pauseMenuUI.SetActive (false);
		Time.timeScale = 1f;
		GameIsPaused = false;

	}

	void Pause () {

		pauseMenuUI.SetActive (true);
		Time.timeScale = 0f;
		GameIsPaused = true;
		helpPanel.SetActive (false);
	}

	public void LoadMenu ()
	{
		Time.timeScale = 1f;
		SceneManager.LoadScene ("Menu");
		GameIsPaused = false;
	}

	public void QuitGame ()
	{
		Application.Quit ();
	}
}
