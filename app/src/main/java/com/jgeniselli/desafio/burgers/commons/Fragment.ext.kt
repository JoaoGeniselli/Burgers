import android.app.AlertDialog
import android.support.v4.app.Fragment
import com.jgeniselli.desafio.burgers.R

public fun Fragment.displaySimpleAlert(message: String) {
    AlertDialog.Builder(activity)
            .setTitle(R.string.warning)
            .setMessage(message)
            .setNeutralButton(R.string.ok, null)
            .show()
}