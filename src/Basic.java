public class Basic extends Free {
    private int nr_B;

    public Basic(String name, int nr_B) {
        super(name);
        this.nr_B = nr_B;
    }

    @Override
    public String decrement() {
        if (nr_B == 0)
            return super.decrement();
        else {
            this.nr_B--;
            return "Basic";
        }
    }
}