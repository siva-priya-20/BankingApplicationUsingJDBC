package Services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Entity.User;
import Properties.Utilities;
import Repository.BankRepo;

public class BankServices implements BankRepo {
	@Override
	public String register(User user) {

	    String userSql = "INSERT INTO user(user_name,email,mobile,password) VALUES(?,?,?,?)";

	    String bankSql = "INSERT INTO bankdetails(user_id,acno,amount) VALUES(?,?,?)";

	    try (Connection con = Utilities.databaseConnecting()) {

	        con.setAutoCommit(false);

	        try {
	            // 1. Insert user details
	            PreparedStatement ps = con.prepareStatement(
	                userSql, java.sql.Statement.RETURN_GENERATED_KEYS
	            );

	            ps.setString(1, user.getUsername());
	            ps.setString(2, user.getEmail());
	            ps.setString(3, user.getMobile());
	            ps.setString(4, user.getPassword());

	            ps.executeUpdate();

	            // 2. Get generated user_id
	            ResultSet rs = ps.getGeneratedKeys();

	            int userId = 0;

	            if (rs.next()) {
	                userId = rs.getInt(1);
	            }

	            // 3. Generate account number
	            String accountNumber = String.valueOf(
	                100000000000L + new java.util.Random().nextInt(900000000)
	            );

	            // 4. Insert bank account details
	            PreparedStatement ps1 = con.prepareStatement(bankSql);

	            ps1.setInt(1, userId);
	            ps1.setString(2, accountNumber);
	            ps1.setBigDecimal(3, BigDecimal.ZERO);

	            ps1.executeUpdate();

	            // 5. Save both operations
	            con.commit();

	            return "Registration successful!\nYour Account Number: " + accountNumber;

	        } catch (SQLException e) {

	            con.rollback();
	            return "Registration failed: " + e.getMessage();
	        }

	    } catch (SQLException e) {
	        return "Database error: " + e.getMessage();
	    }
	}

	@Override
	public String login(String email, String password) {

	    String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

	    try (Connection con = Utilities.databaseConnecting();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, email);
	        ps.setString(2, password);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String name = rs.getString("user_name");

	            return "Welcome " + name + "! Login successful.";
	        } else {
	            return "Invalid email or password.";
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "Login failed due to database error.";
	    }
	}

	@Override
	public void deposite(double amount, String acno) {

	    String sql = "UPDATE bankdetails SET amount = amount + ? WHERE acno = ?";

	    try (Connection con = Utilities.databaseConnecting();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setDouble(1, amount);
	        ps.setString(2, acno);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            System.out.println("Amount deposited successfully!");
	        } else {
	            System.out.println("Account not found!");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void withdraw(double amount, String acno) {

	    if (amount <= 0) {
	        System.out.println("Please enter a valid amount");
	        return;
	    }

	    String sql = "UPDATE bankdetails SET amount = amount - ? WHERE acno = ? AND amount >= ?";

	    try (Connection con = Utilities.databaseConnecting();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setDouble(1, amount);
	        ps.setString(2, acno);
	        ps.setDouble(3, amount);

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            System.out.println("Withdrawal successful!");
	        } else {
	            System.out.println("Insufficient balance or account not found!");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public String checkBalance(String acno) {

	    String sql = "SELECT amount FROM bankdetails WHERE acno = ?";

	    try (Connection con = Utilities.databaseConnecting();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, acno);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            BigDecimal balance = rs.getBigDecimal("amount");

	            return "Your account balance is: " + balance;
	        } else {
	            return "Account not found";
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "Unable to check balance";
	    }
	    
	}
}