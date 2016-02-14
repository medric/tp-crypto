import java.math.BigInteger;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Provider {
	private ArrayList<BigInteger> answers;
	
	private Integer ceiling;
	public Paillier paillier;
	
	/**
	 * constructor
	 * @param _anwsers an ArrayList of String that will be converted to an ArrayList of BigInteger
	 */
	public Provider(ArrayList<String> _anwsers, Paillier p) {
		try {
			this.paillier = p;
			
			this.answers = (ArrayList<BigInteger>) _anwsers.stream()
					.map(m -> this.cipher(Main.textToBigInteger(m)))
					.collect(Collectors.toList()); 
			
			this.setCeiling(this.answers.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public ArrayList<BigInteger> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<BigInteger> answers) {
		this.answers = answers;
	}

	public Integer getCeiling() {
		return ceiling;
	}

	public void setCeiling(Integer i) {
		this.ceiling = i;
	}
	
	public BigInteger cipher(BigInteger m) {	
		try {
			return this.paillier.encrypt(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<BigInteger> respond(ArrayList<BigInteger> ciphers) {
		ArrayList<BigInteger> secrets = new ArrayList<BigInteger>();
		for(Integer i = 0; i < this.ceiling; i++) {
			BigInteger secret = this.answers.get(i).multiply(ciphers.get(i));
			secrets.add(secret);
		}
		
		return secrets;
	}
	
	public BigInteger decrypt(BigInteger I, BigInteger i) throws Exception {
		BigInteger M = I.multiply(this.paillier.encrypt(i.mod(this.paillier.getN())).mod(this.paillier.getNsquare())).modPow(this.paillier.randomZN(), this.paillier.getNsquare()); 
		return M;
	}
}
