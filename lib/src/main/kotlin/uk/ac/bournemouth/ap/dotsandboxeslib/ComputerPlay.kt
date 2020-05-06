package uk.ac.bournemouth.ap.dotsandboxeslib

open class ComputerPlay : ComputerPlayer() {
    override fun makeMove(game: DotsAndBoxesGame) {
        val allMoves = game.lines.filter { !it.isDrawn }

        val chosenMove = allMoves.random()
        chosenMove.drawLine()
    }
}