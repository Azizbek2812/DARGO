package api.dargo.uz.dto;

public class AppResponse <T>{
    private T data;

    public AppResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
