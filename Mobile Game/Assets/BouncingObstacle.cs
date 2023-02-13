using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BouncingObstacle : MonoBehaviour
{
    ObstacleSpawner stopSpawn;
    Vector3 initialPos;
    Vector3 groundPos;
    Vector3 distance;
    GameObject ground;
    GameObject spawn;
    Rigidbody rb;
    public float bounce;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
        ground = GameObject.Find("Ground");
        spawn = GameObject.Find("Obstacle Positions");
        stopSpawn = spawn.GetComponent<ObstacleSpawner>();
        initialPos.y = transform.position.y;
        groundPos.y = ground.transform.position.y;
        distance.y = initialPos.y - groundPos.y;

        if (initialPos.y < 0)
        {
            Destroy(this.gameObject);
            stopSpawn.ChooseObstacle();
        }
    }

    void FixedUpdate()
    {
        rb.AddForce(new Vector3(0, -45, 0), ForceMode.Acceleration);
    }

    private void OnCollisionEnter(Collision other)
    {
        if (other.gameObject.tag == "MapLimit")
            rb.AddForce(new Vector3(0, bounce * distance.y, 0), ForceMode.Impulse);
    }
}
