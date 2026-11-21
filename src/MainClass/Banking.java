
package MainClass;

import java.util.Scanner;
import Controller.bankController;
import Entity.User;

public class Banking {

    public static void main(String[] args) {

        User user = new User();
        bankController bc = new bankController();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n1.Register");
            System.out.println("2.Login");
            System.out.println("3.Exit");
            System.out.println("Choose any one option");

            int n = sc.nextInt();

            switch (n) {

                case 1:
                    System.out.println("Enter username:");
                    String name = sc.next();

                    System.out.println("Enter email:");
                    String email = sc.next();

                    System.out.println("Enter mobile number:");
                    String mobile = sc.next();

                    System.out.println("Enter password:");
                    String pwd = sc.next();

                    user.setUsername(name);
                    user.setEmail(email);
                    user.setMobile(mobile);
                    user.setPassword(pwd);

                    System.out.println(bc.newUser(user));
                    break;

                case 2:

                    System.out.println("Enter your email:");
                    String email1 = sc.next();

                    System.out.println("Enter password:");
                    String pwd1 = sc.next();

                    String result = bc.ExistingUserLogin(email1, pwd1);

                    System.out.println(result);

                    if (result.startsWith("Welcome ")) {

                        while (true) {

                            System.out.println("\n1.Check balance");
                            System.out.println("2.Deposit amount");
                            System.out.println("3.Withdrawal amount");
                            System.out.println("4.Logout");

                            System.out.println("Enter any option as per your requirement");

                            int choice = sc.nextInt();

                            switch (choice) {

                                case 1:
                                    System.out.println("Enter your account number");
                                    String acno = sc.next();

                                    System.out.println(bc.checkAccountBalance(acno));
                                    break;

                                case 2:
                                    System.out.println("Enter amount to be deposited");
                                    double amount = sc.nextDouble();

                                    System.out.println("Enter account number");
                                    String acno1 = sc.next();

                                    bc.depositAmount(amount, acno1);
                                    break;

                                case 3:
                                    System.out.println("Enter amount to be withdraw");
                                    double amount1 = sc.nextDouble();

                                    System.out.println("Enter account number");
                                    String acno2 = sc.next();

                                    bc.withdrawAmount(amount1, acno2);
                                    break;

                                case 4:
                                    System.out.println("Logout successful!");
                                    break;

                                default:
                                    System.out.println("Please enter valid number");
                            }

                            if (choice == 4) {
                                break;
                            }
                        }

                    } else {
                        System.out.println("Please login with valid details.");
                    }

                    break;

                case 3:
                    System.out.println("Thank you for using our banking application!");
                    sc.close();
                    return;

                default:
                    System.out.println("Please enter valid number");
            }
        }
    }
}
