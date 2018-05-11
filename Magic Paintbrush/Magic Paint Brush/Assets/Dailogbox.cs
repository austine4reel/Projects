using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Dailogbox : MonoBehaviour {
	public static GameObject LiangBox;

	// Use this for initialization
	void Start () {
		
		
	}

	void Update(){
		
	}
	
	// Update is called once per frame
	public static void flipDaiogBox () {
		
		LiangBox.gameObject.transform.localScale += new Vector3(-1,0,0 );

	}
}
