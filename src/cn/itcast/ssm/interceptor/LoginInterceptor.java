package cn.itcast.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//登陆认证的拦截器
public class LoginInterceptor implements HandlerInterceptor{
	//执行Handler方法之后
	//应用场景：统一异常处理、统一日志处理
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}
	//进入Handler方法之后，返回modleandview之前
	//应用场景从ModelAndView出发，将公用的模型数据（比如菜单的导航）传到视图，也可以在这里边统一指定视图
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	//在进入Handler方法之前执行
	//用于身份认证和身份授权
	//比如身份认证，如果身份认证不通过，表示当前用户没有登陆，需要此方法拦截不再向下执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		//return false 标识拦截，不再向下执行
		//获取请求的url
		String url = request.getRequestURI();
		//判断url是否是公开地址（实际使用时将公开地址配置到文件中）
		//这里公开地址是登陆提交的地址
		if (url.indexOf("login.action")>0) { 
			//如果进行登陆提交，放行
			return true;
		}
		//判断session
	HttpSession session = request.getSession();
	//从session中取出用户身份信息
	String username = (String) session.getAttribute("username");
	if (username!=null) {
		//身份存在，放行
		return true;
	}
	//执行到这里标识用户身份需要认证，跳转到登陆页面
	request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	return false;
	}

}
