package com.nish.cashwallet.api;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nish.cashwallet.service.NoteExchangeService;
import com.nish.cashwallet.vo.Wallet;

@RestController
@RequestMapping("/api/v1")
public class WalletRestController {
	
	@Value("#{'${standard.notes}'.split(',')}") 
	private List<Integer> currencyDeonimications;
	
	@Autowired
	private NoteExchangeService noteExchangeService;

	@PostMapping(path = "/wallet/noteexchange")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Wallet walletChanger(@RequestBody @Validated Wallet wallet) {
		if(wallet.getNotes().stream().anyMatch(note -> !currencyDeonimications.contains(note))) {
			throw new IllegalArgumentException("Invalid notes");
		}
		int totalAmount = wallet.getNotes().stream().reduce(0,Integer::sum);
		if(totalAmount != wallet.getAmount()) {
			return Wallet.builder().notes(Collections.EMPTY_LIST).build();
		}
		return noteExchangeService.exchangeNotesInWalletTohighDenomination(wallet);

	}
}
