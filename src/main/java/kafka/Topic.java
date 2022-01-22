package kafka;

import java.util.List;

public class Topic {
    private int id;
    private List<Partition> partitionList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Partition> getPartitionList() {
        return partitionList;
    }

    public void setPartitionList(List<Partition> partitionList) {
        this.partitionList = partitionList;
    }
}
