using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[RequireComponent(typeof(Rigidbody2D))]

public class shootBubble : MonoBehaviour {
	[SerializeField]
	private float speed;
	private Rigidbody2D myRidigbody;
	private Vector2 direction;
	private bool shoot;
	public GameObject bubble;
	[SerializeField]
	private GameObject bubbleprefab;

	public GameObject bubbleanimationObject;



	// Use this for initialization
	void Start () {
		shoot = true;
		myRidigbody = GetComponent<Rigidbody2D>();


	}
	
	// Update is called once per frame
	void FixedUpdate () {
		if (shoot) {
			StartCoroutine (coolDown ());
		} else {
			Destroy (bubble);

		}


	}
	public void Initialize(Vector2 direction){
		this.direction = direction;
	
	}

	void OnTriggerEnter2D(Collider2D other){

		if (other.tag == "makefree") {

			AudioScript.PlaySound ("coin");
			//Destroy (other.gameObject);
			other.gameObject.GetComponent<Rigidbody2D>().gravityScale=-1;
			other.gameObject.transform.GetChild(0).gameObject.SetActive(true);
			other.gameObject.transform.GetChild(1).gameObject.SetActive(true);

			//GameObject tmp=(GameObject)Instantiate (bubbleprefab, transform.position, Quaternion.identity);
			//tmp.GetComponent<shootBubble> ().Initialize (Vector2.down);



		}




	}
	void OnBecaeInvisible(){
		Destroy (gameObject);
	}

	IEnumerator coolDown()
	{
		myRidigbody.velocity = direction * speed;
		yield return new WaitForSeconds (1);
	
		shoot = false;

	}

	IEnumerator waitforEnemyDestroy(GameObject other)
	{
		
		yield return new WaitForSeconds (2);
		Destroy(other.gameObject);

	}
}
