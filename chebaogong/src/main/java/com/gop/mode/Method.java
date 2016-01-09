package com.gop.mode;

import java.util.Arrays;

public class Method {

	private String id;
	private String method;
	private String[] args;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "Method [id=" + id + ", method=" + method + ", args=" + Arrays.toString(args) + "]";
	}

}
