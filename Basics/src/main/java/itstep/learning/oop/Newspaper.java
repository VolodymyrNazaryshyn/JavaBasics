package itstep.learning.oop;
import java.time.LocalDate;

public class Newspaper extends Literature {
    private LocalDate dateOfPublication = LocalDate.now();

    public Newspaper(LocalDate dateOfPublication, String title) {
        this.dateOfPublication = dateOfPublication;
        super.setTitle( title ) ;
    }

    public LocalDate getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(LocalDate dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    @Override
    public String toString() {
        return super.toString() + " date of publication: " + this.dateOfPublication ;
    }
}
