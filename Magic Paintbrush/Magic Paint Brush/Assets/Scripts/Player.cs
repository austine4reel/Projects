using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class Player : MonoBehaviour {
	private Rigidbody2D myRigidbody;

	private Animator myAnimator;

	[SerializeField]
	private float movementSpeed;
	private bool facingRight;
	private bool slide;

	[SerializeField]
	private Transform[] groundPoints;

	[SerializeField]
	private float groundRadius;

	[SerializeField]
	private LayerMask whatIsGround;
	private bool isGrounded;
	private bool jump;
	private bool land;
	[SerializeField]
	private float jumpForce;
	[SerializeField]
	private bool airControl;
	GameMaster gm;

	[SerializeField]
	private GameObject bubbleprefab;

	public int lives;

	//AudioScript audioscriptjump;
	//AudioScript audioscriptcollectcoin;
	private 






	// Use this for initialization
	void Start () {
		facingRight = true;

		myRigidbody = GetComponent<Rigidbody2D>();
		myAnimator = GetComponent<Animator> ();
		gm = GameObject.Find("GameManager").GetComponent<GameMaster> ();
		//gm = GameObject.Find ("endLevel").GetComponent<GameMaster> ();
		lives = 5;
		//audioscriptjump =GameObject.Find("AudioObject").GetComponent<AudioScript> ();
		//audioscriptcollectcoin =GameObject.Find("AudioObject").GetComponent<AudioScript>();


		
	}
	void Update(){
		
	}
	
	// Update is called once per frame
	void FixedUpdate () {
		float horizontal = Input.GetAxis ("Horizontal");
		isGrounded = IsGrounded ();
		HandleInput ();
		HandleMovement(horizontal);
		Flip (horizontal);


		HandleLayers ();

		ResetValues ();

		
	}
	private void HandleMovement(float horizontal){
		if (!myAnimator.GetBool ("slide") && (isGrounded || airControl)) {
			myRigidbody.velocity = new Vector2 (horizontal*movementSpeed, myRigidbody.velocity.y);  //x=-1,y=0
			myAnimator.SetFloat("speed",Mathf.Abs(horizontal));
		}


		if (isGrounded && jump) {
			isGrounded = false;
			myRigidbody.AddForce (new Vector2(0,jumpForce));
			myAnimator.SetTrigger ("jump");

			AudioScript.PlaySound ("jump");
			myAnimator.SetBool ("land",true);
		}
		if (slide && !this.myAnimator.GetCurrentAnimatorStateInfo (0).IsName ("Slide")) {
			myAnimator.SetBool ("slide", true);
		}else if(!this.myAnimator.GetCurrentAnimatorStateInfo(0).IsName ("Slide")){
			myAnimator.SetBool ("slide", false);
		}
	}
	private void HandleInput(){
		if (Input.GetKeyDown (KeyCode.LeftControl)) {
			slide = true;
		}
		if(Input.GetKeyDown(KeyCode.Space)){
			jump = true;

		}
		if (Input.GetKeyDown (KeyCode.LeftShift)) {
			myAnimator.SetTrigger ("throw");
			Throwbubble (0);
		}
	}

	private void Flip(float horizontal){
		
		if (horizontal > 0 && !facingRight || horizontal < 0 && facingRight) {
			facingRight = !facingRight;
			Vector3 theScale = transform.localScale;
			theScale.x *= -1;
			transform.localScale = theScale;
		}
	}
	private void ResetValues(){
		slide = false;
		jump = false;
	}

	private bool IsGrounded(){
		if (myRigidbody.velocity.y <= 0) {
			foreach(Transform point in groundPoints){
				Collider2D[] colliders = Physics2D.OverlapCircleAll (point.position, groundRadius, whatIsGround);
				for (int i = 0; i < colliders.Length; i++) {
					if (colliders [i].gameObject != gameObject) {
						myAnimator.ResetTrigger ("jump");
						myAnimator.ResetTrigger ("throw");
						myAnimator.SetBool ("land",false);
						return true;
					}
				}
			}
		}
		return false;
	}
	private void HandleLayers(){
		if (!isGrounded) {
			myAnimator.SetLayerWeight (1, 1);
		}else{
			myAnimator.SetLayerWeight (1, 0);

			}
		
		}
	public void Throwbubble(int value){
		if (!isGrounded && value == 1 || isGrounded && value == 0) {
			if (facingRight) {
				GameObject tmp=(GameObject)Instantiate (bubbleprefab, transform.position, Quaternion.identity);
				tmp.GetComponent<shootBubble> ().Initialize (Vector2.right);
			} else {
				GameObject tmp=(GameObject)Instantiate (bubbleprefab, transform.position, Quaternion.identity);
				tmp.GetComponent<shootBubble> ().Initialize (Vector2.left);
			}
		}

	}
	void OnTriggerEnter2D(Collider2D other){
		if (other.tag == "coin") {
			gm.coinCollected ();
			AudioScript.PlaySound ("coin");
			Destroy (other.gameObject);
		}
		if (other.tag == "life") {
			
			AudioScript.PlaySound ("jump");
			Destroy (other.gameObject);
		}
		if (other.tag == "endLevel") {
			
			StartCoroutine (coolDown());

		}
		if (other.tag == "die") {
			
			myAnimator.SetBool ("die", true);
			lives--;

			if (lives > 0) {
				
				StartCoroutine (LateCall());

				} else {
				
			}
			AudioScript.PlaySound ("jump");
			//Destroy (other.gameObject);
		}

	}


	IEnumerator LateCall()
	{
		yield return new WaitForSeconds (2);
		myAnimator.SetBool ("die", false);

		myRigidbody.MovePosition (new Vector2(10,6));


	}
	IEnumerator coolDown()
	{
		yield return new WaitForSeconds (2);
		SceneManager.LoadScene (SceneManager.GetActiveScene ().buildIndex + 1);



	}





}
