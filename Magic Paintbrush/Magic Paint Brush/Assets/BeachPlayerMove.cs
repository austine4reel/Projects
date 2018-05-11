using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BeachPlayerMove : MonoBehaviour {
	public static Animator playeranimatorinbeach;
	public static Rigidbody2D playerbody;



	// Use this for initialization

	void Start () {
		playeranimatorinbeach=GetComponent<Animator> ();
		playerbody = GetComponent<Rigidbody2D>();



	}
	// Update is called once per frame
	public static void doSomething (string action) {
		switch (action) {
		case "move":
			playeranimatorinbeach.SetTrigger ("move");
			playerbody.velocity = new Vector2 (6*10, playerbody.velocity.y);  //x=-1,y=0
			playeranimatorinbeach.SetFloat("speed",Mathf.Abs(6));
			break;
		case "idle":
			playeranimatorinbeach.SetTrigger ("idle");
			break;
	

		}

	}


}
