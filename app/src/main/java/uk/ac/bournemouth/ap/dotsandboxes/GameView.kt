package uk.ac.bournemouth.ap.dotsandboxes
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast


//import com.example.logic.StudentDotsBoxGame
import org.example.student.dotsboxgame.StudentDotsBoxGame
import uk.ac.bournemouth.ap.dotsandboxeslib.DotsAndBoxesGame
import uk.ac.bournemouth.ap.dotsandboxeslib.ComputerPlay
import uk.ac.bournemouth.ap.dotsandboxeslib.HumanPlayer
import uk.ac.bournemouth.ap.dotsandboxeslib.Player
import kotlin.math.roundToInt
import kotlin.math.min

class GameView: View {
    var hold = " "
    private val endingMessage: String = hold
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val game = StudentDotsBoxGame(columns = 7, rows = 10, players = listOf(HumanPlayer(), ComputerPlay()))

    private val listenerImp = object: DotsAndBoxesGame.GameChangeListener{
        override fun onGameChange(game: DotsAndBoxesGame) {
           invalidate()
        }
    }
private var overListenerImp = object: DotsAndBoxesGame.GameOverListener {
override fun onGameOver(game: DotsAndBoxesGame, scores: List<Pair<Player, Int>>){

     hold = "Player 1 scored " + scores[0].second + " Player 2 scored " + scores[1].second

    }

    }



    // for display of columns and rows
    private val rowsDis = game.boxes.height
    private val columnsDis = game.boxes.width

    private val rows = game.boxes.height + 1
    private val columns = game.boxes.width + 2


    //dots, background, box
    private val dotCol: Int = Color.rgb(0, 0, 0)
    private val labelCol: Int = Color.BLACK
    private val backCol: Int = Color.rgb(255, 178, 102)
    private val boxCol: Int = Color.rgb(244, 170, 100)
    private val wordsText: String = "Columns:$columnsDis Rows:$rowsDis"



    //player line colours
    private val player1Col: Int = Color.RED
    private val player2Col: Int = Color.BLUE
    private val noPlayerCol: Int = Color.rgb(128, 128, 128)


    //paint variables
    private var dotPaint: Paint
    private var backPaint: Paint
    private var wordsPaint: Paint

    //player paint variables
    private var noPlayerLinePaint: Paint
    private var player1LinePaint: Paint
    private var player2LinePaint: Paint

    private var emptyBoxPaint: Paint
    private var player1BoxPaint: Paint
    private var player2BoxPaint: Paint

    private val myGestureDetector = GestureDetector(context, MyGestureListener())


   // private var tempCol: Paint = Paint().apply { style = Paint.Style.FILL;color = Color.BLACK }


    init {
        //paint object for drawing circles in onDraw -- also configure it
        dotPaint = Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = dotCol
        }
        backPaint = Paint().apply {
            // Set up the paint style
            style = Paint.Style.FILL
            color = backCol
        }
        wordsPaint = Paint().apply {
            color = labelCol

            textAlign = Paint.Align.CENTER
            textSize = 50.toFloat()
            typeface = Typeface.SANS_SERIF
            typeface= Typeface.DEFAULT_BOLD
        }

        noPlayerLinePaint = Paint().apply {
            // Set up the paint style

            color = noPlayerCol
            strokeWidth = 15f
        }
        player1LinePaint = Paint().apply {
            // Set up the paint style

            color = player1Col
            strokeWidth = 16f
        }
        player2LinePaint = Paint().apply {
            // Set up the paint style
            //style = Paint.Style.FILL
            color = player2Col
            strokeWidth = 16f
        }
        player1BoxPaint = Paint().apply {
            // Set up the paint style
            style = Paint.Style.FILL
            color = player1Col
        }
        player2BoxPaint = Paint().apply {
            // Set up the paint style
            style = Paint.Style.FILL
            color = player2Col
        }
        emptyBoxPaint = Paint().apply {
            // Set up the paint style
            style = Paint.Style.FILL
            color = boxCol
        }
        dotPaint = Paint().apply {
            // Set up the paint style
            style = Paint.Style.FILL
            color = dotCol
        }


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val squareSize: Float
        var paint: Paint



// Measure the size of the canvas, we could take into account padding here
        val canvasWidth = width.toFloat()
        val canvasHeight = height.toFloat()


