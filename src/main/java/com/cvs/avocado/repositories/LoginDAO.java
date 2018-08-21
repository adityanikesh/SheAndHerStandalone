package com.cvs.avocado.repositories;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cvs.avocado.models.User;


@Repository
public class LoginDAO {

	@Autowired
	DataSource datasource;

	public void saveHello(List<User> userList ) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = this.datasource.getConnection();
			if(con != null) {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement("insert into users (username, password) values (?,?)");
				for(User user: userList) {
					pstmt.setString(1, user.getUsername());
					pstmt.setString(2, user.getPassword());
					pstmt.addBatch();
				}
				pstmt.executeBatch();
				con.commit();
			}

		} catch(BatchUpdateException bue) {
			try {
				con.commit();
				int[] updateCount = bue.getUpdateCounts();
				int index = 0;
				for(int i: updateCount) {
					if(i==Statement.EXECUTE_FAILED) {
						System.out.println("Following user entry failed: " + userList.get(index).getUsername());
					} else {
						System.out.println("Following user entry is successfull: "+ userList.get(index).getUsername());
					}
					index++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		} finally {
			this.resourceClose(con, pstmt);
		}
	}

	private void resourceClose(Connection con, PreparedStatement pstmt) {
		try {
			if(con != null && !con.isClosed()) {
				con.close();
			}
			if(pstmt !=null && !pstmt.isClosed()) {
				pstmt.close();
			}
		} catch(SQLException se) {
			System.out.println(se.getMessage());
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
