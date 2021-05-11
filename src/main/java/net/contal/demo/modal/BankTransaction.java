package net.contal.demo.modal;

import javax.persistence.*;
import java.util.Date;

//TODO complete this class
@Entity
@Table
public class BankTransaction {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private CustomerAccount customerAccount;

    @GeneratedValue
    private double transactionAmount;

    @GeneratedValue
    private Date transactionDate;

    /**
     * @return The transaction id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the bank transaction id.
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return CustomerAccount object associated with the transaction.
     */
    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    /**
     * Sets the CustomerAccount object associated with the transaction.
     *
     * @param customerAccount
     */
    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
    }

    /**
     * @return The transaction amount.
     */
    public double getTransactionAmount() {
        return transactionAmount;
    }

    /**
     * Sets the transaction amount.
     *
     * @param transactionAmount
     */
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    /**
     * @return Date of the transaction.
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the Date of the transaction.
     *
     * @param transactionDate
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "BankTransaction{" +
               "id=" + id +
               ", customerAccount=" + customerAccount +
               ", transactionAmount=" + transactionAmount +
               ", transactionDate=" + transactionDate +
               '}';
    }
}
