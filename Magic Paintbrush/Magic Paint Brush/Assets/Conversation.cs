using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System.Threading;

public class Conversation : MonoBehaviour {
	//Instance Variables this Script

	public  Text LiangText;
	public  Text MermaidText;
	public GameObject paintBrush;
	public GameObject LiangBox;
	public GameObject MermaidBox;
	public GameObject Mermaid;
//	ViewController viewController;
	public float speed;
	public Rigidbody2D PlayerRigidBody;
	private bool loop;

	// List of messages used for looping dailog boxes. Even: Mermaid  // Odd: Liang
	ArrayList al = new ArrayList(new string[] { "Hello! I’m Mimi, the mermaid...", "Hi! I’m Liang!", "Thank You for cleaning the beach Liang!", "It’s beautiful now. Isn't it? :D ", "I have a present for you.","Thank you!","Draw as much as you like.","I don't have any paper to paint on.","Draw any where, but don't harm anyone using it","I will not.","Have a great day,Liang. Bye","Same to you,Bye."});


	// Starts When on first load
	void Start(){
		loop = false;

	// Initializing ViewController Script 
		//viewController=GetComponent<ViewController>();
	
		//calling for background working methods Like popping dailog boxed and moving objects.



	}

	// Update Every Frame.
	void Update() {
		if (loop) {
			//Destroying Mermaid Object after finishing the dailogbox
			Mermaid.SetActive(false);
			LiangText.text = "Jump and Get the Brush. Press 'Space Bar'";
			loop = false;
			//Calling HandleView() Method passing Drawing as String Parameter

		}

	}

	public void ShowConversation(){
		StartCoroutine (coolDown());
	}
	public void ShowMoreInfo(){
		StartCoroutine (moreInfo());
	}

	//  Running Methods and Running ArrayList in Background Thread.
	IEnumerator coolDown()
	{
		//Looping through each ArrayList Object ( List of Messages for dailog box.)
		foreach (var item in al) {


			// Condition: True if reminder is Zero,
			if (al.IndexOf (item) % 2 == 0) {
				// Show Mermaid Dailog box and  Hide Liang Dailog Box 

				MermaidBox.SetActive (true);
				LiangBox.SetActive (false);

				// Add String Value to Mermaid Dailog Box Text
				MermaidText.text = item.ToString ();

			} else {

				// Condition: True if index of current lopped item is 5
				//True when Mermaid Says "I have a present for you."
				if (al.IndexOf (item) == 5) {

					// Show Paint Brush 
					paintBrush.SetActive (true);

					//Dont process thread for 1 secs 
					yield return new WaitForSeconds (3);


				}

				MermaidBox.SetActive (false);
				LiangBox.SetActive (true);

				LiangText.text = item.ToString ();


			}



			yield return new WaitForSeconds (3);





		}
		loop = true;	

	}


	void DestroyObject(GameObject Object){
		Destroy (Object);
	}


	IEnumerator moreInfo()
	{
		

		LiangText.text = "Great";
		yield return new WaitForSeconds (2);
		LiangText.text = "Slide to Paint. \n \n Press:  ⇠ or ⇢ + LeftCntrl ";





	}


}
