using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AttackingLandMonster : MonoBehaviour {
	public Transform target;//set target from inspector instead of looking in Update
	public float speed = 3f;
	public GameObject colliderLeft;
	public GameObject colliderRight;
	private bool colliderLeftcheck;
	private bool colliderRightcheck;
	private Rigidbody2D EnemyRigidbody;



	void Start () {
		colliderLeftcheck = true;
		EnemyRigidbody = GetComponent<Rigidbody2D> ();


	}

	void Update(){

				
			//move towards the player
			if(colliderRightcheck){
				if (Vector3.Distance(transform.position,target.position)>1f){//move if distance from target is greater than 1
					transform.Translate(new Vector3(-speed* Time.deltaTime,0,0) );


				}
			}
			if(colliderLeftcheck){
				if (Vector3.Distance(transform.position,target.position)>1f){//move if distance from target is greater than 1
					transform.Translate(new Vector3(speed* Time.deltaTime,0,0) );

				}
			}
			


		//move towards the player



	}
	void OnTriggerEnter2D(Collider2D other){

		if (other.tag == "colliderleft") {
			//StartCoroutine (coolDown ());
			colliderLeftcheck = true;
			colliderRightcheck = false;

		}
		if (other.tag == "colliderright") {
			//StartCoroutine (coolDown ());
			colliderRightcheck = true;
			colliderLeftcheck = false;
		}
	


	
	}


	IEnumerator coolDown()
	{
		yield return new WaitForSeconds (2);
	
	}
}
