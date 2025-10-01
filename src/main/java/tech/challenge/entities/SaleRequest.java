package tech.challenge.entities;

public class SaleRequest {

    private String carId;
    private String buyerCpf;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;

    public SaleRequest() {}

    public SaleRequest(String carId, String buyerCpf, String buyerName, String buyerEmail, String buyerPhone) {
        this.carId = carId;
        this.buyerCpf = buyerCpf;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerPhone = buyerPhone;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBuyerCpf() {
        return buyerCpf;
    }

    public void setBuyerCpf(String buyerCpf) {
        this.buyerCpf = buyerCpf;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }
}