package gd.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.collections.List;
import gd.web.entity.ChartDataEntity;
import gd.web.entity.StationEntity;
import gd.web.service.ChartDataService;
import gd.web.entity.viewModel.*;
import gd.web.util.Util;
import gd.web.util.Enum;

@Controller
@RequestMapping(value="/chartdata")
public class ChartDataController {
	@Autowired
	private ChartDataService chartDataService;

	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	

	/**
	 *  an initialization method
	 *  when this method will be called? 
	 *  	when OPERATOR sign in, Ajax nothing to return.
	 *  	when ADMINISTRATOR want to watch the data of the station, Ajax nothing to return.
	 *  	when 
	 * @param staId
	 * @param currHour
	 */
	@RequestMapping(value="/init/{staId}",method = RequestMethod.POST)
	public void initChart(@PathVariable int staId,int currHour){
		ChartDataEntity chartDataEntity = chartDataService.getEntityByStaId(staId,Enum.IN.toString());
		if(chartDataEntity == null){
			return;
		}
		//get miss hours that user unlogin.
		int missHour = currHour+24-chartDataEntity.getCurrHour();
		if(missHour>0){
			chartDataService.resetChart(missHour,staId);
		}
	}
	/**
	 * a recipient method for Ajax. 
	 * @param id
	 * @param datas
	 * @param currHour
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/update/{id}",method = RequestMethod.POST)
	public @ResponseBody UserInfo updateChartData(@PathVariable int id,String datas,int currHour,HttpSession session){
		
		UserInfo ui = new UserInfo();
		
		if((Integer)session.getAttribute("staId") != null && (Integer)session.getAttribute("staId")!= 0){
			ServletContext context = session.getServletContext(); 
			Map<Integer,String> userMap = (Map<Integer, String>) context.getAttribute("user_map");
			if(userMap == null){
				return null;
			}

			if(!userMap.containsKey((Integer)session.getAttribute("userId"))){
				session.setAttribute("priv", Enum.NULLPRIV.toString());
				session.removeAttribute("userId");
				session.removeAttribute("userName");
				session.removeAttribute("staId");
			}
		}
		if(session.getAttribute("priv")==null || !session.getAttribute("priv").equals(Enum.OPERATOR.toString())){
			return null;
		}
		ChartDataEntity chartDataEntity = chartDataService.getEntityById(id);
		if(chartDataEntity==null){
			return null;
		}
		if(session.isNew()){
			System.out.println("the session is new and that means something wrong!!");
		}
		//
/*		System.out.println("\nstart of the updateChartData!!!!");
		try{
			System.out.println("user id is : "+session.getAttribute("userId") + " user name is : "+session.getAttribute("userName"));
			System.out.println(session.getId());
			System.out.println("create time is  : " + new Date(session.getCreationTime()));
			System.out.println("now : "+new Date());
			System.out.println("last access time to now(minutes) : " + (Calendar.getInstance().getTimeInMillis() - session.getLastAccessedTime())/(1000*60));
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
		System.out.println("end of the updateChartData!!!!\n");*/
		
		chartDataEntity.setDatas(datas);
		chartDataEntity.setCurrHour(currHour);
		chartDataEntity.setModifyTime(new Date());
		chartDataService.updateChartData(chartDataEntity);

		chartDataService.processStreamTable(chartDataEntity);
		return ui;
		
	}
	/**
	 * a recipient method for Ajax. get station dynamic data.
	 * @param clientHour
	 * @param staId
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/wholeState",method=RequestMethod.POST)
	public @ResponseBody ChartData wholeStateAjax(int clientHour,int staId,HttpSession session){
		/*if(session.getAttribute("staId")==null){
			return null;
		}*/
		
		
		return chartDataService.getReturnData(clientHour,staId);
	}
	
	/**
	 * go to the page.
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/wholeState",method = RequestMethod.GET)
	public String wholeState(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session==null){
			return "jsp/map";
		}
		/*session.getAttribute("userName");
		session.getAttribute("userId");
		session.getAttribute("priv");
		session.getAttribute("staId");
		


		System.out.println(session.getAttribute("userName"));
		System.out.println(session.getAttribute("userId"));
		System.out.println(session.getAttribute("priv"));
		System.out.println(session.getAttribute("staId"));	*/
		return "jsp/wholeState";
	}
	/**
	 * a recipient method for Ajax. get all station dynamic data.
	 * @param clientHour
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/wholeWholeState",method = RequestMethod.POST)
	public @ResponseBody ChartData wholeWholePost(int clientHour,HttpSession session){
		if(session.getAttribute("priv")==null){
			return null;
		}
		return chartDataService.getWholeWholeData(clientHour);
	}
	/**
	 * go to the page.
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/get/{id}",method = RequestMethod.GET)
	public String getWholeStateById(@PathVariable int id,Model model){
		model.addAttribute("staId", id);
		return "jsp/wholeState";
	}
	/**
	 * go to the page.
	 * @return
	 */
	@RequestMapping(value="/wholeWholeState",method = RequestMethod.GET)
	public String wholeWholeGet(){		
		return "jsp/wholeWholeState";
	}
	
	
	
	
	//uesless now!!!
	@RequestMapping(value="/add",method = RequestMethod.GET)
	public String setChartData(){
		ChartDataEntity cdeIN = new ChartDataEntity();
		cdeIN.setStaId(6);
		cdeIN.setCurrHour(Util.getCurrHour());
		cdeIN.setDirection(gd.web.util.Enum.IN.toString());
		//chartDataService.addChartData(cdeIN);
		ChartDataEntity cdeOUT = new ChartDataEntity();
		cdeOUT.setStaId(6);
		cdeOUT.setCurrHour(Util.getCurrHour());
		cdeOUT.setDirection(gd.web.util.Enum.OUT.toString());
		//chartDataService.addChartData(cdeOUT);
		return null;
	}
	
}
