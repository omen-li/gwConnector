package com.dusun.dubbo.service.redisService;

public interface FrontConnectionService {

	/**
	 * 
	* @Title: getFrontConnectionRecorde
	* @Description: 获取前置长链接记录
	* @param @param busiKey
	* @param @return    
	* @return String    
	* @throws
	* @author omen  www.liyidong.com 
	* @date 2015年6月10日 上午9:31:43
	 */
	public String getFrontConnectionRecorde(String busiKey);
	
	/**
	 * 
	* @Title: setFrontConnectionRecorde
	* @Description: 保存前置长连接记录
	* @param @param busiKey
	* @param @param frontIp    
	* @return void    
	* @throws
	* @author omen  www.liyidong.com 
	* @date 2015年6月10日 上午9:32:22
	 */
	public void setFrontConnectionRecorde(String busiKey,String frontIp);
	
	/**
	 * 
	* @Title: delFrontConnectionRecorde
	* @Description: 删除前置长连接记录
	* @param @param busiKey
	* @param @return    
	* @return String    
	* @throws
	* @author omen  www.liyidong.com 
	* @date 2015年6月10日 上午9:32:37
	 */
	public void delFrontConnectionRecorde(String busiKey);
	
}
