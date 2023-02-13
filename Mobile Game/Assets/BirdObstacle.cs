using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BirdObstacle : MonoBehaviour
{
    Rigidbody rb;
    Vector3 startPos;
    Vector3 upPos;
    Vector3 downPos;
    public float speed;
    bool reset;
    ObstacleSpawner stopSpawn;
    GameObject spawn;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
        startPos.y = transform.position.y;
        upPos.y = startPos.y + 0.5f;
        downPos.y = startPos.y - 0.5f;

        spawn = GameObject.Find("Obstacle Positions");
        stopSpawn = spawn.GetComponent<ObstacleSpawner>();

        if (startPos.y < 0)
        {
            Destroy(this.gameObject);
            stopSpawn.ChooseObstacle();
        }
    }

    void FixedUpdate()
    {
        if (reset != true)
        rb.AddForce(new Vector3(0, speed, 0), ForceMode.Acceleration);
        
        if (transform.position.y > upPos.y)
        {
            reset = true;
            rb.AddForce(new Vector3(0, -speed, 0), ForceMode.Acceleration);
        }

        if (transform.position.y < downPos.y)
        {
            reset = false;
        }
    }
}
