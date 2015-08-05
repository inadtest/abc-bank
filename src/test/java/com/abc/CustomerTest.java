package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testTransferFromCheckingtoSavings() {
    	Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        
    	Customer john = new Customer("John")
        .openAccount(savingsAccount);
    	assertEquals("you do not have checking account to transfer from", john.transferFromCheckingtoSavings(55.0));
    	john.openAccount(checkingAccount);
    	savingsAccount.deposit(660);
    	
    	checkingAccount.deposit(100);
    	john.transferFromCheckingtoSavings(55.0);
    	assertEquals("Statement for John\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $660.00\n" +
                "  deposit $55.00\n" +
                "Total $715.00\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $55.00\n" +
                "Total $45.00\n" +
                "\n" +
                "Total In All Accounts $760.00", john.getStatement());
    }
    
    @Test
    public void testTransferFromSavingtoCheckings() {
    	Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
    	Customer charlie = new Customer("Charlie")
        .openAccount(savingsAccount);
    	assertEquals("you do not have checking account to transfer to", charlie.transferFromSavingtoCheckings(55.0));
    	charlie.openAccount(checkingAccount);
    	savingsAccount.deposit(660);
    	checkingAccount.deposit(100);
    	charlie.transferFromSavingtoCheckings(55.0);
    	assertEquals("Statement for Charlie\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $660.00\n" +
                "  withdrawal $55.00\n" +
                "Total $605.00\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $55.00\n" +
                "Total $155.00\n" +
                "\n" +
                "Total In All Accounts $760.00", charlie.getStatement());
    	
    }
    
}