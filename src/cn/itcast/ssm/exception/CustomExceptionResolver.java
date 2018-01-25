package cn.itcast.ssm.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
//全局异常处理器
public class CustomExceptionResolver implements HandlerExceptionResolver {

	@Override
	//handler：就是处理器适配器要执行Handler对象（只有method）
	//ex:系统抛出的异常
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
		//解析除异常类型
		
		//如果该异常类型是系统自定义的异常，直接取出异常信息，在错误页面展示
//		String message = null;
//		if( ex instanceof CustomException) {
//			message = ((CustomException)ex).getMessage();
//		}else{
//			//如果该异常类型不是系统自定义的异常，构造一个自定义异常类型（信息为“未知错误”）
//			message = "未知错误";
//		}
		//上边的代码变为
		CustomException customException = null;
		if(ex instanceof CustomException) {
			customException = (CustomException)ex;
		}else {
			customException = new CustomException("未知错误");
		}
		//错误信息
		String message = customException.getMessage();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message",message);
		modelAndView.setViewName("error");
		return modelAndView;
	}

}