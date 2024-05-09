package carsdealership;

import java.util.Date;
import java.util.UUID;

enum PaymentMethod {
    Cache,
    Visa,
    Mada,
}

class Sale {
    private String id;
    private CostomerAccount costomer;
    private SalesManAccount salesMan;
    private Product product;
    private long saleTime; // In Unix time stamp
    private double totalBill;
    private PaymentMethod paymentMethod;

    // Payment via sales man.
    Sale(CostomerAccount costomer, SalesManAccount salesMan, Product product, double totalBill,
            PaymentMethod paymentMethod) {
        this.costomer = costomer;
        this.salesMan = salesMan;
        this.product = product;

        this.totalBill = totalBill;
        this.paymentMethod = paymentMethod;
        this.saleTime = System.currentTimeMillis();
        this.id = UUID.randomUUID().toString();
    }

    // Self payment without any sales man.
    Sale(CostomerAccount costomer, Product product, double totalBill, PaymentMethod paymentMethod) {
        this(costomer, null, product, totalBill, paymentMethod);
    }

    public String getId() {
        return this.id;
    }

    public CostomerAccount getCostomer() {
        return this.costomer;
    }

    public SalesManAccount getSalesMan() {
        return this.salesMan;
    }

    public Product getProduct() {
        return this.product;
    }

    public double getTotalBill() {
        return this.totalBill;
    }

    public long getSaleTime() {
        return this.saleTime;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public String getSaleDateString() {
        return new Date(this.saleTime).toString();
    }
}
