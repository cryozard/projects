using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LaserSpawner : MonoBehaviour
{
    public Transform[] randomPos;
    public GameObject laser;
    public ScoreHandler distance;
    float countdown;
    float laserCountdown;
    int laserChance;
    bool laserActive;
    public GameObject laserWarning;
    Vector3 laserPos;

    void Start()
    {
        countdown = 1f;
        laserActive = false;
        laserCountdown = 2f;
    }

    void Update()
    {
        if (ObjectSpawner.playerAlive == true)
        {
            if (distance.distance > 1000)
            {
                countdown -= Time.deltaTime;
                if (countdown <= 0)
                {
                    if (laserActive != true)
                        laserChance = Random.Range(0, 101);

                    if (laserChance < 6 && laserActive != true)
                    {
                        laserActive = true;
                        StartCoroutine(SpawnLaser());
                    }
                    if (laserChance >= 6)
                        countdown = 1;
                }
            }
        }
    }

    void Laser()
    {
        int posNumber = Random.Range(0, randomPos.Length);
        Instantiate(laser, laserPos, Quaternion.identity);
        StartCoroutine(DestroyLaser());
    }

    IEnumerator DestroyLaser()
    {
        yield return new WaitForSeconds(1f);
        Destroy(GameObject.FindGameObjectWithTag("Laser"));
        Destroy(GameObject.FindGameObjectWithTag("LaserWarning"));
        laserActive = false;
        countdown = 1;
    }

    IEnumerator SpawnLaser()
    {
        int posNumber = Random.Range(0, randomPos.Length);
        Instantiate(laserWarning, randomPos[posNumber].position, Quaternion.identity);
        laserPos = randomPos[posNumber].position;
        yield return new WaitForSeconds(2f);
        Laser();
    }
}
