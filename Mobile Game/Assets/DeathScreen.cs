using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class DeathScreen : MonoBehaviour
{
    public GameObject player;
    public GameObject deathScreenPanel;

    void Start()
    {
        
    }

    void Update()
    {
        if (player == null)
            DeathScreenPanel();
    }

    public void DeathScreenPanel()
    {
        ObjectSpawner.playerAlive = false;
        deathScreenPanel.SetActive(true);
    }

    public void RestartButton()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        ObjectSpawner.playerAlive = true;
    }
}
