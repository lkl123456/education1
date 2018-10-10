package com.adks.admin.controller.zhiji;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adks.admin.util.LogUtil;
import com.adks.dubbo.api.data.zhiji.Adks_rank;
import com.adks.dubbo.api.interfaces.admin.log.LogApi;
import com.adks.dubbo.api.interfaces.admin.zhiji.ZhijiApi;
import com.adks.dubbo.commons.Page;

@Controller
@RequestMapping(value="/zhiji")
public class ZhijiController {
	private final Logger logger = LoggerFactory.getLogger(ZhijiController.class);
	
	@Autowired
	private ZhijiApi zhijiApi;
	@Autowired
	private LogApi logApi;
	
	private Integer parent;
	
	@RequestMapping({"/zhijiList"})
	public String home(HttpServletRequest request, HttpServletResponse response,Model model,Integer parentId) throws IOException {
    	logger.debug("进入 ZhijiController  zhijiList...");
    	if(parentId ==null){
    		parentId=0;
    	}
    	Adks_rank rank=zhijiApi.getZhijiById(parentId);
    	Integer overParentId=0;
    	if(parentId!=0){
    		if(rank!=null){
    			overParentId=rank.getParentId();
    		}
    	}
    	//父级id
    	model.addAttribute("parentId", parentId);
    	model.addAttribute("parentName",rank==null?null:rank.getRankName());
    	//上一个页面的父级if
    	model.addAttribute("overParentId", overParentId);
    	return "zhiji/zhijiList";
    }
	
	@RequestMapping(value="/getZhijiListJson", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public Page getZhijiListJson(Integer page, Integer rows,Integer parentId, String s_org_name){
		Page<List<Map<String, Object>>> page_bean=null;
		try {
			page_bean = new Page<List<Map<String, Object>>>();
			page_bean.setCurrentPage(page);
			page_bean.setPageSize(rows);
			Map map = new HashMap<>();
			if(parentId ==null){
				parentId=0;
			}
			map.put("parentId", parentId);
			if(s_org_name != null){
				s_org_name= java.net.URLDecoder.decode(s_org_name,"UTF-8");
				map.put("rankName", s_org_name);
			}
			page_bean.setMap(map);
			page_bean = zhijiApi.getZhijiListPage(page_bean);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return page_bean;
	}
	
	 /**
     * 机构管理主界面，获取用户列表json数据
     * @return
	 * @throws IOException 
     */
	@RequestMapping(value="/getZhijisJson", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public List<Adks_rank> getUsersJson( HttpServletResponse response) throws IOException {
    	List<Adks_rank> list=null;
    	list=zhijiApi.getZhijisListAll();
    	return list;
    }
    
    /**
     * 
     * @Title getZhijisJsonAddSpace
     * @Description：用于用户、班级学员搜索时使用，添加一个空节点
     * @author xrl
     * @Date 2017年6月5日
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/getZhijisJsonAddSpace", produces = {"application/json; charset=UTF-8"})
    @ResponseBody
    public List<Adks_rank> getZhijisJsonAddSpace( HttpServletResponse response) throws IOException {
    	List<Adks_rank> list=null;
    	list=zhijiApi.getZhijisListAll();
    	List<Adks_rank> newList=new ArrayList<>();
    	Adks_rank rank=new Adks_rank();
    	rank.setName("");
    	rank.setId(0);
    	rank.setRankId(0);
    	rank.setRankName("");
    	rank.setText("");
    	newList.add(rank);
    	for (Adks_rank adks_rank : list) {
			newList.add(adks_rank);
		}
    	return newList;
    }
    
    /**
     * 保存职级
     * @return
	 * @throws IOException 
     */
	@RequestMapping(value="/saveZhiji", produces = {"text/html;charset=utf-8"})
    @ResponseBody
    public void saveZhijiJson(HttpServletRequest request, HttpServletResponse response,Adks_rank rank) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		String message = "error";
		try {
			if(rank != null){
				boolean flag = true;
				// 网校唯一标识
				if(rank.getRankName() != null){
					Map<String, Object> temp = zhijiApi.getZhijiByName(rank.getRankName());
					if(temp != null && temp.size() > 0 && 
							(((rank.getRankId() == null) || temp.get("rankId") != null && rank.getRankId() != MapUtils.getIntValue(temp, "rankId")) 
							)){
						message = "snnameExists";
						flag = false;
					}
				}
				if(flag){
					if(rank.getParentId()==null){
						rank.setParentId(0);
					}
					rank.setIsdelete(2);
					if(rank.getOrderNum()==null){
						rank.setOrderNum(1);
					}
					Integer dataId=zhijiApi.saveZhiji(rank);
					//数据操作成功，保存操作日志
					if(dataId!=null&&dataId!=0){
						Map map=(Map)request.getSession().getAttribute("user");
						//添加/修改 标识
						if(rank.getRankId()==null){
							logApi.saveLog(dataId, rank.getRankName(), LogUtil.LOG_ZHIJI_MODULEID, LogUtil.LOG_ADD_TYPE, map);
						}else{
							logApi.saveLog(rank.getRankId(), rank.getRankName(), LogUtil.LOG_ZHIJI_MODULEID, LogUtil.LOG_UPDATE_TYPE, map);
						}
					}
					message = "succ";
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write(message.toString());
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 删除职级信息
	 * @param request
	 * @param response
	 * @param ids
	 */
	@RequestMapping(value = "/delZhiji", method=RequestMethod.POST)
	public void delZhiji(HttpServletRequest request, HttpServletResponse response, String ids){
		response.setContentType("text/html;charset=utf-8");
		try {
			if(ids != null){
				zhijiApi.deleteZhijiByIds(ids);
				String[] rankIds=ids.split(",");
				Map map=(Map)request.getSession().getAttribute("user");
				for (String rankId : rankIds) {
					//数据操作成功，保存操作日志
					if(rankId!=null){
						//获取该操作执行人信息
						logApi.saveLog(Integer.valueOf(rankId), null, LogUtil.LOG_ZHIJI_MODULEID, LogUtil.LOG_DELETE_TYPE, map);
					}
				}
			}
			PrintWriter pWriter = response.getWriter();
			pWriter.write("succ");
			pWriter.flush();
			pWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
