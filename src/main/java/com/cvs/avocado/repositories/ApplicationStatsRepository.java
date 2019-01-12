package com.cvs.avocado.repositories;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.cvs.avocado.models.AppInfo;
import com.cvs.avocado.models.ClientStats;
import com.cvs.avocado.models.ServerStats;

@Repository
public class ApplicationStatsRepository {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String INSERT_APP_INFO_SP = "insert_app_info_SP";
	private static final String INSERT_SERVER_STATS_SP = "insert_server_stats_SP";
	private static final String INSERT_CLIENT_STATS_SP = "insert_client_stats_SP";
	
	public boolean insertAppInfo(AppInfo appInfo) {
		Map<String, Object> out = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withProcedureName(INSERT_APP_INFO_SP)
					.declareParameters(new SqlOutParameter("result", java.sql.Types.BIT));
			SqlParameterSource in = new MapSqlParameterSource().addValues(this.getFieldsForAppInfo(appInfo));
			out = jdbcCall.execute(in);
		} catch(Exception ex) {
			log.error("Error occured while inserting appinfo. Reason: " + ex.getMessage());
		}
		return (Boolean)out.get("result");
	}

	public boolean insertServerStats(ServerStats serverStats) {
		Map<String, Object> out = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withProcedureName(INSERT_SERVER_STATS_SP)
					.declareParameters(new SqlOutParameter("result", java.sql.Types.BIT));
			SqlParameterSource in = new MapSqlParameterSource().addValues(this.getFieldsForServerStats(serverStats));
			out = jdbcCall.execute(in);

		} catch(Exception ex) {
			log.error("Error occured while inserting client stats. Reason: " + ex.getMessage());
		}
		return (Boolean)out.get("result");
	}

	public boolean insertClientStats(ClientStats clientStats) {
		Map<String, Object> out = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withProcedureName(INSERT_CLIENT_STATS_SP)
					.declareParameters(new SqlOutParameter("result", java.sql.Types.BIT));
			SqlParameterSource in = new MapSqlParameterSource().addValues(this.getFieldsForClientStats(clientStats));
			out = jdbcCall.execute(in);

		} catch(Exception ex) {
			log.error("Error occured while inserting client stats. Reason: " + ex.getMessage());
		}
		return (Boolean)out.get("result");
	}

	private Map<String, Object> getFieldsForAppInfo(AppInfo appInfo) {
		Map<String, Object> appInfoMap = new HashMap<String, Object>(4);
		
		appInfoMap.put("cid_in", appInfo.getCustId());
		appInfoMap.put("did_in", appInfo.getDeptId());
		appInfoMap.put("adpl_app_id_in", appInfo.getAdplAppId());
		appInfoMap.put("server_IP_in", appInfo.getServerIP());
		appInfoMap.put("server_port_in", appInfo.getServerPort());
		appInfoMap.put("protocol_in", appInfo.getServerIP());
		appInfoMap.put("pid_in", appInfo.getPid());
		appInfoMap.put("management_IP_in", appInfo.getManagementIP());
		appInfoMap.put("uuid_in", appInfo.getUuid());
		appInfoMap.put("socket_uuid_in", appInfo.getSocketUUID());

		return appInfoMap;
	}

	private Map<String, Object> getFieldsForServerStats(ServerStats serverStats){
		Map<String, Object> serverStatsMap = new HashMap<String, Object>(16);
		serverStatsMap.put("cust_id_in", serverStats.getCustId());
		serverStatsMap.put("dept_id_in", serverStats.getDeptId());
		serverStatsMap.put("adpl_app_id_in", serverStats.getAdplAppId());
		serverStatsMap.put("pid_in", serverStats.getPid());
		serverStatsMap.put("server_port_in", serverStats.getServerPort());
		serverStatsMap.put("server_ip_in", serverStats.getServerIP());
		serverStatsMap.put("protocol_in", serverStats.getProtocol());
		serverStatsMap.put("uuid_in", serverStats.getUUID());
		serverStatsMap.put("socket_uuid_in", serverStats.getSocketUUID());
		serverStatsMap.put("client_count_in", serverStats.getClientCount());
		serverStatsMap.put("sess_allowed_in",serverStats.getSessAllowed());
		serverStatsMap.put("sess_rejected_in", serverStats.getSessRejected());
		serverStatsMap.put("sess_rej_policies_in", serverStats.getSessRejPolicies());
		serverStatsMap.put("sess_rej_custid_in", serverStats.getSessRejCustId());
		serverStatsMap.put("sess_rej_depid_in", serverStats.getSessRejDeptId());
		serverStatsMap.put("sess_rej_osfails_in", serverStats.getSessRejOSFails());

		return serverStatsMap;

	}

	private Map<String, Object> getFieldsForClientStats(ClientStats clientStats){
		Map<String, Object> clientStatsMap = new HashMap<String, Object>(24);
		clientStatsMap.put("client_port_in", clientStats.getClient_port());
		clientStatsMap.put("client_ip_in",clientStats.getClient_ip());
		clientStatsMap.put("client_uuid_in", clientStats.getClient_uuid());
		clientStatsMap.put("server_uuid_in", clientStats.getServer_uuid());
		clientStatsMap.put("server_socket_uuid_in", clientStats.getServer_socket_uuid());
		clientStatsMap.put("send_count_in", clientStats.getSend_count());
		clientStatsMap.put("recv_count_in", clientStats.getRecv_count());
		clientStatsMap.put("send_bytes_in", clientStats.getSend_bytes());
		clientStatsMap.put("recv_bytes_in", clientStats.getRecv_bytes());
		clientStatsMap.put("pl_allowed_in", clientStats.getPl_allowed());
		clientStatsMap.put("pl_rej_policies_in", clientStats.getPl_rej_policies());
		clientStatsMap.put("pl_rej_custid_in", clientStats.getPl_rej_custid());
		clientStatsMap.put("pl_rej_depid_in", clientStats.getPl_rej_depid());
		clientStatsMap.put("pl_rej_secsig_in", clientStats.getPl_rej_secsig());
		clientStatsMap.put("pl_rej_sqlinj_in", clientStats.getPl_rej_sqlinj());
		clientStatsMap.put("pl_bytes_rej_policies_in", clientStats.getPl_bytes_rej_policies());
		clientStatsMap.put("pl_bytes_rej_custid_in", clientStats.getPl_bytes_rej_custid());
		clientStatsMap.put("pl_bytes_rej_depid_in", clientStats.getPl_bytes_rej_depid());
		clientStatsMap.put("pl_bytes_rej_secsig_in", clientStats.getPl_bytes_rej_secsig());
		clientStatsMap.put("pl_bytes_rej_sqlinj_in", clientStats.getPl_bytes_rej_sqlinj());
		clientStatsMap.put("cust_dept_mismatch_count_in", clientStats.getCust_dept_mismatch_count());
		clientStatsMap.put("sig_mismatch_count_in", clientStats.getSig_mismatch_count());
		clientStatsMap.put("close_reason_in", clientStats.getClose_reason());
		clientStatsMap.put("close_timestamp_in", new Timestamp(clientStats.getClose_timestamp()));

		return clientStatsMap;
	}
}
