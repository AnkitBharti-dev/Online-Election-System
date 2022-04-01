package com.OnlineElectionSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Admin {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/adminLoginPage") 
	public String adminLoginPage() {
		return "Admin/adminLogin";
	}
	@PostMapping("/adminLogin")
	public String adminLogin(HttpServletRequest req) throws SQLException {
		String id = req.getParameter("id");
		String psw = req.getParameter("psw");
		
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from login where id=?");
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			String getPSW = rs.getString("psw");
			if(psw.equals(getPSW))
				return "Admin/adminControls";
		}
		return "Admin/adminLogin";
	}
	
	@GetMapping("/voterApplication")
	public String voterApplication(HttpServletRequest req) throws SQLException {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from signUpRequest");
		ResultSet res = stmt.executeQuery();
		
		while(res.next()) {
			  Map<String,String> s = new HashMap<String,String>();
			  s.put("request_id", res.getString("request_id"));
			  s.put("name", res.getString("Name"));
			  s.put("fathername", res.getString("FatherName"));
			  s.put("email", res.getString("email"));
			  s.put("address", res.getString("address"));
			  list.add(s);
			}
			req.setAttribute("list", list);
			return "Admin/voterApplicationList";
	}
}
