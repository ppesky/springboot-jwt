package io.ppesky.auth.web.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DefaultResponseEntity<E> {

	private long timestamp;
	
	private E entity;
	
}
