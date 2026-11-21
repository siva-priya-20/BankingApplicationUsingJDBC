package Repository;

import Entity.User;

public interface BankRepo {
      String register(User user);
      String login(String email,String password);
      void deposite(double amont,String acno);
      void withdraw(double amount,String acno);
      String checkBalance(String acno);
}
