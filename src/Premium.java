public class Premium extends Basic {
    private int nr_P;

    public Premium(String name, int nr_B, int nr_P) {
        super(name, nr_B);
        this.nr_P = nr_P;
    }

    @Override
    public String decrement() {
        if (this.nr_P == 0) {
            return super.decrement();
        } else {
            this.nr_P--;
            return "Premium";
        }
    }
}