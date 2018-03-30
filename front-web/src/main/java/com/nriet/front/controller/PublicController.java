package com.nriet.front.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/front")
public class PublicController {
	private final static Logger logger = LoggerFactory.getLogger(PublicController.class);

	@RequestMapping(value = "/")
	public String index() {
		logger.info("logback 成功了");
		logger.error("logback 成功了");
		return "index";
	}
}
