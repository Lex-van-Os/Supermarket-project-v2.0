import java.util.ArrayList;
import java.util.HashMap;

public class testProduct {
    int product_id;
    String product_name;
    double price;
    int in_stock;
    String amount;
    int category_id;
    int department_id;
    String column_name;
    int number;

    public static class Builder {
        private int product_id;
        private String product_name;
        private double price;
        private int in_stock;
        private String amount;
        private int category_id;
        private int department_id;
        private String column_name;
        private int number;

        public Builder(int product_id) {
            this.product_id = product_id;
        }

        public Builder productName(String product_name) {
            this.product_name = product_name;
            return this;
        }

        public Builder productPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder productInStock(int in_stock) {
            this.in_stock = in_stock;
            return this;
        }

        public Builder productAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public Builder productNumber(int number) {
            this.number = number;
            return this;
        }

        public testProduct build() {
            testProduct testProduct = new testProduct();
            testProduct.product_id = this.product_id;
            testProduct.product_name = this.product_name;
            testProduct.price = this.price;
            testProduct.in_stock = this.in_stock;
            testProduct.amount = this.amount;
            testProduct.number = this.number;

            return testProduct;
        }
    }

    public static void main(String[] args) {
        setArray();
    }
//
//    public void setNumber(int number) {
//        this.number = number;
//    }

//    public testProduct(int num, boolean orderToken) {
//        if (orderToken) {
//            number = num;
//            System.out.println("testProduct constructor called..number is.." + number);
//        }
//    }

//    public static void setArray() {
//        testProduct[] products = new testProduct[10];
//        for(int i=0; i<products.length; i++){
//            products[i] = new testProduct();//this will call constructor.
//            products[i].setNumber(i+1);
//        }
//        System.out.println(products[0].number);
//    }

    public static void setArray() {
        HashMap<Integer, testProduct> products = new HashMap<Integer, testProduct>();
//        testProduct yetAnotherTest = new testProduct();

        products.put(4, new testProduct.Builder(4)
                .productName("Banaan")
                .productPrice(511.95)
                .productInStock(51)
                .productAmount("200g")
                .productNumber(2)
                .build());

        products.put(32, new testProduct.Builder(32)
                .productName("Appel")
                .productPrice(21.31)
                .productInStock(21)
                .productAmount("500g")
                .productNumber(59)
                .build());

        for (int i : products.keySet()) {
            System.out.println(products.get(i).product_name);
            testProduct quatro = products.get(i);
//            System.out.println(quatro.product_name);
        }

//        products.add(1, new testProduct.Builder(32)
//                .productName("Appel")
//                .productPrice(21.31)
//                .productInStock(21)
//                .productAmount("500g")
//                .productNumber(59)
//                .build());

//        products[i].setNumber(i + 1);
//        System.out.println(products[0].number);
    }
}
