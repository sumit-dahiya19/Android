import android.app.Service
import android.content.*
import android.database.Cursor
import android.net.Uri
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity

fun main() {



}
public class Activity:AppCompatActivity()
{

}
public class service:Service(){
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}
public class myReceiver:BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("Not yet implemented")
    }
}
public class contenetProvider:ContentProvider(){
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun onCreate(): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

}
