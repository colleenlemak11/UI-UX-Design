package com.cpsc333fa23.gc7

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cpsc333fa23.memory.Memory
import com.cpsc333fa23.memory.WinConditionDialogFragment

private const val TAG = "Lifecycle"


class MainActivity : AppCompatActivity(), WinConditionDialogFragment.OnWinConditionSelectedListener {
    private var clickCount = 0
    private var matchesLeft: Int = 0
    private lateinit var playAgainButton: Button
    private lateinit var matchButton: Button
    private lateinit var game: Memory
    private lateinit var defaultIcon: String

    override fun onWinConditionClick(choice: Boolean){
        if (choice)
            resetGame()
        else
            findViewById<Button>(R.id.play_again_button).visibility = View.VISIBLE
    }

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

        defaultIcon = resources.getString(R.string.default_card_icon)

        registerForContextMenu(findViewById(R.id.game_title))

        Log.d(TAG, "onCreate")

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
            //totalNumberOfMatches : Int
            game.totalNumberOfMatches = savedInstanceState.getInt("totalNumberOfMatches")

            //matches: MutableMap<Int, Boolean> = mutableMapOf()
            //get matches string from savedInstanceState
            val gameMatchesString = savedInstanceState.getString("matches")
            //split string and put back into MutableMap (see: .split(), .associate{}
            val gameMatches = gameMatchesString
                ?.split(",")
                ?.associate {
                    val (left, right) = it.split("=")
                    left.toInt() to right.toBoolean()
                }
            //assign parsed string map to game matches
            game.matches = gameMatches as MutableMap<Int, Boolean>

            //var currentPair: Pair<Int, Int> = Pair(-1, -1)
            //get pairString from savedInstanceState
            val pairString = savedInstanceState.getString("pair")
            //parse string to left and right values
            val left = pairString?.split(",")?.first()
            val right = pairString?.split(",")?.last()
            //update currentPair value of game board
            //Hint: see setPairValue() syntax/use
            if (left != null && right != null) {
                game.currentPair = game.currentPair.copy(left.toInt(), right.toInt())
            }

            //var board: MutableList<String>
            //get gameBoardString from savedInstanceState
            val gameBoardString = savedInstanceState.getString("board")
            //split string and put back into MutableList (see: .split())
            val gameBoard = gameBoardString?.split(",") as MutableList<String>
            //update board value of game board
            game.board = gameBoard

            //Get/update matchesLeft from savedInstanceState
            matchesLeft = savedInstanceState.getInt("matchesLeft")
            //update matchesLeft View text
            findViewById<TextView>(R.id.matches_left_text_view).text = matchesLeft.toString()

            //Get/update clickCount from savedInstanceState
            clickCount = savedInstanceState.getInt("clickcount")

            //get currentBoardStateString from savedInstanceState and parse (hint: ArrayList<String>)
            //(what the board looked like when state changed, e.g., 1 card flipped over)
            val currentBoardState = savedInstanceState.getString("currentBoardState")?.split(",") as ArrayList<String>

            //Get match and play again button view state (aka visibility) from savedInstanceState
            val matchButtonViewState = savedInstanceState.getInt("matchAgainState")
            val playButtonViewState = savedInstanceState.getInt("playAgainState")

            //reset UI using currentBoardState and match/play button view states
            //could be a function similar to resetGame(), e.g., restoreGameState() or in this block
            restoreGameState(currentBoardState, playButtonViewState, matchButtonViewState)

            matchButton.text = savedInstanceState.getString("matchButtonText")

