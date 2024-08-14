package com.cpsc333f23.gc5

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cpsc333f23.memory.Memory

class MainActivity : AppCompatActivity() {
    private var clickCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //game board object
        var game = Memory(6, this)

        //get a list of the card TextViews
        val cardList: List<TextView> = getCardViews()
        //add a click event listener for each of the card TextViews
        for (card in cardList){
            card.setOnClickListener {
                selectCard(it, game.board)
            }
        }

        // This is the "hard coded" version of the above

    /*
        var card = findViewById<TextView>(R.id.r1c1)
        card.setOnClickListener {
            selectCard(it, game.board)
        }
        card = findViewById<TextView>(R.id.r1c2)
        card.setOnClickListener {
            selectCard(it, game.board)
        }

        card = findViewById<TextView>(R.id.r2c1)
        card.setOnClickListener {
            selectCard(it, game.board)
        }

        card = findViewById<TextView>(R.id.r2c2)
        card.setOnClickListener {
            selectCard(it, game.board)
        }

        card = findViewById<TextView>(R.id.r3c1)
        card.setOnClickListener {
            selectCard(it, game.board)
        }

        card = findViewById<TextView>(R.id.r3c2)
        card.setOnClickListener {
            selectCard(it, game.board)
        }
    */

        //endregion code body
    }

    /**
     * The function `selectCard` is a Kotlin function that takes a `View` and a `List<String>` as
     * parameters and updates the text of certain `TextView` elements based on the provided `board`
     * list.
     *
     * @param view The `view` parameter is the specific view (in this case, a TextView) that was
     * clicked by the user. It represents the card that the user selected.
     * @param board The `board` parameter is a list of strings representing the current state of the
     * game board. Each string in the list represents a card on the board. The order of the strings in
     * the list corresponds to the position of the cards on the board.
     * @return The function does not explicitly return a value. It returns nothing (Unit) by default.
     */
    private fun selectCard(view: View, board: List<String>) {
        //if it's the 2nd click, check match and reset cards
        if (clickCount == 2){
            clickCount = 0 //reset click counter

            //get list of card TextViews
            val cardViews = getCardViews()

            //reset each view
            //TODO: only reset if there hasn't been a match
            //TODO: update number of matches counter if there has been a match
            //TODO: notify the user that there was a match (Toast?)
            for (card in cardViews){
                card.text = resources.getString(R.string.default_card_icon)
            }
            return //board reset or matches updated, all done in this method
        }
        else { //have not clicked 2 cards yet, reveal the card

            val clickedCardTextView = view as TextView

            if (clickedCardTextView.text.toString() == getString(R.string.default_card_icon)){
                //for debugging purposes, prints out clicked id string rather than integer
                //println(resources.getResourceEntryName(clickedCardTextView.id))
                println(clickedCardTextView.text)

                //hard-coded solution for now
                //TODO: in the future - create scheme for programmatically generated layout and pick method
                when (resources.getResourceEntryName(clickedCardTextView.id)){
                    "r1c1" -> { clickedCardTextView.text = board[0] }
                    "r1c2" -> { clickedCardTextView.text = board[1] }
                    "r2c1" -> { clickedCardTextView.text = board[2] }
                    "r2c2" -> { clickedCardTextView.text = board[3] }
                    "r3c1" -> { clickedCardTextView.text = board[4] }
                    "r3c2" -> { clickedCardTextView.text = board[5] }
                }
                clickCount++
            }
        }
    }

    /**
     * The function `getCardViews()` returns an ArrayList of TextViews that are contained within
     * LinearLayouts in the mainLayout.
     *
     * @return The function `getCardViews()` returns an `ArrayList` of `TextView` objects.
     */
    private fun getCardViews() : ArrayList<TextView> {
        val mainLayout = findViewById<LinearLayout>(R.id.main_layout)
        val innerLinearLayoutList = ArrayList<LinearLayout>()
        val cardTextViewList = ArrayList<TextView>()

        for (i in 0 until mainLayout.childCount) {
            if (mainLayout.getChildAt(i) is LinearLayout) innerLinearLayoutList.add(
                mainLayout.getChildAt(
                    i
                ) as LinearLayout
            )
        }

        for (layout in innerLinearLayoutList){
            for (i in 0 until layout.childCount) {
                if (layout.getChildAt(i) is TextView){
                    cardTextViewList.add(layout.getChildAt(i) as TextView)
                }
            }
        }
        return cardTextViewList
    }
}