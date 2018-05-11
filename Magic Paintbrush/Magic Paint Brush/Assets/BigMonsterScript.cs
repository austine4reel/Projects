using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BigMonsterScript : MonoBehaviour {
	public Rigidbody2D monsterRigidbody;

	void Update(){
	}



	void OnTriggerEnter2D(Collider2D other){
	if (other.tag == "breakBigmonster") {
			
			//transform.localScale += new Vector3(1, 1, 0);
			//transform.position = new Vector3 (0,1,0);
			//transform.position = new Vector2 (transform.position.x*1,transform.position.y);

	}
}
}
