using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AudioScript : MonoBehaviour {
	public static AudioClip jump, collectcoin,bubbling;
	static AudioSource audioSrc;



	// Use this for initialization
	void Start () {
		jump=Resources.Load<AudioClip>("jump");
		collectcoin=Resources.Load<AudioClip>("coin");
		bubbling=Resources.Load<AudioClip>("bubbling");
		audioSrc = GetComponent<AudioSource> ();
		
		
	}
	
	// Update is called once per frame
	public static void PlaySound (string clip) {
		switch (clip) {
		case "jump":
			audioSrc.PlayOneShot (jump);
			break;
		case "coin":
			audioSrc.PlayOneShot (collectcoin);
			break;
		case "bubbling":
			audioSrc.PlayOneShot (bubbling);
			break;

		}
		
	}



}
