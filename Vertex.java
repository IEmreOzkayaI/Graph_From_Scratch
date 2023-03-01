package Graph;

public class Vertex<T> {

    private T label;
    private int load;
    private int distribuited;
    private int remainedPower;
    private boolean isVisited;
    private int capacity;
    private int path = 0;
    private T predecessor;

    public Vertex() {
        this.label = null;
        this.load = 0;
        this.distribuited = 0;
        this.remainedPower = 0;
        this.isVisited = false;
    }

    public Vertex(T label) {
        this.label = label;
        this.load = 0;
        this.distribuited = 0;
        this.remainedPower = 0;
        this.isVisited = false;
    }

    public Vertex(T label, int load) {
        this.label = label;
        this.load = load;
        this.distribuited = 0;
        this.remainedPower = 0;
        this.isVisited = false;
    }

    public T getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(T predecessor) {
        this.predecessor = predecessor;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public T getLabel() {
        return label;
    }

    public void setLabel(T label) {
        this.label = label;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getDistribuited() {
        return distribuited;
    }

    public void setDistribuited(int distributionCapacity) {
        this.distribuited = distributionCapacity;
    }

    public int getRemainedPower() {
        return remainedPower;
    }

    public void setRemainedPower(int remainedPower) {
        this.remainedPower = remainedPower;
    }

}
