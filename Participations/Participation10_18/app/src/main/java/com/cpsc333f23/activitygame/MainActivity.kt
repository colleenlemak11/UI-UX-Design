package com.cpsc333f23.activitygame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cpsc333f23.memory.Memory
import android.util.Log

private const val TAG = "Lifecycle"
private const val KEY_NUM_MATCHES = "totalNumberOfMatches"

class MainActivity : AppCompatActivity() {
    var totalNumberOfMatches = 0
    private var clickCount = 0
    private var matchesLeft: Int = 0
    private lateinit var playAgainButton: Button
    private lateinit var matchButton: Button
    private lateinit var game: Memory

    /**
     * The `onCreate` function in Kotlin sets up the game board, adds event listeners to buttons and
     * card TextViews, and initializes the matchesLeft variable.
     *
     * @param savedInstanceState The `savedInstanceState` parameter is a Bundle object that contains
     * the data that was saved in the `onSaveInstanceState()` method. This bundle can be used to
     * restore the activity's state when it is recreated, such as after a configuration change or when
     * the activity is destroyed and recreated by the system.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //game board object
        game = Memory(6, this)

        //add match button event listener
        matchButton = findViewById(R.id.match_button)
        matchButton.setOnClickListener{
            selectCard(it, game)
        }

        //add play again event listeners
        playAgainButton = findViewById(R.id.play_again_button)
        playAgainButton.setOnClickListener{
            resetGame()
        }

        //get a list of the card TextViews
        val cardList: List<TextView> = getCardViews()
        //add a click event listener for each of the card TextViews
        for (card in cardList){
            card.setOnClickListener {
                selectCard(it, game)
            }
        }

        if (savedInstanceState != null){
            //Note: properties in comments below to help remember which Memory properties we have to keep track of and types, etc.

            //totalNumberOfMatches : Int
            totalNumberOfMatches = savedInstanceState.getInt(KEY_NUM_MATCHES)


            //matches: MutableMap<Int, Boolean> = mutableMapOf()
            //get matches string from savedInstanceState

            //split string and put back into MutableMap (see: .split(), .associate{}

            //assign parsed string map to game matches

            //var currentPair: Pair<Int, Int> = Pair(-1, -1)
            //get pairString from savedInstanceState

            //parse string to left and right values

            //update currentPair value of game board
            //Hint: see setPairValue() syntax/use

            //var board: MutableList<String>
            //get gameBoardString from savedInstanceState

            //split string and put back into MutableList (see: .split())

            //update board value of game board

            //Get/update matchesLeft from savedInstanceState

            //update matchesLeft View text

            //Get/update clickCount from savedInstanceState

            //get currentBoardStateString from savedInstanceState and parse (hint: ArrayList<String>)
            //(what the board looked like when state changed, e.g., 1 card flipped over)

            //Get match and play again button view state (aka visibility) from savedInstanceState

            //reset UI using currentBoardState and match/play button view states
            //could be a function similar to resetGame(), e.g., restoreGameState() or in this block

            updateDebugTextView() //TODO: remove when done debugging
        }
        else {
            matchesLeft = (findViewById<TextView>(R.id.matches_left_text_view).text.toString().toInt())
        }

        //endregion code body
    }

    /**
     * The function onSaveInstanceState in Kotlin is used to save the state of a game, including the
     * total number of matches, the matches, the current pair, the board, the matches left, the
     * visibility of the match and play again buttons, the click count, and the current board state.
     *
     * @param outState The `outState` parameter is a Bundle object that is used to store the state of
     * the activity. It is passed as an argument to the `onSaveInstanceState` method. The state
     * information is stored in key-value pairs, where the keys are strings and the values can be of
     * various types such
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Note: properties in comments below to help remember which Memory properties we have to keep track of and types, etc.
        //need to save all parts of the game so we can restore them when recreating the activity
        //totalNumberOfMatches : Int
        outState.putInt(KEY_NUM_MATCHES, totalNumberOfMatches)

        //matches: MutableMap<Int, Boolean> = mutableMapOf()

        //Hint: .toString() but we'll have to clean it up a little... use the debugger and view
        // the output of .toString() to determine how you'd like to package up the string and
        // store it in the Bundle

        //var currentPair: Pair<Int, Int> = Pair(-1, -1)
        //Hint: same as above when using .toString()

        //var board: MutableList<String>
        //Hint: same as above when using .toString()

        //Save matchesleft

        //Need to save the match and play again button visibility state

        //Save clickcount

        //Get and save current board view state, i.e., what exactly does the board look like right now?
        // e.g., 1 card flipped? 3? 5?
        //Hint: need to package it up in some manner so we can un-stringify it when restoring state
    }

    /**
     * The function `selectCard` is responsible for handling the logic when a card is clicked in a
     * memory game, including checking for matches and updating the UI accordingly.
     *
     * @param view The "view" parameter is the view that was clicked by the user. In this case, it is a
     * TextView representing a card in the memory game.
     * @param game The "game" parameter is an instance of the "Memory" class, which represents the
     * current state of the memory game being played. It contains information such as the board layout,
     * the current pair of cards being clicked, the matches found so far, and the number of matches
     * left to find.
     * @return The method does not have a return type, so it does not return anything.
     */
    private fun selectCard(view: View, game: Memory) {
        //if it's the 2nd click, check match and reset cards
        if (clickCount == 2){
            findViewById<Button>(R.id.match_button).visibility = View.INVISIBLE
            clickCount = 0 //reset click counter
            game.currentPair = Pair(-1, -1) //reset pair

            //get list of card TextViews
            val cardViews = getCardViews()

            //reset each view
            var i = 0
            for (card in cardViews){
                if (game.matches[i] == true){
                    i++
                    continue
                }
                else {
                    card.text = resources.getString(R.string.default_card_icon)
                    i++
                }
            }
            updateDebugTextView() //TODO: remove when done debugging
            return //board reset or matches updated, all done in this method
        }
        else { //have not clicked 2 cards yet, reveal the card

            val clickedCardTextView = view as TextView

            if (clickedCardTextView.text.toString() == getString(R.string.default_card_icon)){
                //for debugging purposes, prints out clicked id string rather than integer
                //println(resources.getResourceEntryName(clickedCardTextView.id))

                //hard-coded solution for now
                //TODO: in the future - create scheme for programmatically generated layout and pick method
                when (resources.getResourceEntryName(clickedCardTextView.id)){
                    "r1c1" -> { clickedCardTextView.text = game.board[0]
                        setPairValue(game, clickCount, 0)}
                    "r1c2" -> { clickedCardTextView.text = game.board[1]
                        setPairValue(game, clickCount, 1)}
                    "r2c1" -> { clickedCardTextView.text = game.board[2]
                        setPairValue(game, clickCount, 2)}
                    "r2c2" -> { clickedCardTextView.text = game.board[3]
                        setPairValue(game, clickCount, 3)}
                    "r3c1" -> { clickedCardTextView.text = game.board[4]
                        setPairValue(game, clickCount, 4)}
                    "r3c2" -> { clickedCardTextView.text = game.board[5]
                        setPairValue(game, clickCount, 5)}
                }
                clickCount++

                val matchButton = findViewById<Button>(R.id.match_button)

                if (clickCount == 2) {
                    //check matches
                    if (game.board[game.currentPair.first] == game.board[game.currentPair.second]){
                        //match!
                        game.matches[game.currentPair.first] = true
                        game.matches[game.currentPair.second] = true
                        //Toast
                        //update count
                        matchesLeft--
                        val matchesLeftTextView = findViewById<TextView>(R.id.matches_left_text_view)
                        matchesLeftTextView.text = matchesLeft.toString()

                        matchButton.text = resources.getString(R.string.match)
                        matchButton.visibility = View.VISIBLE
                    }
                    else {
                        matchButton.text = resources.getString(R.string.no_match)
                        matchButton.visibility = View.VISIBLE
                    }

                    if (matchesLeft == 0){
                        Toast.makeText(this, "All matches found, congratulations!", Toast.LENGTH_LONG).show()
                        findViewById<Button>(R.id.match_button).visibility = View.INVISIBLE
                        findViewById<Button>(R.id.play_again_button).visibility = View.VISIBLE
                        clickCount = 0
                    }
                }
            }
        }
        updateDebugTextView() //TODO: remove when done debugging
    }

