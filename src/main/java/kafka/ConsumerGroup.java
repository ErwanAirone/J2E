package kafka;

import java.util.List;

public class ConsumerGroup {
    private int id;
    private List<Consumer> consumerList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Consumer> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(List<Consumer> consumerList) {
        this.consumerList = consumerList;
    }

}
