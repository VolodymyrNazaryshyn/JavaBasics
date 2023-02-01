package itstep.learning.oop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Hologram extends Literature implements Presentable {
    private Date date;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    public Hologram(String title, String date) throws ParseException {
        this.date = dateFormat.parse(date);
        super.setTitle(title);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("Hologram: %s (%s)", super.getTitle(), dateFormat.format(this.date));
    }
}