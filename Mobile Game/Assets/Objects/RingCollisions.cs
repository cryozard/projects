using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RingCollisions : MonoBehaviour
{

    private void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.tag == "DeleteObj")
            Destroy(this.gameObject);
    }

    private void OnCollisionEnter(Collision other)
    {
        if (other.gameObject.tag == "Obstacle")
            Destroy(this.gameObject);
    }
}
