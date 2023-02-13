using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObstacleSpawner : MonoBehaviour
{
    public Transform[] objSpawnPoints;
    public GameObject[] obstacleSpawnRate;
    public GameObject ball;
    public GameObject bird;
    private float countdown;
    Vector3 currentPos;
    Vector3 secondPos;
    Vector3 dupePos;

    void Start()
    {
        countdown = ObjectSpawner.maxTime;
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
                ChooseObstacle();
                countdown = ObjectSpawner.maxTime;
            }
        }
    }

    public void ChooseObstacle()
    {
        int randomObstacle = Random.Range(1, 11);
        if (randomObstacle > 2)
            SpawnObstacle();

        if (randomObstacle == 1)
            StartCoroutine(SpawnBall());

        if (randomObstacle == 2)
            StartCoroutine(SpawnBird());

        Debug.Log(randomObstacle);
    }

    IEnumerator DupeObstacle()
    {
        yield return new WaitForSeconds(ObjectSpawner.maxTime / 2);

        int posNumber = Random.Range(0, objSpawnPoints.Length);
        int posNumberSpawnRate = Random.Range(0, obstacleSpawnRate.Length);
        currentPos = objSpawnPoints[posNumber].position;
        if (ObjectSpawner.playerAlive == true)
        {
            Instantiate(obstacleSpawnRate[posNumberSpawnRate], currentPos, Quaternion.Euler(0, 60, 0));
            int dupePosNumber = Random.Range(0, objSpawnPoints.Length);
            int dupeChance = Random.Range(0, 101);
            if (currentPos != objSpawnPoints[dupePosNumber].position && dupeChance < 41)
            {
                Instantiate(obstacleSpawnRate[posNumberSpawnRate], objSpawnPoints[dupePosNumber].position, Quaternion.Euler(0, 60, 0));
            }
            int secondDupePosNumber = Random.Range(0, objSpawnPoints.Length);
            int secondDupeChance = Random.Range(0, 101);
            secondPos = objSpawnPoints[dupePosNumber].position;
            if (objSpawnPoints[secondDupePosNumber].position != secondPos && objSpawnPoints[dupePosNumber].position != currentPos)
            {
                Instantiate(obstacleSpawnRate[posNumberSpawnRate], objSpawnPoints[secondDupePosNumber].position, Quaternion.Euler(0, 60, 0));
            }
        }
    }

    public void SpawnObstacle()
    {
        StartCoroutine(DupeObstacle());

        int posNumber = Random.Range(0, objSpawnPoints.Length);
        int posNumberSpawnRate = Random.Range(0, obstacleSpawnRate.Length);
        dupePos = objSpawnPoints[posNumber].position;
        int dupeChance = Random.Range(0, 101);

        if (dupeChance < 41)
        {
            Instantiate(obstacleSpawnRate[posNumberSpawnRate], dupePos, Quaternion.Euler(0, 60, 0));
            int dupePosNumber = Random.Range(0, objSpawnPoints.Length);
            int secondDupeChance = Random.Range(0, 101);
            if (dupePos != objSpawnPoints[dupePosNumber].position && secondDupeChance < 61)
            {
                Instantiate(obstacleSpawnRate[posNumberSpawnRate], objSpawnPoints[dupePosNumber].position, Quaternion.Euler(0, 60, 0));
            }
        }
    }

    IEnumerator SpawnBall()
    {
        yield return new WaitForSeconds(ObjectSpawner.maxTime / 2);
        int posNumber = Random.Range(0, objSpawnPoints.Length);
        Instantiate(ball, objSpawnPoints[posNumber].position, Quaternion.identity);
    }

    IEnumerator SpawnBird()
    {
        yield return new WaitForSeconds(ObjectSpawner.maxTime / 2);
        int posNumber = Random.Range(0, objSpawnPoints.Length);
        Instantiate(bird, objSpawnPoints[posNumber].position, Quaternion.identity);
    }
}
