using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.VFX;

public class PlayerManager : MonoBehaviour
{
    private Rigidbody rb;
    public float speed;
    public ScoreHandler scoreUpdate;
    bool shieldActive;
    bool timeSlowed;
    float slowedTime;
    float lerpTime = 0f;
    bool gunActive;
    public GameObject projectileEmitter;
    float gunTime;
    public GameObject destroySelf;
    public GameObject destroyObstacle;
    public VisualEffect flamethrower;

    void Start()
    {
        rb = GetComponent<Rigidbody>();
        ObjectSpawner.playerAlive = true;
        Time.timeScale = 1f;
        projectileEmitter.SetActive(false);
        flamethrower.Stop();
    }

    void FixedUpdate()
    {
        if (ScoreHandler.gameHasStarted == true)
        {
            if (Input.GetKey(KeyCode.Space) || Input.GetMouseButton(0))
            {
                rb.AddForce(new Vector3(0, speed, 0), ForceMode.Acceleration);

            }
            else
            {
                rb.AddForce(new Vector3(0, -speed, 0), ForceMode.Acceleration);
            }
            transform.eulerAngles = new Vector3(180 - rb.velocity.y * -3f, -90, rb.velocity.y * 1.5f);
        }
    }

    private void Update()
    {
        if (gunActive == true)
        {
            gunTime -= Time.deltaTime;
            if (gunTime <= 0)
            {
                projectileEmitter.SetActive(false);
                flamethrower.Stop();
                gunActive = false;
            }
        }

        if (timeSlowed == true)
        {
            slowedTime -= Time.deltaTime;
            Time.timeScale = 0.5f;
            Time.fixedDeltaTime = 0.02f * Time.timeScale;
        }
        if (slowedTime <= 1)
        {
            if (Time.timeScale < 1 || ObjectSpawner.playerAlive == false)
            {
                lerpTime += Time.deltaTime;
                Time.timeScale = Mathf.Lerp(0.5f, 1, lerpTime / 1);
            }
            else if(Time.timeScale > 1)
            {
                Time.timeScale = 1;
                lerpTime = 0f;
            }
            if (Time.timeScale == 1)
                lerpTime = 0f;

            Time.fixedDeltaTime = 0.02f;
            timeSlowed = false;
        }
    }

    private void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.tag == "CollectRing")
        {
            Destroy(other.gameObject);
            scoreUpdate.ScoreUpdate();
        }
        if (other.gameObject.tag == "ShieldPowerup")
        {
            Destroy(other.gameObject);
            if (shieldActive == false)
                shieldActive = true;
            else
            {
                scoreUpdate.scoreCount += 10;
                StartCoroutine(scoreUpdate.IncrementScore());
            }
        }

        if (other.gameObject.tag == "GunPowerup")
        {
            Destroy(other.gameObject);
            if (gunActive != true)
            {
                projectileEmitter.SetActive(true);
                flamethrower.Play();
                gunActive = true;
                gunTime = 10;
            }
            else
            {
                scoreUpdate.scoreCount += 10;
                StartCoroutine(scoreUpdate.IncrementScore());
            }
        }
        if (other.gameObject.tag == "TimePowerup")
        {
            Destroy(other.gameObject);

            if (timeSlowed == false)
            {
                lerpTime = 0f;
                timeSlowed = true;
                slowedTime = 5f;
            }
            else
            {
                scoreUpdate.scoreCount += 10;
                StartCoroutine(scoreUpdate.IncrementScore());
            }
        }
        if (other.gameObject.tag == "MapLimit")
        {
            Destroy(this.gameObject);
            Instantiate(destroySelf, transform.position, Quaternion.identity);
        }

        if (other.gameObject.tag == "Laser")
        {
            Destroy(this.gameObject);
            Instantiate(destroySelf, transform.position, Quaternion.identity);
        }
    }

    private void OnCollisionEnter(Collision other)
    {
        if (shieldActive == true)
        {
            if (other.gameObject.tag == "Obstacle")
            {
                Destroy(other.gameObject);
                shieldActive = false;
                Instantiate(destroyObstacle, other.transform.position, Quaternion.identity);
            }
        }
        else if (other.gameObject.tag == "Obstacle")
        {
            Destroy(this.gameObject);
            Destroy(other.gameObject);
            Instantiate(destroySelf, transform.position, Quaternion.identity);
            Instantiate(destroyObstacle, other.transform.position, Quaternion.identity);
        }
    }
}
