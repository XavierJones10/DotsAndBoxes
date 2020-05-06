package org.example.student.dotsboxgame

import uk.ac.bournemouth.ap.dotsandboxeslib.*
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.Matrix
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.MutableMatrix
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.MutableSparseMatrix
import uk.ac.bournemouth.ap.dotsandboxeslib.matrix.SparseMatrix

class StudentDotsBoxGame(columns: Int, rows: Int, players: List<Player>) : AbstractDotsAndBoxesGame() {


    override val players: List<Player> = players.toList() //TODO("You will need to get players from your constructor")

    override var currentPlayer: Player = players[0] //get()= TODO("Determine the current player, like keeping" + "the index into the players list")


    // NOTE: you may want to me more specific in the box type if you use that type in your class
    override val boxes: Matrix<StudentBox> = MutableMatrix(columns, rows, ::StudentBox)



    override val lines: SparseMatrix<StudentLine> =
        MutableSparseMatrix(columns + 1, rows * 2 + 1, ::StudentLine) { x, y ->
            y % 2 == 1 || x < columns
        }
    override var isFinished: Boolean = false
        //get() = TODO("Provide this getter. Note you can make it a var to do so")

    override fun playComputerTurns() {
        var current = currentPlayer
        while (current is ComputerPlayer && ! isFinished) {
            current.makeMove(this)
            current = currentPlayer
        }
        }


    /**
     * This is an inner class as it needs to refer to the game to be able to look up the correct
     * lines and boxes. Alternatively you can have a game property that does the same thing without
     * it being an inner class.
     */
    inner class StudentLine(lineX: Int, lineY: Int) : AbstractLine(lineX, lineY) {
        override val isDrawn: Boolean = false
            //get() = TODO("Provide this getter. Note you can make it a var to do so")


        override val adjacentBoxes: Pair<StudentBox?, StudentBox?>
            get() {
                if(lineY%2 == 0){

                    return if(lineY-1 == -1){

                        Pair(boxes[lineX, lineY], null)

                    }else if (lineY/2 > boxes.maxHeight){


                        Pair(boxes[lineX, (lineY/2)-1], null)
                    }else{


                        Pair(boxes[lineX, (lineY/2)-1], boxes[lineX, (lineY/2)])
                    }


                }else{
                    return if(lineX-1 == -1){
                        Pair(boxes[lineX, (lineY-1/2)], null)
                    }else if(lineX == boxes.maxWidth){

                        Pair(boxes[lineX-1, (lineY-1/2)], null)

                    }else{
                        Pair(boxes[lineX-1, (lineY-1/2)], boxes[lineX, (lineY-1/2)])
                    }
                }



                //TODO("You need to look up the correct boxes for this to work")
            }

        override fun drawLine() {
            TODO("Implement the logic for a player drawing a line. Don't forget to inform the listeners (fireGameChange, fireGameOver)")
            // NOTE read the documentation in the interface, you must also update the current player.
        }
    }

    inner class StudentBox(boxX: Int, boxY: Int) : AbstractBox(boxX, boxY) {

        override val owningPlayer: Player?
            get() = TODO("Provide this getter. Note you can make it a var to do so")

        /**
         * This must be lazy or a getter, otherwise there is a chicken/egg problem with the boxes
         */
        override val boundingLines: Iterable<DotsAndBoxesGame.Line>
            get() = TODO("Look up the correct lines from the game outer class")

    }
}