package itstep.learning.oop;

public class Journal extends Literature {
    private int number ;

    public Journal(int number, String title) {
        this.number = number ;
        super.setTitle( title ) ;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return super.getTitle() + " (№ " + this.number + ")" ;
    }
}
