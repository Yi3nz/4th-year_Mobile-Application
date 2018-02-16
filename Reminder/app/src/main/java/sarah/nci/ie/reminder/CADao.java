package sarah.nci.ie.reminder;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * The database queries declarations
 */
@Dao
public interface CADao {
    @Query("SELECT * FROM ca")
    List<CA> getAllCAs();

    @Insert
    void insertAll(CA... cas);
}
