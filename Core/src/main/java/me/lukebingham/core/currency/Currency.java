package me.lukebingham.core.currency;

/**
 * Created by LukeBingham on 19/03/2017.
 */
public class Currency {

    private String name;
    private double balance, tax;
    private CurrencyType currencyType;

    public Currency(String name, CurrencyType currencyType, double tax) {
        this.name = name;
        this.currencyType = currencyType;
        this.tax = tax;
    }

    public String getName() {
        return name;
    }

    public String getName(boolean color) {
        return (color ? this.currencyType.getColor() + this.name : this.name);
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public double getTax() {
        return tax;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void take(double amount) {
        if(this.balance - amount < 0) {
            this.balance = 0;
            return;
        }

        this.balance -= amount;
    }

    /**
     * Give a specified user economy,
     * If tax is enabled, the amount given will be devided by tax.
     *
     * @param amount
     * @param tax
     * @return
     */
    public double give(double amount, boolean tax) {
        if(tax && this.tax == 0) tax = false;
        if(this.balance + amount > Integer.MAX_VALUE) {
            this.balance = Integer.MAX_VALUE;
            return 0;
        }

        this.balance += tax ? amount *= this.tax/100.0f : amount;
        return tax ? amount : 0;
    }

    public void give(double amount) {
        give(amount, false);
    }
}