    /**
     * The function `setPairValue()` sets the value of a pair based on the click count and index clicked.
     *
     * @param game The `game` parameter is an instance of the `Memory` class. It represents the current
     * state of the memory game.
     * @param clickCount The `clickCount` parameter represents the number of times a user has clicked
     * on a card in a memory game.
     * @param indexClicked The `indexClicked` parameter represents the index of the card that was
     * clicked in the game.
     */
    private fun setPairValue(game: Memory, clickCount: Int, indexClicked: Int){
        when (clickCount){
            0 -> {
                game.currentPair = game.currentPair.copy(indexClicked, game.currentPair.second)
            }
            1 -> {
                game.currentPair = game.currentPair.copy(game.currentPair.first, indexClicked)
            }
        }
    }

    /**
     * The function `getCardViews()` returns an ArrayList of TextViews that are children of a
     * GridLayout.
     *
     * @return The function `getCardViews()` returns an `ArrayList` of `TextView` objects.
     */
    private fun getCardViews() : ArrayList<TextView> {
        val cardGridLayout = findViewById<GridLayout>(R.id.cards_grid_layout)
        val cardTextViewList = ArrayList<TextView>()

        for (i in 0 until cardGridLayout.childCount) {
            if (cardGridLayout.getChildAt(i) is TextView) cardTextViewList.add(
                cardGridLayout.getChildAt(
                    i
                ) as TextView
            )
        }
        return cardTextViewList
    }

    /**
     * The function `resetGame()` resets the game by reinitializing the game object, resetting each
     * card view, hiding the play again button, and resetting the matches counter.
     */
    private fun resetGame(){
        //reinitialize game
        game = Memory(6, this)

        //reset each view
        val cardViews = getCardViews()
        for (card in cardViews){
            card.text = resources.getString(R.string.default_card_icon)
        }
        //hide button
        findViewById<Button>(R.id.play_again_button).visibility = View.INVISIBLE
        //reset matches counter
        matchesLeft = game.totalNumberOfMatches
        findViewById<TextView>(R.id.matches_left_text_view).text = matchesLeft.toString()
    }

    /**
     * The function updates a TextView with debug information about clickCount and matchesLeft.
     */
    private fun updateDebugTextView(){
        val debugTextView = findViewById<TextView>(R.id.debug_text_view)
        val debugText = "clickcount: $clickCount, matchesLeft: $matchesLeft"
        debugTextView.text = debugText
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }
}