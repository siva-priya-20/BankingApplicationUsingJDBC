package Controller;

import Entity.User;
import Services.BankServices;

public class bankController {
	BankServices bs=new BankServices();
     public String newUser(User user) {
    	 return bs.register(user);
     }
     
     public String ExistingUserLogin(String email,String password) {
		return bs.login(email, password);
    	 
     }
     
     public  String checkAccountBalance(String acno) {
    	 return bs.checkBalance(acno);
    	 
     }
    public void depositAmount(double amount,String acno) {
    	bs.deposite(amount, acno);
    }
    public void withdrawAmount(double amount,String acno) {
    	bs.withdraw(amount, acno);
    }
}
