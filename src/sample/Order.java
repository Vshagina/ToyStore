package sample;
public class Order {
    private int order_id;
    private String order_name;
    private int product_amount;

    public Order(int order_id, String order_name, int product_amount) {
        this.order_id = order_id;
        this.order_name = order_name;
        this.product_amount = product_amount;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public int getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(int product_amount) {
        this.product_amount = product_amount;
    }
}
