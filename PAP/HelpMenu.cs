using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class HelpMenu : MonoBehaviour {

	public GameObject helpPanel;

	void OnTriggerEnter (Collider col)
	{
		if (col.gameObject.tag == "Player")
			helpPanel.SetActive (true);
	}

	void OnTriggerExit (Collider col)
	{
		if (col.gameObject.tag == "Player")
			helpPanel.SetActive (false);
	}
}
