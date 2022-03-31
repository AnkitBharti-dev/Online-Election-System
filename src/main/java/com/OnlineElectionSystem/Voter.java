package com.OnlineElectionSystem;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Voter {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/VoterLoginPage")
	public String voterLoginPage() {
		return "Voter/login";
	}
	
	@GetMapping("/VoterSignUpPage") 
	public String voterSignUpPage() {
		return "Voter/signUp";
	}
	
	@PostMapping("/voterSignUp")
	public String voterSignUp(HttpServletRequest req) throws SQLException {
		
		String request_id = this.generateRequestNo();
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String father_name = req.getParameter("father_name");
		String address = req.getParameter("address");
		
		req.setAttribute("request_id", request_id);
		
		  Connection con = jdbcTemplate.getDataSource().getConnection();
		  CallableStatement stmt = con.prepareCall("call storedRequest(?,?,?,?,?)");
		  stmt.setString(1,request_id); 
		  stmt.setString(2,name);
		  stmt.setString(3,father_name); 
		  stmt.setString(4,email); 
		  stmt.setString(5, address);
		  stmt.executeUpdate();
		 
		return "Voter/request_verification";
	}

	@SuppressWarnings("resource")
	private String generateRequestNo() throws SQLException { 
		  // TODO Auto-generated method stub 
		  String num = Voter.geneateNo(); 
		  Connection con = jdbcTemplate.getDataSource().getConnection(); 
		  String query = "select * from signUpRequest where request_id=?"; 
		  PreparedStatement stmt = con.prepareStatement(query); 
		  stmt.setString(1, num); 
		  ResultSet rs = stmt.executeQuery(); 
		  while(rs.next()) { 
			  num = Voter.geneateNo();
			  stmt.setString(1,num); 
			  rs = stmt.executeQuery(); 
			  } 
		  return num; 
		  }
	  
	  private static String geneateNo() { 
		  // TODO Auto-generated method stub 
		  Random random = new Random(); 
		  String num = ""; 
		  for(int i=1;i<=10;i++) 
			  num += random.nextInt(10); 
		  return num; 
	  } 
}
