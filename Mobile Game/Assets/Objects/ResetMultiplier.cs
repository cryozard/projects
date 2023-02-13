using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ResetMultiplier : MonoBehaviour
{
    public ScoreHandler scoreUpdate;

    private void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.tag == "CollectRing")
        {
            Destroy(other.gameObject);
            scoreUpdate.currentAmount = 0;
        }
    }
}
