package cn.itcast.ssm.controller.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.cglib.core.Converter;

//实现converter接口
public class CustomDateConverter implements org.springframework.core.convert.converter.Converter<String, Date> {

	@Override
	public Date convert(String source) {
		//将日期串转成日期类型（格式）
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			//转换成功返回
			return simpleDateFormat.parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//如果参数绑定失败
		return null;
	}

}
