package net.contal.demo.modal;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class CustomerAccount {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany
    private List<BankTransaction> transactions;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @GeneratedValue
    private int accountNumber;

    @GeneratedValue
    private double accountBalance;

    /**
     * @return The CustomerAccount Id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the CustomerAccount Id.
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return A List of BankTransactions associated with the account.
     */
    public List<BankTransaction> getTransactions() {
        return transactions;
    }

    /**
     * Sets the List of BankTransactions.
     *
     * @param transactions
     * @see CustomerAccount#getTransactions() as it is preferred over using this method to modify the List of BankTransactions.
     */
    public void setTransactions(List<BankTransaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * @return The first name associated with the account.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Sets the first name associated with the account.
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The last name associated with the account.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the last name associated with the account.
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The account number.
     */
    public int getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * Sets the account number.
     *
     * @param accountNumber
     */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return The balance on the account.
     */
    public double getAccountBalance() {
        return this.accountBalance;
    }

    /**
     * Sets the balance on the account.
     *
     * @param accountBalance
     */
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
               "id=" + id +
               ", transactions=" + transactions +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", accountNumber=" + accountNumber +
               ", accountBalance=" + accountBalance +
               '}';
    }
}
