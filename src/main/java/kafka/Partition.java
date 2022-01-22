package kafka;

import java.util.List;

public class Partition<TYPE_T> {
    private int id;
    private List<TYPE_T> typeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TYPE_T> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<TYPE_T> typeList) {
        this.typeList = typeList;
    }
}