        val diameterX: Float = canvasWidth / columns.toFloat()
        val diameterY: Float = canvasHeight / rows.toFloat()


// Choose the smallest of the two
        squareSize = if (diameterX < diameterY)
            diameterX
        else
            diameterY



//Background
        canvas.drawRect(0f, 0f, canvasWidth, canvasHeight, backPaint)
        //Display of selected rows and columns
        canvas.drawText(wordsText, canvasWidth/2, canvasHeight -140, wordsPaint)
        //Scores
        canvas.drawText(endingMessage, canvasWidth/2, canvasHeight , wordsPaint)

        for (col in 1 until columns - 1) {
            for (row in 1 until rows) {
                val leftCorner = squareSize * col
                val rightCorner = (squareSize * col) + squareSize
                val topCorner = squareSize * row
                val bottomCorner = (squareSize * row) + squareSize


                //Draw square
                canvas.drawRect(leftCorner, topCorner, rightCorner, bottomCorner, emptyBoxPaint)
            }
        }
        for (col in 1 until columns - 1) {
            for (row in 1 until rows + 1) {
                val leftCorner = squareSize * col
                val rightCorner = (squareSize * col) + squareSize
                val topCorner = squareSize * row
                val bottomCorner = (squareSize * row) + squareSize


                //Draw top line
                canvas.drawLine(leftCorner, topCorner, rightCorner, topCorner, noPlayerLinePaint )
            }
        }
        for (col in 1 until columns) {
            for (row in 1 until rows) {
                val leftCorner = squareSize * col
                val rightCorner = (squareSize * col) + squareSize
                val topCorner = squareSize * row
                val bottomCorner = (squareSize * row) + squareSize


                //Draw left line
                canvas.drawLine(leftCorner, topCorner, leftCorner, bottomCorner, noPlayerLinePaint )
            }
        }
        val dotRadius = squareSize / 8
        for (col in 1 until columns) {
            for (row in 1 until rows + 1) {
                val x = squareSize * col
                val y = squareSize * row


                //Draw square
                canvas.drawCircle(x, y, dotRadius, dotPaint)
            }
        }
    }


    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return myGestureDetector.onTouchEvent(ev) || super.onTouchEvent(ev)
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(ev: MotionEvent): Boolean {
            return true
        }

        override fun onSingleTapUp(ev: MotionEvent): Boolean {


            if (!game.isFinished) {

//Getting the width and height
                val widthOfSquare = width.toFloat() / columns

                val heightOfSquare = height.toFloat() / rows



//location of where the user touches
                var xTouch = ev.x.toInt()
                var yTouch = ev.y.toInt()


                val squareSize: Float
                squareSize = if(widthOfSquare < heightOfSquare) {
                    widthOfSquare
                }else{
                    heightOfSquare
                }

                var columnForTap = (xTouch / (squareSize).roundToInt()) -1
                var rowForTap = (yTouch / (squareSize).roundToInt()) -1

                columnForTap = columnForTap.coerceIn(0, game.boxes.width)
                rowForTap = rowForTap.coerceIn(0, game.boxes.height)



                //lines
                val leftLineForTouch = Pair(columnForTap, (rowForTap * 2) + 1)
                val rightLineForTouch = Pair(columnForTap + 1, (rowForTap * 2) + 1)
                val topLineForTouch = Pair(columnForTap, rowForTap * 2)
                val bottomLineForTouch = Pair(columnForTap, (rowForTap + 1) * 2)


                val lineInUse: Pair<Int, Int>


//works out the postions of the lines in the box around the click
                val positionOfLeftLine = squareSize * (columnForTap + 1)
                val positionTopLine = squareSize * (rowForTap + 1)
                val positionRightLine = (squareSize * (columnForTap + 1)) + squareSize
                val positionBottomLine = (squareSize * (rowForTap + 1)) + squareSize



//works out the closet line to the click
                val distanceToLeftLine  = xTouch - positionOfLeftLine
                val distanceToRightLine = positionRightLine - xTouch
                val distanceToTopLine = yTouch - positionTopLine
                val distanceToBottomLine = positionBottomLine - yTouch

                val lineX = min(distanceToLeftLine, distanceToRightLine)
                val lineY = min(distanceToTopLine, distanceToBottomLine)

                lineInUse = when (min(lineX, lineY)){
                    distanceToLeftLine -> leftLineForTouch
                    distanceToRightLine -> rightLineForTouch
                    distanceToTopLine -> topLineForTouch
                    else -> bottomLineForTouch
                }

val useLine = game.lines[lineInUse.first, lineInUse.second]

                try{

                    useLine.drawLine()
                }catch (e:IllegalStateException){

                    invalidate()
                }
                return true

            }
            return false

        }
    }
}


