package za.co.wethinkcode.toyrobot.maze;


public class MyParameters implements Comparable<MyParameters>{
    private double id;
    private int count;
    private Node node;

    public MyParameters(double id, int count, Node node) {
        this.id = id;
        this.count = count;
        this.node = node;
    }


    public double getId() {
        return id;
    }


    public int getCount() {
        return count;
    }


    public Node getNode() {
        return node;
    }


    public boolean equals(MyParameters other){
        return this.getId() == other.getId();
    }


    /**
     * Method to decide which elments of the priority queue it should compare to decide which one should come first.
     * @param other
     * @return - 0, attribute being compared is equal. 1, if attribute of current object is bigger
     * -1, if attribute of other object is bigger.
     */
    @Override
    public int compareTo(MyParameters other) {
        if (this.getId() == other.getId()){
            if (this.count == other.getCount()){
                return 0;
            } else if (this.count > other.getCount()) {
                return 1;
            } else {
                return -1;
            }
        } else if (this.getId() > other.getId()){
            return 1;
        } else {
            return -1;
        }
    }
}
