package com.yuehe.app.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuehe.app.entity.Tool;
import com.yuehe.app.service.ToolService;
import com.yuehe.app.util.IdType;
import com.yuehe.app.util.YueHeUtil;


@Controller
public class ToolController{
	 @ModelAttribute("module")
	    String module() {
	        return "tool";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(ToolController.class);
	
	@Autowired
	private final ToolService toolService;
	@Autowired
	public ToolController(ToolService toolService) {
		this.toolService = toolService;
	}

	@GetMapping("/getToolList") 
	public  String toolOverview(Model model){
		List<Tool> toolList =new ArrayList<Tool>();
		toolList = toolService.getAllTools();
		 LOGGER.info("toolList {}", toolList);
		model.addAttribute("toolList",toolList);
		
		return "user/tool.html";
	}
	@PostMapping("/createTool")
    public String createTool( @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "major", required = false) String major,
                                       @RequestParam(name = "price", required = false) int price,
                                       @RequestParam(name = "buyDate", required = false) String buyDate,
                                       @RequestParam(name = "buyFrom", required = false) String buyFrom,
                                       @RequestParam(name = "operateExpense", required = false) String operateExpense,
                                       @RequestParam(name = "description", required = false) String description
                                       ) 
	{
        int idNums = toolService.getBiggestIdNumber();
        String id = YueHeUtil.getId(IdType.TOOL,idNums);
        Tool tool =new Tool();
        tool.setId(id);
        tool.setName(name);
        tool.setMajor(major);
        tool.setPrice(price);
        try {
        		tool.setBuyDate(new SimpleDateFormat("dd/MM/yyyy").parse(buyDate));
     		} catch (ParseException e) {
     			e.printStackTrace();
     		}
        tool.setBuyFrom(buyFrom);
        tool.setDescription(description);
        LOGGER.info("tool:",tool);

        if (tool != null) {
            LOGGER.info("Saved {}", toolService.create(tool));
        }

        return "redirect:/getToolList";
    }
	
    

}
