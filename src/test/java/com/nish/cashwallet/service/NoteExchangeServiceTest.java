package com.nish.cashwallet.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.nish.cashwallet.vo.Wallet;

class NoteExchangeServiceTest {

	private NoteExchangeService exchangeService = new NoteExchangeServiceImpl();
	@Test
	void testExchangeNotesInWalletTohighDenomination() {
		ReflectionTestUtils.setField(exchangeService, "currencyDeonimications", Arrays.asList(50, 20, 10, 5, 1));
		Wallet walletActual = exchangeService.exchangeNotesInWalletTohighDenomination(Wallet.builder().amount(100).build());
		List<Integer> notes = Arrays.asList(50,50);
		assertThat(walletActual).isEqualTo(Wallet.builder().notes(notes).build());
	}
	
	@Test
	void testExchangeNotesInWalletTohighDenominationHavingTotal110() {
		ReflectionTestUtils.setField(exchangeService, "currencyDeonimications", Arrays.asList(50, 20, 10, 5, 1));
		Wallet walletActual = exchangeService.exchangeNotesInWalletTohighDenomination(Wallet.builder().amount(110).build());
		List<Integer> notes = Arrays.asList(50,50,10);
		assertThat(walletActual).isEqualTo(Wallet.builder().notes(notes).build());
	}

}
