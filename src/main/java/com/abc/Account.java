package com.abc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private String accountNumber = null;
    public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Transaction> transactions;

    public Account(int accountType) {
    	accountNumber = String.valueOf(System.currentTimeMillis());
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        transactions.add(new Transaction(-amount));
    }
}


    public double interestEarned() {
    	DecimalFormat df = new DecimalFormat("#########.#####");
    	double interest = 0.0;
        double amount = sumTransactions();
        double diffDays = diffDates();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000) {
                	interest = Math.pow((amount*0.001/365), diffDays);
                    return Double.parseDouble(df.format(interest));
                }
                else {
                    interest = 1 +  Math.pow(((amount-1000) * 0.002/365), diffDays);
                    return Double.parseDouble(df.format(interest));
                }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	// if there is no withdrawal in the past 10 days, interest rate 5 %, else 0.1%
            	boolean isThereWithDraw = isThereWithdrawl();
                /*if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;*/
            	if (isThereWithDraw) {
            		interest = Math.pow((amount * .001/365), diffDays);
            		return Double.parseDouble(df.format(interest));
            	} else {
            		
            		interest = Math.pow((amount * .05/365), diffDays);
            		return Double.parseDouble(df.format(interest));
            	}
            default:
                interest = Math.pow((amount * 0.001/365), diffDays);
                return Double.parseDouble(df.format(interest));
        }
    }

    
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
    	double amount = 0.0;

    	for (Transaction t: transactions)
    		amount += t.getAmount();

    	
        return amount;
    }

    private Date getInitialTransactionDate() {
    	return transactions.get(0).getTransactionDate();
    }
   
    private double diffDates() {
    	double daysdiff=0;
    	Date firstDate = getInitialTransactionDate();
    	Date now = DateProvider.getInstance().now();
    	long diff = now.getTime() - firstDate.getTime();
    	long diffDays = diff / (24 * 60 * 60 * 1000)+1;
    	daysdiff = (double) diffDays;
    	return daysdiff;
    	
    }
    public int getAccountType() {
        return accountType;
    }
    
    private boolean isThereWithdrawl() {
    	long diff = 0;
    	long diffDays = 0;
    	Date now = DateProvider.getInstance().now();
    	Date transactionDate = null;
    	for(Transaction t: transactions) {
    		if (t.getAmount() < 0) {
    			transactionDate = t.getTransactionDate();
    			diff = now.getTime() - transactionDate.getTime();
    	    	diffDays = diff / (24 * 60 * 60 * 1000)+1;
    			if (diffDays < 10) {
    				return true;
    			}
    		}
    	}
    	return false;
    }

}