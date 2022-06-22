package com.nish.cashwallet.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.nish.cashwallet.service.NoteExchangeService;
import com.nish.cashwallet.vo.Wallet;

@ExtendWith(SpringExtension.class)
class WalletRestControllerTest {

	@Mock
	private NoteExchangeService noteExchangeService;

	@InjectMocks
	private WalletRestController walletRestController;

	@Test
	void testWalletChanger() {
		ReflectionTestUtils.setField(walletRestController, "currencyDeonimications", Arrays.asList(50, 20, 10, 5, 1));
		when(noteExchangeService.exchangeNotesInWalletTohighDenomination(ArgumentMatchers.any(Wallet.class)))
				.thenReturn(Wallet.builder().notes(Arrays.asList(50, 50)).amount(100).build());
		Wallet walletActual = walletRestController
				.walletChanger(Wallet.builder().amount(100).notes(Arrays.asList(50, 20, 10, 10, 10)).build());
		assertThat(walletActual).isEqualTo(Wallet.builder().notes(Arrays.asList(50, 50)).amount(100).build());

	}

	@Test
	void testWalletChangerInvalidNotes() {
		ReflectionTestUtils.setField(walletRestController, "currencyDeonimications", Arrays.asList(50, 20, 10, 5, 1));
		when(noteExchangeService.exchangeNotesInWalletTohighDenomination(ArgumentMatchers.any(Wallet.class)))
				.thenReturn(Wallet.builder().notes(Arrays.asList(50, 50)).amount(100).build());
		assertThrows(IllegalArgumentException.class, () -> {
			walletRestController
					.walletChanger(Wallet.builder().amount(100).notes(Arrays.asList(50, 20, 10, 10, 11)).build());
		});
	}
	
	@Test
	void testWalletChangerTotalMismatch() {
		ReflectionTestUtils.setField(walletRestController, "currencyDeonimications", Arrays.asList(50, 20, 10, 5, 1));
		when(noteExchangeService.exchangeNotesInWalletTohighDenomination(ArgumentMatchers.any(Wallet.class)))
				.thenReturn(Wallet.builder().notes(Arrays.asList(50, 50)).amount(100).build());
		Wallet walletActual = walletRestController
				.walletChanger(Wallet.builder().amount(101).notes(Arrays.asList(50, 20, 10, 10, 10)).build());
		assertThat(walletActual).isEqualTo(Wallet.builder().notes(Arrays.asList()).amount(null).build());

	}

}
