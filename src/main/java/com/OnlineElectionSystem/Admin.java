package com.OnlineElectionSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Admin {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostMapping("/adminLogin")
	public String adminLogin(HttpServletRequest req) throws SQLException {
		String id = req.getParameter("id");
		String psw = req.getParameter("psw");
		
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from loginId where id=?");
		stmt.setString(1, id);
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			String getPSW = rs.getString("psw");
			if(psw.equals(getPSW))
				return "Admin/adminControls";
		}
		return "Admin/adminLogin";
	}
}
