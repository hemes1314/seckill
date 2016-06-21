package org.keith.seckill.exception;

/**
 * 重复秒杀异常（运行期异常）
 */
public class RepeatKillException extends SeckillException {
	private static final long serialVersionUID = -4400208434390336265L;

	public RepeatKillException(String message) {
		super(message);
	}

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

}
