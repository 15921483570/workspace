package com.gop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gop.PostRpc.PostService;
import com.gop.mode.Method;
import com.gop.rpc.model.JsonRpcModel;

@Controller
@Scope("prototype")
public class RpcMethodController {

	@Autowired
	@Qualifier("postService")
	private PostService postService;

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@RequestMapping("/exe")
	@ResponseBody
	public String executeMethod(@RequestBody String methodStr) {
		System.out.println(methodStr + "=========");
		Method method = JSONObject.parseObject(methodStr, Method.class);
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("code", 200);
		String result = null;
		try {
			redisTemplate.opsForValue().set(method.getId(), methodStr);
			result = postService.exeMethod(new JsonRpcModel(method.getId(), method.getMethod(), method.getArgs()));
		} catch (Exception e) {
			jsonObject.put("code", 400);
			jsonObject.put("result", e.getMessage());
			return jsonObject.toJSONString();
		}
		jsonObject.put("result", result);
		return jsonObject.toJSONString();
	}

	@RequestMapping("/query/{id}")
	@ResponseBody
	public String getExe(@PathVariable("id") String id) {
		System.out.println(id);
		JSONObject jsonObject = new JSONObject();
		String result = null;
		try {

			result = redisTemplate.opsForValue().get(id);
		} catch (Exception e) {
			jsonObject.put("code", 400);
			jsonObject.put("result", e.getMessage());
			return jsonObject.toJSONString();
		}
		jsonObject.put("result", result);
		return jsonObject.toJSONString();
	}

}
