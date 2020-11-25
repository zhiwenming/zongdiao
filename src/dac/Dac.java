package dac;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.transaction.support.TransactionTemplate;

import model.UpFile;
import model.Doc;

//import model.A_tuo;
//import model.TuoCount;
//import model.TuoCountThreeMonth;
//import model.TuoTwoyears;





public final class Dac {
	private static Dac instance = null;
	private JdbcTemplate jdbcTemplate = null;
	private TransactionTemplate transactionTemplate = null;

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	private Properties propSQL = new Properties();

	private Dac() {
		String sqlFileName = getClass().getName();
		sqlFileName = sqlFileName.replace('.', '/');
		sqlFileName = "/" + sqlFileName + ".xml";
		//.println(sqlFileName);
//			propSQL.loadFromXML(getClass().getResourceAsStream(sqlFileName));
	}

	public static Dac getInstance() {
		if (instance == null) {
			instance = new Dac();
		}
		return instance;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Properties getPropSQL() {
		return propSQL;
	}
	public Integer testDac() {
//	String sql = propSQL.getProperty("getDifSiteIds");
		String sql ="select 1+1 from dual";
		Integer tietaList =jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println("tietaList:"+tietaList);

	
		return tietaList;
}
	public List<UpFile> getUpfilesByName(String name) {
		String sql = "select * from upfiles where name=?";
		List<UpFile> docList = jdbcTemplate.query(sql, new Object[] { name }, new BeanPropertyRowMapper<UpFile>(UpFile.class));
		if (docList!=null)
			return docList;
		else
			return null;
	}
	public boolean delDoc(String name) {
		String sql ="delete from docs where name = ?";
		try {
			return 1 == jdbcTemplate.update(sql, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public Doc getDoc(String name) {
		String sql = "select * from docs where name = ?";
		List<Doc> docList = jdbcTemplate.query(sql, new Object[] { name }, new BeanPropertyRowMapper<Doc>(Doc.class));
		if (docList.size() >0)
			return docList.get(0);
		else
			return null;
	}
	public boolean delUpFile(String name) {
	String sql = "delete from upfiles where name = ?";
	String commit="commit";

	try {
		jdbcTemplate.update(commit);
		return 1 == jdbcTemplate.update(sql, name);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return false;
}
	public void addDoc(final Doc doc) {
		try {
			jdbcTemplate.execute(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addDoc");
					String sql = "insert into docs(name,fullname,uploadedtime) values(?,?,?)";
					return conn.prepareStatement(sql, new String[] { "id" });
				}
			}, new PreparedStatementCallback<Integer>() {
				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, doc.getName());
					//.println(doc.getName());
					ps.setString(2, doc.getFullName());
					//.println(doc.getFullName());
					ps.setTimestamp(3, doc.getUploadedTime());
					//.println(doc.getUploadedTime());
//					ps.setLong(4, doc.getUserId());
					int ret = ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next())
						doc.setId(rs.getLong(1));
					return ret;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void addUpfiles(final UpFile doc) {
		try {
			jdbcTemplate.execute(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addDoc");
					String sql = "insert into upfiles(name,file,size,type,zhuanye) values(?,?,?,?,?)";
					return conn.prepareStatement(sql, new String[] { "id" });
				}
			}, new PreparedStatementCallback<Integer>() {
				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, doc.getName());
					//.println(doc.getName());
					ps.setString(2, doc.getFile());
					//.println(doc.getFullName());
					ps.setString(3, doc.getSize());
					//.println(doc.getUploadedTime());
					ps.setString(4, doc.getType());
					ps.setString(5, doc.getZhuanye());
					int ret = ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next())
						doc.setId(rs.getLong(1));
					return ret;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<UpFile> getUpfiles(String zhuanye) {
	String sql = "select * from upfiles where zhuanye=?";
	List<UpFile> docList = jdbcTemplate.query(sql, new Object[] { zhuanye }, new BeanPropertyRowMapper<UpFile>(UpFile.class));
	if (docList!=null)
		return docList;
	else
		return null;
}
//	public void addAtuo(A_tuo tieta) {
//	try {
//		jdbcTemplate.execute(new PreparedStatementCreator() {
//			@Override
//			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//				String sql = "insert into atuo (k_a,k_b,k_c,k_d,k_e,k_f,k_g,k_h,k_i,k_j,k_k,k_l,k_m,k_n,k_o) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//				return conn.prepareStatement(sql);
//			}
//		}, new PreparedStatementCallback<Integer>() {
//			@Override
//			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//				// ps.setString(1, tieta.getBill_month());
//				ps.setString(1, tieta.k_a);
//				ps.setString(2, tieta.k_b);
//				ps.setString(3, tieta.k_c);
//				ps.setString(4, tieta.k_d);
//				ps.setString(5, tieta.k_e);
//				ps.setString(6, tieta.k_f);
//				ps.setString(7, tieta.k_g);
//				ps.setString(8, tieta.k_h);
//				ps.setString(9, tieta.k_i);
//				ps.setString(10, tieta.k_j);
//				ps.setString(11, tieta.k_k);
//				ps.setString(12, tieta.k_l);
//				ps.setString(13, tieta.k_m);
//				ps.setString(14, tieta.k_n);
//				ps.setString(15, tieta.k_o);
////				long date = new java.util.Date().getTime();
////				java.sql.Timestamp date2 = new java.sql.Timestamp(date);
//				//.println("date2"+date2);
////				ps.setTimestamp(2, date2);
//				
//				int ret = ps.executeUpdate();
//				return ret;
//			}
//		});
//		String commit="commit";
//		jdbcTemplate.update(commit);
//
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//}
//	public List<A_tuo> getAtuoByatuo(A_tuo atuo) {
//	String sql ="select *  from atuo where k_a =? and k_b =? and k_c =? and k_d =? and k_e =? and k_f =? and k_g =? and k_h =? and k_i =? and k_j =? and k_k =? and k_l =? and k_m =? and k_n =? and k_o =?  ";	
//	List<A_tuo> tietaList = jdbcTemplate.query(sql, new Object[] { atuo.k_a,atuo.k_b,atuo.k_c,atuo.k_d,atuo.k_e,atuo.k_f,atuo.k_g,atuo.k_h,atuo.k_i,atuo.k_j,atuo.k_k,atuo.k_l,atuo.k_m,atuo.k_n,atuo.k_o},	new BeanPropertyRowMapper<A_tuo>(A_tuo.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	
//	public List<A_tuo> getAllAtuos() {
//	String sql ="select *  from atuo  ";	
//	List<A_tuo> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<A_tuo>(A_tuo.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<A_tuo> getAllAtuosDateWrong() {
//	String sql ="select * from atuo where k_d not like '%-%'  ";	
//	List<A_tuo> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<A_tuo>(A_tuo.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<TuoCount> getTuoCountDaiWei() {
//	String sql ="select k_i as name,count(k_i) as counts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_i order by counts desc ";	
//	List<TuoCount> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<TuoCount>(TuoCount.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<TuoCount> getTuoCountReason() {
//	String sql ="select k_j as name,count(k_j) as counts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_j order by counts desc";	
//	List<TuoCount> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<TuoCount>(TuoCount.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<TuoCount> getTuoCountArea() {
//	String sql ="select k_b as name,count(k_b) as counts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_b order by counts desc";	
//	List<TuoCount> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<TuoCount>(TuoCount.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<TuoCount> getTuoCountMonth() {
//	String sql ="select substring(k_d,1,7) as name,count(substring(k_d,1,7)) as counts from (select * from atuo where k_d like concat((select year(now())),'%') )as a group by substring(k_d,1,7)";
//	List<TuoCount> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<TuoCount>(TuoCount.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<TuoCount> getTuoCountThreeTimes() {
//	String sql =" select k_g as name,count(k_g) as counts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_g order by count(a.k_g) desc limit 12";
//	List<TuoCount> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<TuoCount>(TuoCount.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<TuoTwoyears> getTuoCountTwoYear() {
//	String sql ="select ifnull(d.oneName,\"空\") as oneName, ifnull(d.oneCounts ,0)as oneCounts,ifnull(d.twoName,\"空\")as twoName,ifnull(d.twoCounts ,0)as twoCounts from (select c1.name as oneName,c1.counts as oneCounts,c2.name as twoName,c2.counts as twoCounts from (select substring(k_d,1,7) as name,count(substring(k_d,1,7)) as counts from (select * from atuo where k_d like concat((select year(now())),'%') )as a group by substring(k_d,1,7)) c1 left join (select substring(k_d,1,7) as name,count(substring(k_d,1,7)) as counts from (select * from atuo where k_d like concat((select year(now())-1),'%') )as a group by substring(k_d,1,7)) c2 on substring(c1.name,6,2)=substring(c2.name,6,2) union select c1.name as oneName,c1.counts as oneCounts ,c2.name as twoName,c2.counts as twoCounts from (select substring(k_d,1,7) as name,count(substring(k_d,1,7)) as counts from (select * from atuo where k_d like concat((select year(now())),'%') )as a group by substring(k_d,1,7)) c1 right join (select substring(k_d,1,7) as name,count(substring(k_d,1,7)) as counts from (select * from atuo where k_d like concat((select year(now())-1),'%') )as a group by substring(k_d,1,7)) c2 on substring(c1.name,6,2)=substring(c2.name,6,2)) as d";
//	List<TuoTwoyears> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<TuoTwoyears>(TuoTwoyears.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<TuoCountThreeMonth> getTuoCountDaiWeiThreeMonth() {
//	String sql ="select ifnull(threeName,\"空\") as threeName,ifnull(threeCounts,0) as threeCounts,ifnull(twoName,\"空\") as twoName,ifnull(twoCounts,0) as twoCounts,ifnull(oneName,\"空\") as oneName,ifnull(oneCounts,0) as oneCounts from (select * from (select k_i as threeName,count(k_i) as threeCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 3 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 2 month) as date)) a group by a.k_i) as c1 left  join (select k_i as twoName,count(k_i) as twoCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 2 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 1 month) as date)) a group by a.k_i) as c2 on c1.threeName =c2.twoName left join (select k_i as oneName,count(k_i) as oneCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_i) as c3 on c2.twoName=c3.oneName union select * from (select k_i as threeName,count(k_i) as threeCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 3 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 2 month) as date)) a group by a.k_i) as c1 right  join (select k_i as twoName,count(k_i) as twoCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 2 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 1 month) as date)) a group by a.k_i) as c2 on c1.threeName =c2.twoName right join (select k_i as oneName,count(k_i) as oneCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_i) as c3 on c2.twoName=c3.oneName) as a";
//	List<TuoCountThreeMonth> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<TuoCountThreeMonth>(TuoCountThreeMonth.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<TuoCountThreeMonth> getTuoCountReasonThreeMonth() {
//	String sql ="select ifnull(threeName,\"空\") as threeName,ifnull(threeCounts,0) as threeCounts,ifnull(twoName,\"空\") as twoName,ifnull(twoCounts,0) as twoCounts,ifnull(oneName,\"空\") as oneName,ifnull(oneCounts,0) as oneCounts from (select * from (select k_j as threeName,count(k_j) as threeCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 3 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 2 month) as date)) a group by a.k_j) as c1 left  join (select k_j as twoName,count(k_j) as twoCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 2 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 1 month) as date)) a group by a.k_j) as c2 on c1.threeName =c2.twoName left join (select k_j as oneName,count(k_j) as oneCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_j) as c3 on c2.twoName=c3.oneName union select * from (select k_j as threeName,count(k_j) as threeCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 3 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 2 month) as date)) a group by a.k_j) as c1 right  join (select k_j as twoName,count(k_j) as twoCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 2 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 1 month) as date)) a group by a.k_j) as c2 on c1.threeName =c2.twoName right join (select k_j as oneName,count(k_j) as oneCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_j) as c3 on c2.twoName=c3.oneName) as a;";
//	List<TuoCountThreeMonth> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<TuoCountThreeMonth>(TuoCountThreeMonth.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public List<TuoCountThreeMonth> getTuoCountAreaThreeMonth() {
//	String sql ="select ifnull(threeName,\"空\") as threeName,ifnull(threeCounts,0) as threeCounts,ifnull(twoName,\"空\") as twoName,ifnull(twoCounts,0) as twoCounts,ifnull(oneName,\"空\") as oneName,ifnull(oneCounts,0) as oneCounts from (select * from (select k_b as threeName,count(k_b) as threeCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 3 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 2 month) as date)) a group by a.k_b) as c1 left  join (select k_b as twoName,count(k_b) as twoCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 2 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 1 month) as date)) a group by a.k_b) as c2 on c1.threeName =c2.twoName left join (select k_b as oneName,count(k_b) as oneCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_b) as c3 on c2.twoName=c3.oneName union select * from (select k_b as threeName,count(k_b) as threeCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 3 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 2 month) as date)) a group by a.k_b) as c1 right  join (select k_b as twoName,count(k_b) as twoCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 2 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 1 month) as date)) a group by a.k_b) as c2 on c1.threeName =c2.twoName right join (select k_b as oneName,count(k_b) as oneCounts from (select * from atuo where k_d >(select date_sub(date_sub(date_format(now() , '%Y%-%m%-%d'),interval extract(day from now())-1 day),interval 1 month)) and k_d <(select date_sub(date_sub(date_format(now(),'%Y%-%m%-%d'),interval extract(day from now()) day),interval 0 month) as date)) a group by a.k_b) as c3 on c2.twoName=c3.oneName) as a;";
//	List<TuoCountThreeMonth> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<TuoCountThreeMonth>(TuoCountThreeMonth.class));
//
//if (tietaList.size() == 0)
//	return null;
//else
//	return tietaList;
//}
//	public void resetDataBase() {
//	String delst="truncate table atuo";
//	jdbcTemplate.update(delst);
//	String com="commit";
//	jdbcTemplate.update(com);
//}
//	public void trimDataBase() {
//	String delst="update table atuo set k_d=trim(k_d)";
//	jdbcTemplate.update(delst);
//	String com="commit";
//	jdbcTemplate.update(com);
//}
//select * from atuo where k_d not like '%-%';
//	public List<Fileentry> getFileNamesByarea(String k_b) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String sql ="select distinct(k_a) as filename from station_info where area =? and k_a not in (select k_a from old)";	
////			String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
//			List<Fileentry> tietaList = jdbcTemplate.query(sql, new Object[] { k_b},	new BeanPropertyRowMapper<Fileentry>(Fileentry.class));
//		//.println("dac71行");
//		//.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<ExcelModel> getExcelModelByEM(ExcelModel em) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String sql ="select em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j,em.k_k,em.k_l,em.k_m,em.k_n,em.k_o,em.k_p,em.k_q,em.k_r,em.k_s,em.k_t,em.type,em.id from iprandata em where K_A like '%'||?||'%' ";	
////			String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
//			List<ExcelModel> tietaList = jdbcTemplate.query(sql, new Object[] { em.k_a},	new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
//		//.println("dac71行");
//		//.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<ExcelModel> getSdhExcelModelByEM(ExcelModel em) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String sql ="select em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j,em.k_k,em.k_l,em.k_m,em.k_n,em.k_o,em.k_p,em.k_q,em.k_r,em.k_s,em.k_t,em.type from iprandata em where K_A=? and k_b=? and k_c=? and k_d=? and k_e=?  and k_f=? and k_g=? and k_h=? and k_i=? and k_j=? ";	
////			String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
//			List<ExcelModel> tietaList = jdbcTemplate.query(sql, new Object[] { em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j},	new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
//		//.println("dac71行");
//		//.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public ExcelModel getIpranByid(int id) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String sql ="select em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j,em.k_k,em.k_l,em.k_m,em.k_n,em.k_o,em.k_p,em.k_q,em.k_r,em.k_s,em.k_t,em.type,em.id from iprandata em where id =? ";	
////			String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
//			List<ExcelModel> tietaList = jdbcTemplate.query(sql, new Object[] { id},	new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
//		//.println("dac71行");
//		//.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList.get(0);
//	}
//	public ExcelModel getIpranByvcid(String vcid) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String sql ="select em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j,em.k_k,em.k_l,em.k_m,em.k_n,em.k_o,em.k_p,em.k_q,em.k_r,em.k_s,em.k_t,em.type,em.id from iprandata em where k_h =? ";	
////			String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
//			List<ExcelModel> tietaList = jdbcTemplate.query(sql, new Object[] { vcid},	new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
//		//.println("dac71行");
//		//.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList.get(0);
//	}
//	public List<ExcelModel> getIpranByEM(ExcelModel em) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////			String sql ="select em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j,em.k_k,em.k_l,em.k_m,em.k_n,em.k_o,em.k_p,em.k_q,em.k_r,em.k_s,em.k_t,em.type from iprandata em where nvl(K_A,' ') like '%'||?||'%' and nvl(k_b,' ') like '%'||?||'%'  and nvl(k_c,' ') like '%'||?||'%'and nvl(k_d,' ') like '%'||?||'%'and nvl(k_e,' ') like '%'||?||'%'and nvl(k_f,' ') like '%'||?||'%'and nvl(k_g,' ') like '%'||?||'%'and nvl(k_h,' ') like '%'||?||'%'and nvl(k_i,' ') like '%'||?||'%'and nvl(k_j,' ') like '%'||?||'%'and nvl(k_k,' ') like '%'||?||'%'and nvl(k_l,' ') like '%'||?||'%'and nvl(k_m,' ') like '%'||?||'%'and nvl(k_n,' ') like '%'||?||'%'and nvl(k_o,' ') like '%'||?||'%'and nvl(k_p,' ') like '%'||?||'%'and nvl(k_q,' ') like '%'||?||'%'and nvl(k_r,' ') like '%'||?||'%'and nvl(k_s,' ') like '%'||?||'%'and nvl(k_t,' ') like '%'||?||'%' order by id desc";	
//////			String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
////			List<ExcelModel> tietaList = jdbcTemplate.query(sql, new Object[] { em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j,em.k_k,em.k_l,em.k_m,em.k_n,em.k_o,em.k_p,em.k_q,em.k_r,em.k_s,em.k_t},	new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
////		//.println("dac71行");
//		//.println(tietaList.size());
//		String sql ="select em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j,em.k_k,em.k_l,em.k_m,em.k_n,em.k_o,em.k_p,em.k_q,em.k_r,em.k_s,em.k_t,em.type from iprandata em where nvl(K_A,' ') like '%'||?||'%' and (nvl(k_b,' ') like '%'||?||'%'  Or nvl(k_c,' ') like '%'||?||'%' or nvl(k_d,' ') like '%'||?||'%') order by id desc";	
////		String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
//		List<ExcelModel> tietaList = jdbcTemplate.query(sql, new Object[] { em.k_a,em.k_b,em.k_b,em.k_b},	new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
//	
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<ExcelModel> getSdhIpranByEM(ExcelModel em) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String sql ="select em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j,em.k_k,em.k_l,em.k_m,em.k_n,em.k_o,em.k_p,em.k_q,em.k_r,em.k_s,em.k_t,em.type from iprandata em where nvl(K_A,' ') like '%'||?||'%' and nvl(k_b,' ') like '%'||?||'%'  and nvl(k_c,' ') like '%'||?||'%'and nvl(k_d,' ') like '%'||?||'%'and nvl(k_e,' ') like '%'||?||'%'and id = 'sdh' order by id desc";	
////			String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
//			List<ExcelModel> tietaList = jdbcTemplate.query(sql, new Object[] { em.k_a,em.k_b,em.k_c,em.k_d,em.k_e},	new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
//		//.println("dac71行");
//		//.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<ExcelModel> getIpranData() {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String sql ="select em.k_a,em.k_b,em.k_c,em.k_d,em.k_e,em.k_f,em.k_g,em.k_h,em.k_i,em.k_j,em.k_k,em.k_l,em.k_m,em.k_n,em.k_o,em.k_p,em.k_q,em.k_r,em.k_s,em.k_t ,em.type,em.id  from iprandata em where org='新加'";	
////			String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
//			List<ExcelModel> tietaList = jdbcTemplate.query(sql, 	new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
//		//.println("dac71行");
//		//.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Fileentry> getErFileNamesByarea(String k_b) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String sql ="select distinct(k_a) as filename from station_info where area =? ";	
////			String sql ="select distinct(k_a) as filename from stations where k_a ='36573'";	
//			List<Fileentry> tietaList = jdbcTemplate.query(sql, new Object[] { k_b},	new BeanPropertyRowMapper<Fileentry>(Fileentry.class));
//		//.println("dac71行");
//		//.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Fileentry> getFileNamesbyId(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select distinct(k_a) as filename from station_info where k_a=? and k_a not in (select k_a from old) ";
//		List<Fileentry> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Fileentry>(Fileentry.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<ExcelModel> getExcelModels() {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from iprandata ";
//		List<ExcelModel> tietaList = jdbcTemplate.query(sql,new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Fileentry> getErFileNamesbyId(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select distinct(k_a) as filename from station_info where k_a=? ";
//		List<Fileentry> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Fileentry>(Fileentry.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Ke_wang> getKeWangGuan() {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String sql ="select * from ke_wang";	
//		List<Ke_wang> tietaList = jdbcTemplate.query(sql,	new BeanPropertyRowMapper<Ke_wang>(Ke_wang.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Ke_wang> getKeWangbyIds(String k_a) {
////	String sql = propSQL.getProperty("getDifSiteIds");
//		String	sql ="select * from ke_wang where k_a=? ORDER BY K_B";
//	List<Ke_wang> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//			new BeanPropertyRowMapper<Ke_wang>(Ke_wang.class));
//	// //.println(tietaList.size());
//	if (tietaList.size() == 0)
//		return null;
//	else
//		return tietaList;
//}
//	public List<Lu_you> getLuyoubyNames(String nameluyou) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from lu_you where name like '%'||?||'%' ";
//		List<Lu_you> tietaList = jdbcTemplate.query(sql, new Object[] { nameluyou},
//				new BeanPropertyRowMapper<Lu_you>(Lu_you.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Stationinfodata> getStationinfoByid(String s_id) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from stationinfodata where s_id like '%'||?||'%' ";
//		List<Stationinfodata> tietaList = jdbcTemplate.query(sql, new Object[] { s_id},
//				new BeanPropertyRowMapper<Stationinfodata>(Stationinfodata.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Stationinfodata> getStationinfobyNames(String s_name_device) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from stationinfodata where s_name_device like '%'||?||'%' ";
//		List<Stationinfodata> tietaList = jdbcTemplate.query(sql, new Object[] { s_name_device},
//				new BeanPropertyRowMapper<Stationinfodata>(Stationinfodata.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Stationinfodata> getStationinfobyNamesStation(String s_name_station) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from stationinfodata where s_name_station like '%'||?||'%' ";
//		List<Stationinfodata> tietaList = jdbcTemplate.query(sql, new Object[] { s_name_station},
//				new BeanPropertyRowMapper<Stationinfodata>(Stationinfodata.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Ke_wang> getKeWangbyNames(String k_b) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from ke_wang where k_b like '%'||?||'%' ";
//		List<Ke_wang> tietaList = jdbcTemplate.query(sql, new Object[] { k_b},
//				new BeanPropertyRowMapper<Ke_wang>(Ke_wang.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	
//	public List<Stationinfodata> getDevicebyNames(String k_b) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from Stationinfodata where s_name_device like '%'||?||'%' ";
//		List<Stationinfodata> tietaList = jdbcTemplate.query(sql, new Object[] { k_b},
//				new BeanPropertyRowMapper<Stationinfodata>(Stationinfodata.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Ban_ka> getBankabyNames(String k_b) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from ban_ka where k_c like '%'||?||'%' ";
//		List<Ban_ka> tietaList = jdbcTemplate.query(sql, new Object[] { k_b},
//				new BeanPropertyRowMapper<Ban_ka>(Ban_ka.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Dan_lian> getDanLianbyIds(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from Dan_lian where k_name=? ";
//		List<Dan_lian> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Dan_lian>(Dan_lian.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Cdma> getCdmabyIds(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from cdma where k_a=? ORDER BY K_B";
//		List<Cdma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Cdma>(Cdma.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Rru> getRrubyIds(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from rru where k_a=? ORDER BY K_B";
//		List<Rru> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Rru>(Rru.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Bbu> getBbubyIds(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//			String	sql ="select * from bbu where k_a=? ORDER BY K_d";
//		List<Bbu> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Bbu>(Bbu.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//
//	public List<Ban_ka> getBankabyIds(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac102 行");
//			String	sql ="select * from ban_ka where k_a=? order by k_c";
//		List<Ban_ka> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Ban_ka>(Ban_ka.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//
//	public List<Stations> getStationbyIds(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//		//.println(k_a+"dac116行");
//			String	sql ="select *  from stations where k_a=? and k_a not in (select k_a from old) ";
//		List<Stations> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Stations>(Stations.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Stations> getErStationbyIds(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//		//.println(k_a+"dac116行");
////			String	sql ="select k_a,  from stations where k_a=? and k_a in (select k_a from station_info) ";
//		String sql="select s.k_a ,si.area as k_b ,s.k_bj,s.k_e,s.k_q from stations s join station_info si on s.k_a=si.k_a and s.k_a=?";
//		List<Stations> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Stations>(Stations.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Stations> getStations() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from stations  ";
//		List<Stations> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Stations>(Stations.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Stations> getStations_b() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from stations_b  ";
//		List<Stations> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Stations>(Stations.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Stations> getStations_bbyid(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from stations_b where k_a=? ";
//		List<Stations> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Stations>(Stations.class));
//		 //.println("tietaList:"+tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Station_Yin> getStations_yin() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from station_yin_b where k_k not like '%无隐患%'";
//		List<Station_Yin> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Station_Yin>(Station_Yin.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Power_Yin> getPower_yin() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from power_yin_b  where k_q is null";
//		List<Power_Yin> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Power_Yin>(Power_Yin.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Device_Yin> getDevice_yin() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from Device_Yin_b where  k_n is null ";
//		List<Device_Yin> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Device_Yin>(Device_Yin.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Ban_ka> getBankas() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from ban_ka_b  ";
//		List<Ban_ka> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Ban_ka>(Ban_ka.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Bbu> getBbus() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from Bbu_b  ";
//		List<Bbu> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Bbu>(Bbu.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Cdma> getCdmals() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from Cdma_b  ";
//		List<Cdma> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Cdma>(Cdma.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Ke_wang> getkwls() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from Ke_wang_b  ";
//		List<Ke_wang> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Ke_wang>(Ke_wang.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<No_ke_wang> getnkls() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from No_ke_wang_b  ";
//		List<No_ke_wang> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<No_ke_wang>(No_ke_wang.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Power> getPls() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from Power_b  ";
//		List<Power> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Power>(Power.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Rru> getRuls() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from Rru_b  ";
//		List<Rru> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Rru>(Rru.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Zhi_fang> getZls() {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(k_a+"dac116行");
//			String	sql ="select *  from Zhi_fang_b  ";
//		List<Zhi_fang> tietaList = jdbcTemplate.query(sql, 
//				new BeanPropertyRowMapper<Zhi_fang>(Zhi_fang.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Power> getPowerbyIds(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//		//.println(k_a+"dac116行");
//			String	sql ="select *  from power where k_a=?  ";
//		List<Power> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Power>(Power.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Station_info> getStationInfobyIds(String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//		//.println(k_a+"dac129行");
//			String	sql ="select k_a  from station_info where k_a=? and k_a not in (select k_a from old)";
//		List<Station_info> tietaList = jdbcTemplate.query(sql, new Object[] { k_a},
//				new BeanPropertyRowMapper<Station_info>(Station_info.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Files> getFilesbyPaths(String name,String filepath) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(name+"dac129行");
//			String	sql ="select filepath  from files where name=? and filepath like '%'||?||'%'";
//		List<Files> tietaList = jdbcTemplate.query(sql, new Object[] { name,filepath},
//				new BeanPropertyRowMapper<Files>(Files.class));
//		 //.println(tietaList);
////		//.println(tietaList+"dac129行");
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<String> getDirsbyPaths(String name) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(name+"dac129行");
//			String	sql ="select filepath  from files where name=? and filepath not like '%.%'";
//		List<Files> tietaList = jdbcTemplate.query(sql, new Object[] { name},
//				new BeanPropertyRowMapper<Files>(Files.class));
////		//.println(tietaList);
//		List<String> filepaths=new ArrayList<String>();
//		if (tietaList.size() == 0)
//			return null;
//		else
//		{
//			for(Files f:tietaList) {
//				filepaths.add(f.filepath);
//			}
//			return filepaths;
//		}
////			return tietaList;
//	}
//	public List<Files> getFilesbyPath(String name,String k_a,String k_b,String k_c,String k_d,String k_e,String k_f,String k_g,String k_h,String k_i,String k_j) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//		//.println(k_a+"dac129行");
//			String	sql ="select filepath  from files where name=? and filepath like '%'||?||'%' and filepath not like '%'||?||'%'and filepath not like '%'||?||'%'and filepath not like '%'||?||'%'and filepath not like '%'||?||'%'and filepath not like '%'||?||'%'and filepath not like '%'||?||'%'and filepath not like '%'||?||'%'and filepath not like '%'||?||'%'and filepath not like '%'||?||'%'" ;
//		List<Files> tietaList = jdbcTemplate.query(sql, new Object[] { name,k_a,k_b,k_c,k_d,k_e,k_f,k_g,k_h,k_i,k_j},
//				new BeanPropertyRowMapper<Files>(Files.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<String> getFilesPathbyPaths(String name) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//		//.println(k_a+"dac129行");
//			String	sql ="select filepath  from files where name=? ";
//		List<String> tietaList = jdbcTemplate.query(sql, new Object[] { name},
//				new BeanPropertyRowMapper<String>(String.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<ExcelModel> getExcelModelbyem(String vcid) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//		//.println(k_a+"dac129行");
//			String	sql ="select k_a,k_b,k_c,k_d,k_e,k_f,k_g,k_h,k_i,k_j,k_k,k_l,k_m,k_n,k_o,k_p,k_q,k_r ,type from iprandata where k_h=? ";
////		String	sql ="select *  from iprandata where k_h=? ";
//		List<ExcelModel> tietaList = jdbcTemplate.query(sql, new Object[] { vcid},new BeanPropertyRowMapper<ExcelModel>(ExcelModel.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<BiaoQian> getBiaoQianbyem(String vcid) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(vcid+"dac129行");
//			String	sql ="select *  from biaoqian where k_g=? and k_g is not null";
//		List<BiaoQian> tietaList = jdbcTemplate.query(sql, new Object[] { vcid},
//				new BeanPropertyRowMapper<BiaoQian>(BiaoQian.class));
////		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Exam> getExambyem(String vcid) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(vcid+"dac129行");
//			String	sql ="select *  from exams where k_c=? ";
//		List<Exam> tietaList = jdbcTemplate.query(sql, new Object[] { vcid},
//				new BeanPropertyRowMapper<Exam>(Exam.class));
////		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Exam> getExamsDanxuan(String zhuanye) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(vcid+"dac129行");
//			String	sql ="select *  from exam_b where k_a='单选' and k_b=? ";
//		List<Exam> tietaList = jdbcTemplate.query(sql, new Object[] { zhuanye},
//				new BeanPropertyRowMapper<Exam>(Exam.class));
////		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Exam> getExamsDuoxuan(String zhuanye) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(vcid+"dac129行");
//			String	sql ="select *  from exam_b where k_a='多选' and k_b=? ";
//		List<Exam> tietaList = jdbcTemplate.query(sql, new Object[] { zhuanye},
//				new BeanPropertyRowMapper<Exam>(Exam.class));
////		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public List<Exam> getExamsDanFromExams( String zhuanye) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(vcid+"dac129行");
//			String	sql ="select * from (select * from exams where k_a='单选' and k_b=? order by dbms_random.value()) where rownum <20";
//		List<Exam> tietaList = jdbcTemplate.query(sql, new Object[] { zhuanye},
//				new BeanPropertyRowMapper<Exam>(Exam.class));
////		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	
//	public List<Exam> getExamsDuoFromExams( String zhuanye) {
////		String sql = propSQL.getProperty("getDifSiteIds");
////		//.println(vcid+"dac129行");
//			String	sql ="select * from (select * from exams where k_a='多选' and k_b=? order by dbms_random.value()) where rownum <20";
//		List<Exam> tietaList = jdbcTemplate.query(sql, new Object[] { zhuanye},
//				new BeanPropertyRowMapper<Exam>(Exam.class));
////		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	
//	public List<BiaoQian> getTestBiaoQianbyem(String vcid,String k_a) {
////		String sql = propSQL.getProperty("getDifSiteIds");
//		//.println(k_a+"dac129行");
//			String	sql ="select *  from testbiaoqian where k_g=? and k_a=?";
//		List<BiaoQian> tietaList = jdbcTemplate.query(sql, new Object[] { vcid,k_a},
//				new BeanPropertyRowMapper<BiaoQian>(BiaoQian.class));
//		 //.println(tietaList);
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	}
//	public void addFiles(Files tieta) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into files values(?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, tieta.name);
//					ps.setString(2, tieta.filepath);
////					long date = new java.util.Date().getTime();
////					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
//					//.println("date2"+date2);
////					ps.setTimestamp(2, date2);
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//			String commit="commit";
//			jdbcTemplate.update(commit);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void addIdhistory(Fileentry tieta) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into id_history values(?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, tieta.filename);
//					long date = new java.util.Date().getTime();
//					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
//					//.println("date2"+date2);
//					ps.setTimestamp(2, date2);
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void delOld_ErrStation(String k_a) {
//		String delst="delete from errstation where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	public void delBiaoQian(String area) {
//		String delst="delete from biaoqian where area=? and to_char(today,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')";
//		jdbcTemplate.update(delst, area);
//	}
//	public void delExam_bDanXuan(String zhuanye) {
//		String delst="delete from exam_b where k_b=? and k_a='单选'";
//		jdbcTemplate.update(delst, zhuanye);
//	}
//	public void delExam_bDuoXuan(String zhuanye) {
//		String delst="delete from exam_b where k_b=? and k_a='多选'";
//		jdbcTemplate.update(delst, zhuanye);
//	}
//	public void delTestBiaoQian(String area) {
//		String delst="delete from testbiaoqian where area=? and to_char(today,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')";
//		jdbcTemplate.update(delst, area);
//	}
//	public void delBiaoQianById(String id) {
//		String delst="delete from biaoqian where id=? ";
//		jdbcTemplate.update(delst, id);
//	}
//	public void delTestBiaoQianById(String id) {
//		String delst="delete from testbiaoqian where id=? ";
//		jdbcTemplate.update(delst, id);
//	}
//	public void delPath(String name,String path) {
//		String delst="delete from files where name=? and filepath=?";
//		jdbcTemplate.update(delst, name,path);
//		String commit="commit";
//		jdbcTemplate.update(commit);
//	}
//	
//	
//	public void delIpran(String dels) {
//		String delst="delete from iprandata where id=? ";
//		jdbcTemplate.update(delst, dels);
//		String commit="commit";
//		jdbcTemplate.update(commit);
//	}
//	public void addErrStation(String tieta) {
//		
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into errstation values(?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, tieta);
////					long date = new java.util.Date().getTime();
////					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
////					//.println("date2"+date2);
////					ps.setTimestamp(2, date2);
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//			
//			String commit="commit";
//			jdbcTemplate.update(commit);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void addChangeKa(Change_ka changeka) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into change_ka values(?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, changeka.k_b);
//					ps.setString(2, changeka.new_ka);
//					ps.setString(3, changeka.old_ka);
////					long date = new java.util.Date().getTime();
////					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
////					//.println("date2"+date2);
////					ps.setTimestamp(2, date2);
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void delOld_Station(String k_a) {
//		String delst="delete from stations_b where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	
//	public void addIpranData(ExcelModel st,String org) {
////		String delst="delete from stations_b where k_a=?";
////		jdbcTemplate.update(delst, st);
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into iprandata values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval,?,?,?)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					ps.setString(15, st.k_o);
//					ps.setString(16, st.k_p);
//					ps.setString(17, st.k_q);
//					ps.setString(18, st.k_r);
//					ps.setString(19, st.k_s);
//					ps.setString(20, st.k_t);
//					ps.setString(21, org);
//					long date = new java.util.Date().getTime();
//					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
//					//.println("date2"+date2);
//					ps.setTimestamp(22, date2);
//					ps.setString(23, st.type);
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void addBiaoQian(BiaoQian st) {
////		String delst="delete from stations_b where k_a=?";
////		jdbcTemplate.update(delst, st);
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into biaoqian values(?,?,?,?,?,?,?,?,ase.nextval,?,?,?)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					
//					ps.setString(9, st.area);
//					long date = new java.util.Date().getTime();
//					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
//					//.println("date2"+date2);
//					ps.setTimestamp(10, date2);
//					ps.setString(11, st.k_i);
//					
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void addExam(Exam st) {
////		String delst="delete from stations_b where k_a=?";
////		jdbcTemplate.update(delst, st);
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into exams (k_a,k_b,k_c,k_d,k_e,k_f,k_g,k_h,k_i,id) values(?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);		
//					ps.setString(9, st.k_i);
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void addExam_b(Exam st) {
////		String delst="delete from stations_b where k_a=?";
////		jdbcTemplate.update(delst, st);
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into exam_b (k_a,k_b,k_c,k_d,k_e,k_f,k_g,k_h,k_i,id) values(?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);		
//					ps.setString(9, st.k_i);
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void addTestBiaoQian(BiaoQian st) {
////		String delst="delete from stations_b where k_a=?";
////		jdbcTemplate.update(delst, st);
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into testbiaoqian values(?,?,?,?,?,?,?,?,ase.nextval,?,?,?)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);		
//					ps.setString(9, st.area);
//					long date = new java.util.Date().getTime();
//					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
//					//.println("date2"+date2);
//					ps.setTimestamp(10, date2);
//					ps.setString(11, st.k_i);
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void addStation(Stations st) {
////		String delst="delete from stations_b where k_a=?";
////		jdbcTemplate.update(delst, st);
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into stations_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					ps.setString(15, st.k_o);
//					ps.setString(16, st.k_p);
//					ps.setString(17, st.k_q);
//					ps.setString(18, st.k_r);
//					ps.setString(19, st.k_s);
//					ps.setString(20, st.k_t);
//					ps.setString(21, st.k_u);
//					ps.setString(22, st.k_v);
//					ps.setString(23, st.k_w);
//					ps.setString(24, st.k_x);
//					ps.setString(25, st.k_y);
//					ps.setString(26, st.k_z);
//					ps.setString(27, st.k_aa);
//					ps.setString(28, st.k_ab);
//					ps.setString(29, st.k_ac);
//					ps.setString(30, st.k_ad);
//					ps.setString(31, st.k_ae);
//					ps.setString(32, st.k_af);
//					ps.setString(33, st.k_ag);
//					ps.setString(34, st.k_ah);
//					ps.setString(35, st.k_ai);
//					ps.setString(36, st.k_aj);
//					ps.setString(37, st.k_ak);
//					ps.setString(38, st.k_al);
//					ps.setString(39, st.k_am);
//					ps.setString(40, st.k_an);
//					ps.setString(41, st.k_ao);
//					ps.setString(42, st.k_ap);
//					ps.setString(43, st.k_aq);
//					ps.setString(44, st.k_ar);
//					ps.setString(45, st.k_as);
//					ps.setString(46, st.k_at);
//					ps.setString(47, st.k_au);
//					ps.setString(48, st.k_av);
//					ps.setString(49, st.k_aw);
//					ps.setString(50, st.k_ax);
//					ps.setString(51, st.k_ay);
//					ps.setString(52, st.k_az);
//					ps.setString(53, st.k_ba);
//					ps.setString(54, st.k_bb);
//					ps.setString(55, st.k_bc);
//					ps.setString(56, st.k_bd);
//					ps.setString(57, st.k_be);
//					ps.setString(58, st.k_bf);
//					ps.setString(59, st.k_bg);
//					ps.setString(60, st.k_bh);
////					ps.setString(61, st.bi);
//					ps.setString(61, st.k_bj);
//
////					ps.setString(2, st.k_b);
////					ps.setString(3, st.k_c);
////					long date = new java.util.Date().getTime();
////					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
////					//.println("date2"+date2);
////					ps.setTimestamp(2, date2);
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void delOld_StationYin(String k_a) {
//		String delst="delete from station_yin_b where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	public void addStationYin(Station_Yin st) {
//		
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into station_yin_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					ps.setString(15, st.k_o);
//					ps.setString(16, st.k_p);
//					ps.setString(17, st.k_q);
//					ps.setString(18, st.k_r);
//					ps.setString(19, st.k_s);
//					ps.setString(20, st.k_t);
//					ps.setString(21, st.k_u);
//					ps.setString(22, st.k_v);
//					ps.setString(23, st.k_w);
//					ps.setString(24, st.k_x);
//					ps.setString(25, st.k_y);
//					ps.setString(26, st.k_z);
//					ps.setString(27, st.k_aa);
//					ps.setString(28, st.k_ab);
//					ps.setString(29, st.k_ac);
//					ps.setString(30, st.k_ad);
//					ps.setString(31, st.k_ae);
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void delOld_PowerYin(String k_a) {
//		String delst="delete from power_yin_b where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	public void addPowerYin(Power_Yin st) {
//		
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into power_yin_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					ps.setString(15, st.k_o);
//					ps.setString(16, st.k_p);
//					ps.setString(17, st.k_q);
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void delOld_DeviceYin(String k_a) {
//		String delst="delete from device_yin_b where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	public void addDeviceYin(Device_Yin st) {
//		
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into device_yin_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void delOld_zhifang(String k_a) {
//		String delst="delete from zhi_fang_b where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	public void addZhifang(Zhi_fang st) {
//	
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into zhi_fang_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					ps.setString(15, st.k_o);
//					ps.setString(16, st.k_p);
//					
//
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void delOld_rru(String k_a) {
//		String delst="delete from rru_b where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	public void addRru(Rru st) {
//		
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into rru_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					ps.setString(15, st.k_o);
//					ps.setString(16, st.k_p);
//					ps.setString(17, st.k_q);
//					ps.setString(18, st.k_r);
//					ps.setString(19, st.k_s);
//					ps.setString(20, st.k_t);
//					ps.setString(21, st.k_u);
//					ps.setString(22, st.k_v);
//					ps.setString(23, st.k_w);
//					ps.setString(24, st.k_x);
//					ps.setString(25, st.k_y);
//					ps.setString(26, st.k_z);
//					ps.setString(27, st.k_aa);
//					ps.setString(28, st.k_ab);
//					ps.setString(29, st.k_ac);
//					
//
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void delOld_banka(String k_a) {
//		String delst="delete from ban_ka_b where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	public void addBanka(Ban_ka st) {
//		
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into ban_ka_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					ps.setString(15, st.k_o);
//					ps.setString(16, st.k_p);
//					ps.setString(17, st.k_q);
//					ps.setString(18, st.k_r);
//					ps.setString(19, st.k_s);
//					
//					
//
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void delOld_jijia(String k_a) {
//		String delst="delete from ji_jia_b where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	
//	public void addJiJia(Ji_jia st) {
////		String delst="delete from ji_jia_b where k_a=?";
////		jdbcTemplate.update(delst, st.k_a);
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into ji_jia_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					ps.setString(15, st.k_o);
//					ps.setString(16, st.k_p);
//					ps.setString(17, st.k_q);
//					ps.setString(18, st.k_r);
//					
//					
//					
//
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void delOld_KeWang(String k_a) {
//		String delst="delete from ke_wang_b where k_a=?";
//		jdbcTemplate.update(delst, k_a);
//	}
//	public void addKeWang(Ke_wang st) {
//		
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = "insert into ke_wang_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//					return conn.prepareStatement(sql, new String[] { "id"});
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, st.k_a);
//					ps.setString(2, st.k_b);
//					ps.setString(3, st.k_c);
//					ps.setString(4, st.k_d);
//					ps.setString(5, st.k_e);
//					ps.setString(6, st.k_f);
//					ps.setString(7, st.k_g);
//					ps.setString(8, st.k_h);
//					ps.setString(9, st.k_i);
//					ps.setString(10, st.k_j);
//					ps.setString(11, st.k_k);
//					ps.setString(12, st.k_l);
//					ps.setString(13, st.k_m);
//					ps.setString(14, st.k_n);
//					ps.setString(15, st.k_o);
//					ps.setString(16, st.k_p);
//					ps.setString(17, st.k_q);
//					ps.setString(18, st.k_r);
//					ps.setString(19, st.k_s);
//					ps.setString(20, st.k_t);
//					ps.setString(21, st.k_u);
//					ps.setString(22, st.k_v);
//					ps.setString(23, st.k_w);
//					ps.setString(24, st.k_x);
//					ps.setString(25, st.k_y);
//					ps.setString(26, st.k_z);
//					ps.setString(27, st.k_aa);
//					ps.setString(28, st.k_ab);
//					ps.setString(29, st.k_ac);
//					ps.setString(30, st.k_ad);
//					ps.setString(31, st.k_ae);
//					ps.setString(32, st.k_af);
//					ps.setString(33, st.k_ag);
//					ps.setString(34, st.k_ah);
//					ps.setString(35, st.k_ai);
//					ps.setString(36, st.k_aj);
//	
//		
//
////					ps.setString(2, st.k_b);
////					ps.setString(3, st.k_c);
////					long date = new java.util.Date().getTime();
////					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
////					//.println("date2"+date2);
////					ps.setTimestamp(2, date2);
//					
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public boolean Updatekid(String k_a,String k_b) {
//		
////	String sql = propSQL.getProperty("delUserByAccount");
//		String sql="update ke_wang set k_a=? where k_b =?";
//	return 1 == jdbcTemplate.update(sql, k_a,k_b);
//}
//	public boolean UpdateIpranData(int id) {
//		
////		String sql = propSQL.getProperty("delUserByAccount");
//			String sql="update iprandata set org='更新' where id =?";
//		return 1 == jdbcTemplate.update(sql, id);
//	}
//	public boolean BackupIpran(int id) {
//		
////		String sql = propSQL.getProperty("delUserByAccount");
//			String sql="insert into ipranbackup select * from iprandata where id=?";
//		return 1 == jdbcTemplate.update(sql,id);
//	}
//	public boolean UpdateIpran(String k_a,String k_b,String k_c,String k_d,String k_e,String k_f,String k_g,String k_h,String k_i,String k_j,String k_k,String k_l,String k_m,String k_n,String k_o,String k_p,String k_q,String k_r,String k_s,String k_t,int id) {
//		
////		String sql = propSQL.getProperty("delUserByAccount");
//			String sql="update iprandata set k_a=? , k_b=? , k_c=?, k_d=?, k_e=?, k_f=?, k_g=?, k_h=?, k_i=?, k_j=?, k_k=?, k_l=?, k_m=?, k_n=?, k_o=?, k_p=?, k_q=?, k_r=?, k_s=?, k_t=? where id =?";
//		return 1 == jdbcTemplate.update(sql, k_a,k_b,k_c,k_d,k_e,k_f,k_g,k_h,k_i,k_j,k_k,k_l,k_m,k_n,k_o,k_p,k_q,k_r,k_s,k_t,id);
//	}
//	public boolean DelRepeat() {
////		String sql = propSQL.getProperty("delUserByAccount");
//		List<String> sqllist=new ArrayList<String>();
//			String delstation="delete from stations_b where k_a||k_k||k_i||k_j in (select k_a||k_k||k_i||k_j from stations_b group by k_a||k_k||k_i||k_j )and rowid not  in (select min(rowid)from stations_b group by k_a||k_k||k_i||k_j)";
//			sqllist.add(delstation);
//			String delbanka="delete from ban_ka_b where k_a||k_f||k_d||k_e in (select k_a||k_f||k_d||k_e from ban_ka_b group by k_a||k_f||k_d||k_e )and rowid not  in (select min(rowid)from ban_ka_b group by k_a||k_f||k_d||k_e)";
//			String delbbu="delete from bbu_b where k_a||k_l||k_m||k_d in (select k_a||k_l||k_m||k_d from bbu_b group by k_a||k_l||k_m||k_d )and rowid not  in (select min(rowid)from bbu_b group by k_a||k_l||k_m||k_d)";
//			String delcdma="delete from cdma_b where k_a||k_n||k_m||k_o in (select k_a||k_n||k_m||k_o from cdma_b group by k_a||k_n||k_m||k_o )and rowid not  in (select min(rowid)from cdma_b group by k_a||k_n||k_m||k_o)";
//			String delkewang="delete from ke_wang_b where k_a||k_b in (select k_a||k_b from ke_wang_b group by k_a||k_b )and rowid not  in (select min(rowid)from ke_wang_b group by k_a||k_b)";
//			String delnokewang="delete from no_ke_wang_b where k_a||k_b||k_e||k_f in (select k_a||k_b||k_e||k_f from no_ke_wang_b group by k_a||k_b||k_e||k_f )and rowid not  in (select min(rowid)from no_ke_wang_b group by k_a||k_b||k_e||k_f)";
//			String delpower="delete from power_b where k_a||k_b||k_f in (select k_a||k_b||k_f from power_b group by k_a||k_b||k_f )and rowid not  in (select min(rowid)from power_b group by k_a||k_b||k_f)";
//			String delrru="delete from rru_b where k_a||k_h in (select k_a||k_h from rru_b group by k_a||k_h )and rowid not  in (select min(rowid)from rru_b group by k_a||k_h)";
//			String delzhifang="delete from zhi_fang_b where k_a||k_c||k_f in (select k_a||k_c||k_f from zhi_fang_b group by k_a||k_c||k_f )and rowid not  in (select min(rowid)from zhi_fang_b group by k_a||k_c||k_f)";
//			String comm="commit";
//			sqllist.add(delbanka);
//			sqllist.add(delbbu);
//			sqllist.add(delcdma);
//			sqllist.add(delkewang);
//			sqllist.add(delnokewang);
//			sqllist.add(delpower);
//			sqllist.add(delrru);
//			sqllist.add(delzhifang);
//			sqllist.add(comm);
//			for(String sql:sqllist) {
//				jdbcTemplate.update(sql);
//			}
//			
//		return 1==1; 
//	}
//	public boolean Updatebankid(String k_a,String k_b) {
////		String sql = propSQL.getProperty("delUserByAccount");
//			String sql="update ban_ka set k_a=? where k_c =?";
//		return 1 == jdbcTemplate.update(sql, k_a,k_b);
//	}
//	
//	
//		public boolean commit() {
//			String sql="commit";
//			return 1==jdbcTemplate.update(sql);
//		}
//		public void delOld_Bbu(String k_a) {
//			String delst="delete from bbu_b where k_a=?";
//			jdbcTemplate.update(delst, k_a);
//		}
//		public void addBbu(Bbu st) {
//			
//			try {
//				jdbcTemplate.execute(new PreparedStatementCreator() {
//					@Override
//					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//						String sql = "insert into bbu_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//						return conn.prepareStatement(sql, new String[] { "id"});
//					}
//				}, new PreparedStatementCallback<Integer>() {
//					@Override
//					public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//						// ps.setString(1, tieta.getBill_month());
//						ps.setString(1, st.k_a);
//						ps.setString(2, st.k_b);
//						ps.setString(3, st.k_c);
//						ps.setString(4, st.k_d);
//						ps.setString(5, st.k_e);
//						ps.setString(6, st.k_f);
//						ps.setString(7, st.k_g);
//						ps.setString(8, st.k_h);
//						ps.setString(9, st.k_i);
//						ps.setString(10, st.k_j);
//						ps.setString(11, st.k_k);
//						ps.setString(12, st.k_l);
//						ps.setString(13, st.k_m);
//						ps.setString(14, st.k_n);
//						ps.setString(15, st.k_o);
//						ps.setString(16, st.k_p);
//						ps.setString(17, st.k_q);
//						ps.setString(18, st.k_r);
//						ps.setString(19, st.k_s);
//						ps.setString(20, st.k_t);
//						ps.setString(21, st.k_u);
//						ps.setString(22, st.k_v);
//						ps.setString(23, st.k_w);
//						ps.setString(24, st.k_x);
//						ps.setString(25, st.k_y);
//						ps.setString(26, st.k_z);
//						
//
//
//						
//						int ret = ps.executeUpdate();
//						return ret;
//					}
//				});
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		public void delOld_Cdma(String k_a) {
//			String delst="delete from cdma_b where k_a=?";
//			jdbcTemplate.update(delst, k_a);
//		}
//		public void addExcelModel(ExcelModel st,String org) {
//			//.println(st);
//			try {
//				jdbcTemplate.execute(new PreparedStatementCreator() {
//					@Override
//					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//						String sql = "insert into iprandata values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval,?,?)";
////						String sql = "insert into iprandata (k_a,id)values(?,ase.nextval)";
//						
//						return conn.prepareStatement(sql, new String[] { "id"});
//					}
//				}, new PreparedStatementCallback<Integer>() {
//					@Override
//					public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//						// ps.setString(1, tieta.getBill_month());
//						ps.setString(1, st.k_a);
//						ps.setString(2, st.k_b);
//						ps.setString(3, st.k_c);
//						ps.setString(4, st.k_d);
//						ps.setString(5, st.k_e);
//						ps.setString(6, st.k_f);
//						ps.setString(7, st.k_g);
//						ps.setString(8, st.k_h);
//						ps.setString(9, st.k_i);
//						ps.setString(10, st.k_j);
//						ps.setString(11, st.k_k);
//						ps.setString(12, st.k_l);
//						ps.setString(13, st.k_m);
//						ps.setString(14, st.k_n);
//						ps.setString(15, st.k_o);
//						ps.setString(16, st.k_p);
//						ps.setString(17, st.k_q);
//						ps.setString(18, st.k_r);
//						ps.setString(19, st.k_s);
//						ps.setString(20, st.k_t);
//						ps.setString(21, org);
//						long date = new java.util.Date().getTime();
//						java.sql.Timestamp date2 = new java.sql.Timestamp(date);
//						//.println("date2"+date2);
//						ps.setTimestamp(22, date2);
//						ps.setString(23, st.type);
//						
//						Integer ret = ps.executeUpdate();
//						return ret;
//					}
//				});
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
////			String commit="coommit";
////			jdbcTemplate.execute(commit);
//		}
//						
//		public void addCdma(Cdma st) {
//			
//			try {
//				jdbcTemplate.execute(new PreparedStatementCreator() {
//					@Override
//					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//						String sql = "insert into cdma_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//						return conn.prepareStatement(sql, new String[] { "id"});
//					}
//				}, new PreparedStatementCallback<Integer>() {
//					@Override
//					public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//						// ps.setString(1, tieta.getBill_month());
//						ps.setString(1, st.k_a);
//						ps.setString(2, st.k_b);
//						ps.setString(3, st.k_c);
//						ps.setString(4, st.k_d);
//						ps.setString(5, st.k_e);
//						ps.setString(6, st.k_f);
//						ps.setString(7, st.k_g);
//						ps.setString(8, st.k_h);
//						ps.setString(9, st.k_i);
//						ps.setString(10, st.k_j);
//						ps.setString(11, st.k_k);
//						ps.setString(12, st.k_l);
//						ps.setString(13, st.k_m);
//						ps.setString(14, st.k_n);
//						ps.setString(15, st.k_o);
//						ps.setString(16, st.k_p);
//						ps.setString(17, st.k_q);
//						ps.setString(18, st.k_r);
//						ps.setString(19, st.k_s);
//						ps.setString(20, st.k_t);
//						ps.setString(21, st.k_u);
//						ps.setString(22, st.k_v);
//						ps.setString(23, st.k_w);
//						ps.setString(24, st.k_x);
//						ps.setString(25, st.k_y);
//						ps.setString(26, st.k_z);
//						ps.setString(27, st.k_aa);
//						ps.setString(28, st.k_ab);
//						ps.setString(29, st.k_ac);
//						ps.setString(30, st.k_ad);
//
//
//						
//						int ret = ps.executeUpdate();
//						return ret;
//					}
//				});
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		public void delOld_NoKeWang(String k_a) {
//			String delst="delete from no_ke_wang_b where k_a=?";
//			jdbcTemplate.update(delst, k_a);
//		}
//		public void addNoKeWang(No_ke_wang st) {
//			
//			try {
//				jdbcTemplate.execute(new PreparedStatementCreator() {
//					@Override
//					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//						String sql = "insert into No_ke_wang_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//						return conn.prepareStatement(sql, new String[] { "id"});
//					}
//				}, new PreparedStatementCallback<Integer>() {
//					@Override
//					public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//						// ps.setString(1, tieta.getBill_month());
//						ps.setString(1, st.k_a);
//						ps.setString(2, st.k_b);
//						ps.setString(3, st.k_c);
//						ps.setString(4, st.k_d);
//						ps.setString(5, st.k_e);
//						ps.setString(6, st.k_f);
//						ps.setString(7, st.k_g);
//						ps.setString(8, st.k_h);
//						ps.setString(9, st.k_i);
//						ps.setString(10, st.k_j);
//						ps.setString(11, st.k_k);
//						ps.setString(12, st.k_l);
//						ps.setString(13, st.k_m);
//						ps.setString(14, st.k_n);
//						ps.setString(15, st.k_o);
//						ps.setString(16, st.k_p);
//						ps.setString(17, st.k_q);
//						ps.setString(18, st.k_r);
//						ps.setString(19, st.k_s);
//						ps.setString(20, st.k_t);
//						ps.setString(21, st.k_u);
//						ps.setString(22, st.k_v);
//						
//						
//
//
//						
//						int ret = ps.executeUpdate();
//						return ret;
//					}
//				});
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		public void delOld_Power(String k_a) {
//			String delst="delete from power_b where k_a=?";
//			jdbcTemplate.update(delst, k_a);
//		}
//		public void addPower(Power st) {
//			
//			try {
//				jdbcTemplate.execute(new PreparedStatementCreator() {
//					@Override
//					public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//						String sql = "insert into power_b values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,ase.nextval)";
//						return conn.prepareStatement(sql, new String[] { "id"});
//					}
//				}, new PreparedStatementCallback<Integer>() {
//					@Override
//					public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//						// ps.setString(1, tieta.getBill_month());
//						ps.setString(1, st.k_a);
//						ps.setString(2, st.k_b);
//						ps.setString(3, st.k_c);
//						ps.setString(4, st.k_d);
//						ps.setString(5, st.k_e);
//						ps.setString(6, st.k_f);
//						ps.setString(7, st.k_g);
//						ps.setString(8, st.k_h);
//						ps.setString(9, st.k_i);
//						ps.setString(10, st.k_j);
//						ps.setString(11, st.k_k);
//						ps.setString(12, st.k_l);
//						ps.setString(13, st.k_m);
//						ps.setString(14, st.k_n);
//						ps.setString(15, st.k_o);
//						ps.setString(16, st.k_p);
//						ps.setString(17, st.k_q);
//						ps.setString(18, st.k_r);
//						ps.setString(19, st.k_s);
//						ps.setString(20, st.k_t);
//						ps.setString(21, st.k_u);
//						ps.setString(22, st.k_v);
//						ps.setString(23, st.k_w);
//						ps.setString(24, st.k_x);
//						ps.setString(25, st.k_y);
//						ps.setString(26, st.k_z);
//						
//
//
//						
//						int ret = ps.executeUpdate();
//						return ret;
//					}
//				});
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		public List<Er_wei_ma> getErs(String k_a) {
//		String sql = "select * from (select k_a,'机房' as k_b,k_e as k_c,'' as k_d,' ' as k_e,' 'as k_f,'' as k_h from stations_b        union  (select k_a,'设备' as k_b,k_b as k_c,k_k as k_d,k_g as k_e,k_h as k_f,k_d as k_h from ke_wang_b) union(select k_a ,'机架' as k_b,k_b as k_c,k_e as k_d,k_d as k_e,'' as k_f,'' as k_g from ji_jia_b) order by k_e,k_c,k_f  )where k_a=? ";
//		List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//				new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));
//	
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//	
//	}
//		public List<Er_wei_ma> getErStaion(String k_a) {
//		String sql = "select k_a,'机房' as k_b,k_e as k_c,'' as k_d,' ' as k_e,' 'as k_f,'' as k_h from stations_b where k_a=? ";
//		List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//				new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));	
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;	
//	}
//		public List<Er_wei_ma> getErKeWang(String k_a) {
//			String sql = "select k_a,'设备' as k_b,k_b as k_c,k_k as k_d,k_g as k_e,k_h as k_f,k_d as k_h from ke_wang_b where k_a=? ";
//			List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//					new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<Er_wei_ma> getErJiJia(String k_a) {
//			String sql = "select k_a ,'机架' as k_b,k_b as k_c,k_e as k_d,k_d as k_e,'' as k_f,'' as k_g from ji_jia_b where k_a=? ";
//			List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//					new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<Er_wei_ma> getErNoKeWang(String k_a) {
//			String sql = "select k_a,'非网管' as k_b,k_b as k_c,k_j as k_d,k_e,k_f,'' as k_h from no_ke_wang_b where k_a=? ";
//			List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//					new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<Er_wei_ma> getErPower(String k_a) {
//			String sql = "select k_a,'动环' as k_b,k_b as k_c,k_j as k_d,k_h as k_e,'' as k_f,k_g as k_h from power_b where k_a=? ";
//			List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//					new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<Er_wei_ma> getErBbu(String k_a) {
//			String sql = "select k_a ,'BBU'as k_b,k_c ,k_k as k_d,k_d as k_e,k_e as k_f,'' as k_h from bbu_b where k_a=? ";
//			List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//					new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<Er_wei_ma> getErCdma(String k_a) {
//			String sql = "select k_a,'CDMA'as k_b,k_m as k_c,k_n as k_d,k_i as k_e,k_j as k_f,'' as k_h from cdma_b where k_a=? ";
//			List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//					new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<Er_wei_ma> getErRru(String k_a) {
//			String sql = "select k_a,'RRU'as k_b,k_h as k_c,k_k as k_d,'' as k_e,'' as k_f,'' as k_h from rru_b where k_a=? ";
//			List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//					new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<Er_wei_ma> getErZhiFang(String k_a) {
//			String sql = "select k_a,'直放'as k_b,k_c ,'' as k_d,'' as k_e,'' as k_f,'' as k_g from zhi_fang_b where k_a=? ";
//			List<Er_wei_ma> tietaList = jdbcTemplate.query(sql, new Object[] { k_a },
//					new BeanPropertyRowMapper<Er_wei_ma>(Er_wei_ma.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<BiaoQianOut> getBiaoQianOut() {
////			String sql = "select area,(select count(k_g) from biaoqian where to_char(today,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')) as todaynum,count(k_g) as sumnum from biaoqian group by area ";
////			String sql="select a.area,b.todaynum,a.sumnum from (select area,count(k_g) as sumnum from biaoqian group by area) a left join ( select area, count(k_g) as todaynum from biaoqian group by area,to_char(today,'yyyy-mm-dd') having to_char(today,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd'))b on a.area=b.area";
////			String sql="select a.area,b.todaynum,a.sumnum,a.dhidnum from (select area,count(k_g) as sumnum ,count(distinct(k_a)) as dhidnum from biaoqian group by area) a left join ( select area, count(k_g) as todaynum from biaoqian group by area,to_char(today,'yyyy-mm-dd') having to_char(today,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd'))b on a.area=b.area order by a.sumnum desc";
////			String sql="select a.area,nvl(b.todaynum,0) as todaynum,nvl(a.sumnum,0) as sumnum,nvl(a.dhidnum,0) as dhidnum from (select area,count(k_g) as sumnum ,count(distinct(k_a)) as dhidnum from biaoqian group by area) a left join ( select area, count(k_g) as todaynum from biaoqian group by area,to_char(today,'yyyy-mm-dd') having to_char(today,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd'))b on a.area=b.area order by a.sumnum desc";
//			String sql="select a.area,nvl(b.todaynum,0) as todaynum,nvl(a.sumnum,0) as sumnum,nvl(a.dhidnum,0) as dhidnum,nvl(c.errnum,0) as errnum from (select area,count(k_g) as sumnum ,count(distinct(k_a)) as dhidnum from biaoqian group by area) a left join ( select area, count(k_g) as todaynum from biaoqian group by area,to_char(today,'yyyy-mm-dd') having to_char(today,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd'))b on a.area=b.area left join (select count(k_g)as errnum,areaa from (select a.area as areaa,a.k_g,b.area as areab from testbiaoqian a ,testbiaoqian b where a.k_g=b.k_g and a.k_a<>b.k_a)group by areaa union (select count(k_g)as errnum,areaa from (select a.area as areaa,a.k_g from testbiaoqian a where length(a.k_g)<>12)group by areaa))c on a.area=c.areaa order by a.sumnum desc";
//			
//			List<BiaoQianOut> tietaList = jdbcTemplate.query(sql,
//					new BeanPropertyRowMapper<BiaoQianOut>(BiaoQianOut.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<ErrBiaoQianInfo> getErrBiaoQianInfo( String area) {
////		"select a.area,nvl(b.todaynum,0) as todaynum,nvl(a.sumnum,0) as sumnum,nvl(a.dhidnum,0) as dhidnum,nvl(c.errnum,0) as errnum from (select area,count(k_g) as sumnum ,count(distinct(k_a)) as dhidnum from biaoqian group by area) a left join ( select area, count(k_g) as todaynum from biaoqian group by area,to_char(today,'yyyy-mm-dd') having to_char(today,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd'))b on a.area=b.area left join (select count(k_g)as errnum,areaa from (select a.area as areaa,a.k_g,b.area as areab from testbiaoqian a ,testbiaoqian b where a.k_g=b.k_g and a.k_a<>b.k_a)group by areaa)c on a.area=c.areaa order by a.sumnum desc";
////			String sql="select a.area as areaa,a.id as eid,a.k_a as k_a,b.k_a as k_b,a.k_g as erweima,b.area as areab,nvl(a.k_i,0) as k_ai,nvl(b.k_i,0) as k_bi from testbiaoqian a ,testbiaoqian b where a.k_g=b.k_g and a.k_a<>b.k_a and a.area=?";
//			
//			String sql="select a.area as areaa,a.id as eid,a.k_a as k_a,b.k_a as k_b,a.k_g as erweima,b.area as areab,nvl(a.k_i,0) as k_ai,nvl(b.k_i,0) as k_bi from testbiaoqian a ,testbiaoqian b where a.k_g=b.k_g and a.k_a<>b.k_a and a.area=? union select a.area as areaa,a.id as eid,a.k_a as k_a,'二维码长度异常' as k_b,a.k_g as erweima, '空' as areab,nvl(a.k_i,0) as k_ai ,'空' as k_bi from testbiaoqian a where length(a.k_G)<>12 and a.area=?";
//			
//			List<ErrBiaoQianInfo> tietaList = jdbcTemplate.query(sql,new Object[] { area,area},
//					new BeanPropertyRowMapper<ErrBiaoQianInfo>(ErrBiaoQianInfo.class));	
//			if (tietaList.size() == 0)
//				return null;
//			else
//				return tietaList;	
//		}
//		public List<ErrBiaoQianInfo> getErrBiaoQianNull( String area) {
////			"select a.area,nvl(b.todaynum,0) as todaynum,nvl(a.sumnum,0) as sumnum,nvl(a.dhidnum,0) as dhidnum,nvl(c.errnum,0) as errnum from (select area,count(k_g) as sumnum ,count(distinct(k_a)) as dhidnum from biaoqian group by area) a left join ( select area, count(k_g) as todaynum from biaoqian group by area,to_char(today,'yyyy-mm-dd') having to_char(today,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd'))b on a.area=b.area left join (select count(k_g)as errnum,areaa from (select a.area as areaa,a.k_g,b.area as areab from testbiaoqian a ,testbiaoqian b where a.k_g=b.k_g and a.k_a<>b.k_a)group by areaa)c on a.area=c.areaa order by a.sumnum desc";
//				String sql="select a.area as areaa,a.id as eid,a.k_a as k_a, nvl(a.k_i,0) as k_ai  from biaoqian a where k_g is null and area=?";
//				List<ErrBiaoQianInfo> tietaList = jdbcTemplate.query(sql,new Object[] { area},
//						new BeanPropertyRowMapper<ErrBiaoQianInfo>(ErrBiaoQianInfo.class));	
//				if (tietaList.size() == 0)
//					return null;
//				else
//					return tietaList;	
//			}
//	public List<Tieta> getDifSiteNames(String bill_month, String bill_month2) {
//		String sql = propSQL.getProperty("getDifSiteNames");
//		List<Tieta> tietaList = jdbcTemplate.query(sql, new Object[] { bill_month, bill_month2 },
//				new BeanPropertyRowMapper<Tieta>(Tieta.class));
//		// //.println(tietaList.size());
//		if (tietaList.size() == 0)
//			return null;
//		else
//			return tietaList;
//		// return jdbcTemplate.query(sql, new
//		// BeanPropertyRowMapper<Tieta>(Tieta.class));
//		// return jdbcTemplate.query(sql, new Object[] { bill_month }, new
//		// BeanPropertyRowMapper<Tieta>(Tieta.class));
//	}
//
//	public List<Tieta> getDifSiteIds(String bill_month, String bill_month2) {
//	String sql = propSQL.getProperty("getDifSiteIds");
//	List<Tieta> tietaList = jdbcTemplate.query(sql, new Object[] { bill_month, bill_month2 },
//			new BeanPropertyRowMapper<Tieta>(Tieta.class));
//	// //.println(tietaList.size());
//	if (tietaList.size() == 0)
//		return null;
//	else
//		return tietaList;
//}
//
//	public void addTieta(Tieta tieta) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addTieta");
//					return conn.prepareStatement(sql, new String[] { "BILL_MONTH" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, tieta.getBill_month());
//					ps.setString(2, tieta.getProduct_confim_id());
//					ps.setString(3, tieta.getIsp());
//					ps.setString(4, tieta.getIsp_city());
//					ps.setString(5, tieta.getDemand_city());
//					ps.setString(6, tieta.getSite_location_city());
//					ps.setString(7, tieta.getSite_name());
//					ps.setString(8, tieta.getSite_id());
//					ps.setString(9, tieta.getDemand_confim_id());
//					ps.setString(10, tieta.getProduct_property());
//					ps.setString(11, tieta.getService_begin());
//					ps.setString(12, tieta.getProduct_type());
//					ps.setString(13, tieta.getSite_type());
//					ps.setString(14, tieta.getPower_model());
//					ps.setDouble(15, tieta.getPower_cost_init());
//					ps.setDouble(16, tieta.getPower_cost_change());
//					ps.setDouble(17, tieta.getPower_cost_all());
//					ps.setDouble(18, tieta.getPower_cost_back());
//					ps.setDouble(19, tieta.getPower_cost_exceed_init());
//					ps.setDouble(20, tieta.getPower_cost_exceed_change());
//					ps.setDouble(21, tieta.getPower_cost_exceed_all());
//					ps.setDouble(22, tieta.getPower_cost_exceed_back());
//					ps.setDouble(23, tieta.getBattery_cost_init());
//					ps.setDouble(24, tieta.getBattery_cost_change());
//					ps.setDouble(25, tieta.getBattery_cost_all());
//					ps.setDouble(26, tieta.getBattery_cost_back());
//					ps.setString(27, tieta.getProduct_num());
//					ps.setString(28, tieta.getAntenna_highest());
//					ps.setString(29, tieta.getBbu_in_station());
//					ps.setString(30, tieta.getOther_discount());
//					ps.setDouble(31, tieta.getBase_price_for_tieta());
//					ps.setString(32, tieta.getProduct_num2());
//					ps.setString(33, tieta.getAntenna_highest2());
//					ps.setString(34, tieta.getBbu_in_station2());
//					ps.setString(35, tieta.getOther_discount2());
//					ps.setDouble(36, tieta.getBase_price2_for_tieta());
//					ps.setString(37, tieta.getProduct_num3());
//					ps.setString(38, tieta.getAntenna_highest3());
//					ps.setString(39, tieta.getBbu_in_station3());
//					ps.setString(40, tieta.getOther_discount3());
//					ps.setDouble(41, tieta.getBase_price3_for_tieta());
//					ps.setDouble(42, tieta.getUsers_for_tieta());
//					ps.setString(43, tieta.getShare_cost_begin_of_tieta());
//					ps.setDouble(44, tieta.getShare_cost_discount_of_tieta());
//					ps.setString(45, tieta.getHistory_begin_of_tieta());
//					ps.setDouble(46, tieta.getHistory_discount_of_tieta());
//					ps.setDouble(47, tieta.getBase_price_init_of_tieta());
//					ps.setDouble(48, tieta.getAfter_shared_base_price_change());
//					ps.setDouble(49, tieta.getAfter_shared_base_price_all());
//					ps.setDouble(50, tieta.getAfter_shared_base_price_back());
//					ps.setDouble(51, tieta.getBase_price_for_station());
//					ps.setDouble(52, tieta.getBase_price2_for_station());
//					ps.setDouble(53, tieta.getBase_price3_for_station());
//					ps.setDouble(54, tieta.getUsers_for_station());
//					ps.setString(55, tieta.getShare_cost_begin_of_station());
//					ps.setDouble(56, tieta.getDiscount_of_station());
//					ps.setString(57, tieta.getHistory_begin_of_station());
//					ps.setDouble(58, tieta.getHistory_discount_of_station());
//					ps.setDouble(59, tieta.getBase_price_init_of_station());
//					ps.setDouble(60, tieta.getBase_price_change_of_station());
//					ps.setDouble(61, tieta.getBase_price_all_of_station());
//					ps.setDouble(62, tieta.getBase_price_back_of_station());
//					ps.setDouble(63, tieta.getBase_price_for_mating());
//					ps.setDouble(64, tieta.getBase_price2_for_mating());
//					ps.setDouble(65, tieta.getBase_price3_for_mating());
//					ps.setDouble(66, tieta.getUsers_for_mating());
//					ps.setString(67, tieta.getBegin_of_mating());
//					ps.setDouble(68, tieta.getDiscount_of_mating());
//					ps.setString(69, tieta.getHistory_begin_of_mating());
//					ps.setDouble(70, tieta.getHistory_discount_of_mating());
//					ps.setDouble(71, tieta.getBase_price_init_of_mating());
//					ps.setDouble(72, tieta.getBase_price_change_of_mating());
//					ps.setDouble(73, tieta.getBase_price_all_of_mating());
//					ps.setDouble(74, tieta.getBase_price_back_of_mating());
//					ps.setDouble(75, tieta.getBbu_cost_init());
//					ps.setDouble(76, tieta.getBbu_cost_change());
//					ps.setDouble(77, tieta.getBbu_cost_all());
//					ps.setDouble(78, tieta.getBbu_cost_back());
//					ps.setDouble(79, tieta.getCost_for_maintain());
//					ps.setDouble(80, tieta.getCost_for_maintain2());
//					ps.setDouble(81, tieta.getCost_for_maintain3());
//					ps.setDouble(82, tieta.getUsers_for_maintain());
//					ps.setString(83, tieta.getShare_cost_begin_of_maintain());
//					ps.setDouble(84, tieta.getDiscount_of_maintain());
//					ps.setString(85, tieta.getHistory_begin_of_maintain());
//					ps.setDouble(86, tieta.getHistory_discount_of_maintain());
//					ps.setDouble(87, tieta.getCost_init_of_maintain());
//					ps.setDouble(88, tieta.getCost_change_of_maintain());
//					ps.setDouble(89, tieta.getCost_all_of_maintain());
//					ps.setDouble(90, tieta.getCost_back_of_maintain());
//					ps.setDouble(91, tieta.getCost_for_place());
//					ps.setDouble(92, tieta.getUsers_for_place());
//					ps.setString(93, tieta.getShare_cost_begin_of_place());
//					ps.setDouble(94, tieta.getDiscount_of_place());
//					ps.setString(95, tieta.getHistory_begin_of_place());
//					ps.setDouble(96, tieta.getHistory_discount_of_place());
//					ps.setDouble(97, tieta.getCost_init_of_place());
//					ps.setDouble(98, tieta.getCost_change_of_place());
//					ps.setDouble(99, tieta.getCost_all_of_place());
//					ps.setDouble(100, tieta.getCost_back_of_place());
//					ps.setDouble(101, tieta.getCost_for_power());
//					ps.setDouble(102, tieta.getUsers_for_power());
//					ps.setString(103, tieta.getBegin_of_power());
//					ps.setDouble(104, tieta.getDiscount_of_power());
//					ps.setString(105, tieta.getHistory_begin_of_power());
//					ps.setDouble(106, tieta.getHistory_discount_of_power());
//					ps.setDouble(107, tieta.getCost_init_of_power());
//					ps.setDouble(108, tieta.getCost_change_of_power());
//					ps.setDouble(109, tieta.getCost_all_of_power());
//					ps.setDouble(110, tieta.getCost_back_of_power());
//					ps.setDouble(111, tieta.getCost_for_wlan());
//					ps.setDouble(112, tieta.getCost_change_of_wlan());
//					ps.setDouble(113, tieta.getCost_all_of_wlan());
//					ps.setDouble(114, tieta.getCost_back_of_wlan());
//					ps.setDouble(115, tieta.getCost_for_microwave());
//					ps.setDouble(116, tieta.getCost_change_of_microwave());
//					ps.setDouble(117, tieta.getCost_all_of_microwave());
//					ps.setDouble(118, tieta.getCost_back_of_microwave());
//					ps.setDouble(119, tieta.getCost_for_others());
//					ps.setDouble(120, tieta.getCost_change_of_others());
//					ps.setDouble(121, tieta.getCost_all_of_others());
//					ps.setDouble(122, tieta.getCost_back_of_others());
//					ps.setDouble(123, tieta.getCost_for_service());
//					ps.setDouble(124, tieta.getCost_change_of_service());
//					ps.setDouble(125, tieta.getCost_all_of_service());
//					ps.setDouble(126, tieta.getCost_back_of_service());
//					ps.setString(127, tieta.getChange_of_cost_service());
//					ps.setString(128, tieta.getSate_confirm());
//					ps.setString(129, tieta.getProject_change_cost());
//					ps.setDouble(130, tieta.getCost_of_change_project());
//					ps.setDouble(131, tieta.getCost_of_after_change_project());
//					ps.setString(132, tieta.getReason_of_project_change());
//					ps.setDouble(133, tieta.getCost_variation_changed());
//					ps.setDouble(134, tieta.getCost_of_give());
//					ps.setDouble(135, tieta.getCost_of_power());
//					ps.setDouble(136, tieta.getCost_of_change_of_power());
//					ps.setDouble(137, tieta.getCost_of_all_of_power());
//					ps.setDouble(138, tieta.getCost_of_back_of_power());
//					ps.setDouble(139, tieta.getCost_of_oil_power());
//					ps.setDouble(140, tieta.getCost_of_change_of_oil_power());
//					ps.setDouble(141, tieta.getCost_of_all_of_oil_power());
//					ps.setDouble(142, tieta.getCost_of_back_of_oil_power());
//					ps.setString(143, tieta.getIsp_districts());
//					ps.setString(144, tieta.getOrder_property());
//					ps.setString(145, tieta.getOwnership_property());
//					ps.setString(146, tieta.getOwnership_init());
//					ps.setString(147, tieta.getSite_id() + "" + tieta.getBill_month());
//					// ps.setString(148, tieta.getSite_id_and_month());
//					// ps.setInt(147, tieta.getId());
//					// ps.setString(148, tieta.getState());
//					// ps.setString(149, tieta.getReason_of_exception());
//					// ps.setDouble(150, tieta.getAdjust_cost());
//					// ps.setString(148, tieta.getLongitude());
//					// ps.setString(149, tieta.getLatitude());
//					int ret = ps.executeUpdate();
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public boolean updateTieta(TietaPart tietaPart) {
//		String sql = propSQL.getProperty("updateTieta");
//
//		return 1 == jdbcTemplate.update(sql,
//
//				tietaPart.getBill_month(), tietaPart.getProduct_confim_id(), tietaPart.getIsp_city(),
//				tietaPart.getSite_name(), tietaPart.getSite_id(), tietaPart.getAfter_shared_base_price_all(),
//				tietaPart.getBase_price_all_of_station(), tietaPart.getBase_price_all_of_mating(),
//				tietaPart.getCost_all_of_maintain(), tietaPart.getCost_all_of_place(), tietaPart.getCost_all_of_power(),
//				tietaPart.getCost_all_of_service(), tietaPart.getCost_variation_changed(), tietaPart.getIsp_districts(),
//				tietaPart.getOwnership_init(), tietaPart.getReason_of_exception(), tietaPart.getAdjust_cost(),
//				tietaPart.getId());
//
//	}
//
//	public boolean updateTietaALL(Tieta tieta) {
//		String sql = propSQL.getProperty("updateTietaAll");
//
//		return 1 == jdbcTemplate.update(sql,
//
//				tieta.getBill_month(), tieta.getProduct_confim_id(), tieta.getIsp(), tieta.getIsp_city(),
//				tieta.getDemand_city(), tieta.getSite_location_city(), tieta.getSite_name(), tieta.getSite_id(),
//				tieta.getDemand_confim_id(), tieta.getProduct_property(), tieta.getService_begin(),
//				tieta.getProduct_type(), tieta.getSite_type(), tieta.getPower_model(), tieta.getPower_cost_init(),
//				tieta.getPower_cost_change(), tieta.getPower_cost_all(), tieta.getPower_cost_back(),
//				tieta.getPower_cost_exceed_init(), tieta.getPower_cost_exceed_change(),
//				tieta.getPower_cost_exceed_all(), tieta.getPower_cost_exceed_back(), tieta.getBattery_cost_init(),
//				tieta.getBattery_cost_change(), tieta.getBattery_cost_all(), tieta.getBattery_cost_back(),
//				tieta.getProduct_num(), tieta.getAntenna_highest(), tieta.getBbu_in_station(),
//				tieta.getOther_discount(), tieta.getBase_price_for_tieta(), tieta.getProduct_num2(),
//				tieta.getAntenna_highest2(), tieta.getBbu_in_station2(), tieta.getOther_discount2(),
//				tieta.getBase_price2_for_tieta(), tieta.getProduct_num3(), tieta.getAntenna_highest3(),
//				tieta.getBbu_in_station3(), tieta.getOther_discount3(), tieta.getBase_price3_for_tieta(),
//				tieta.getUsers_for_tieta(), tieta.getShare_cost_begin_of_tieta(),
//				tieta.getShare_cost_discount_of_tieta(), tieta.getHistory_begin_of_tieta(),
//				tieta.getHistory_discount_of_tieta(), tieta.getBase_price_init_of_tieta(),
//				tieta.getAfter_shared_base_price_change(), tieta.getAfter_shared_base_price_all(),
//				tieta.getAfter_shared_base_price_back(), tieta.getBase_price_for_station(),
//				tieta.getBase_price2_for_station(), tieta.getBase_price3_for_station(), tieta.getUsers_for_station(),
//				tieta.getShare_cost_begin_of_station(), tieta.getDiscount_of_station(),
//				tieta.getHistory_begin_of_station(), tieta.getHistory_discount_of_station(),
//				tieta.getBase_price_init_of_station(), tieta.getBase_price_change_of_station(),
//				tieta.getBase_price_all_of_station(), tieta.getBase_price_back_of_station(),
//				tieta.getBase_price_for_mating(), tieta.getBase_price2_for_mating(), tieta.getBase_price3_for_mating(),
//				tieta.getUsers_for_mating(), tieta.getBegin_of_mating(), tieta.getDiscount_of_mating(),
//				tieta.getHistory_begin_of_mating(), tieta.getHistory_discount_of_mating(),
//				tieta.getBase_price_init_of_mating(), tieta.getBase_price_change_of_mating(),
//				tieta.getBase_price_all_of_mating(), tieta.getBase_price_back_of_mating(), tieta.getBbu_cost_init(),
//				tieta.getBbu_cost_change(), tieta.getBbu_cost_all(), tieta.getBbu_cost_back(),
//				tieta.getCost_for_maintain(), tieta.getCost_for_maintain2(), tieta.getCost_for_maintain3(),
//				tieta.getUsers_for_maintain(), tieta.getShare_cost_begin_of_maintain(), tieta.getDiscount_of_maintain(),
//				tieta.getHistory_begin_of_maintain(), tieta.getHistory_discount_of_maintain(),
//				tieta.getCost_init_of_maintain(), tieta.getCost_change_of_maintain(), tieta.getCost_all_of_maintain(),
//				tieta.getCost_back_of_maintain(), tieta.getCost_for_place(), tieta.getUsers_for_place(),
//				tieta.getShare_cost_begin_of_place(), tieta.getDiscount_of_place(), tieta.getHistory_begin_of_place(),
//				tieta.getHistory_discount_of_place(), tieta.getCost_init_of_place(), tieta.getCost_change_of_place(),
//				tieta.getCost_all_of_place(), tieta.getCost_back_of_place(), tieta.getCost_for_power(),
//				tieta.getUsers_for_power(), tieta.getBegin_of_power(), tieta.getDiscount_of_power(),
//				tieta.getHistory_begin_of_power(), tieta.getHistory_discount_of_power(), tieta.getCost_init_of_power(),
//				tieta.getCost_change_of_power(), tieta.getCost_all_of_power(), tieta.getCost_back_of_power(),
//				tieta.getCost_for_wlan(), tieta.getCost_change_of_wlan(), tieta.getCost_all_of_wlan(),
//				tieta.getCost_back_of_wlan(), tieta.getCost_for_microwave(), tieta.getCost_change_of_microwave(),
//				tieta.getCost_all_of_microwave(), tieta.getCost_back_of_microwave(), tieta.getCost_for_others(),
//				tieta.getCost_change_of_others(), tieta.getCost_all_of_others(), tieta.getCost_back_of_others(),
//				tieta.getCost_for_service(), tieta.getCost_change_of_service(), tieta.getCost_all_of_service(),
//				tieta.getCost_back_of_service(), tieta.getChange_of_cost_service(), tieta.getSate_confirm(),
//				tieta.getProject_change_cost(), tieta.getCost_of_change_project(),
//				tieta.getCost_of_after_change_project(), tieta.getReason_of_project_change(),
//				tieta.getCost_variation_changed(), tieta.getCost_of_give(), tieta.getCost_of_power(),
//				tieta.getCost_of_change_of_power(), tieta.getCost_of_all_of_power(), tieta.getCost_of_back_of_power(),
//				tieta.getCost_of_oil_power(), tieta.getCost_of_change_of_oil_power(),
//				tieta.getCost_of_all_of_oil_power(), tieta.getCost_of_back_of_oil_power(), tieta.getIsp_districts(),
//				tieta.getOrder_property(), tieta.getOwnership_property(), tieta.getOwnership_init(),
//				tieta.getSite_id() + "" + tieta.getBill_month()
//		// tieta.getSite_id_and_month()
//		// tieta.getSite_id(),+""+tieta.getBill_month()
//
//		);
//
//	}
//
//	public List<Tieta> allTietas() {
//		String sql = propSQL.getProperty("allTietas");
//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Tieta>(Tieta.class));
//	}
//
//	public List<LiuLiang> allLiuLiangs() {
//		String sql = propSQL.getProperty("allLiuLiangs");
//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<LiuLiang>(LiuLiang.class));
//	}
//
//	public List<Electric> allElectrics() {
//		String sql = "select * from electric";
//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Electric>(Electric.class));
//	}
//
//	public void addElectric(Electric electric) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addElectric");
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, electric.getSite_id());
//					ps.setString(2, electric.getCity());
//					ps.setString(3, electric.getDistrict());
//					ps.setString(4, electric.getSite_name());
//					ps.setString(5, electric.getSite_address());
//					ps.setDouble(6, electric.getLongitude());
//					ps.setDouble(7, electric.getLatitude());
//					ps.setString(8, electric.getIspself_id());
//					ps.setString(9, electric.getPower_supply_sytle());
//					ps.setString(10, electric.getAccount_style());
//					ps.setString(11, electric.getElectric_meter_id());
//					ps.setString(12, electric.getOrder_no());
//					java.util.Date date7 = new java.util.Date();
//					date7 = electric.getBill_month();
//					java.sql.Date date8 = new java.sql.Date(date7.getTime());
//					ps.setDate(13, date8);
//					java.util.Date date = new java.util.Date();
//					date = electric.getMeter_reading();
//					java.sql.Date date2 = new java.sql.Date(date.getTime());
//					ps.setDate(14, date2);
//					java.util.Date date3 = new java.util.Date();
//					date3 = electric.getMeter_reading_last();
//					java.sql.Date date4 = new java.sql.Date(date3.getTime());
//					ps.setDate(15, date4);
//					ps.setDouble(16, electric.getMeter_reading_num());
//					ps.setDouble(17, electric.getMeter_reading_num_last());
//					ps.setDouble(18, electric.getWatt());
//					ps.setDouble(19, electric.getElectric_price());
//					ps.setString(20, electric.getElectric_loss_cost());
//					ps.setDouble(21, electric.getAdjust_cost());
//					ps.setDouble(22, electric.getFees());
//
//					java.util.Date date5 = new java.util.Date();
//					date5 = electric.getPay_date();
//					java.sql.Date date6 = new java.sql.Date(date.getTime());
//					ps.setDate(23, date6);
//
//					ps.setString(24, electric.getNote_style());
//					ps.setString(25, electric.getSupply());
//					ps.setDouble(26, electric.getShare_percent());
//					ps.setDouble(27, electric.getTax_factors());
//					ps.setDouble(28, electric.getShare_fees());
//					ps.setString(29, electric.getCustomer());
//					ps.setString(30, electric.getBill_no());
//					ps.setDouble(31, electric.getNo_tax_fees());
//					ps.setDouble(32, electric.getShare_watt());
//
//					int ret2 = ps.executeUpdate();
//					return ret2;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void addSiteInventory(SiteInventory siteInventory) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addSiteInventory");
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, siteInventory.getArea());
//					ps.setString(2, siteInventory.getSite_id());
//					ps.setString(3, siteInventory.getIsp_id());
//					ps.setString(4, siteInventory.getSite_name());
//					ps.setString(5, siteInventory.getAmmeter());
//					ps.setDouble(6, siteInventory.getTelecom_percent());
//					ps.setDouble(7, siteInventory.getCurrent_load());
//					ps.setDouble(8, siteInventory.getShunt_measurement());
//					ps.setDouble(9, siteInventory.getTelecom_current_load());
//					ps.setDouble(10, siteInventory.getTelecom_shunt_measurement());
//					if (siteInventory.getShare_percent() == null || siteInventory.getShare_percent().equals("")) {
//						ps.setInt(11, 0);
//					} else {
//						ps.setInt(11, siteInventory.getShare_percent());
//					}
//
//					int ret2 = ps.executeUpdate();
//					return ret2;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void addLiuLiang(LiuLiang liuLiang) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addLiuLiang");
//					return conn.prepareStatement(sql, new String[] { "CITY" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setString(1, tieta.getBill_month());
//					ps.setString(1, liuLiang.getCity());
//					java.util.Date date = new java.util.Date();
//					date = liuLiang.getAnalyse_time();
//					java.sql.Date date2 = new java.sql.Date(date.getTime());
//					// //.println(date2);
//					ps.setDate(2, date2);
//					ps.setString(3, liuLiang.getObject_id());
//					ps.setString(4, liuLiang.getObject_name());
//					ps.setDouble(5, liuLiang.getData_on_flows());
//					int ret2 = ps.executeUpdate();
//					return ret2;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void addLog(Log log) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addLog");
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					ps.setString(1, log.getAccount());
//					long date = new java.util.Date().getTime();
//					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
//					//.println(date2);
//					ps.setTimestamp(2, date2);
//					ps.setString(3, log.getOperation());
//					int ret2 = ps.executeUpdate();
//					return ret2;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void addErrorLog(ErrorLog errorLog) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addErrorLog");
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					ps.setString(1, errorLog.getAccount());
//					long date = new java.util.Date().getTime();
//					java.sql.Timestamp date2 = new java.sql.Timestamp(date);
//					//.println(date2);
//					ps.setTimestamp(2, date2);
//					ps.setString(3, errorLog.getError());
//					int ret2 = ps.executeUpdate();
//					return ret2;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public List<GongCan> allGongCans() {
//		String sql = propSQL.getProperty("allGongCans");
//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<GongCan>(GongCan.class));
//	}
//
//	public void addGongCan(GongCan gongCan) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addGongCan");
//					return conn.prepareStatement(sql, new String[] { "community_id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					ps.setInt(1, gongCan.getCommunity_id());
//					ps.setInt(2, gongCan.getStation_id());
//					ps.setInt(3, gongCan.getCell_id());
//					ps.setString(4, gongCan.getPlan_station_name());
//					ps.setInt(5, gongCan.getSubnet());
//					ps.setString(6, gongCan.getCounties());
//					ps.setInt(7, gongCan.getCluster_number());
//					ps.setString(8, gongCan.getX6_x7());
//					ps.setString(9, gongCan.getEci());
//					ps.setInt(10, gongCan.getTac_id());
//					ps.setInt(11, gongCan.getStation_num());
//					ps.setString(12, gongCan.getStation_name());
//					ps.setInt(13, gongCan.getCommunity_num());
//					ps.setString(14, gongCan.getCommunity_name());
//					ps.setInt(15, gongCan.getPci());
//					ps.setInt(16, gongCan.getPrach());
//					ps.setDouble(17, gongCan.getLongitude());
//					ps.setDouble(18, gongCan.getLatitude());
//					ps.setInt(19, gongCan.getCover());
//					ps.setString(20, gongCan.getCover_type());
//					ps.setDouble(21, gongCan.getDown_point());
//					ps.setDouble(22, gongCan.getUp_point());
//					ps.setInt(23, gongCan.getFrequency_band());
//					ps.setInt(24, gongCan.getBearing());
//					ps.setDouble(25, gongCan.getAntenna_highest());
//					ps.setInt(26, gongCan.getMechanical_downtilt());
//					ps.setInt(27, gongCan.getElectrical_downtilt());
//					ps.setString(28, gongCan.getAntenna_type());
//					ps.setString(29, gongCan.getTower_mast_type());
//					ps.setString(30, gongCan.getRru_id());
//					ps.setString(31, gongCan.getUser_id());
//					ps.setString(32, gongCan.getHigh_speed_rail());
//					ps.setString(33, gongCan.getArea_type());
//					ps.setString(34, gongCan.getSuper_community());
//					ps.setString(35, gongCan.getZoom_out_pro());
//					ps.setString(36, gongCan.getOnu_ip());
//					ps.setString(37, gongCan.getOwner());
//					ps.setString(38, gongCan.getRail_province());
//					ps.setString(39, gongCan.getSite_id());
//					ps.setString(40, gongCan.getSite_discern());
//					ps.setString(41, gongCan.getDai_wei());
//					ps.setString(42, gongCan.getProduct_confim_id());
//					ps.setString(43, gongCan.getBbu_site_id());
//					ps.setString(44, gongCan.getWeb_tuples());
//					ps.setString(45, gongCan.getTop_speed());
//
//					// ps.setString(1, tieta.getBill_month());
//					// ps.setString(1, liuLiang.getCity());
//					// java.util.Date date=new java.util.Date();
//					// date=liuLiang.getAnalyse_time();
//					// java.sql.Date date2=new java.sql.Date(date.getTime());
//					//// //.println(date2);
//					// ps.setDate(2, date2);
//					// ps.setString(3, liuLiang.getObject_id());
//					// ps.setString(4, liuLiang.getObject_name());
//					// ps.setDouble(5, liuLiang.getData_on_flows());
//					int ret2 = ps.executeUpdate();
//					return ret2;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void addGongCanJiNan(GongcanJiNan gongcanJiNan) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addGongCanJiNan");
//					return conn.prepareStatement(sql, new String[] { "area_id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					ps.setString(1, gongcanJiNan.getArea_id());
//					ps.setString(2, gongcanJiNan.getSite_id());
//					ps.setString(3, gongcanJiNan.getCell_id());
//					ps.setString(4, gongcanJiNan.getSite_num());
//					int ret2 = ps.executeUpdate();
//					return ret2;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	// public List<Tieta> geTietaBysite_id_not_in_gongcan() {
//	// String sql = propSQL.getProperty("geTietaBysite_id_not_in_gongcan");
//	//
//	// return jdbcTemplate.query(sql, new
//	// BeanPropertyRowMapper<Tieta>(Tieta.class));
//	//
//	// }
//	public List<Tieta> geTietaBysite_id_not_in_gongcanCity(String city) {
//		String sql = propSQL.getProperty("geTietaBysite_id_not_in_gongcanCity");
//		List<Tieta> tietaList = jdbcTemplate.query(sql, new Object[] { city },
//				new BeanPropertyRowMapper<Tieta>(Tieta.class));
//		// //.println(tietaList.size());
//		// if (tietaList.size() == 0)
//		// return null;
//		// else
//		return tietaList;
//		// return jdbcTemplate.query(sql, new
//		// BeanPropertyRowMapper<Tieta>(Tieta.class));
//		// return jdbcTemplate.query(sql, new Object[] { bill_month }, new
//		// BeanPropertyRowMapper<Tieta>(Tieta.class));
//	}
//
//	// public Tieta geTietaBysite_id_not_in_gongcan(String site_id_and_month) {
//	// String sql = propSQL.getProperty("geTietaBysite_id_not_in_gongcan");
//	// List<Tieta> tietaList = jdbcTemplate.query(sql, new Object[] {
//	// site_id_and_month },
//	// new BeanPropertyRowMapper<Tieta>(Tieta.class));
//	// if (tietaList.size()>= 1)
//	// return tietaList.get(0);
//	// else
//	// return null;
//	// }
//	public Tieta getTietaBysite_id_and_month(String site_id_and_month) {
//		String sql = propSQL.getProperty("getTietaBysite_id_and_month");
//		List<Tieta> tietaList = jdbcTemplate.query(sql, new Object[] { site_id_and_month },
//				new BeanPropertyRowMapper<Tieta>(Tieta.class));
//		if (tietaList.size() >= 1)
//			return tietaList.get(0);
//		else
//			return null;
//	}
//
//	public Tieta getState(String site_id_and_month) {
//		String sql = propSQL.getProperty("getState");
//		List<Tieta> tietaList = jdbcTemplate.query(sql, new Object[] { site_id_and_month },
//				new BeanPropertyRowMapper<Tieta>(Tieta.class));
//		if (tietaList.size() >= 1)
//			return tietaList.get(0);
//		else
//			return null;
//	}
//
//	public Tieta getUsed_State(String site_id_and_month) {
//		String sql = propSQL.getProperty("getUsed_State");
//		List<Tieta> tietaList = jdbcTemplate.query(sql, new Object[] { site_id_and_month },
//				new BeanPropertyRowMapper<Tieta>(Tieta.class));
//		if (tietaList.size() >= 1)
//			return tietaList.get(0);
//		else
//			return null;
//	}
//
//	public User getUser(String account, String passwd) {
//		String sql = propSQL.getProperty("getUserByAccountAndPasswd");
//		List<User> userList = jdbcTemplate.query(sql, new Object[] { account, passwd },
//				new BeanPropertyRowMapper<User>(User.class));
//		if (userList.size() >= 1)
//			return userList.get(0);
//		else
//			return null;
//	}
//
//	public boolean accoutExist(String account) {
//		return accoutExist(account, -1);
//	}
//
//	public boolean accoutExist(String account, long excludedId) {
//		String sql = propSQL.getProperty("accoutExist");
//		return jdbcTemplate.queryForObject(sql, Long.class, account, excludedId) > 0;
//	}
//
//	public List<User> allUsers() {
//		String sql = propSQL.getProperty("allUsers");
//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
//	}
//
//	public User getUser(long id) {
//		String sql = propSQL.getProperty("getUserById");
//		List<User> userList = jdbcTemplate.query(sql, new Object[] { id }, new BeanPropertyRowMapper<User>(User.class));
//		if (userList.size() == 1)
//			return userList.get(0);
//		else
//			return null;
//	}
//
//	public User getUser(String account) {
//		String sql = propSQL.getProperty("getUserByAccount");
//		List<User> userList = jdbcTemplate.query(sql, new Object[] { account },
//				new BeanPropertyRowMapper<User>(User.class));
//		if (userList.size() == 1)
//			return userList.get(0);
//		else
//			return null;
//	}
//
//	public void addUser(User user) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addUser");
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					// ps.setInt(1, (int) user.getId());
//					ps.setString(1, user.getAccount());
//					ps.setString(2, user.getPasswd());
//					ps.setString(3, user.getName());
//					ps.setLong(4, user.getPhoneNum());
//					ps.setString(5, user.getCity());
//
//					int ret = ps.executeUpdate();
//					ResultSet rs = ps.getGeneratedKeys();
//					if (rs.next())
//						user.setId(rs.getLong(1));// 鍒ゆ柇鏄惁-1锛屼笉鏄?-1娣诲姞鎴愬姛銆?
//					return ret;
//				}
//			});
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public boolean chgUser(User user) {
//		String sql = propSQL.getProperty("chgUser");
//		return 1 == jdbcTemplate.update(sql, user.getAccount(), user.getPasswd(), user.getName(), user.getPhoneNum(),
//				user.getCity(), user.getId());
//	}
//
//	public boolean delUser(long id) {
//		String sql = propSQL.getProperty("delUserById");
//		try {
//			return 1 == jdbcTemplate.update(sql, id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	public boolean delUser(String account) {
//		String sql = propSQL.getProperty("delUserByAccount");
//		return 1 == jdbcTemplate.update(sql, account);
//	}
//
//	// public int[] delEmps(long[] ids) {
//	// String sql = propSQL.getProperty("delUserById");
//	// List<Object[]> argsList = new ArrayList<Object[]>();
//	// for (long id : ids)
//	// argsList.add(new Object[] { id });
//	// return jdbcTemplate.batchUpdate(sql, argsList);
//	// }
//	//
//	// public int[] delEmps(String[] accounts) {
//	// String sql = propSQL.getProperty("delUserByAccount");
//	// List<Object[]> argsList = new ArrayList<Object[]>();
//	// for (String account : accounts)
//	// argsList.add(new Object[] { account });
//	// return jdbcTemplate.batchUpdate(sql, argsList);
//	// }
//	//
//	public List<User> findUserByAccount(String account) {
//		String sql = propSQL.getProperty("findUserByAccount");
//		return jdbcTemplate.query(sql, new Object[] { account }, new BeanPropertyRowMapper<User>(User.class));
//	}
//	public List<User> findUserById(Long id) {
//		String sql = propSQL.getProperty("findUserById");
//		return jdbcTemplate.query(sql, new Object[] { id }, new BeanPropertyRowMapper<User>(User.class));
//	}
//	public List<User> findUserByCity(String city) {
//		String sql = propSQL.getProperty("findUserByCity");
//		return jdbcTemplate.query(sql, new Object[] { city }, new BeanPropertyRowMapper<User>(User.class));
//	}
//
//	// public List<Express> allExpresses() {
//	// String sql = propSQL.getProperty("allExpresses");
//	// return jdbcTemplate.query(sql, new
//	// BeanPropertyRowMapper<Express>(Express.class));
//	// }
//	//
//	// public void addExpress(Express express) {
//	// try {
//	// jdbcTemplate.execute(new PreparedStatementCreator() {
//	// @Override
//	// public PreparedStatement createPreparedStatement(Connection conn) throws
//	// SQLException {
//	// String sql = propSQL.getProperty("addExpress");
//	// return conn.prepareStatement(sql, new String[] { "id" });
//	// }
//	// }, new PreparedStatementCallback<Integer>() {
//	// @Override
//	// public Integer doInPreparedStatement(PreparedStatement ps) throws
//	// SQLException, DataAccessException {
//	// ps.setString(1, express.getCompanyName());
//	// ps.setString(2, express.getContactName());
//	// ps.setString(3, express.getAddress());
//	// ps.setString(4, express.getPostalCode());
//	// ps.setString(5, express.getPhone());
//	// int ret = ps.executeUpdate();
//	// ResultSet rs = ps.getGeneratedKeys();
//	// if (rs.next())
//	// express.setId(rs.getLong(1));
//	// return ret;
//	// }
//	// });
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// }
//	// }
//	//
//	// public boolean chgExpress(Express express) {
//	// String sql = propSQL.getProperty("chgExpress");
//	// return 1 == jdbcTemplate.update(sql, express.getCompanyName(),
//	// express.getContactName(), express.getAddress(),
//	// express.getPostalCode(), express.getPhone(), express.getId());
//	// }
//	//
//	// public boolean delExpress(long id) {
//	// String sql = propSQL.getProperty("delExpress");
//	// try {
//	// return 1 == jdbcTemplate.update(sql, id);
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// }
//	// return false;
//	// }
//	//
//	// public List<Express> findExpressByCompanyName(String companyName) {
//	// String sql = propSQL.getProperty("findExpressByCompanyName");
//	// return jdbcTemplate.query(sql, new Object[] { companyName }, new
//	// BeanPropertyRowMapper<Express>(Express.class));
//	// }
//	//
//	// public Express getExpress(long id) {
//	// String sql = propSQL.getProperty("getExpress");
//	// List<Express> expressList = jdbcTemplate.query(sql, new Object[] { id },
//	// new BeanPropertyRowMapper<Express>(Express.class));
//	// if (expressList.size() == 1)
//	// return expressList.get(0);
//	// else
//	// return null;
//	// }
//	//
//	// public List<Supplier> allSuppliers() {
//	// String sql = propSQL.getProperty("allSuppliers");
//	// return jdbcTemplate.query(sql, new
//	// BeanPropertyRowMapper<Supplier>(Supplier.class));
//	// }
//	//
//	// public void addSupplier(Supplier supplier) {
//	// try {
//	// jdbcTemplate.execute(new PreparedStatementCreator() {
//	// @Override
//	// public PreparedStatement createPreparedStatement(Connection conn) throws
//	// SQLException {
//	// String sql = propSQL.getProperty("addSupplier");
//	// return conn.prepareStatement(sql, new String[] { "id" });
//	// }
//	// }, new PreparedStatementCallback<Integer>() {
//	// @Override
//	// public Integer doInPreparedStatement(PreparedStatement ps) throws
//	// SQLException, DataAccessException {
//	// ps.setString(1, supplier.getCompanyName());
//	// ps.setString(2, supplier.getContactName());
//	// ps.setString(3, supplier.getAddress());
//	// ps.setString(4, supplier.getPostalCode());
//	// ps.setString(5, supplier.getPhone());
//	// int ret = ps.executeUpdate();
//	// ResultSet rs = ps.getGeneratedKeys();
//	// if (rs.next())
//	// supplier.setId(rs.getLong(1));
//	// return ret;
//	// }
//	// });
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// }
//	// }
//	//
//	// public boolean chgSupplier(Supplier supplier) {
//	// String sql = propSQL.getProperty("chgSupplier");
//	// return 1 == jdbcTemplate.update(sql, supplier.getCompanyName(),
//	// supplier.getContactName(),
//	// supplier.getAddress(), supplier.getPostalCode(), supplier.getPhone(),
//	// supplier.getId());
//	// }
//	//
//	// public boolean delSupplier(long id) {
//	// String sql = propSQL.getProperty("delSupplier");
//	// try {
//	// return 1 == jdbcTemplate.update(sql, id);
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// }
//	// return false;
//	// }
//	//
//	// public List<Supplier> findSupplierByCompanyName(String companyName) {
//	// String sql = propSQL.getProperty("findSupplierByCompanyName");
//	// return jdbcTemplate.query(sql, new Object[] { companyName },
//	// new BeanPropertyRowMapper<Supplier>(Supplier.class));
//	// }
//	//
//	// public Supplier getSupplier(long id) {
//	// String sql = propSQL.getProperty("getSupplier");
//	// List<Supplier> supplierList = jdbcTemplate.query(sql, new Object[] { id },
//	// new BeanPropertyRowMapper<Supplier>(Supplier.class));
//	// if (supplierList.size() == 1)
//	// return supplierList.get(0);
//	// else
//	// return null;
//	// }
//	//
//	// public void addOrder(Order order) {
//	// try {
//	// jdbcTemplate.execute(new PreparedStatementCreator() {
//	// @Override
//	// public PreparedStatement createPreparedStatement(Connection conn) throws
//	// SQLException {
//	// String sql = propSQL.getProperty("addOrder");
//	// return conn.prepareStatement(sql, new String[] { "id" });
//	// }
//	// }, new PreparedStatementCallback<Integer>() {
//	// @Override
//	// public Integer doInPreparedStatement(PreparedStatement ps) throws
//	// SQLException, DataAccessException {
//	// ps.setString(1, order.getProductName());
//	// ps.setDouble(2, order.getUnitPrice());
//	// ps.setDouble(3, order.getQuantity());
//	// ps.setLong(4, order.getSupplier());
//	// ps.setLong(5, order.getExpress());
//	// ps.setLong(6, order.getCustomer());
//	// ps.setTimestamp(7, order.getOrderDate());
//	// ps.setString(8, order.getExpressNumber());
//	// ps.executeUpdate();
//	// ResultSet rs = ps.getGeneratedKeys();
//	// if (rs.next())
//	// order.setId(rs.getLong(1));
//	// return 0;
//	// }
//	// });
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// }
//	// }
//	//
//	// public boolean chgOrder(Order order) {
//	// String sql = propSQL.getProperty("chgOrder");
//	// if (jdbcTemplate.update(sql,
//	// new Object[] { order.getProductName(), order.getUnitPrice(),
//	// order.getQuantity(), order.getSupplier(),
//	// order.getExpress(), order.getOrderDate(), order.getExpressNumber(),
//	// order.getId() }) == 1)
//	// return true;
//	// else
//	// return false;
//	// }
//	//
//	// public boolean delOrder(long id) {
//	// String sql = propSQL.getProperty("delOrder");
//	// if (jdbcTemplate.update(sql, new Object[] { id }) == 1)
//	// return true;
//	// else
//	// return false;
//	// }
//	//
//	// class OrderRowMapper implements RowMapper<OrderWrapper> {
//	// @Override
//	// public OrderWrapper mapRow(ResultSet rs, int num) throws SQLException {
//	// Order order = new Order();
//	// order.setId(rs.getLong("id"));
//	// order.setProductName(rs.getString("productName"));
//	// order.setUnitPrice(rs.getDouble("unitPrice"));
//	// order.setQuantity(rs.getDouble("quantity"));
//	// order.setOrderDate(rs.getTimestamp("orderDate"));
//	// order.setExpressNumber(rs.getString("expressNumber"));
//	// order.setExpress(rs.getLong("express.id"));
//	// order.setCustomer(rs.getLong("customer.id"));
//	// order.setSupplier(rs.getLong("supplier.id"));
//	//
//	// Express express = new Express();
//	// express.setId(rs.getLong("express.id"));
//	// express.setCompanyName(rs.getString("express.companyName"));
//	// express.setContactName(rs.getString("express.contactName"));
//	// express.setAddress(rs.getString("express.address"));
//	// express.setPostalCode(rs.getString("express.postalCode"));
//	// express.setPhone(rs.getString("express.phone"));
//	//
//	// Supplier supplier = new Supplier();
//	// supplier.setId(rs.getLong("supplier.id"));
//	// supplier.setCompanyName(rs.getString("supplier.companyName"));
//	// supplier.setContactName(rs.getString("supplier.contactName"));
//	// supplier.setAddress(rs.getString("supplier.address"));
//	// supplier.setPostalCode(rs.getString("supplier.postalCode"));
//	// supplier.setPhone(rs.getString("supplier.phone"));
//	//
//	// User customer = new User();
//	// customer.setId(rs.getLong("customer.id"));
//	// customer.setAccount(rs.getString("customer.account"));
//	// customer.setPasswd(rs.getString("customer.passwd"));
//	// customer.setName(rs.getString("customer.name"));
//	// customer.setBirthday(rs.getDate("customer.birthday"));
//	// customer.setSex(rs.getString("customer.sex"));
//	// customer.setHobbies(rs.getString("customer.hobbies"));
//	//
//	// OrderWrapper orderWrapper = new OrderWrapper();
//	// orderWrapper.setCustomer(customer);
//	// orderWrapper.setExpress(express);
//	// orderWrapper.setOrder(order);
//	// orderWrapper.setSupplier(supplier);
//	//
//	// return orderWrapper;
//	// }
//	// }
//	//
//	// public List<OrderWrapper> allOrders() {
//	// String sql = propSQL.getProperty("allOrders");
//	// ;
//	// return jdbcTemplate.query(sql, new OrderRowMapper());
//	// }
//	//
//	// public List<OrderWrapper> getOrdersOfUser(long userId) {
//	// String sql = propSQL.getProperty("getOrdersOfUser");
//	//
//	// return jdbcTemplate.query(sql, new Object[] { userId }, new
//	// OrderRowMapper());
//	// }
//
//	// public Order getOrder(long id) {
//	// String sql = propSQL.getProperty("getOrder");
//	// ;
//	// List<Order> orderList = jdbcTemplate.query(sql, new Object[] { id },
//	// new BeanPropertyRowMapper<Order>(Order.class));
//	// if (orderList.size() == 1)
//	// return orderList.get(0);
//	// else
//	// return null;
//	// }
//	//
//	public void addDoc(Doc doc) {
//		try {
//			jdbcTemplate.execute(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String sql = propSQL.getProperty("addDoc");
//					return conn.prepareStatement(sql, new String[] { "id" });
//				}
//			}, new PreparedStatementCallback<Integer>() {
//				@Override
//				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//					ps.setString(1, doc.getName());
//					//.println(doc.getName());
//					ps.setString(2, doc.getFullName());
//					//.println(doc.getFullName());
//					ps.setTimestamp(3, doc.getUploadedTime());
//					//.println(doc.getUploadedTime());
//					ps.setLong(4, doc.getUserId());
//					int ret = ps.executeUpdate();
//					ResultSet rs = ps.getGeneratedKeys();
//					if (rs.next())
//						doc.setId(rs.getLong(1));
//					return ret;
//				}
//			});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public Doc getDoc(long id) {
//		String sql = propSQL.getProperty("getDoc");
//		List<Doc> docList = jdbcTemplate.query(sql, new Object[] { id }, new BeanPropertyRowMapper<Doc>(Doc.class));
//		if (docList.size() == 1)
//			return docList.get(0);
//		else
//			return null;
//	}
//
//	public List<Doc> allDocs() {
//		String sql = propSQL.getProperty("allDocs");
//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Doc>(Doc.class));
//
//	}
//
//	public List<Doc> getDocsOfUser(long userId) {
//		String sql = propSQL.getProperty("getDocsOfUser");
//		return jdbcTemplate.query(sql, new Object[] { userId }, new BeanPropertyRowMapper<Doc>(Doc.class));
//	}
//
//	public boolean delDoc(long id) {
//		String sql = propSQL.getProperty("delDoc");
//		try {
//			return 1 == jdbcTemplate.update(sql, id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// public boolean addEmp(Emp emp) {
//	// String sql = propSQL.getProperty("addEmp");
//	// return 1 == jdbcTemplate.update(sql,
//	// emp.getEmployeeId(),
//	// emp.getName(),
//	// emp.getSalary());
//	// }
//	//
//	// public boolean textTx() {
//	// return transactionTemplate.execute(new TransactionCallback<Boolean>(){
//	//
//	// @Override
//	// public Boolean doInTransaction(TransactionStatus ts) {
//	// try{
//	// jdbcTemplate.update("INSERT INTO tx (id) values (?)", new Object[]{1});
//	// jdbcTemplate.update("INSERT INTO tx (id) values (?)", new Object[]{1});
//	// }catch(Exception e){
//	// e.printStackTrace();
//	// ts.setRollbackOnly();
//	// return false;
//	// }
//	// return true;
//	// }
//
//	// });
//	// }
//
//	public boolean setState(String state, String reason, Integer id) {
//		String sql = propSQL.getProperty("setStateAndReason");
//		return 1 == jdbcTemplate.update(sql, new Object[] { state, reason, id });
//
//	}
//
//	public List<LiuLiang> jizhanliuliangList(Date start, Date end) {
//
//		String sql = "select sum(l.data_on_flows ) data_on_flows,j.analyse_time analyse_time, j.object_id , city from (select substr(object_id,0,length(object_id)-   instr( reverse(object_id    ),'.')) object_id,sum(data_on_flows) , ANALYSE_TIME from liuliang  "
//				+ "where analyse_time > ?  AND analyse_time <= ?"
//				+ "group by substr(object_id,0,length(object_id)-   instr( reverse(object_id    ),'.')),  ANALYSE_TIME) j , liuliang l WHERE  instr(l.object_id , j.object_id) > 0 AND l.analyse_time - j.analyse_time < 5 "
//				+ "AND l.analyse_time - j.analyse_time >= 0 " + "group BY j.object_id ,j.analyse_time , city";
//		return jdbcTemplate.query(sql, new Object[] { start, end },
//				new BeanPropertyRowMapper<LiuLiang>(LiuLiang.class));
//
//	}
//
//	public List<SiteFlow> checkCostCity(Date start, String city2, Date end, String city) {
//		String sql = "select k1.analyse_time analyse_date,k1.site_id,sum(k2.flows) flow_data " + "from "
//				+ "(select l.analyse_time ,g.site_id,sum(l.data_on_flows) flows " + "from gongcan g,liuliang l "
//				+ "where g.community_id=  replace( substr(l.object_id,4,length(l.object_id)),'.','') and l.ANALYSE_TIME>=? and l.ANALYSE_TIME< ?  and city=substr(?,0,2)"
//				+ "group by l.analyse_time ,g.site_id) k1, "
//				+ "(select l.analyse_time ,g.site_id,sum(l.data_on_flows) flows " + "from gongcan g,liuliang l "
//				+ "where g.community_id=  replace( substr(l.object_id,4,length(l.object_id)),'.','')and l.ANALYSE_TIME>=? and l.ANALYSE_TIME< ? and city=substr(?,0,2) "
//				+ "group by l.analyse_time ,g.site_id) k2 "
//				+ "where k1.site_id=k2.site_id and k2.analyse_time - k1.analyse_time>=0 and k2.analyse_time - k1.analyse_time<5 "
//				+ "group by k1.analyse_time , k1.site_id " + "having sum(k2.flows)=0 ";
//
//		return jdbcTemplate.query(sql, new Object[] { start, end, city2, start, end, city },
//				new BeanPropertyRowMapper<SiteFlow>(SiteFlow.class));
//	}
//	// public List<Tieta> getTietaByMonth(String monthStr) {
//	// String sql = "select * from tieta where instr(bill_month,?)>0 ";
//	//
//	// return jdbcTemplate.query(sql, new Object[] {monthStr},new
//	// BeanPropertyRowMapper<Tieta>(Tieta.class));
//	//
//	// }
//
//	public List<Tieta> getTietaByMonthCity(String monthStr, String city) {
//		String sql = "select * from tieta where instr(bill_month,?)>0 and instr(?,site_location_city)>0";
//
//		return jdbcTemplate.query(sql, new Object[] { monthStr, city }, new BeanPropertyRowMapper<Tieta>(Tieta.class));
//
//	}
//
//	public List<Tieta> getTietaByMonthState(String monthStr, String state, String city) {
//		String sql = "select * from tieta where instr(bill_month,?)>0 and instr(state,?)>0 and instr(?,site_location_city)>0";
//
//		return jdbcTemplate.query(sql, new Object[] { monthStr, state, city },
//				new BeanPropertyRowMapper<Tieta>(Tieta.class));
//
//	}
//
//	public List<Tieta> getTietaByMonthStateCity(String monthStr, String state, String city) {
//		String sql = "select * from tieta where instr(bill_month,?)>0 and instr(state,?)>0 and instr(?,site_location_city)>0 ";
//
//		return jdbcTemplate.query(sql, new Object[] { monthStr, state, city },
//				new BeanPropertyRowMapper<Tieta>(Tieta.class));
//
//	}
//
//	public List<Tieta> getHistoryTietaByMonthStateCity(String monthStr, String state, String used_state, String city) {
//		String sql = "select * from tieta where instr(bill_month,?)>0 and (instr(state,?)>0 or instr(used_state,?)>0) and instr(?,site_location_city)>0";
//
//		return jdbcTemplate.query(sql, new Object[] { monthStr, state, used_state, city },
//				new BeanPropertyRowMapper<Tieta>(Tieta.class));
//
//	}
//
//	public boolean setMultiState(List<Tieta> tietaList, String state, String reason) {
//
//		if (null != tietaList && !tietaList.isEmpty()) {
//			Dac dac = Dac.getInstance();
//			for (Tieta tieta : tietaList) {
//				String string = tieta.getReason_of_exception();
//				if (tieta.getReason_of_exception().equals("鏃?")) {
//					string = "";
//				}
//				String string2 = string + "," + reason;
//
//				// //.println(string2+"------姣旇緝reason涔嬪墠String2 dac.java");
//				// //.println(reason+"------姣旇緝reason涔嬪墠 reason dac.java");
//				if (string.contains(reason)) {
//
//					string2 = string;
//				}
//				// //.println(string2+"------姣旇緝reason涔嬪悗 String2 dac.java");
//				// //.println(reason+"------姣旇緝reason涔嬪悗reason dac.java");
//				dac.setState(state, string2, tieta.getId());
//			}
//			return true;
//		} else
//			return false;
//	}
//
//	public List<LiuLiang> getLiuliangByDateCity(Date start, Date end, String city) {
//		String sql = "select * from liuliang where ANALYSE_TIME>=? and ANALYSE_TIME<=? and city=substr(?,0,2)";
//
//		return jdbcTemplate.query(sql, new Object[] { start, end, city },
//				new BeanPropertyRowMapper<LiuLiang>(LiuLiang.class));
//
//	}
//
//	public List<TowerCost> getTowerCostByMonthCity(String monthStr, String state, String city) {
//		String sql = "select id, state, bill_month, product_confim_id, isp_city, "
//				+ "site_name, site_id, after_shared_base_price_all, base_price_all_of_station, "
//				+ "base_price_all_of_mating, cost_all_of_maintain, cost_all_of_place, cost_all_of_power, "
//				+ "cost_all_of_service, cost_variation_changed, isp_districts, ownership_init "
//				+ "from tieta where instr(bill_month,?)>0 and instr(state,?)>0 and instr(?,site_location_city)>0";
//
//		return jdbcTemplate.query(sql, new Object[] { monthStr, state, city },
//				new BeanPropertyRowMapper<TowerCost>(TowerCost.class));
//
//	}
//
//	public double getSumFlowByMonthCity(String month, String city) {
//		String sql = "select sum(data_on_flows) from liuliang where to_char(analyse_time,'yyyyMM')=? and city=substr(?,0,2)";
//		Double flow = jdbcTemplate.queryForObject(sql, new Object[] { month, city }, Double.class);
//		if (flow == null)
//			return 0.0;
//		double temp = flow / 1024 / 1024;
//		DecimalFormat df = new DecimalFormat("#.00");
//		String tempStr = df.format(temp);
//		temp = Double.valueOf(tempStr);
//
//		return temp;
//	}
//
//	public double getSumCostByMonthCity(String month, String city) {
//		String sql = "select sum(cost_all_of_service) from tieta where bill_month=? and instr(?,site_location_city)>0";
//		Double cost = jdbcTemplate.queryForObject(sql, new Object[] { month, city }, Double.class);
//
//		if (cost == null)
//			return 0.0;
//		double temp = cost / 10000;
//		DecimalFormat df = new DecimalFormat("#.00");
//		String tempStr = df.format(temp);
//		temp = Double.valueOf(tempStr);
//
//		return temp;
//	}
//
//	public double getSumCostByYearAndStateCity(String year, String state, String city) {
//		String sql = "select sum(cost_all_of_service) from tieta where instr(bill_month,?)>0 and state= ? and instr(?,site_location_city)>0";
//		Double cost = jdbcTemplate.queryForObject(sql, new Object[] { year, state, city }, Double.class);
//		if (cost == null)
//			return 0.0;
//		double temp = cost / 10000;
//		DecimalFormat df = new DecimalFormat("#.00");
//		String tempStr = df.format(temp);
//		temp = Double.valueOf(tempStr);
//
//		return temp;
//	}
//
//	public double getSumCostSaveByYearCity(String year, String city) {
//		String sql = "select sum(cost_all_of_service-adjust_cost) from tieta where adjust_cost>=0 and instr(bill_month,?)>0 and instr(?,site_location_city)>0";
//		// String sql ="select sum(adjust_cost) from tieta where adjust_cost>=0 and
//		// instr(bill_month,?)>0";
//		Double cost = jdbcTemplate.queryForObject(sql, new Object[] { year, city }, Double.class);
//
//		if (cost == null)
//			return 0.0;
//		double temp = cost / 10000;
//		DecimalFormat df = new DecimalFormat("#.00");
//		String tempStr = df.format(temp);
//		temp = Double.valueOf(tempStr);
//
//		return temp;
//	}
//
//	public double getSumCostCheckByYearCity(String year, String city) {
//		String sql = "select sum(cost_all_of_service) from tieta where instr(bill_month,?)>0 and instr(?,site_location_city)>0";
//		Double cost = jdbcTemplate.queryForObject(sql, new Object[] { year, city }, Double.class);
//
//		if (cost == null)
//			return 0.0;
//		double temp = cost / 10000;
//		DecimalFormat df = new DecimalFormat("#.00");
//		String tempStr = df.format(temp);
//		temp = Double.valueOf(tempStr);
//
//		return temp;
//	}
//
//	public int getCountNeedHandleByYearCity(String year, String city) {
//		String sql = "select count(*) from tieta where instr(bill_month,?)>0 and adjust_cost is null and state='寮傚父' and instr(?,site_location_city)>0";
//		Integer count = jdbcTemplate.queryForObject(sql, new Object[] { year, city }, Integer.class);
//
//		if (count == null)
//			return 0;
//		else
//			return count;
//	}
//
//	public List<Tieta> getTietaNeedHandleByMonthCity(String monthStr, String city) {
//		String sql = "select * from tieta where instr(bill_month,?)>0 and adjust_cost is null and state='寮傚父' and instr(?,site_location_city)>0";
//
//		return jdbcTemplate.query(sql, new Object[] { monthStr, city }, new BeanPropertyRowMapper<Tieta>(Tieta.class));
//
//	}
//
//	public List<FlowsOfMonth> getSumFlowByYearCity(String year, String city) {
//		String sql = "select sum(data_on_flows)/1024/1024 flow,EXTRACT (MONTH FROM analyse_time) month from liuliang where to_char(analyse_time,'yyyy')=? and city=substr(?,0,2) group by EXTRACT (MONTH FROM analyse_time) ";
//		return jdbcTemplate.query(sql, new Object[] { year, city },
//				new BeanPropertyRowMapper<FlowsOfMonth>(FlowsOfMonth.class));
//	}
//
//	public List<CostOfMonth> getSumCostByYearCity(String year, String city) {
//		String sql = "select sum(cost_all_of_service)/10000 cost ,bill_month month from tieta where instr(BILL_MONTH,?)>0 and state='姝ｅ父' and instr(?,site_location_city)>0 group by bill_month";
//		return jdbcTemplate.query(sql, new Object[] { year, city },
//				new BeanPropertyRowMapper<CostOfMonth>(CostOfMonth.class));
//	}// 鐜板湪鐘舵?佹槸姝ｅ父鐨勯搧濉旀?昏垂鐢?

}
