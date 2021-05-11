package net.contal.demo;

import java.util.Random;

public abstract class AccountNumberUtil {
    
    /**
     * @return random integer used for account id's
     */
    public static int generateAccountNumber() {
        return new Random().nextInt();
    }

}
