package sarah.nci.ie.reminder;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * CA's room database
 */

@Database(entities = {CA.class}, version =1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract  CADao caDao();

}
