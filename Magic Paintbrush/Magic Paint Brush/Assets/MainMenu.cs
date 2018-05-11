using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour {

    public static bool GameIsPaused = false;
    public GameObject pausedMenu;



	
	void Start(){
		
		
	}
	void Update(){
		
		if (Input.GetKeyDown (KeyCode.Escape))
        {
            if (GameIsPaused)
            {
                ResumeGame();
            }
            else
            {
                PauseGame();
            }
        }
    }

	public void PlayGame(){
		//Conversationscript.handleConversation ();
		StartCoroutine (coolDown());

		SceneManager.LoadScene (SceneManager.GetActiveScene ().buildIndex + 1);
		
		
	}

	public void QuitGame(){
		Debug.Log ("Quit");
		Application.Quit ();

	}


    public void ResumeGame()
    {
        pausedMenu.SetActive(false);
        Time.timeScale = 1f;
        GameIsPaused = false;

    }

    void PauseGame()
    {
        pausedMenu.SetActive(true);
        Time.timeScale = 0f;
        GameIsPaused = true;
    }

    public void Restart()
    {
        Time.timeScale = 1f;
        SceneManager.LoadScene("StartUp");
    }

    IEnumerator coolDown()
	{
		
		yield return new WaitForSeconds (2);




	}



}
