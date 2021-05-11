package net.contal.demo.services;

import net.contal.demo.AccountNumberUtil;
import net.contal.demo.DbUtils;
import net.contal.demo.modal.BankTransaction;
import net.contal.demo.modal.CustomerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * TODO complete this service class
 * TODO use BankServiceTest class
 */
@Service
@Transactional
public class BankService {

    //USE this class to access database , you can call openASession to access database
    private final DbUtils dbUtils;
    private String hql = "from CustomerAccount ca where ca.accountNumber =:accountNumber";

    @Autowired
    public BankService(DbUtils dbUtils) {
        this.dbUtils = dbUtils;
    }


    /**
     * Save customAccount to database
     * return AccountNumber
     *
     * @param customerAccount populate this (firstName , lastName ) already provided
     * @return accountNumber
     */
    public int createAnAccount(CustomerAccount customerAccount) {

        // Generate account number:
        int accountNumber = AccountNumberUtil.generateAccountNumber();

        // Set CustomerAccount account number to the generated account number;
        customerAccount.setAccountNumber(accountNumber);

        // Set transactions to an empty ArrayList.
        customerAccount.setTransactions(new ArrayList<>());

        // Save CustomerAccount to the database.
        dbUtils.openASession().saveOrUpdate(customerAccount);

        // Return account number:
        return accountNumber;
    }


    /**
     * TODO implement this functions
     * <p>
     * Disclaimer: I am not sure what is being asking for in regards
     * to the fact that there can be multiple customer accounts with the same account id.
     *
     * @param accountNumber target account number
     * @param amount        amount to register as transaction
     * @return boolean , if added as transaction
     */
    public boolean addTransactions(int accountNumber, Double amount) {

        /**
         *TODO
         * Find and account by using accountNumber (Only write the query in hql String  )
         * create Transaction for account with provided  amount
         * return true if added , return false if account dont exist , or amount is null
         */

        /** TODO write Query to get account by number un comment section below , catch query   */

        if (amount != null) {
            try {
                // Retrieve first CustomerAccount associated with the given accountNumber.
                CustomerAccount account = getAccount(accountNumber);
                if (account != null) {

                    // Create new transaction and include all necessary data.
                    BankTransaction transaction = new BankTransaction();
                    transaction.setCustomerAccount(account);
                    transaction.setTransactionAmount(amount);
                    transaction.setTransactionDate(new Date());

                    // Add transaction to customer account.
                    account.getTransactions().add(transaction);

                    // Save updated/create data.
                    dbUtils.openASession().saveOrUpdate(transaction);
                    dbUtils.openASession().saveOrUpdate(account);
                    return true;

                }

            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }


    /**
     * TODO implement this functions
     * <p>
     * Disclaimer: I am not sure what is being asking for in regards
     * to the fact that there can be multiple customer accounts with the same account id.
     *
     * @param accountNumber target account
     * @return account balance
     */
    public double getBalance(int accountNumber) {

        // Define balance variable.
        double balance = 0d;

        // Retrieve results from db query.
        CustomerAccount account = getAccount(accountNumber);

        // Check if the account exists.
        if (account != null) {

            // Get transactions for only the first match as this system doesn't use
            // unique account numbers only db Id's which we can't search for with the provided method.
            List<BankTransaction> transactions = account.getTransactions();
            for (BankTransaction transaction : transactions) {

                //Update balance variable.
                balance += transaction.getTransactionAmount();
            }
        }

        // returns the balance associated with the accountNumber
        return balance;
    }


    /**
     * TODO implement this functions
     * ADVANCE TASK
     *
     * @param accountNumber accountNumber
     * @return HashMap [key: date , value: double]
     */
    public Map<Date, Double> getDateBalance(int accountNumber) {

        // Query to get all BankTransactions with from the account holder.
        String dateBalanceQuery = "from BankTransaction bt where bt.customerAccount.accountNumber =:accountNumber";
        List<BankTransaction> transactions = this.dbUtils.openASession()
                                                         .createQuery(dateBalanceQuery, BankTransaction.class)
                                                         .setParameter("accountNumber", accountNumber)
                                                         .getResultList();

        // Hashmap to store Date, Balance pair.
        HashMap<Date, Double> dateBalanceMap = new HashMap<>();

        // Process all date in transactions.
        transactions.forEach(transaction -> {

            // Define variables for commonly used
            Date transactionDate = transaction.getTransactionDate();
            double amount = transaction.getTransactionAmount();
            Double balance = dateBalanceMap.get(transactionDate);

            // Put data in hashmap whereby if the date exists
            // already provide the sum of the transaction and
            // existing balance, otherwise provide only the transaction.
            dateBalanceMap.put(
                    transactionDate,
                    balance == null
                    ? amount
                    : balance + amount
            );
        });

        // Returns the dateBalanceMap as specified per the provided method args.
        return dateBalanceMap;
    }

    /**
     * Retrieves the first CustomerAccount associated with the provided account number.
     *
     * @param accountNumber
     * @return
     */
    public CustomerAccount getAccount(int accountNumber) {
        // Retrieve results from db query.
        List<CustomerAccount> accounts = this.dbUtils.openASession().createQuery(hql, CustomerAccount.class)
                                                     .setParameter("accountNumber", accountNumber)
                                                     .getResultList();

        // Return first account associated to the provided accountNumber.
        if (!accounts.isEmpty()) return accounts.get(0);
        return null;
    }

    // Wasn't sure what is asked for with regards to #6 as it seems to be very similar to getDateBalance(int)

}
