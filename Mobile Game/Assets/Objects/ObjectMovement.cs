using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObjectMovement : MonoBehaviour
{
    public static float speed;
    public float deletePos;
    void Start()
    {

    }

    void Update()
    {
        transform.Translate(Vector3.left * Time.deltaTime * speed, Space.World);

        if (transform.position.x <= -deletePos)
            Destroy(this.gameObject);
    }
}
