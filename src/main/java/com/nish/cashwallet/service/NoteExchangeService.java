package com.nish.cashwallet.service;

import com.nish.cashwallet.vo.Wallet;

public interface NoteExchangeService {
	/**
	 * Handles exchange of notes in wallet to high denomincation notes
	 * 
	 * @param wallet wallet abstracting notes and total value in wallet
	 * @return returns a wallet where notes are replaced with high denomination
	 *         notes
	 */
	public Wallet exchangeNotesInWalletTohighDenomination(Wallet wallet);
}
