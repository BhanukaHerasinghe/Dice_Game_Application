package com.example.androidcwdicegame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class newgame : AppCompatActivity() {

    //This line declares a TextView variable named computerscoretextview, which is used to display the computer's score.
    private lateinit var computerscoretextview: TextView

    //This line declares a TextView variable named userscoretextview, which is used to display the user's score.
    private lateinit var userscoretextview: TextView

    //This line declares a TextView variable named gettingtargetmarks, which is used to display the target marks
    private lateinit var gettingtargetmarks:TextView

    //This line declares an ArrayList variable named computer, which will be used to store a list of ImageView objects representing the computer's choices.
    private var computer = ArrayList<ImageView>()

    //This line declares an ArrayList variable named user, which will be used to store a list of ImageView objects representing the user's choices
    private var user = ArrayList<ImageView>()

    private var computerscore = MutableList(5){0}

    private var userscore = MutableList(5) {0}

    private val userselection =MutableList(5) {false}

    private var chances:Int=0

    private var targetmarks: Int=101

    private var numofwins: Int=0

    private var numofloss: Int=0

    private var updatefinalscore = MutableList(2) {0}


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newgame)

        // Find the "throw" and "score" buttons in the layout
        val throwbutton = findViewById<Button>(R.id.throwbutton)
        val scorebutton = findViewById<Button>(R.id.scorebutton)

        val getwins = findViewById<TextView>(R.id.num_wins)

        // Find the "get target score"  in the layout
        gettingtargetmarks = findViewById(R.id.targetbut)



        // Find the computer and user score TextViews in the layout
        computerscoretextview =findViewById(R.id.computertext)
        userscoretextview = findViewById(R.id.usertext)

        // Find the computer and user dice ImageViews in the layout, and add them to their respective lists
        computer.add(findViewById<ImageView>(R.id.imageButton_computer_1))
        computer.add(findViewById<ImageView>(R.id.imageButton_computer_2))
        computer.add(findViewById<ImageView>(R.id.imageButton_computer_3))
        computer.add(findViewById<ImageView>(R.id.imageButton_computer_4))
        computer.add(findViewById<ImageView>(R.id.imageButton_computer_5))


        user.add(findViewById<ImageView>(R.id.imageButton_user_1))
        user.add(findViewById<ImageView>(R.id.imageButton_user_2))
        user.add(findViewById<ImageView>(R.id.imageButton_user_3))
        user.add(findViewById<ImageView>(R.id.imageButton_user_4))
        user.add(findViewById<ImageView>(R.id.imageButton_user_5))

        // Set up an event listener for the "target score"
         gettingtargetmarks.setOnClickListener {

             if (chances==0){
                 creatingthetargetscore(gettingtargetmarks)
             }
         }


        //This line uses a for loop to iterate over each element in the user list of dice images, along with its index value.
        for ((index, diceimage) in user.withIndex()) {
            diceimage.setOnClickListener {
                val setBorder: Drawable =
                    ResourcesCompat.getDrawable(resources, R.drawable.diceborder, null)!!

                //This line checks if the current diceimage has a null background.
                if (diceimage.background == null) {
                    diceimage.background = setBorder  //This line sets the background of the current diceimage to the setBorder drawable.

                    userselection[index] = true  //This line sets the index value of the userselection list to true, indicating that the user has selected this dice.
                } else {
                    diceimage.background = null  //This line removes the background of the current diceimage.
                    userselection[index] = false  //This line sets the index value of the userselection list to false, indicating that the user has deselected this dice.
                }
            }
        }


        throwbutton.setOnClickListener {
            chances++
            //Increase the user's total amount of opportunities to throw
            scorebutton.isEnabled = true
            if (chances>2) {
            }
            // Check if the user has selected any dice to roll
            var choosedview =false
            for (i in userselection){
                if (i) choosedview =true
            }
            // If the user has selected dice to roll, roll them back and display new dice images
            if (choosedview)
                rollingback()
            else
                imageforinterface()
            // If the user has completed a round of 3 throws, disable the "score" button, update the score
            if (chances%3==0){
                scorebutton.isEnabled =false
                upnewscorevutton(getwins)


                for ((index,images) in user.withIndex() ) {
                    userselection[index] = false
                    images.background = null

                }
            }

            // if the game ties give them another one chance to win.
            if( updatefinalscore[0] == updatefinalscore[1] && updatefinalscore[0] >= targetmarks && chances % 3 !=0 )
            {
                Toast.makeText(this, "The Game Tied. This will Continue Until the Tie is Broken", Toast.LENGTH_SHORT).show()
                imageforinterface()
            }





        }

        scorebutton.setOnClickListener{
            scorebutton.isEnabled =false  //This line disables the score button so in that time user cannot click it .
            if (chances%3 !=0){
                upnewscorevutton(getwins)
            }

        }

    }


    //Generates a random integer between the specified start and end values
    private fun setuprandomnumber(start: Int, end: Int): Int {

        return (start..end).random()

    }

    //Generates a random integer between 1 and 6 (inclusive) and sets the corresponding image in the specified ImageView.
    private fun setuprandomimage(photoview: ImageView): Int {
        val randomnumber = setuprandomnumber(1, 6)

        val resid =resources.getIdentifier("die_face_$randomnumber","drawable","com.example.androidcwdicegame")
        photoview.setImageResource(resid)

        return randomnumber
    }

    //Generates random images for the computer player by calling setuprandomimage for each ImageView in the specified list
    private fun randomImageforComputer(imagelists: List<ImageView>){
        for ((index,photoview) in imagelists.withIndex() ){

            var createrandomnumber =setuprandomimage(photoview)
            computerscore[index]=createrandomnumber

        }
    }
    //Generates random images for the user player by calling setuprandomimage for each ImageView in the specified list.
    private fun randomImageforUser(imagelists: List<ImageView>) {
        for ((index,photoview) in imagelists.withIndex() ){
            var createrandomnumber = setuprandomimage(photoview)
            userscore[index]=createrandomnumber
        }
    }

    //Sets a random image in the specified ImageView and adds the corresponding number to the userscore array.
    private fun readytoimageview(index :Int,photo :ImageView) {
        var createrandomnumber = setuprandomimage(photo)
        userscore[index]=createrandomnumber
    }

    //Generates random images for the computer and user players by calling the corresponding functions
    private fun imageforinterface (){
        randomImageforComputer(computer)
        randomImageforUser(user)
    }


    private fun rollingback (){
        for ( (index,selected) in userselection.withIndex())
            if (!selected) {
                readytoimageview(index,user[index])
            }

    }
    //Rolls back the selected images for the user player by calling the readytoimageview function for each unselected ImageView in the user list.
    private fun updatetotargetscore(targetscore: TextView){
        targetscore.text = "Target - $targetmarks"
    }

    @SuppressLint("MissingInflatedId")
    private fun creatingthetargetscore(targetscoretext: TextView) {

        // Step 2: define a PopupWindow object
        lateinit var popupdisplay: PopupWindow

        // Step 3: inflate the layout file
        val popup = layoutInflater.inflate(R.layout.popupfortargetmark, null)

        // Step 4: set up the popup window
        popupdisplay = PopupWindow(popup, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)

        // Step 5: display the popup window
        popupdisplay.showAtLocation(popup, Gravity.CENTER, 0, 0)

        // Step 6: retrieve the input
        val submitButton = popup.findViewById<Button>(R.id.popup_button)

        submitButton.setOnClickListener {
            val usertext = popup.findViewById<EditText>(R.id.popup_input)
            val input = usertext.text.toString().toInt()
            // set the input to the target.
            targetmarks = input
            updatetotargetscore(targetscoretext)
            popupdisplay.dismiss()
        }


    }
    //Updates the final scores and displays them in the corresponding TextViews. Also checks if the game has ended
    private fun upnewscorevutton(numberofwins: TextView){
        val computerfinalmarks = computerscore.sum()   // Sums up the scores for the computer and user players
        val updateuserfinalmarks = userscore.sum()

        updatefinalscore[0]  += computerfinalmarks   // Adds the final scores to the updatefinalscore array
        updatefinalscore[1] += updateuserfinalmarks

        computerscoretextview.text = "computer - " + updatefinalscore[0].toString()     // Displays the final scores in the corresponding TextViews
        userscoretextview.text ="user - " + updatefinalscore[1].toString()

        // Checks if the game has ended by calling the targetachievement function and displays the appropriate message
        if(targetachievement() == 0){
            gamedecision("You lost the Game!!!!",false)
            numofloss++
            leftconerwins(numberofwins)
        }else if (targetachievement() == 1) {
            gamedecision("The Game Is Draw",true)

        }else if (targetachievement() == 2){
            gamedecision("You are The Winner...!!!",true)
            numofwins++
            leftconerwins(numberofwins)
        }else if (targetachievement()==-1){

        }

    }

    @SuppressLint("MissingInflatedId")
    private fun gamedecision (msg:String, decision:Boolean) {

        lateinit var popupdisplay: PopupWindow

        val popup = layoutInflater.inflate(R.layout.winlostpopup, null)

        popupdisplay = PopupWindow(
            popup,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        )

        popupdisplay.showAtLocation(popup, Gravity.CENTER, 0, 0)

        val cancelButton = popup.findViewById<Button>(R.id.cancel_button)

        cancelButton.setOnClickListener {

            val canbutton = Intent(this,MainActivity::class.java)
            startActivity(canbutton)
            finish()
            popupdisplay.dismiss()
        }

        val makedecision = popup.findViewById<TextView>(R.id.win_title)
        makedecision.text = msg

        if (decision) {
            makedecision.setTextColor(ContextCompat.getColor(this, R.color.green))
        } else {
            makedecision.setTextColor(ContextCompat.getColor(this, R.color.red))
        }

    }

    private fun targetachievement() : Int {

        var comscore: Int = updatefinalscore[0]
        var usermarks: Int = updatefinalscore[1]

        if (comscore>usermarks && comscore >= targetmarks){
            return 0

        }else if (comscore == usermarks && comscore >=targetmarks){
            return 1

        }else if ( usermarks>comscore &&usermarks>=targetmarks){
            return 2
        }

        return -1


    }

    private fun leftconerwins(numberofwins:TextView) {

        numberofwins.text="H: $numofwins / c: $numofloss"

    }

}