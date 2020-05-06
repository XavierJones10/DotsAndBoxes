package uk.ac.bournemouth.ap.dotsandboxes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

private var mGameView: GameView? = null


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        mGameView = GameView(this)
        //setContentView(R.layout.activity_main)
        setContentView(mGameView)
    }
}
