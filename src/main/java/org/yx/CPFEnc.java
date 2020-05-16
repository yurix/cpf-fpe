package org.yx;

import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.config.GenericDomain;
import com.idealista.fpe.config.GenericTransformations;

/**
 * Classe de Prova de Conceito para Criptografia e anonimização de dados de um CPF com preservação de formato.
 * (Brazilian CPF Format Preserving Encryption - FPE)
 * 
 * Geração de CPF reutilizada da lib
 * https://github.com/muriloferreira/utilities 
 * 
 * Lib de Format Preserving Encryption para Java
 * https://github.com/idealista/format-preserving-encryption-java
 * 
 * @author Yuri Negócio
 * 
 *
 */
public class CPFEnc {
	private FormatPreservingEncryption formatPreservingEncryption;
	
	
	
	public CPFEnc( byte[] key ) { 
		NumberAlphabet alphabet = new NumberAlphabet();
		GenericDomain numberDomain =  new GenericDomain ( alphabet, new GenericTransformations(alphabet.availableCharacters()), new GenericTransformations(alphabet.availableCharacters()));
		this.formatPreservingEncryption = FormatPreservingEncryptionBuilder
		        .ff1Implementation()
		        .withDomain( numberDomain )
		        .withDefaultPseudoRandomFunction(key)
		        .withDefaultLengthRange()
		        .build();
	}
	

	private int mod(int dividendo, int divisor) {
		return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
	}

	public String encrypt( String plaincpf ) {
		//input validation
		if ((plaincpf == null) || (plaincpf.length()!=11)) throw new RuntimeException("invalid input size (11 digit cpf).");
		
		//input split
		String prefix = plaincpf.substring(0,8);
		String stategroup = plaincpf.substring(8,9);
		
		//fpe encryption
		String enccpf = this.formatPreservingEncryption.encrypt(prefix, prefix.getBytes());
		
		//cpf rebuild
		int n1 = Character.getNumericValue(enccpf.charAt(0));
		int n2 = Character.getNumericValue(enccpf.charAt(1));
		int n3 = Character.getNumericValue(enccpf.charAt(2));
		int n4 = Character.getNumericValue(enccpf.charAt(3));
		int n5 = Character.getNumericValue(enccpf.charAt(4));
		int n6 = Character.getNumericValue(enccpf.charAt(5));
		int n7 = Character.getNumericValue(enccpf.charAt(6));
		int n8 = Character.getNumericValue(enccpf.charAt(7));
		int n9 = Character.getNumericValue(stategroup.charAt(0));
		
		
		int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

		d1 = 11 - (mod(d1, 11));

		if (d1 >= 10)
			d1 = 0;

		int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

		d2 = 11 - (mod(d2, 11));

		String newcpf = null;

		if (d2 >= 10)
			d2 = 0;
		newcpf = "";

		newcpf = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;

		return newcpf;
	}

}
