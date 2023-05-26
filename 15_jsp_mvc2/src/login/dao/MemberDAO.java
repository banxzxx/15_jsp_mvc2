package login.dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import login.dto.MemberDTO;
 
public class MemberDAO {
 
    private MemberDAO() {}
    private static MemberDAO instance = new MemberDAO();
    public static MemberDAO getInstance() {
        return instance;
    }
    
    private Connection conn 		= null;
    private PreparedStatement pstmt = null;
    private ResultSet rs 			= null;
    
    public void getConnection() {
        
    	try {
    		
    		Context initCtx = new InitialContext();
    		Context envCtx = (Context)initCtx.lookup("java:comp/env");
    		DataSource ds = (DataSource)envCtx.lookup("jdbc/pool2");
    		conn = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
    
    public void getClose() {
    	if (rs != null)    {try {rs.close();}   catch (SQLException e) {}}
    	if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
        if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
    }
    
    
    public boolean checkDuplicateId(String memberId) {
    	
    	boolean isDuple = false;
    	
    	try {
    		
			getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE MEMBER_ID = ?");
			pstmt.setString(1 , memberId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				isDuple = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
    	
    	return isDuple;
    	
    }
    
    
    
    public void registerMember(MemberDTO memberDTO) {
    	
    	try {
    		
			getConnection();
			pstmt = conn.prepareStatement("INSERT INTO MEMBER VALUES(? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , NOW())");
			pstmt.setString(1, memberDTO.getMemberId());
			pstmt.setString(2, memberDTO.getMemberNm());
			pstmt.setString(3, memberDTO.getImgNm());
			pstmt.setString(4, memberDTO.getPasswd());
			pstmt.setString(5, memberDTO.getSex());
			pstmt.setString(6, memberDTO.getBirthDt());
			pstmt.setString(7, memberDTO.getHp());
			pstmt.setString(8, memberDTO.getSmsstsYn());
			pstmt.setString(9, memberDTO.getEmail());
			pstmt.setString(10, memberDTO.getEmailstsYn());
			pstmt.setString(11, memberDTO.getZipcode());
			pstmt.setString(12, memberDTO.getRoadAddress());
			pstmt.setString(13, memberDTO.getJibunAddress());
			pstmt.setString(14, memberDTO.getNamujiAddress());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
    	
    }
    
    
    public boolean loginMember(MemberDTO memberDTO) {
    	
    	boolean isLogin = false;
    	
    	try {
    		
			getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE MEMBER_ID = ? AND PASSWD = ?");
			pstmt.setString(1, memberDTO.getMemberId());
			pstmt.setString(2, memberDTO.getPasswd());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				isLogin = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
    	
    	return isLogin;
    	
    }
    
    
    public MemberDTO getMemberDetail(String memberId) {
        
    	MemberDTO memberDTO = new MemberDTO();
        
    	try {
    		
            getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM MEMBER WHERE MEMBER_ID = ?");
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	memberDTO = new MemberDTO();
            	memberDTO.setMemberId(rs.getString("MEMBER_ID"));
            	memberDTO.setMemberNm(rs.getString("MEMBER_NM"));
            	memberDTO.setImgNm(rs.getString("IMG_NM"));
            	memberDTO.setPasswd(rs.getString("PASSWD"));
            	memberDTO.setSex(rs.getString("SEX"));
            	memberDTO.setBirthDt(rs.getString("BIRTH_DT"));
            	memberDTO.setHp(rs.getString("HP"));
            	memberDTO.setSmsstsYn(rs.getString("SMSSTS_YN"));
            	memberDTO.setEmail(rs.getString("EMAIL"));
            	memberDTO.setEmailstsYn(rs.getString("EMAILSTS_YN"));
            	memberDTO.setZipcode(rs.getString("ZIPCODE"));
            	memberDTO.setRoadAddress(rs.getString("ROAD_ADDRESS"));
            	memberDTO.setJibunAddress(rs.getString("JIBUN_ADDRESS"));
            	memberDTO.setNamujiAddress(rs.getString("NAMUJI_ADDRESS"));
            	memberDTO.setJoinDt(rs.getDate("JOIN_DT"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	getClose();
        }
    	
        return memberDTO;
    
    }
    
    
    public void deleteMember(String memberId) {
    	
    	try {
    		
    		getConnection();
    		pstmt = conn.prepareStatement("DELETE FROM MEMBER WHERE MEMBER_ID = ?");
    		pstmt.setString(1, memberId);
    		pstmt.executeUpdate();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		getClose();
    	}
    	
    }
    
    
    public void updateMember(MemberDTO memberDTO) {
        
    	try {
    		
            getConnection();
            
            int idx = 1;
            
            String sql = "UPDATE MEMBER SET ";
		           sql += "MEMBER_NM = ?,";
		           if (!memberDTO.getImgNm().equals("")) {	// 이미지가 ""이 아닐경우 > 이미지 수정을 했을 경우
		        	   sql += "IMG_NM = ?,";
		           }
                   sql += "SEX = ?,";
                   sql += "BIRTH_DT = ?,";
                   sql += "HP = ?,";
                   sql += "SMSSTS_YN = ?,";
                   sql += "EMAIL = ?,";
                   sql += "EMAILSTS_YN = ?,";
                   sql += "ZIPCODE = ?,";
                   sql += "ROAD_ADDRESS = ?,";
                   sql += "JIBUN_ADDRESS = ?,";
                   sql += "NAMUJI_ADDRESS = ?";
                   sql += "WHERE MEMBER_ID = ?";
                   
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(idx++, memberDTO.getMemberNm());
            if (!memberDTO.getImgNm().equals("")) {
            	pstmt.setString(idx++, memberDTO.getImgNm());
            }
            pstmt.setString(idx++, memberDTO.getSex());
            pstmt.setString(idx++, memberDTO.getBirthDt());
            pstmt.setString(idx++, memberDTO.getHp());
            pstmt.setString(idx++, memberDTO.getSmsstsYn());
            pstmt.setString(idx++, memberDTO.getEmail());
            pstmt.setString(idx++, memberDTO.getEmailstsYn());
            pstmt.setString(idx++, memberDTO.getZipcode());
            pstmt.setString(idx++, memberDTO.getRoadAddress());
            pstmt.setString(idx++, memberDTO.getJibunAddress());
            pstmt.setString(idx++, memberDTO.getNamujiAddress());
            pstmt.setString(idx, memberDTO.getMemberId());
            pstmt.executeUpdate();
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	getClose();
        }
    	
    }
   
    
    
}
