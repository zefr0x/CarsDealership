package carsdealership;

enum PaymentMethod {
    Cache,
    Visa,
    Mada,
}

class SaleOperation {
    private String id;
    private String costomerId;
    private String salesManId;
    private String productId;
    private long saleTime; // In Unix time stamp
    private String paymentMethod;

    // Payment via sales man.
    SaleOperation(String id, String costomerId, String salesManId, String productId, String paymentMethod, long time) {
        this.id = id;
        this.costomerId = costomerId;
        this.salesManId = salesManId;
        this.productId = productId;

        this.paymentMethod = paymentMethod;
        this.saleTime = time;
    }

    public String getId() {
        return this.id;
    }

    public String getCostomerId() {
        return this.costomerId;
    }

    public String getSalesManId() {
        return this.salesManId;
    }

    public String getProductId() {
        return this.productId;
    }

    public long getSaleTime() {
        return this.saleTime;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }
}
