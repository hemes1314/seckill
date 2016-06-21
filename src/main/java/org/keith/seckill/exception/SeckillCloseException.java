package org.keith.seckill.exception;

/**
 * 秒杀关闭异常
 */
public class SeckillCloseException extends SeckillException {

	private static final long serialVersionUID = 7479245018762918458L;

	public SeckillCloseException(String message) {
		super(message);
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

}
