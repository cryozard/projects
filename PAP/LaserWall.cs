using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LaserWall : MonoBehaviour {

	public static bool laserWall;
	public GameObject laser;

	void Start () {
		
	}

	void Update () {

		if (laserWall == true) {

			laser.SetActive (true);
		} 

		else {

			laser.SetActive (false);
		}
	}
}
