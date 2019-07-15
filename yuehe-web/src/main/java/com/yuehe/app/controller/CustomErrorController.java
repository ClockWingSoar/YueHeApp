package com.yuehe.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuehe.app.util.BaseResponse;
import com.yuehe.app.util.YueHeUtil;

/**
	 * 通用错误处理器.
	 * @author Wang.ch
	 *
	 */
	//@Order(Ordered.HIGHEST_PRECEDENCE)
	//@ControllerAdvice
	//@Controller
	//@RequestMapping("${server.error.path:${error.path:/error}}")
	public class CustomErrorController extends AbstractErrorController {
	    public CustomErrorController(ErrorAttributes errorAttributes) {
	        super(errorAttributes);
	    }
	    private static final Logger log = LoggerFactory.getLogger(CustomErrorController.class);
	    @Value("${server.error.path:${error.path:/error}}")
	    private static String errorPath = "/error";

	    /**
	     * 500错误.
	     * @param req
	     * @param rsp
	     * @param ex
	     * @return
	     * @throws Exception
	     */
	    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	    @ExceptionHandler(Exception.class)
	    public ModelAndView serverError(HttpServletRequest req, HttpServletResponse rsp, Exception ex) throws Exception {
	        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/api/**");
	        if (matcher.matches(req)) {
	            log.error("!!! request uri:{} from {} server exception:{}", req.getRequestURI(), YueHeUtil.getIpAddress(req), ex.getMessage());
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.setSerializationInclusion(Include.NON_NULL);
	            String msg = mapper.writeValueAsString(BaseResponse.newFail(BaseResponse.STATUS_ERROR, "系统繁忙,请稍候重试"));
	            return handleJSONError(rsp, msg, HttpStatus.OK);
	        } else {
	            throw ex;
	        }
	    }

	    /**
	     * 404的拦截.
	     * @param request
	     * @param response
	     * @param ex
	     * @return
	     * @throws Exception
	     */
	    @ResponseStatus(code = HttpStatus.NOT_FOUND)
	    @ExceptionHandler(NoHandlerFoundException.class)
	    public ResponseEntity<?> notFound(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
	            log.error("!!! request uri:{} from {} not found exception:{}", request.getRequestURI(), YueHeUtil.getIpAddress(request), ex.getMessage());
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.setSerializationInclusion(Include.NON_NULL);
	            String msg = mapper.writeValueAsString(BaseResponse.newFail(BaseResponse.STATUS_BADREQUEST, "你访问的资源不存在"));
	            handleJSONError(response, msg, HttpStatus.OK);
	            return null;
	    }

	    /**
	     * 参数不完整错误.
	     * @param req
	     * @param rsp
	     * @param ex
	     * @return
	     * @throws Exception
	     */
	    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ModelAndView methodArgumentNotValidException(HttpServletRequest req, HttpServletResponse rsp, MethodArgumentNotValidException ex) throws Exception {
	        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/api/**");
	        if (matcher.matches(req)) {
	            BindingResult result = ex.getBindingResult();
	            List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
	            StringBuffer msg = new StringBuffer();
	            fieldErrors.stream().forEach(fieldError -> {
	                msg.append("[" + fieldError.getField() + "," + fieldError.getDefaultMessage() + "]");
	            });
	            ObjectMapper mapper = new ObjectMapper();
	            mapper.setSerializationInclusion(Include.NON_NULL);
	            String json = mapper.writeValueAsString(BaseResponse.newFail(BaseResponse.STATUS_BADREQUEST, "参数不合法:" + msg.toString()));
	            return handleJSONError(rsp, json, HttpStatus.OK);
	        } else {
	            throw ex;
	        }
	    }

	    @RequestMapping
	    @ResponseBody
	    public ResponseEntity<?> handleErrors(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        HttpStatus status = getStatus(request);
	        if (status == HttpStatus.NOT_FOUND) {
	            return notFound(request, response, null);
	        }
	        return handleErrors(request, response);
	    }

	    @RequestMapping(produces = "text/html")
	    public ModelAndView handleHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        return null;
	    }

	    protected ModelAndView handleViewError(String url, String errorStack, String errorMessage, String viewName) {
	        ModelAndView mav = new ModelAndView();
	        mav.addObject("exception", errorStack);
	        mav.addObject("url", url);
	        mav.addObject("msg", errorMessage);
	        mav.addObject("timestamp", new Date());
	        mav.setViewName(viewName);
	        return mav;
	    }

	    protected ModelAndView handleJSONError(HttpServletResponse rsp, String errorMessage, HttpStatus status) throws IOException {
	        rsp.setCharacterEncoding("UTF-8");
	        rsp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
	        rsp.setStatus(status.value());
	        PrintWriter writer = rsp.getWriter();
	        writer.write(errorMessage);
	        writer.flush();
	        writer.close();
	        return null;
	    }

	    @Override
	    public String getErrorPath() {
	        return errorPath;
	    }

}
