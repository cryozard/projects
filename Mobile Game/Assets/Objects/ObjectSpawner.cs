using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObjectSpawner : MonoBehaviour
{
    public Transform[] objSpawnPoints;
    public GameObject[] ringSpawnRate;
    public static float maxTime;
    private float countdown;
    public static bool playerAlive;

    void Start()
    {

        countdown = maxTime;
    }

    void Update()
    {
        if (ScoreHandler.gameHasStarted == true)
        {
            if (countdown != 0)
            {
                countdown -= Time.deltaTime;
            }
            if (countdown <= 0)
            {
                SpawnRing();
                countdown = maxTime;
            }
        }
    }

    void SpawnRing()
    {
        int posNumber = Random.Range(0, objSpawnPoints.Length);
        int posNumberSpawnRate = Random.Range(0, ringSpawnRate.Length);
        if (playerAlive == true)
        {
            Instantiate(ringSpawnRate[posNumberSpawnRate], objSpawnPoints[posNumber].position, Quaternion.Euler(0, 90, 0));
        }
    }
}
