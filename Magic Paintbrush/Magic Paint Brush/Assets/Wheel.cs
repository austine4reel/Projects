using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Wheel : MonoBehaviour
{
    [SerializeField]
    public float maxspeed = 500f;
    [SerializeField]
    public float minspeed = 100f;
    public static float speed;
    private float position = 0.0f;
    private float minradius = 4f;
    private CircleCollider2D other;


    // Use this for initialization
    void Start()
    {
        other = transform.GetComponent<CircleCollider2D>();
        speed = Random.Range(minspeed, maxspeed);

    }

    // Update is called once per frame
    void FixedUpdate()
    {
        if (speed != 0)
        {
            speed *= 0.99f;
            if (speed < 0.01f)
            { speed = 0f; }
            if (other.radius > minradius)
                other.radius *= 0.99f;
            else
                other.radius = minradius;
            position -= speed;
            position = position % 360;
            transform.rotation = Quaternion.Lerp(transform.rotation, Quaternion.Euler(0, 0, position), 1);
        }
    }
}
