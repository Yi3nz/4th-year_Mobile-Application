package sarah.nci.ie.reminder;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * CA class
 */

@Entity
public class CA {

    //Declare the variables
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "ca_subject")
    private String Subject;

    @ColumnInfo(name = "ca_title")
    private String CATitle;

    @ColumnInfo(name = "ca_date")
    private String date;

    public CA(String Subject, String CATitle, String date) {
        this.Subject = Subject;
        this.CATitle = CATitle;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getCATitle() {
        return CATitle;
    }

    public void setCATitle(String CATitle) {
        this.CATitle = CATitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
