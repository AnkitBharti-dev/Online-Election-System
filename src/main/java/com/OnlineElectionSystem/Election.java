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
public class Election {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/showAcceptedCandidateList")
	public String showAcceptedCandidateList(HttpServletRequest req) throws SQLException {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from candidate");
		ResultSet res = stmt.executeQuery();
		
		while(res.next()) {
			  Map<String,String> s = new HashMap<String,String>();
			  s.put("application_no", res.getString("application_no"));
			  s.put("voter_id", res.getString("voter_id"));
			  s.put("name", res.getString("name"));
			  s.put("party_name", res.getString("party_name"));
			  s.put("symbol", res.getString("symbol"));
			  list.add(s);
			}
		req.setAttribute("list", list);
		return "Voter/showAcceptedCandidateList";
	}
	
	@GetMapping("/createElection")
	public String createElection(HttpServletRequest req) throws SQLException {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from candidate");
		ResultSet res = stmt.executeQuery();
		
		while(res.next()) {
			  Map<String,String> s = new HashMap<String,String>();
			  s.put("application_no", res.getString("application_no"));
			  s.put("voter_id", res.getString("voter_id"));
			  s.put("name", res.getString("name"));
			  s.put("party_name", res.getString("party_name"));
			  s.put("symbol", res.getString("symbol"));
			  list.add(s);
			}
		req.setAttribute("list", list);
		
		stmt = con.prepareStatement("update electionActive set active='1'");
		stmt.executeUpdate();
		
		stmt = con.prepareStatement("truncate table vote");
		stmt.executeUpdate();
		return "Admin/electionCreated";
	}
	
	@GetMapping("/declareResult")
	public String declareResult(HttpServletRequest req) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("update electionActive set active=0");
		stmt.executeUpdate();
		
		stmt = con.prepareStatement("select *, count(*) as votes from vote group by registration_no");
		ResultSet res = stmt.executeQuery();
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		while(res.next()) {
			  Map<String,String> s = new HashMap<String,String>();
			  s.put("name", res.getString("name"));
			  s.put("party_name", res.getString("party_name"));
			  s.put("symbol", res.getString("symbol"));
			  s.put("votes", "votes");
			  list.add(s);
			}
		req.setAttribute("list", list);
		
		return "Admin/declareResult";
	}
	
	@PostMapping("/giveVote")
	public String giveVote(HttpServletRequest req) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from vote where voter_id=?");
		stmt.setString(1, req.getParameter("voter_id"));
		ResultSet res = stmt.executeQuery();
		if(res.next()) 
			return "Voter/AlreadyVoted";
		stmt = con.prepareStatement("insert into vote values(?,?,?,?,?)");
		stmt.setString(1, req.getParameter("registration_no"));
		stmt.setString(2, req.getParameter("name"));
		stmt.setString(3, req.getParameter("party_name"));
		stmt.setString(4, req.getParameter("symbol"));
		stmt.setString(5, req.getParameter("voter_id"));
		stmt.executeUpdate();
		return "Voter/sucessfullyVote";
	}
}
