using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Rewards : MonoBehaviour
{

    private int rewardpointer = 1;
    private static int reward = 1;

    void Update()
    {
        if (Wheel.speed > 0f)
        {
            reward = rewardpointer;
        }
    }

    private void OnTriggerEnter2D(Collider2D pointer)
    {
        if (pointer.tag == "liferw")
        {
            rewardpointer = 1;
        }
        if (pointer.tag == "doublejumprw")
        {
            rewardpointer = 2;
        }
        if (pointer.tag == "coinrw")
        {
            rewardpointer = 3;
        }
        if (pointer.tag == "shieldrw")
        {
            rewardpointer = 4;
        }
    }
}
