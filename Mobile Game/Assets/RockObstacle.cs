using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RockObstacle : MonoBehaviour
{
    void Start()
    {
        transform.rotation = Random.rotation;
    }

    void Update()
    {
        float dt = Time.deltaTime;
        transform.Rotate(50f * dt, 50f * dt, 50f * dt);
    }
}
