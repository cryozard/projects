using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ObstacleManager : MonoBehaviour
{
    float destroyCountdown;
    GameObject score;
    ScoreHandler scoreUpdate;
    public GameObject destroy;

    void Start()
    {
        score = GameObject.Find("Canvas");
        scoreUpdate = score.GetComponent<ScoreHandler>();
        destroyCountdown = 0.1f;
    }

    private void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.tag == "DeleteObj")
            Destroy(this.gameObject);

        if (other.gameObject.tag == "Ring")
            Destroy(this.gameObject);

        if (other.gameObject.tag == "GunPowerup")
            Destroy(other.gameObject);

        if (other.gameObject.tag == "TimePowerup")
            Destroy(other.gameObject);

        if (other.gameObject.tag == "ShieldPowerup")
            Destroy(other.gameObject);
    }

    private void OnTriggerStay(Collider other)
    {
        if (other.gameObject.tag == "ProjectileEmitter")
        {
            destroyCountdown -= Time.deltaTime;
            if (destroyCountdown <= 0)
            {
                Destroy(this.gameObject);
                scoreUpdate.scoreCount += 1;
                Instantiate(destroy, transform.position, Quaternion.identity);
                StartCoroutine(scoreUpdate.IncrementScore());
            }
        }
    }

    private void OnCollisionEnter(Collision other)
    {
        if (other.gameObject.tag == "Obstacle")
            Destroy(this.gameObject);
    }
}
