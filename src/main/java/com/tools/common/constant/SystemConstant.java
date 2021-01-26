package com.tools.common.constant;

/**
 * 系统级静态变量
 * 爪哇笔记 https://blog.52itstyle.vip
 * @author 小柒2012
 */
public class SystemConstant {

	/**
	 * 文件分隔符
	 */
	public static final String SF_FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * 数据标识
	 */
	public static final String DATA_ROWS = "rows";

	/**
	 * 成功
	 */
	public static final String SUCCESS = "success";
	/**
	 * 失败
	 */
	public static final String ERROR = "error";
	/**
	 * 分頁
	 */
	public static final Integer PAGE_SIZE = 10;

	public static final String FILE = "file";

	/**
	 * 真
	 */
	public static final String TRUE = "true";
	/**
	 * 假
	 */
	public static final String FALSE = "false";

	/**
	 * 删除
	 */
	public static final Short DELETE_STATUS_YES = 0;

	public static final Short DELETE_STATUS_NO = 1;


	/**
	 * 色情：0  正常 1 色情
	 */
	public static final Short PORN_STATUS_YES = 1;

	public static final Short PORN_STATUS_NO = 0;

	/**
	 * 头像：0  默认 1 上传
	 */
	public static final Short AVATAR_STATUS_YES = 1;

	public static final Short AVATAR_STATUS_NO = 0;

    /**
     * 订阅消息：0  否 1 是
     */
    public static final Short SUBSCRIBE_STATUS_YES = 1;

    public static final Short SUBSCRIBE_STATUS_NO = 0;

    /**
     * 今日推荐：0  否 1 是
     */
    public static final Short HOT_STATUS_YES = 1;

    public static final Short HOT_STATUS_NO = 0;

    /**
     * 签到：0  否 1 是
     */
    public static final Short SIGN_STATUS_YES = 1;

    public static final Short SIGN_STATUS_NO = 0;


    /**
     * 500 标识
     */
    public static final String CODE_500 = "500";

	/**
	 * 200 标识
	 */
	public static final String CODE_200 = "200";

	/**
	 * CODE 标识
	 */
	public static final String CODE_0 = "0";

	/**
	 * 菜单类型
	 */
	public enum MenuType {
		/**
		 * 目录
		 */
		CATALOG(0),
		/**
		 * 菜单
		 */
		MENU(1),
		/**
		 * 按钮
		 */
		BUTTON(2);

		private final int value;

		private MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 通用字典
	 */
	public enum MacroType {

		/**
		 * 类型
		 */
		TYPE(0),

		/**
		 * 参数
		 */
		PARAM(1);

		private final int value;

		private MacroType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	/**
	 * 通用变量，表示可用、禁用、显示、隐藏、是、否
	 */
	public enum StatusType {

		/**
		 * 禁用，隐藏
		 */
		DISABLE(0),

		/**
		 * 可用，显示
		 */
		ENABLE(1),

		/**
		 * 显示
		 */
		SHOW(1),

		/**
		 * 隐藏
		 */
		HIDDEN(0),

		/**
		 * 是
		 */
		YES(1),

		/**
		 * 否
		 */
		NO(0);

		private final int value;

		private StatusType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
