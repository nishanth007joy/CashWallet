package com.nish.cashwallet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nish.cashwallet.vo.Wallet;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteExchangeServiceImpl implements NoteExchangeService {
	
	@Value("#{'${standard.notes}'.split(',')}") 
	private List<Integer> currencyDeonimications;

	@Override
	public Wallet exchangeNotesInWalletTohighDenomination(Wallet wallet) {
		log.info("Started conversion of currency to bigger denomincation");
		Integer amount = wallet.getAmount();
		return Wallet.builder().notes(recursiveConversion(amount)).build();
	}

	/**
	 * Recursive logic to find highest denomination currency
	 * 
	 * @param balanceAmount
	 * @return
	 */
	private List<Integer> recursiveConversion(Integer balanceAmount) {
		Optional<Integer> startingCurrency = currencyDeonimications.stream()
				.filter(currencyVal -> currencyVal <= balanceAmount).findFirst();
		List<Integer> convertedCurrency = new ArrayList<>();
		if (startingCurrency.isPresent()) {
			int numberOfCurrency = balanceAmount / startingCurrency.get();
			int balance = balanceAmount % startingCurrency.get();
			convertedCurrency = IntStream.range(1, numberOfCurrency + 1).boxed().map(currenct -> startingCurrency.get())
					.collect(Collectors.toList());
			if (balance > 0) {
				convertedCurrency.addAll(recursiveConversion(balance));
			}
		}
		return convertedCurrency;
	}

}
