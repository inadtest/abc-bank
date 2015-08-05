package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
   

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }
    
    public synchronized String transferFromCheckingtoSavings(double amount) {
    	String s = "";
    	if (amount < 0) {
    		return "transfer amount cannot be less than 0";
    	}
    	boolean checkingAccExists = false;
    	Account checkingType = null;
    	Account savingType = null;
    	boolean savingAccExists = false;
    	 for (Account a : accounts) {
    		if (a.getAccountType() == Account.CHECKING) {
    			checkingAccExists = true;
    			checkingType = a;
    		} else if (a.getAccountType() == Account.SAVINGS) {
    			savingAccExists = true;
    			savingType = a;
    		}
    	 }
    	 
    	 if (!checkingAccExists ) {
    		 return "you do not have checking account to transfer from";
    	 } else if(!savingAccExists) {
    		 return "you do not have saving account to transfer to";
    	 }
    	 
    	 if (checkingType.sumTransactions() > amount) {
    		 checkingType.withdraw(amount);
    		 savingType.deposit(amount);
    	 } else {
    		 s = "You do not have sufficient amount in your checking account to transfer";
    	 }
    	 return s;
    }

    public synchronized String transferFromSavingtoCheckings(double amount) {
    	String s = "";
    	if (amount < 0) {
    		return "transfer amount cannot be less than 0";
    	}
    	boolean checkingAccExists = false;
    	Account checkingType = null;
    	Account savingType = null;
    	boolean savingAccExists = false;
    	 for (Account a : accounts) {
    		if (a.getAccountType() == Account.CHECKING) {
    			checkingAccExists = true;
    			checkingType = a;
    		} else if (a.getAccountType() == Account.SAVINGS) {
    			savingAccExists = true;
    			savingType = a;
    		}
    	 }
    	 
    	 if (!checkingAccExists ) {
    		 return "you do not have checking account to transfer to";
    	 } else if(!savingAccExists) {
    		 return "you do not have saving account to transfer from";
    	 }
    	 
    	 if (savingType.sumTransactions() > amount) {
    		 savingType.withdraw(amount);
    		 checkingType.deposit(amount);
    	 } else {
    		 s = "You do not have sufficient amount in your saving account to transfer";
    	 }
    	 return s;
    }
    
    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}