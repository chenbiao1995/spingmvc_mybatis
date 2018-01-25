package cn.itcast.ssm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.controller.validation.ValidationGroup1;
import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

//商品的controller
@Controller
//为了对url进行分类管理，可以在这里定义根路径，最终访问url是根路径+子路径
@RequestMapping("/items")
public class ItemsController {
	@Autowired
	private ItemsService itemsService;
	//商品查询
	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request,ItemsQueryVo itemsQueryVo)throws Exception {
		
		List<ItemsCustom> itemsList = itemsService.findItemsList(null);

		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("items/itemList");

		return modelAndView;
	}

	//商品信息修改页面展示
//	@RequestMapping("/editItems")
//	public ModelAndView editItems() throws Exception {
//		//调用service根据商品id查询商品信息
//		ItemsCustom itemsCustom = itemsService.findItemsById(1);
//		ModelAndView modelAndView = new ModelAndView();
//		//将商品信息放到model
//		modelAndView.addObject("itemsCustom", itemsCustom);
//		modelAndView.setViewName("items/editItems");
//		return modelAndView;
//
//	}
//	
	//@RequestMapping("/editItems")
	//限制http请求方法可以post和get
	@RequestMapping(value="/editItems",method={RequestMethod.POST,RequestMethod.GET})
	//商品信息修改提交
	//在需要校验的pojo前边添加@Validated 在需要校验的pojo后边添加BindingResult来接收校验信息
	//注意@Validated和BindingResult bindingResult是配对出现的，并且形参顺序是固定的（一前一后）。
	//value= {ValidationGroup1.class}:指定使用ValidationGroup1分组的校验
	//@ModelAttribute：指定pojo回显到页面中的request的key
	public String editItemsSubmit(Model model,HttpServletRequest request,Integer id,
			@ModelAttribute(value="items") @Validated(value= {ValidationGroup1.class}) ItemsCustom itemsCustom,BindingResult bindingResult,
			MultipartFile items_pic) throws Exception {
//		request.getParameter("id");
		//获取校验错误信息
		if(bindingResult.hasErrors()) {
			//输出错误信息
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for( ObjectError error:allErrors) {
				//输出错误信息
				System.out.println(error.getDefaultMessage());
			}
			//将错误信息传到页面
			model.addAttribute("allErrors", allErrors);
			//出错重新到商品修改页面
			return "items/editItems";
		}
		
		String originalFilename = items_pic.getOriginalFilename();
		//上传图片
		if (items_pic!=null && originalFilename!=null && originalFilename.length()>0) {
			//存储图片的物理路径
			String pic_path = "C:\\Users\\CB\\Pictures\\Saved Pictures";
			//上传图片的原始名称
			//新的图片名称
			String newFilename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
			//新的图片
			File newFile = new File(pic_path+newFilename);
			//将内存中的数据写入磁盘
			items_pic.transferTo(newFile);
			//将新的图片名称写到itemsCustom中
			itemsCustom.setPic(newFilename);
			
		}
		//调用service更新商品信息，页面需要将商品信息传到此页面
		itemsService.updateItems(id, itemsCustom);
		//返回ModelAndView
		//重定向到商品的查询列表
//		return "redirect:queryItems.action";
		//页面转发
		return "forward:queryItems.action";

	}
	@RequestMapping("/editItems")
	//@RequestParam里边指定request传入参数名称和形参进行绑定
	//required属性指定参数是否需要传入
	public String editItems(Model model,@RequestParam(value="id",required=true) Integer items_id ) throws Exception {
		//调用service根据商品id查询商品信息
		ItemsCustom itemsCustom = itemsService.findItemsById(items_id);
		//判断商品是否为空，根据id了没有查询到商品，抛出一场，提示用户商品信息不存在
		if(itemsCustom == null){
			throw new CustomException("修改的商品信息不存在");
		}
		//通过形参中的model将model中的数据传到页面
		model.addAttribute("itemsCustom",itemsCustom);
		return "items/editItems";

	}
	
	//查询商品信息，输出json
	///itemsView/{id}里边的{id}表示将这个位置的参数传到@PathVariable指定的名称中
	@RequestMapping("/itemsView/{id}/{type}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id,@PathVariable("type") String abc) throws Exception {
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
		return itemsCustom;
	}
	//批量删除商品信息
	@RequestMapping("/deleteItems")
	public String deleteItems(Integer[] items_id) throws Exception {
		//调用service批量删除商品
		//
		return "success";
	}
	//批量修改商品页面，将商品信息查询出来，在页面中可以编辑商品信息
	@RequestMapping("/editItemsQuery")
	public ModelAndView editItemsQuery(HttpServletRequest request,ItemsQueryVo itemsQueryVo)throws Exception {
		
		List<ItemsCustom> itemsList = itemsService.findItemsList(null);

		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("items/editItemsQuery");

		return modelAndView;
	}
	//批量修改商品提交
	//通过itemsQueryVo接收批量提交的商品信息，把商品信息存储到itemsQueryVo中的itemList属性中
	@RequestMapping("/editItemsAllSubmit.action")
	public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception {
		return "success";
	}
}
