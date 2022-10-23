/*
 * Copyright 2018 datagear.tech
 *
 * Licensed under the LGPLv3 license:
 * http://www.gnu.org/licenses/lgpl-3.0.html
 */

package org.datagear.analysis;

import java.io.Serializable;

import org.datagear.util.i18n.AbstractLabeled;
import org.datagear.util.i18n.Labeled;

/**
 * 图表属性。
 * <p>
 * 此类描述{@linkplain ChartPlugin#renderChart(RenderContext, ChartDefinition)}的{@linkplain ChartDefinition#setAttrValues(java.util.Map)}支持设置的属性元信息。
 * </p>
 * 
 * @author datagear@163.com
 *
 */
public class ChartAttribute extends AbstractLabeled implements DataNameType, Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_TYPE = "type";
	public static final String PROPERTY_NAME_LABEL = Labeled.PROPERTY_NAME_LABEL;
	public static final String PROPERTY_DESC_LABEL = Labeled.PROPERTY_DESC_LABEL;
	public static final String PROPERTY_REQUIRED = "required";
	public static final String PROPERTY_ARRAY = "array";
	public static final String PROPERTY_INPUT_TYPE = "inputType";
	public static final String PROPERTY_INPUT_PAYLOAD = "inputPayload";

	/** 名称 */
	private String name;

	/** 类型 */
	private String type;

	/** 是否必须 */
	private boolean required;
	
	/**是否数组*/
	private boolean array;

	/** 界面输入框类型 */
	private String inputType = "";

	/** 界面输入框载荷，比如：输入框为下拉选择时，定义选项内容JSON；输入概况为日期时，定义日期格式 */
	private String inputPayload = "";

	public ChartAttribute()
	{
	}

	public ChartAttribute(String name, String type, boolean required, boolean array)
	{
		super();
		this.name = name;
		this.type = type;
		this.required = required;
		this.array = array;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 获取数据类型，参考{@linkplain DataType}。
	 */
	@Override
	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public boolean isArray()
	{
		return array;
	}

	public void setArray(boolean array)
	{
		this.array = array;
	}

	/**
	 * 获取输入框类型，常用类型参考{@linkplain InputType}。
	 * 
	 * @return
	 */
	public String getInputType()
	{
		return inputType;
	}

	public void setInputType(String inputType)
	{
		this.inputType = inputType;
	}

	public String getInputPayload()
	{
		return inputPayload;
	}

	public void setInputPayload(String inputPayload)
	{
		this.inputPayload = inputPayload;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " [name=" + name + ", type=" + type + "]";
	}

	/**
	 * {@linkplain ChartAttribute#getType()}枚举。
	 * 
	 * @author datagear@163.com
	 *
	 */
	public static class DataType
	{
		/** 字符串 */
		public static final String STRING = "STRING";

		/** 布尔值 */
		public static final String BOOLEAN = "BOOLEAN";

		/** 数值 */
		public static final String NUMBER = "NUMBER";
	}
	
	/**
	 * 常用的{@linkplain ChartAttribute#getInputType()}枚举。
	 * 
	 * @author datagear@163.com
	 *
	 */
	public static class InputType
	{
		/** 文本框 */
		public static final String TEXT = "text";

		/** 下拉框 */
		public static final String SELECT = "select";

		/** 单选框 */
		public static final String RADIO = "radio";

		/** 复选框 */
		public static final String CHECKBOX = "checkbox";

		/** 文本域 */
		public static final String TEXTAREA = "textarea";

		/** 日期 */
		public static final String DATE = "date";

		/** 时间 */
		public static final String TIME = "time";

		/** 日期时间 */
		public static final String DATETIME = "datetime";

		/** 颜色 */
		public static final String COLOR = "color";
	}
}