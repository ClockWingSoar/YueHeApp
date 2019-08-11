package com.yuehe.app.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.yuehe.app.entity.Tool;
import com.yuehe.app.service.ToolService;
import com.yuehe.app.util.IdType;
import com.yuehe.app.util.YueHeUtil;

//import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ToolController{
	 @ModelAttribute("module")
	    String module() {
	        return "tool";
	    }
	 private final static Logger LOGGER = LoggerFactory.getLogger(ToolController.class);
	 private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
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
		model.addAttribute("subModule", "toolList");
		return "user/toolList.html";
	}
	@GetMapping("/getToolNewItem")
	public String toolNewItem(Model model) {
		model.addAttribute("subModule", "toolNewItem");

		return "user/toolNewItem.html";
	}
	
	@GetMapping("/tool/edit/{id}")
	public String toolEditItem(Model model, @PathVariable("id") String id) {
		getToolDetail(model, id);
		return "user/toolEditItem.html";
	}
	public void getToolDetail(Model model, String id) {
		Tool tool = toolService.getById(id);
		model.addAttribute("tool", tool);
	}

	@PostMapping("/tool/update/{id}")
	public String updateToolItem(Model model, @PathVariable("id") String id, @Valid Tool tool, BindingResult result,
			RedirectAttributes attr) {
		if (result.hasErrors()) {
			// to add back the initial data of tool edit item and the error message
			LOGGER.debug("result tool:{}", result.getAllErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.tool", result);
			model.addAttribute("tool", tool);
			return "user/toolEditItem.html";
		}
		LOGGER.debug("update tool:", tool);
		String buyDate = tool.getBuyDate();
		try {
			tool.setBuyDate(simpleDateFormat.format(new SimpleDateFormat("MM/dd/yyyy").parse(buyDate)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (tool != null) {
			LOGGER.info("updated {}", toolService.create(tool));
		}
		attr.addFlashAttribute("message", "仪器-" + id + " 更新成功");
		return "redirect:/getToolList";
	}

	@GetMapping("/tool/delete/{id}")
	public String deleteToolItem(@PathVariable("id") String id, Tool tool, Model model, RedirectAttributes attr) {
		System.err.println("delete tool item with id=" + id);
		// Tool tool
		LOGGER.info("deleting {}", toolService.getById(id));
		LOGGER.info("deleting frontend tool{}", tool);
		// toolService.delete(tool);
		toolService.deleteById(id);
		attr.addFlashAttribute("message", "仪器-" + id + " 删除成功");
		return "redirect:/getToolList";
	}
	@PostMapping("/createTool")
    public String createTool( @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "major", required = false) String major,
                                       @RequestParam(name = "price", required = false) int price,
                                       @RequestParam(name = "buyDate", required = false) Date buyDate,
                                       @RequestParam(name = "buyFrom", required = false) String buyFrom,
                                       @RequestParam(name = "operateExpense", required = false) String operateExpense,
									   @RequestParam(name = "description", required = false) String description, 
									   RedirectAttributes attr
                                       ) 
	{
        int idNums = toolService.getBiggestIdNumber();
        String id = YueHeUtil.getId(IdType.TOOL,idNums);
        Tool tool =new Tool();
        tool.setId(id);
        tool.setName(name);
        tool.setMajor(major);
        tool.setPrice(price);
        tool.setBuyDate(simpleDateFormat.format(buyDate));
        tool.setBuyFrom(buyFrom);
        tool.setDescription(description);
        LOGGER.info("tool:",tool);

        if (tool != null) {
            LOGGER.info("Saved {}", toolService.create(tool));
        }
		attr.addFlashAttribute("message", "客户-" + id + " 创建成功");
        return "redirect:/getToolList";
    }
	
    

}
