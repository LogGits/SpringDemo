package net.contal.demo.controllers;

import net.contal.demo.modal.BankTransaction;
import net.contal.demo.modal.CustomerAccount;
import net.contal.demo.services.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {
    final Logger logger = LoggerFactory.getLogger(BankController.class);
    final BankService dataService;

    public BankController(BankService dataService) {
        this.dataService = dataService;
    }

    /**
     * @param account {firstName:"" , lastName:"" }
     * @return created CustomerAccount object's accountNumber.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public long createBankAccount(@RequestBody CustomerAccount account) {
        logger.info("{}", account.toString());
        return dataService.createAnAccount(account);
    }

    /**
     * Creates an account with requirements of firstName, lastName, accountBalance.
     * #4
     *
     * @param firstName
     * @param lastName
     * @param accountBalance
     */
    @RequestMapping(method = RequestMethod.POST, value = "/createfull")
    public void createBankAccount(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("accountBalance") double accountBalance) {
        // Create new account object and apply all necessary data.
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setFirstName(firstName);
        customerAccount.setLastName(lastName);
        customerAccount.setAccountBalance(accountBalance);

        // Run existing method for account creation within the BankController (dataService)
        dataService.createAnAccount(customerAccount);
    }

    /**
     * #5
     *
     * @param accountNumber
     * @return account data in a string or an error message if no account is associated with the provided accountNumber.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/account")
    public String getAccountDetails(@RequestParam("accountNumber") int accountNumber) {
        // Get the first account associated with the account number;
        CustomerAccount account = dataService.getAccount(accountNumber);

        // Return either print of account info or an error message.
        return account != null
               ? account.toString()
               : String.format("Error: Couldn't find the account associated with id: %d.", accountNumber);
    }

    /**
     * #6
     *
     * @param accountNumber BankAccount number
     * @param amount        Amount as Transaction
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addtransaction")
    public void addTransaction(@RequestParam("accountNumber") String accountNumber, @RequestParam("amount") Double amount) {
        logger.info("Bank Account number is :{} , Transaction Amount {}", accountNumber, amount);
        try {
            int accountNum = Integer.parseInt(accountNumber);
            dataService.addTransactions(accountNum, amount);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @param accountNumber
     * @return last 10 (or less) transactions that are associated with an account id.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/transactions")
    public List<BankTransaction> addTransaction(@RequestParam("accountNumber") String accountNumber) {
        logger.info("Bank Account number is :{}", accountNumber);

        List<BankTransaction> transactionList = new ArrayList<>();
        try {
            int accountNum = Integer.parseInt(accountNumber);
            CustomerAccount account = dataService.getAccount(accountNum);
            if (account != null) {
                List<BankTransaction> transactions = account.getTransactions();
                if (!transactions.isEmpty()) {
                    int min = Math.max(0, transactions.size() - 10);
                    for (int i = transactions.size(); i > min; i--) {
                        transactionList.add(transactions.get(i));
                    }
                }
            }
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
        return transactionList;
    }


    /**
     * #8
     *
     * @param accountNumber customer  bank account  number
     * @return balance
     */
    @RequestMapping(method = RequestMethod.POST, value = "/balance")
    public Double getBalance(@RequestParam("accountNumber") String accountNumber) {
        logger.info("Bank Account number is :{}", accountNumber);
        double balance = 0.0d;
        try {
            int accountNum = Integer.parseInt(accountNumber);
            balance = dataService.getBalance(accountNum);
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
        return balance;
    }


}
