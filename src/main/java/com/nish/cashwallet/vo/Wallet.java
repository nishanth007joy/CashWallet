package com.nish.cashwallet.vo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class Wallet {

	@NotNull(message = "Please provide notes to be converted")
	private List<Integer> notes;
	
	@NotNull(message = "Please provide total amount to be converted")
	private Integer amount;

}
