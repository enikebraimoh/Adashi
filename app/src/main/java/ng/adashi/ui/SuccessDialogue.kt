package ng.adashi.ui

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import ng.adashi.R

class SuccessDialogue(activity: Activity) : Dialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.success_dialogue);
    }

}