using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ScoreHandler : MonoBehaviour
{
    public Button startButton;
    public static bool gameHasStarted;
    public Text scoreText;
    public int scoreCount;
    private int scoreMultiplier = 2;
    private int amountNeeded = 10;
    public int currentAmount;
    public float time;
    public int distance;
    public Text distanceText;
    private int distanceMultiplier;
    float timeUntilStart;
    int displayScore;

    void Start()
    {
        distanceMultiplier = 0;
        scoreCount = 0;
        currentAmount = 0;
        ObjectMovement.speed = 10;
        ObjectSpawner.maxTime = 2f;
        time = 0;
        distance = 0;
        gameHasStarted = false;
        timeUntilStart = 0;
    }

    void Update()
    {
        if (gameHasStarted != true)
        {
            timeUntilStart = Time.timeSinceLevelLoad;
        }

        if (distance > 5000)
        {
            ObjectMovement.speed = 15f;
            ObjectSpawner.maxTime = 0.75f;
            distanceMultiplier = 3;
        }
        else if (distance < 5000)
        {
            ObjectMovement.speed += 0.02f * Time.deltaTime;
            ObjectSpawner.maxTime -= 0.005f * Time.deltaTime;
        }
        if (distance > 2000 && distance < 5000)
            distanceMultiplier = 2;

        if (ObjectSpawner.playerAlive == true)
        {
            time = Time.timeSinceLevelLoad - timeUntilStart;
            time *= 20;
            distance = Mathf.RoundToInt(time);
            distanceText.text = distance.ToString();
            scoreText.text = displayScore.ToString();
        }
    }

    public void ScoreUpdate()
    {
        if (currentAmount >= amountNeeded)
            scoreCount = scoreCount + 1 * scoreMultiplier + 1 * distanceMultiplier;

        else if (currentAmount < amountNeeded)
        {
            if (distanceMultiplier == 0)
            {
                scoreCount += 1;
                currentAmount += 1;
            }
            else
            {
                scoreCount += 1 * distanceMultiplier;
                currentAmount += 1;
            }
        }
        StartCoroutine(IncrementScore());
    }

    public void StartGame()
    {
        gameHasStarted = true;
        Destroy(GameObject.FindGameObjectWithTag("StartButton"));
    }

    public IEnumerator IncrementScore()
    {
        while (true)
        {
            if (displayScore < scoreCount)
            {
                displayScore++;
            }
            yield return new WaitForSeconds(1f);
        }
    }
}
