using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameMaster : MonoBehaviour {
	
	public int coin;
	public Text cointcounter;
	private static int heartcount;
	private static GameObject[] hearts;
	public GameObject Firstheart;
	public GameObject Secondheart;
	public GameObject Thirdheart;
	public GameObject Fourthheart;
	public GameObject Fifthheart;
	private static bool die;



	// Use this for initialization
	void Start () {
		heartcount = 5;
		die = false;
		hearts=new GameObject[] {Firstheart,Secondheart,Thirdheart,Fourthheart,Fifthheart};


	}
	
	// Update is called once per frame
	void Update () {
		cointcounter.text = coin.ToString ();
		if (die) {
			
			heartcount = heartcount - 1;
			GameObject gm= (GameObject)hearts.GetValue (heartcount);
			gm.SetActive (false);
			die = false;
		}
		
	}

	public void coinCollected(){
		coin += 1;

	}

	public static void death(int index){
		die = true;
		GameObject gm= (GameObject)hearts.GetValue (index);
		gm.SetActive (false);
		die = false;

	}
}
