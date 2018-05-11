using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
public class ViewController : MonoBehaviour {
	

	public GameObject GarbageObjects;
	public GameObject trashbin;
	public GameObject Mermaid;
	public GameObject SmallBird;
	public GameObject SmallMonster;
	public GameObject smallBigMonster;
	public GameObject BigMonsterView;
	public Rigidbody2D MonsterRigidBody;
	public Rigidbody2D PlayerRigidBody;
	int i;
	private bool flyplayer;
	void Start(){
		flyplayer = false;
		HandleView ("Garbages");
		i = 0;
	
	}

	void Update(){
		if (flyplayer) {
			PlayerRigidBody.gravityScale = -0.55f;
		}

	}

	public void HandleView(string View){
		
		switch (View) {

		case "Garbages":
			GarbageObjects.SetActive (true);

			break;
		case "trashbin":
			trashbin.SetActive (true);
			break;
		case "Mermaid":
			Mermaid.SetActive (true);
		
			break;
		case "Drawing":
			i = i + 1;
			if (i == 1) {
				SmallBird.SetActive (true);
			}
			if (i == 2) {
				SmallMonster.SetActive (true);
			}
			if (i == 3) {
				smallBigMonster.SetActive (true);
				StartCoroutine(coolDown());




				//StartCoroutine(coolDown());
				//MonsterRigidBody.gravityScale = 1;


			}
			break;
		}
	}

	IEnumerator coolDown()
	{
		yield return new WaitForSeconds (4);
		flyplayer = true;
		yield return new WaitForSeconds (2);

		SceneManager.LoadScene (SceneManager.GetActiveScene ().buildIndex + 1);
	



	}

}