            defaultIcon = savedInstanceState.getString("defaultIcon")!!
            //updateDebugTextView()
        }
        else {
            matchesLeft = (findViewById<TextView>(R.id.matches_left_text_view).text.toString().toInt())
        }

        //endregion code body
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.default_card_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val currentDefaultIcon = defaultIcon
        return when (item.itemId){
            R.id.default_card1 -> {
                defaultIcon = resources.getString(R.string.default_card_icon)
                updateBoardDefaultIcon(currentDefaultIcon)
                true
            }
            R.id.default_card2 -> {
                defaultIcon = resources.getString(R.string.default_card_icon_2)
                updateBoardDefaultIcon(currentDefaultIcon)
                true
            }
            R.id.default_card3 -> {
                defaultIcon = resources.getString(R.string.default_card_icon_3)
                updateBoardDefaultIcon(currentDefaultIcon)
                true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private fun updateBoardDefaultIcon(currentDefaultIcon: String) {
        val cards = getCardViews()
        for (card in cards){
            if (card.text == currentDefaultIcon)
                card.text = defaultIcon
        }
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
        //need to save all parts of the game so we can restore them when recreating the activity
        //totalNumberOfMatches : Int
        outState.putInt("totalNumberOfMatches", game.totalNumberOfMatches)
        //matches: MutableMap<Int, Boolean> = mutableMapOf()
        var matchesString = game.matches.toString()
        //Hint: .toString() but we'll have to clean it up a little... use the debugger and view
        // the output of .toString() to determine how you'd like to package up the string and
        // store it in the Bundle
        matchesString = matchesString.substring(1, matchesString.length - 1).replace(" ", "")
        outState.putString("matches", matchesString)
        //var currentPair: Pair<Int, Int> = Pair(-1, -1)
        //Hint: same as above when using .toString()
        var pairString = game.currentPair.toString()
        pairString = pairString.substring(1, pairString.length - 1).replace(" ", "")
        outState.putString("pair", pairString)
        //var board: MutableList<String>
        var boardString = game.board.toString()
        //Hint: same as above when using .toString()
        boardString = boardString.substring(1, boardString.length - 1).replace(" ", "")
        outState.putString("board", boardString)

        //Save matchesleft
        outState.putInt("matchesLeft", matchesLeft)

        //Need to save the match and play again button visibility state
        outState.putInt("matchAgainState", findViewById<Button>(R.id.match_button).visibility)
        outState.putInt("playAgainState", findViewById<Button>(R.id.play_again_button).visibility)

        //Save clickcount
        outState.putInt("clickcount", clickCount)

        //Get and save current board view state, i.e., what exactly does the board look like right now?
        // e.g., 1 card flipped? 3? 5?
        val cardList: List<TextView> = getCardViews()
        var cardListString = ""
        //Hint: need to package it up in some manner so we can un-stringify it when restoring state
        for (card in cardList){
            cardListString += card.text.toString() + ","
        }
        cardListString = cardListString.substring(0,cardListString.length - 1)
        outState.putString("currentBoardState", cardListString)

        outState.putString("matchButtonText", matchButton.text.toString())

        outState.putString("defaultIcon", defaultIcon)
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
                    card.text = defaultIcon
                    i++
                }
            }
            //updateDebugTextView()
            return //board reset or matches updated, all done in this method
        }
        else { //have not clicked 2 cards yet, reveal the card

            val clickedCardTextView = view as TextView

            if (clickedCardTextView.text.toString() == defaultIcon){
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
                        /*Toast.makeText(this, "All matches found, congratulations!", Toast.LENGTH_LONG).show()*/
                        findViewById<Button>(R.id.match_button).visibility = View.INVISIBLE

                        //"Dialog Selected"
                        //findViewById<Button>(R.id.play_again_button).visibility = View.VISIBLE

                        val dialog = WinConditionDialogFragment()
                        dialog.show(supportFragmentManager, "winConditionDialog")


                        clickCount = 0
                    }
                }
            }
        }
        //updateDebugTextView()
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
            card.text = defaultIcon
        }
        //hide button
        findViewById<Button>(R.id.play_again_button).visibility = View.INVISIBLE
        //reset matches counter
        matchesLeft = game.totalNumberOfMatches
        findViewById<TextView>(R.id.matches_left_text_view).text = matchesLeft.toString()
    }

    /**
     * The function `restoreGameState()` restores the game state by reinitializing the game board and resetting the
     * visibility of the match button and play again button.
     *
     * @param gameBoardState An ArrayList of Strings representing the state of the game board. Each
     * element in the ArrayList corresponds to the text value of a card on the game board.
     * @param playButtonViewState The `playButtonViewState` parameter is an integer that represents the
     * visibility state of the play again button. It can have two possible values:
     * @param matchButtonViewState The `matchButtonViewState` parameter is an integer that represents
     * the visibility state of the match button. It can have one of the following values:
     */
    private fun restoreGameState(gameBoardState: ArrayList<String>, playButtonViewState: Int, matchButtonViewState: Int){
        //reinitialize game to last state

        //reset each view to the previous state
        val cardViews = getCardViews()
        for ((i, card) in cardViews.withIndex()){
            card.text = gameBoardState[i]
        }

        findViewById<Button>(R.id.match_button).visibility = matchButtonViewState
        findViewById<Button>(R.id.play_again_button).visibility = playButtonViewState
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

    /**
     * The function updates a TextView with debug information about clickCount and matchesLeft.
     */
    /*private fun updateDebugTextView(){
        val debugTextView = findViewById<TextView>(R.id.debug_text_view)
        val debugText = "clickcount: $clickCount, matchesLeft: $matchesLeft"
        debugTextView.text = debugText
    }*/
}