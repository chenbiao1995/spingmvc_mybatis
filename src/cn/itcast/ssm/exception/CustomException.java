package cn.itcast.ssm.exception;
//系统定义异常类,针对预期的异常，需要再程序中抛出次类异常
public class CustomException extends Exception {
	//异常信息
	public String message;
	public CustomException(String message) {
		super(message);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
