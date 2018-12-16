package com.yuewen.web;

import com.yuewen.Constains;
import com.yuewen.entity.Flight;
import com.yuewen.service.FlightService;
import com.yuewen.vo.ResponseBody;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/flight")
public class FlightController {

	private Logger logger = LoggerFactory.getLogger(FlightController.class);
	@Autowired
	private FlightService flightService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseBody saveFlight(Flight flight) {

		Flight self = flightService.save(flight);
		return new ResponseBody(null, "", true);
	}

	@RequestMapping(value = "/findByCategory", method = RequestMethod.GET)
	public ResponseBody findByCategory(@RequestParam(value = "category", required = true) String category,
									   @RequestParam(value="page", required = false) String page,
									   @RequestParam(value="page", required = false) String pageSize) {
		int pageInt = -1;
		int pageSizeInt = Integer.MAX_VALUE;

		try {
			if(!StringUtils.isEmpty(page)){
				pageInt = Integer.parseInt(page);
			}
			if(!StringUtils.isEmpty(pageSize)){
				pageSizeInt = Integer.parseInt(pageSize);
			}
		} catch (NumberFormatException e) {
			logger.info(e.getMessage());
			return new ResponseBody(null, Constains.ERROR_MSG_WRONG_PARAMTER, false);
		}
		Page<Flight> resultInPage = flightService.findByCategory(category, pageInt, pageSizeInt);
		List<Flight> result = resultInPage.getContent();
		long totalElements = resultInPage.getTotalElements();
		int totalPages = resultInPage.getTotalPages();
		return new ResponseBody(result, "", true, pageInt, pageSizeInt,
				totalElements, totalPages);
	}

}
