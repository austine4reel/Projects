using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemiesScript : MonoBehaviour {
	public static bool flyTrigger;
	private Rigidbody2D EnemiesRigidBody;
	public GameObject showBubbleforFlying;
	private float horizontal;
	private Animator EnemyAnimator;
	public Transform target;//set target from inspector instead of looking in Update
	public float speed = 3f;

	void Start(){
		flyTrigger = false;
		EnemiesRigidBody=GetComponent<Rigidbody2D>();
		EnemyAnimator=GetComponent<Animator> ();


	}
	void Update(){
		horizontal = Input.GetAxis ("Horizontal");
		if(flyTrigger){
			showBubbleforFlying.SetActive (true);
			EnemiesRigidBody.gravityScale=-1;
			dropCoin ();
			//flyTrigger = false;

		}
		if (Vector3.Distance(transform.position,target.position)>1f){//move if distance from target is greater than 1
			transform.Translate(new Vector3(-speed* Time.deltaTime,0,0) );
		}
		
	
	}

	void MoveEnimies(){
		EnemiesRigidBody.velocity = new Vector2 (horizontal*20,EnemyAnimator.velocity.y);  //x=-1,y=0
		EnemyAnimator.SetFloat("speed",Mathf.Abs(horizontal));
		horizontal = horizontal * 20;
	}

	public static void flyEniemies(Rigidbody2D RigidBody){
		//flyTrigger = true;
		//EnemiesRigidBody = RigidBody;


	}
	IEnumerator coolDown()
	{
		yield return new WaitForSeconds (1);
	

	}

	public static void dropCoin (){
		//CoinObject.SetActive (true);
	}



}
